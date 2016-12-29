package none.healthaide.usercase;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.R;
import none.healthaide.data.CaseCursor;
import none.healthaide.data.HealthAidContract;
import none.healthaide.main.MainFragment;

public class CaseDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = CaseDetailFragment.class.getSimpleName();
    private static final int LOADER_CASE_DETAIL = 0;

    private Unbinder unbinder;

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.input_title)
    EditText titleEditText;
    @BindView(R.id.input_start_date)
    EditText startDateEditText;
    @BindView(R.id.input_end_date)
    EditText endDateEditText;
    @BindView(R.id.input_case_describe)
    EditText caseDescribeEditText;
    @BindView(R.id.input_case_type)
    EditText caseTypeEditText;
    @BindView(R.id.input_cure_description)
    EditText cureDescriptionEditText;
    @BindView(R.id.input_hospital)
    EditText hospitalEditText;
    @BindView(R.id.input_doctor)
    EditText doctorEditText;
    @BindView(R.id.case_image_view)
    ImageView photoImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();

        getLoaderManager().initLoader(LOADER_CASE_DETAIL, getArguments(), this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.case_detail));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_CASE_DETAIL:
                CursorLoader cursorLoader = new CursorLoader(getContext());
                int caseId = args.getInt(MainFragment.CASE_ID);
                cursorLoader.setUri(HealthAidContract.CASE_TABLE_CONTENT_URI);
                cursorLoader.setSelection(HealthAidContract.CaseEntry._ID + " = " + caseId);
                return cursorLoader;
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            CaseCursor caseCursor = new CaseCursor(data);
            titleEditText.setText(caseCursor.getTitle());
            startDateEditText.setText(caseCursor.getStartDate());
            endDateEditText.setText(caseCursor.getEndDate());
            caseDescribeEditText.setText(caseCursor.getCaseDescribe());
            caseTypeEditText.setText(caseCursor.getCaseType());
            cureDescriptionEditText.setText(caseCursor.getCureDescription());
            hospitalEditText.setText(caseCursor.getHospital());
            doctorEditText.setText(caseCursor.getDoctor());
            photoImageView.setImageURI(Uri.parse(caseCursor.getPhotoUriStr()));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
