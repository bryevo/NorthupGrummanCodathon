import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Steven on 11/18/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBName.db";
    public static final String LOCATION_ID = "id";
    public static final String LOCATION_TABLE_NAME = "locations";
    public static final String LOCATION_NAME = "location name";
    public static final String LOCATION_XCOORDINATE = "x Coordinate";
    public static final String LOCATION_YCOORDINATE = "y Coordinate";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LOCATION_TABLE_NAME +
                "(" + LOCATION_ID + " INTEGER PRIMARY KEY," + LOCATION_NAME + " TEXT," +
                LOCATION_XCOORDINATE + " TEXT," + LOCATION_YCOORDINATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS" + LOCATION_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertLocation (String locationName, Double xCoordinate, Double yCoordinate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_NAME, locationName);
        contentValues.put(LOCATION_XCOORDINATE, xCoordinate);
        contentValues.put(LOCATION_YCOORDINATE, yCoordinate);
        db.insert(LOCATION_TABLE_NAME, null, contentValues);    //add location to the table
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "select * from " + LOCATION_TABLE_NAME + " where id=" + LOCATION_ID, null);
        return res;
    }

    public boolean upDateLocation(Integer id, String locationName, Double xCoordinate, Double yCoordinate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_NAME, locationName);
        contentValues.put(LOCATION_XCOORDINATE, xCoordinate);
        contentValues.put(LOCATION_YCOORDINATE, yCoordinate);
        db.update(LOCATION_TABLE_NAME, contentValues, "id = ?", new String[] { Integer.toString(id)});  //update location in table
        return true;
    }

    public Integer deleteLocation (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LOCATION_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id)});
    }

    public ArrayList<String> getAllLocations() {
        ArrayList<String> locationList = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "select * from " + LOCATION_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            locationList.add(res.getString(res.getColumnIndex(LOCATION_NAME)));
            res.moveToNext();
        }
        return locationList;
    }
}
