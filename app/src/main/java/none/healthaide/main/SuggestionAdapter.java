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

    @BindView(R.id.suggestion_case_title)
    TextView suggestionCaseTitleTextView;
    @BindView(R.id.suggestion_case_date)
    TextView suggestionCaseDateTextView;

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
        suggestionCaseTitleTextView.setText(cursor.getString(cursor.getColumnIndex(HealthAidContract.CaseEntry.COLUMN_NAME_TITLE)));
        suggestionCaseDateTextView.setText(cursor.getString(cursor.getColumnIndex(HealthAidContract.CaseEntry.COLUMN_NAME_START_DATE)));
    }
}
