package none.healthaide.revisting;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.common.collect.Lists;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.R;
import none.healthaide.data.CaseCursor;
import none.healthaide.data.HealthAidContract;
import none.healthaide.main.CaseListViewAdapter;
import none.healthaide.main.RecentCaseViewHolder;
import none.healthaide.model.Case;
import none.healthaide.utils.DateUtil;
import rx.Observable;
import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class SelectRevisitingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RecentCaseViewHolder.Callback {

    public static final String TAG = SelectRevisitingFragment.class.getSimpleName();
    private static final int CASE_LIST_LOADER = 0;

    private Unbinder unbinder;
    private CaseListViewAdapter caseListViewAdapter;
    private List<Case> caseList = Lists.newArrayList();

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.case_list_view)
    RecyclerView caseRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_revisiting, container, false);
        unbinder = ButterKnife.bind(this, view);
        createRecentCaseView();
        getLoaderManager().initLoader(CASE_LIST_LOADER, null, this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCaseSelected(final Integer caseId) {
        toolbar.setTitle(getString(R.string.select_revisiting_date));
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = DateUtil.dateToString(year, month + 1, day);
                        storeNewRevisitingEvent(caseId, date);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                },
                DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                toolbar.setTitle(getString(R.string.select_revisiting));
            }
        });
        datePickerDialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case CASE_LIST_LOADER:
                return new CursorLoader(
                        getActivity(), HealthAidContract.CASE_TABLE_CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            caseList.clear();
            do {
                CaseCursor caseCursor = new CaseCursor(data);
                caseList.add(new Case()
                        .setId(caseCursor.getId())
                        .setTitle(caseCursor.getTitle())
                        .setStartDate(caseCursor.getStartDate())
                        .setCaseDescribe(caseCursor.getCaseDescribe())
                        .setHospital(caseCursor.getHospital())
                        .setDoctor(caseCursor.getDoctor()));
            } while (data.moveToNext());

            caseListViewAdapter.setCaseList(caseList);
            caseListViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.select_revisiting));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void createRecentCaseView() {
        caseRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        caseListViewAdapter = new CaseListViewAdapter(getActivity(), caseList, this);
        caseRecycleView.setAdapter(caseListViewAdapter);
    }

    private void storeNewRevisitingEvent(final Integer caseId, final String date) {
        Observable.create(new Observable.OnSubscribe<Long>() {

            @Override
            public void call(Subscriber<? super Long> subscriber) {
                ContentValues values = new ContentValues();
                values.put(HealthAidContract.RevisitingEventEntry.COLUMN_NAME_CASE_ID, caseId);
                values.put(HealthAidContract.RevisitingEventEntry.COLUMN_NAME_REVISITING_DATE, date);
                Uri uri = getContext().getContentResolver().insert(HealthAidContract.REVISITING_EVENT_TABLE_CONTENT_URI, values);
                if (uri != null) {
                    String idStr = uri.getPathSegments().get(1);
                    subscriber.onNext(Integer.valueOf(idStr).longValue());
                } else {
                    subscriber.onError(new Exception());
                }
            }
        })
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long id) {
                        Toast.makeText(getActivity(), R.string.add_revisiting_event_success, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
