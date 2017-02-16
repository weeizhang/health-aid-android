package none.healthaide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import none.healthaide.data.HealthAidContract.MedicalRecordsEntry;
import none.healthaide.data.HealthAidContract.RevisitingEventEntry;

public class HealthAidDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION_VER_0_0_0 = 1;
    private static final int CURRENT_DATABASE_VERSION = DATABASE_VERSION_VER_0_0_0;

    private static final String DATABASE_NAME = "health_aid.db";
    private static final int DATABASE_VERSION = CURRENT_DATABASE_VERSION;

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = " NOT NULL";
    private static final String SQL_CREATE_CASE =
            "CREATE TABLE " + MedicalRecordsEntry.TABLE_NAME + " (" +
                    MedicalRecordsEntry._ID + " INTEGER PRIMARY KEY," +
                    MedicalRecordsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_HOSPITAL + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_DOCTOR + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_CURE_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    MedicalRecordsEntry.COLUMN_NAME_PHOTO_URI + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_CASE =
            "DROP TABLE IF EXISTS " + MedicalRecordsEntry.TABLE_NAME;

    private static final String SQL_CREATE_REVISITING_EVNET =
            "CREATE TABLE " + RevisitingEventEntry.TABLE_NAME + " (" +
                    RevisitingEventEntry._ID + " INTEGER PRIMARY KEY," +
                    RevisitingEventEntry.COLUMN_NAME_MEDICAL_RECORDS_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    RevisitingEventEntry.COLUMN_NAME_REVISITING_DATE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    "FOREIGN KEY(" + RevisitingEventEntry.COLUMN_NAME_MEDICAL_RECORDS_ID + ") REFERENCES "
                    + MedicalRecordsEntry.TABLE_NAME + "(" + MedicalRecordsEntry._ID + ")" +
                    " )";

    private static final String SQL_DELETE_REVISITING_EVNET =
            "DROP TABLE IF EXISTS " + RevisitingEventEntry.TABLE_NAME;


    public HealthAidDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CASE);
        db.execSQL(SQL_CREATE_REVISITING_EVNET);
//        db.execSQL("INSERT INTO user_case VALUES (0,\"title\",\"20160920\",\"20160930\",\"hospital\",\"doctor\",\"description\",\"type\",\"cure description\",\"content://media/external/images/media/638\");");
//        db.execSQL("INSERT INTO revisiting_event VALUES (0,0,\"20171030\");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CASE);
        db.execSQL(SQL_DELETE_REVISITING_EVNET);
        db.execSQL(SQL_CREATE_CASE);
        db.execSQL(SQL_CREATE_REVISITING_EVNET);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
