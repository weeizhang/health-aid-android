package none.healthaide.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import none.healthaide.data.HealthAidContract.CaseEntry;
import none.healthaide.model.Case;

public class HealthAidData {
    private static final String TAG = HealthAidData.class.getSimpleName();

    private Context context;

    public HealthAidData(Context context) {
        this.context = context;
    }

    public long insertCase(Case newCase) {
        HealthAidDbHelper dbHelper = new HealthAidDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CaseEntry.COLUMN_NAME_TITLE, newCase.getTitle());
        values.put(CaseEntry.COLUMN_NAME_START_DATE, newCase.getStartDate());
        values.put(CaseEntry.COLUMN_NAME_END_DATE, newCase.getEndDate());
        values.put(CaseEntry.COLUMN_NAME_DESCRIPTION, newCase.getCaseDescribe());
        values.put(CaseEntry.COLUMN_NAME_TYPE, newCase.getCaseType());
        values.put(CaseEntry.COLUMN_NAME_HOSPITAL, newCase.getHospital());
        values.put(CaseEntry.COLUMN_NAME_DOCTOR, newCase.getDoctor());
        values.put(CaseEntry.COLUMN_NAME_CURE_DESCRIPTION, newCase.getCureDescription());

        long newRowId = db.insert(CaseEntry.TABLE_NAME, null, values);
        db.close();
        context.getContentResolver().notifyChange(HealthAidContract.CASE_TABLE_CONTENTURI, null);
        return newRowId;
    }
}
