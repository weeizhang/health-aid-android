package none.healthaide.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.MainActivity;
import none.healthaide.R;
import none.healthaide.data.MedicalRecordsCursor;
import none.healthaide.data.HealthAidContract;
import none.healthaide.me.MeFragment;
import none.healthaide.model.MedicalRecords;
import none.healthaide.revisting.RevisitingFragment;
import none.healthaide.usermedicalrecords.MedicalRecordsDetailFragment;
import none.healthaide.usermedicalrecords.NewMedicalRecordsFragment;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RecentMedicalRecordsViewHolder.Callback {
    public static final String TAG = MainFragment.class.getSimpleName();

    private static final int CASE_LIST_LOADER = 0;
    private static final int SUGGESTION_LIST_LOADER = 1;
    public static final int NEW_MEDICAL_RECORDS_INDEX = 0;
    public static final int REVISITING_INDEX = 1;
    public static final int ME_INDEX = 2;
    public static final String MEDICAL_RECORDS_ID = "medical_records_id";
    public static final String QUERY = "query";

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.feature_grid_view)
    GridView featureGridView;
    @BindView(R.id.medical_records_list_view)
    RecyclerView medicalRecordsRecycleView;
    @BindView(R.id.empty_medical_records_view)
    TextView emptyMedicalRecordsView;

    private SearchView searchView;
    private SuggestionAdapter suggestionAdapter;

    private Unbinder unbinder;
    private TypedArray featureIcons;
    private TypedArray featureNames;

    private MedicalRecordsListViewAdapter medicalRecordsListViewAdapter;
    private List<MedicalRecords> medicalRecordsList = Lists.newArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initActionBar();
        createFeatureGridView();
        createRecentCaseView();

        getLoaderManager().initLoader(CASE_LIST_LOADER, null, this);
        getLoaderManager().initLoader(SUGGESTION_LIST_LOADER, null, this);

        return view;
    }

    private void createFeatureGridView() {
        featureIcons = getResources().obtainTypedArray(R.array.feature_icon);
        featureNames = getResources().obtainTypedArray(R.array.feature_label);
        FeatureViewAdapter featureViewAdapter = new FeatureViewAdapter(getActivity(), featureIcons, featureNames);
        featureGridView.setAdapter(featureViewAdapter);
        featureGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case NEW_MEDICAL_RECORDS_INDEX:
                        ((MainActivity) MainFragment.this.getActivity()).replaceFragment(new NewMedicalRecordsFragment(), NewMedicalRecordsFragment.TAG);
                        break;
                    case REVISITING_INDEX:
                        ((MainActivity) MainFragment.this.getActivity()).replaceFragment(new RevisitingFragment(), RevisitingFragment.TAG);
                        break;
                    case ME_INDEX:
                        ((MainActivity) MainFragment.this.getActivity()).replaceFragment(new MeFragment(), MeFragment.TAG);
                        break;
                    default:
                        Toast.makeText(MainFragment.this.getContext(), "This is " + position + " feature", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createRecentCaseView() {
        medicalRecordsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        medicalRecordsListViewAdapter = new MedicalRecordsListViewAdapter(getActivity(), medicalRecordsList, this);
        medicalRecordsRecycleView.setAdapter(medicalRecordsListViewAdapter);
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        initSuggestionView(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_new_medical_records:
                ((MainActivity) getActivity()).replaceFragment(new NewMedicalRecordsFragment(), NewMedicalRecordsFragment.TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case CASE_LIST_LOADER:
                return new CursorLoader(
                        getActivity(), HealthAidContract.MEDICAL_RECORDS_TABLE_CONTENT_URI, null, null, null, null);
            case SUGGESTION_LIST_LOADER:
                return new CursorLoader(getActivity(), HealthAidContract.MEDICAL_RECORDS_TABLE_CONTENT_URI, null, HealthAidContract.MedicalRecordsEntry.COLUMN_NAME_TITLE + " like '%" + (args == null ? "" : args.getString(QUERY)) + "%'", null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case NEW_MEDICAL_RECORDS_INDEX:
                if (data != null && data.moveToFirst()) {
                    medicalRecordsRecycleView.setVisibility(View.VISIBLE);
                    emptyMedicalRecordsView.setVisibility(View.GONE);
                    medicalRecordsList.clear();
                    do {
                        MedicalRecordsCursor medicalRecordsCursor = new MedicalRecordsCursor(data);
                        medicalRecordsList.add(new MedicalRecords()
                                .setId(medicalRecordsCursor.getId())
                                .setTitle(medicalRecordsCursor.getTitle())
                                .setStartDate(medicalRecordsCursor.getStartDate())
                                .setMedicalRecordsDescribe(medicalRecordsCursor.getMedicalRecordsDescribe())
                                .setHospital(medicalRecordsCursor.getHospital())
                                .setDoctor(medicalRecordsCursor.getDoctor()));
                    } while (data.moveToNext());
                    medicalRecordsListViewAdapter.setMedicalRecordsList(medicalRecordsList);
                    medicalRecordsListViewAdapter.notifyDataSetChanged();
                } else {
                    medicalRecordsRecycleView.setVisibility(View.GONE);
                    emptyMedicalRecordsView.setVisibility(View.VISIBLE);
                }
                break;
            case SUGGESTION_LIST_LOADER:
                if (suggestionAdapter != null) {
                    suggestionAdapter.swapCursor(data);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onMedicalRecordsSelected(Integer medicalRecordsId) {
        showMedicalRecordsDetailFragment(medicalRecordsId);
    }

    private void initSuggestionView(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_filter_medical_records).getActionView();
        searchView.setQueryHint(getString(R.string.search_medical_records));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        suggestionAdapter = new SuggestionAdapter(getActivity(), null);
        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    Bundle data = new Bundle();
                    data.putString(QUERY, newText);
                    getLoaderManager().restartLoader(SUGGESTION_LIST_LOADER, data, MainFragment.this);
                }
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = suggestionAdapter.getCursor();
                if(cursor != null && cursor.moveToPosition(position)) {
                    showMedicalRecordsDetailFragment(cursor.getInt(cursor.getColumnIndex(HealthAidContract.MedicalRecordsEntry._ID)));
                }
                return false;
            }
        });
    }

    private void showMedicalRecordsDetailFragment(int medicalRecordsId) {
        MedicalRecordsDetailFragment fragment = new MedicalRecordsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MEDICAL_RECORDS_ID, medicalRecordsId);
        fragment.setArguments(bundle);
        ((MainActivity) getActivity()).replaceFragment(fragment, MedicalRecordsDetailFragment.TAG);
    }
}
