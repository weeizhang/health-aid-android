package none.healthaide.main;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import none.healthaide.model.Case;
import none.healthaide.usercase.NewCaseFragment;

public class MainFragment extends Fragment {
    public static final String TAG = NewCaseFragment.class.getSimpleName();

    public static final int NEW_CASE_INDEX = 0;

    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.feature_grid_view)
    GridView featureGridView;
    @BindView(R.id.case_list_view)
    RecyclerView caseRecycleView;

    private Unbinder unbinder;
    private TypedArray featureIcons;
    private TypedArray featureNames;

    private List<Case> caseList = Lists.newArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initActionBar();
        createFeatureGridView();
        caseList.add(new Case().setTitle("Title1").setCaseDescribe("Description1").setHospital("Xijing").setDoctor("Doctor"));
        caseList.add(new Case().setTitle("Title2").setCaseDescribe("Description2").setHospital("Xijing").setDoctor("Doctor"));
        createRecentCaseView();

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
                    default:
                        Toast.makeText(MainFragment.this.getContext(), "This is " + position + " feature", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createRecentCaseView() {
        caseRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CaseListViewAdapter caseListViewAdapter = new CaseListViewAdapter(getActivity(), caseList);
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
                //TODO: show search
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
