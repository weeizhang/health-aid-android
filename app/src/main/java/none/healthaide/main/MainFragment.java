package none.healthaide.main;

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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.MainActivity;
import none.healthaide.R;
import none.healthaide.data.HealthAidContract;
import none.healthaide.data.CaseCursor;
import none.healthaide.me.MeFragment;
import none.healthaide.model.Case;
import none.healthaide.revisting.RevisitingFragment;
import none.healthaide.usercase.CaseDetailFragment;
import none.healthaide.usercase.NewCaseFragment;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RecentCaseViewHolder.Callback {
    public static final String TAG = MainFragment.class.getSimpleName();

    private static final int CASE_LIST_LOADER = 0;
    public static final int NEW_CASE_INDEX = 0;
    public static final int REVISITING_INDEX = 1;
    public static final int ME_INDEX = 2;
    public static final String CASE_ID = "case_id";

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.feature_grid_view)
    GridView featureGridView;
    @BindView(R.id.case_list_view)
    RecyclerView caseRecycleView;

    private Unbinder unbinder;
    private TypedArray featureIcons;
    private TypedArray featureNames;

    private CaseListViewAdapter caseListViewAdapter;
    private List<Case> caseList = Lists.newArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initActionBar();
        createFeatureGridView();
        createRecentCaseView();

        getLoaderManager().initLoader(CASE_LIST_LOADER, null, this);

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
                    case NEW_CASE_INDEX:
                        ((MainActivity) MainFragment.this.getActivity()).replaceFragment(new NewCaseFragment(), NewCaseFragment.TAG);
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
        caseRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        caseListViewAdapter = new CaseListViewAdapter(getActivity(), caseList, this);
        caseRecycleView.setAdapter(caseListViewAdapter);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_new_case:
                ((MainActivity) getActivity()).replaceFragment(new NewCaseFragment(), NewCaseFragment.TAG);
                return true;
            case R.id.action_filter_case:
                ((MainActivity) getActivity()).replaceFragment(new SearchFragment(), NewCaseFragment.TAG);
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

    @Override
    public void onCaseSelected(Integer caseId) {
        CaseDetailFragment fragment = new CaseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CASE_ID, caseId);
        fragment.setArguments(bundle);
        ((MainActivity) getActivity()).replaceFragment(fragment, CaseDetailFragment.TAG);
    }
}
