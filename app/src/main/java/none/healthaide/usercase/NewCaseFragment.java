package none.healthaide.usercase;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import none.healthaide.HealthAidApplication;
import none.healthaide.main.MainFragment;
import none.healthaide.R;
import none.healthaide.model.Case;
import none.healthaide.utils.DateUtil;

public class NewCaseFragment extends Fragment {
    public static final String TAG = MainFragment.class.getSimpleName();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HealthAidApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_case, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.new_case_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.input_start_date)
    public void startDateOnClick() {
        createDatePickerDialog(startDateEditText).show();
    }

    @OnClick(R.id.input_end_date)
    public void endDateOnClick() {
        createDatePickerDialog(endDateEditText).show();
    }

    @OnClick(R.id.new_case_submit_button)
    public void newCaseSubmitOnClick() {
        Case newCase = new Case()
                .setTitle(titleEditText.getText().toString())
                .setStartDate(startDateEditText.getText().toString())
                .setEndDate(endDateEditText.getText().toString())
                .setCaseDescribe(caseDescribeEditText.getText().toString())
                .setCaseType(caseTypeEditText.getText().toString())
                .setCureDescription(cureDescriptionEditText.getText().toString())
                .setHospital(hospitalEditText.getText().toString())
                .setDoctor(doctorEditText.getText().toString());
        Intent intent = new Intent(getActivity().getApplicationContext(), CaseService.class);
        intent.putExtra(CaseService.NEW_CASE_EXTRA, newCase);
        intent.setAction(CaseService.ACTION_NEW_CASE);
        getActivity().startService(intent);
        getFragmentManager().popBackStack();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.new_case));
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @NonNull
    private DatePickerDialog createDatePickerDialog(final EditText editText) {
        return new DatePickerDialog(
                getActivity(),
                R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        editText.setText(DateUtil.dateToString(year, month, day));
                    }
                },
                DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
    }
}