package nightbase.nightbase.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nightbase.nightbase.nightbase.model.Event;

public class MyDBHandler extends SQLiteOpenHelper {

    // DB Information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Events.db";

    // Table Name
    public static final String TABLE_NAME = "event";

    // Event info
    public static final String EVENT_ID = "event_id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_DESC = "description";
    public static final String EVENT_LAT = "latitude";
    public static final String EVENT_LONG = "longitude";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_LINK = "link";

    // Singleton
    private static MyDBHandler sInstance;

    public static synchronized MyDBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public MyDBHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + EVENT_ID + " INTEGER,"
                + EVENT_NAME + " TEXT,"
                + EVENT_DESC + " TEXT,"
                + EVENT_LAT + " REAL,"
                + EVENT_LONG + " REAL,"
                + EVENT_DATE + " TEXT,"
                + EVENT_LINK + " TEXT"
                + ")";

        System.out.println("Before creating");
        db.execSQL(CREATE_EVENT_TABLE);
        System.out.println("After creating");
    }

    public void addEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(EVENT_ID, event.getID());
            values.put(EVENT_NAME, event.getID());
            values.put(EVENT_DESC, event.getDescription());
            values.put(EVENT_DATE, event.getDate());
            values.put(EVENT_LAT, event.getLatitude());
            values.put(EVENT_LONG, event.getLongitude());
            values.put(EVENT_LINK, event.getLink());

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();

            System.out.println("added the event to the DB!");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void removeEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {

            db.delete(TABLE_NAME, EVENT_ID + "=" + event.getID(), null);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
