package none.healthaide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HealthAidDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION_VER_0_0_0 = 0;
    private static final int CURRENT_DATABASE_VERSION = DATABASE_VERSION_VER_0_0_0;

    private static final String DATABASE_NAME = "health_aid.db";
    private static final int DATABASE_VERSION = CURRENT_DATABASE_VERSION;

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CASE =
            "CREATE TABLE " + CaseContract.CaseEntry.TABLE_NAME + " (" +
                    CaseContract.CaseEntry._ID + " INTEGER PRIMARY KEY," +
                    CaseContract.CaseEntry.COLUMN_NAME_CASE_ID + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_START_DATE + DATE_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_END_DATE + DATE_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_HOSPITAL + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_DOCTORE + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    CaseContract.CaseEntry.COLUMN_NAME_CURE_DEACRIPTION + TEXT_TYPE + COMMA_SEP +
            " )";

    private static final String SQL_DELETE_CASE =
            "DROP TABLE IF EXISTS " + CaseContract.CaseEntry.TABLE_NAME;


    public HealthAidDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CASE);
        db.execSQL(SQL_CREATE_CASE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
