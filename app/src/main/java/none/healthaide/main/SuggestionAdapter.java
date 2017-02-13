package none.healthaide.main;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.data.HealthAidContract;

public class SuggestionAdapter extends CursorAdapter {

    @BindView(R.id.suggestion_medical_records_title)
    TextView suggestionMedicalRecordsTitleTextView;
    @BindView(R.id.suggestion_medical_records_date)
    TextView suggestionMedicalRecordsDateTextView;

    public SuggestionAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.suggestion_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ButterKnife.bind(this, view);
        suggestionMedicalRecordsTitleTextView.setText(cursor.getString(cursor.getColumnIndex(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_TITLE)));
        suggestionMedicalRecordsDateTextView.setText(cursor.getString(cursor.getColumnIndex(HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_START_DATE)));
    }
}
