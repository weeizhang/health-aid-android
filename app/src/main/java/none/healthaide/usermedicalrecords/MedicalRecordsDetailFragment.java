package none.healthaide.usermedicalrecords;

import android.Manifest;
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

import com.google.common.base.Strings;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.R;
import none.healthaide.data.MedicalRecordsCursor;
import none.healthaide.data.HealthAidContract;
import none.healthaide.main.MainFragment;
import rx.functions.Action1;

public class MedicalRecordsDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = MedicalRecordsDetailFragment.class.getSimpleName();
    private static final int LOADER_MEDICAL_RECORDS_DETAIL = 0;

    private Unbinder unbinder;
    private RxPermissions rxPermissions;

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.input_title)
    EditText titleEditText;
    @BindView(R.id.input_start_date)
    EditText startDateEditText;
    @BindView(R.id.input_end_date)
    EditText endDateEditText;
    @BindView(R.id.input_medical_records_describe)
    EditText medicalRecordsDescribeEditText;
    @BindView(R.id.input_medical_records_type)
    EditText medicalRecordsTypeEditText;
    @BindView(R.id.input_cure_description)
    EditText cureDescriptionEditText;
    @BindView(R.id.input_hospital)
    EditText hospitalEditText;
    @BindView(R.id.input_doctor)
    EditText doctorEditText;
    @BindView(R.id.medical_records_image_view)
    ImageView photoImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_records_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();

        rxPermissions = new RxPermissions(getActivity());
        getLoaderManager().initLoader(LOADER_MEDICAL_RECORDS_DETAIL, getArguments(), this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.medical_records_detail));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_MEDICAL_RECORDS_DETAIL:
                CursorLoader cursorLoader = new CursorLoader(getContext());
                int medicalRecordsId = args.getInt(MainFragment.MEDICAL_RECORDS_ID);
                cursorLoader.setUri(HealthAidContract.MEDICAL_RECORDS_TABLE_CONTENT_URI);
                cursorLoader.setSelection(HealthAidContract.MedicalRecordsEntry._ID + " = " + medicalRecordsId);
                return cursorLoader;
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            final MedicalRecordsCursor medicalRecordsCursor = new MedicalRecordsCursor(data);
            titleEditText.setText(medicalRecordsCursor.getTitle());
            startDateEditText.setText(medicalRecordsCursor.getStartDate());
            endDateEditText.setText(medicalRecordsCursor.getEndDate());
            medicalRecordsDescribeEditText.setText(medicalRecordsCursor.getMedicalRecordsDescribe());
            medicalRecordsTypeEditText.setText(medicalRecordsCursor.getMedicalRecordsType());
            cureDescriptionEditText.setText(medicalRecordsCursor.getCureDescription());
            hospitalEditText.setText(medicalRecordsCursor.getHospital());
            doctorEditText.setText(medicalRecordsCursor.getDoctor());

            rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted && !Strings.isNullOrEmpty(medicalRecordsCursor.getPhotoUriStr())) {
                                photoImageView.setImageURI(Uri.parse(medicalRecordsCursor.getPhotoUriStr()));
                            } else {
                                //Todo: change image resource when permission deny
                                photoImageView.setImageResource(R.drawable.plus);
                            }
                        }
                    });

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
