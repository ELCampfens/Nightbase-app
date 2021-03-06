package nightbase.nightbase;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nightbase.nightbase.Database.MyDBHandler;
import nightbase.nightbase.nightbase.model.Event;

public class EventActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Event event;
    private boolean like_toggle;

    private Toast favoriteToast;
    private static final String TAG = EventActivity.class.getSimpleName();
    MyDBHandler DBHandler = MyDBHandler.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        onWindowFocusChanged(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Get the entire event from the intent.
        this.event = (Event) getIntent().getSerializableExtra("event");

        //Toast init.
        favoriteToast = new Toast(this);
        favoriteToast.setDuration(Toast.LENGTH_SHORT);


        //Title
        TextView titleField = (TextView) findViewById(R.id.eventTitle);
        titleField.setText(event.getName());

        //Description
        TextView descField = (TextView) findViewById(R.id.desc);
        descField.setText(event.getDescription());

        //date
        TextView dateField = (TextView) findViewById(R.id.date);
        dateField.setText(event.getDate());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Previous button
        ImageButton b = (ImageButton) findViewById(R.id.back_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Favorite button, toggle
        final ImageButton favorite_btn = (ImageButton) findViewById(R.id.favorite_event);

        // For the like button img
        like_toggle = DBHandler.checkIfExists(event);
        if(like_toggle) {
            favorite_btn.setImageResource(R.drawable.ic_favorite_true_48dp);
        } else if(!like_toggle) {
            favorite_btn.setImageResource(R.drawable.ic_favorite_false_48dp);
        }

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!like_toggle) {
                    like_toggle = true;
                    favorite_btn.setImageResource(R.drawable.ic_favorite_true_48dp);
                    favoriteToast.makeText(v.getContext(),"Added to favorites!", Toast.LENGTH_SHORT).show();
                    DBHandler.addEvent(event);
                } else if(like_toggle) {
                    like_toggle = false;
                    favorite_btn.setImageResource(R.drawable.ic_favorite_false_48dp);
                    favoriteToast.makeText(v.getContext(),"Removed from favorites!", Toast.LENGTH_SHORT).show();
                    DBHandler.removeEvent(event);
                }

            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        // Add a marker in Hogeschool and move the camera
        LatLng hogeschool = new LatLng(this.event.getLatitude(), this.event.getLongitude());
        mMap.addMarker(new MarkerOptions().position(hogeschool).title("Marker in Hogeschool"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hogeschool,   15.5f));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
