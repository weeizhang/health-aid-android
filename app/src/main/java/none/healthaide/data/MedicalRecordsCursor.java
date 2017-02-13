package none.healthaide.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import none.healthaide.data.HealthAidContract.MedicalRecordsEntry;

public class MedicalRecordsCursor extends CursorWrapper {
    private Cursor cursor;

    public MedicalRecordsCursor(Cursor cursor) {
        super(cursor);
        this.cursor = cursor;
    }

    public Integer getId() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry._ID);
        return getInt(columnIndex);
    }

    public String getTitle() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_TITLE);
        return getString(columnIndex);
    }

    public String getStartDate() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_START_DATE);
        return getString(columnIndex);
    }

    public String getEndDate() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_END_DATE);
        return getString(columnIndex);
    }

    public String getMedicalRecordsDescribe() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_DESCRIPTION);
        return getString(columnIndex);
    }

    public String getMedicalRecordsType() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_TYPE);
        return getString(columnIndex);
    }

    public String getCureDescription() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_CURE_DESCRIPTION);
        return getString(columnIndex);
    }

    public String getHospital() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_HOSPITAL);
        return getString(columnIndex);
    }

    public String getDoctor() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_DOCTOR);
        return getString(columnIndex);
    }

    public String getPhotoUriStr() {
        int columnIndex = cursor.getColumnIndex(MedicalRecordsEntry.COLUMN_NAME_PHOTO_URI);
        return getString(columnIndex);
    }
}
