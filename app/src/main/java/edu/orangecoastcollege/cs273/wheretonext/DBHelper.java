package edu.orangecoastcollege.cs273.wheretonext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Initializes a connection to a database
 * Contains functionality to create, add, remove, and upgrade to the database
 */
class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "WhereToNext";
    private static final String DATABASE_TABLE = "Colleges";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_POPULATION = "population";
    private static final String FIELD_TUITION = "tuition";
    private static final String FIELD_RATING = "rating";
    private static final String FIELD_IMAGE_NAME = "image_name";


    /**
     * Creates the database with context, name, and version
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a table with the given fields and their types
     * @param database databse to create table in
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO:  Write code to create the database
        String createTable = "CREATE TABLE " + DATABASE_TABLE +
                " (" + KEY_FIELD_ID + " INTEGER PRIMARY KEY" +
                ", " + FIELD_NAME + " STRING" +
                ", " + FIELD_POPULATION + " INTEGER" +
                ", " + FIELD_TUITION + " REAL" +
                ", " + FIELD_RATING + " REAL" +
                ", " + FIELD_IMAGE_NAME + " STRING"
                + ")";
        database.execSQL(createTable);
    }

    /**
     * Called on upgrade of the database, deletes existing table and creates the table again
     * @param database database to upgrade
     * @param oldVersion Old version number
     * @param newVersion New version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // TODO:  Write code to upgrade the database
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, GETALL

    /**
     * Adds a college to the database
     *
     * @param college College to be added
     */
    public void addCollege(College college) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // TODO:  Write code to add a College to the database
        // TODO:  Return the id assigned by the database
        if (college.getId() != -1) values.put(KEY_FIELD_ID, college.getId());
        values.put(FIELD_NAME, college.getName());
        values.put(FIELD_POPULATION, college.getPopulation());
        values.put(FIELD_TUITION, college.getTuition());
        values.put(FIELD_RATING, college.getRating());
        values.put(FIELD_IMAGE_NAME, college.getImageName());
        long id = db.insert(DATABASE_TABLE, null, values);
        college.setId(id);
    }

    /**
     * Pulls a list of all the colleges inputted into the database
     *
     * @return The list of all the colleges
     */
    public ArrayList<College> getAllColleges() {
        ArrayList<College> collegeList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        // TODO:  Write the code to get all the colleges from the database.
        Cursor cursor = database.query(DATABASE_TABLE, new String[] {KEY_FIELD_ID, FIELD_NAME, FIELD_POPULATION, FIELD_TUITION, FIELD_RATING, FIELD_IMAGE_NAME}, null, null,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                collegeList.add(new College(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3), cursor.getFloat(4), cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return collegeList;
    }


}
