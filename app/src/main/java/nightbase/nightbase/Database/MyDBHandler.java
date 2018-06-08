package nightbase.nightbase.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    public static final String EVENT_CLICK_COUNTER = "click_counter";

    // Singleton
    private static MyDBHandler sInstance;

    public static synchronized MyDBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDBHandler(context);
        }
        return sInstance;
    }

    public MyDBHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);//
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
                + EVENT_LINK + " TEXT,"
                + EVENT_CLICK_COUNTER + " INTEGER"
                + ")";

        db.execSQL(CREATE_EVENT_TABLE);
    }

    public void addEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(EVENT_ID, event.getID());
            values.put(EVENT_NAME, event.getName());
            values.put(EVENT_DESC, event.getDescription());
            values.put(EVENT_DATE, event.getDate());
            values.put(EVENT_LAT, event.getLatitude());
            values.put(EVENT_LONG, event.getLongitude());
            values.put(EVENT_LINK, event.getLink());

            this.checkIfExists(event);
            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            this.checkIfExists(event);

            System.out.println("added the event to the DB!");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public boolean checkIfExists(Event event) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = null;
        String sql = "SELECT " + EVENT_ID + " FROM " + TABLE_NAME + " WHERE " + EVENT_ID + "=" + event.getID();

        cursor = db.rawQuery(sql, null);

        int amount = cursor.getCount();
        cursor.close();

        if(amount > 0){
            return true;
        }else{
            return false;
        }
    }

    public void removeEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + EVENT_ID + " = " + event.getID() + ";";
        db.execSQL(sql);

        db.close();
    }

    public ArrayList<Event> getAll() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Event> EventList = new ArrayList<Event>();

        Cursor  cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                EventList.add(new Event(
                        cursor.getString(cursor.getColumnIndex(EVENT_NAME)),
                        cursor.getString(cursor.getColumnIndex(EVENT_DESC)),
                        cursor.getString(cursor.getColumnIndex(EVENT_DATE)),
                        cursor.getDouble(cursor.getColumnIndex(EVENT_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(EVENT_LONG)),
                        cursor.getString(cursor.getColumnIndex(EVENT_LINK)),
                        cursor.getInt(cursor.getColumnIndex(EVENT_ID))));

                cursor.moveToNext();
            }
        }
        return EventList;
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
