package none.healthaide.me;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import none.healthaide.R;
import none.healthaide.utils.PreferencesUtil;

public class MeFragment extends Fragment {
    public static final String TAG = MeFragment.class.getSimpleName();

    private Unbinder unbinder;
    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.gender_text_view)
    TextView genderTextView;
    @BindView(R.id.age_text_view)
    TextView ageTextView;
    @BindView(R.id.height_text_view)
    TextView heightTextView;
    @BindView(R.id.weight_text_view)
    TextView weightTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        genderTextView.setText(PreferencesUtil.getUserGender());
        ageTextView.setText(String.format("%1$d", PreferencesUtil.getUserAge()));
        heightTextView.setText(String.format("%1$d cm", PreferencesUtil.getUserHeight()));
        weightTextView.setText(String.format("%1$d kg", PreferencesUtil.getUserWeight()));
    }

    @OnClick(R.id.gender_view)
    public void onGenderViewClick() {
        final String genders[] = new String[]{getString(R.string.gender_male), getString(R.string.gender_female)};
        String currentGender = PreferencesUtil.getUserGender();
        new AlertDialog.Builder(getActivity()).setSingleChoiceItems(genders,
                currentGender.equals(getString(R.string.gender_male)) ? 0 : 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesUtil.setUserGender(genders[which]);
                        genderTextView.setText(genders[which]);
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }

    @OnClick(R.id.age_view)
    public void onAgeViewClick() {
        final EditText editText = new EditText(getActivity());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String age = editText.getText().toString();
                PreferencesUtil.setUserAge(Integer.parseInt(age));
                ageTextView.setText(age);
            }
        };
        showInputDialog(getString(R.string.age), editText, onClickListener);
    }

    @OnClick(R.id.height_view)
    public void onHeightViewClick() {
        final EditText editText = new EditText(getActivity());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String height = editText.getText().toString();
                PreferencesUtil.setUserHeight(Integer.parseInt(height));
                heightTextView.setText(height);
            }
        };
        showInputDialog(getString(R.string.height), editText, onClickListener);
    }

    @OnClick(R.id.weight_view)
    public void onWeightViewClick() {
        final EditText editText = new EditText(getActivity());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String weight = editText.getText().toString();
                PreferencesUtil.setUserWeight(Integer.parseInt(weight));
                weightTextView.setText(weight);
            }
        };
        showInputDialog(getString(R.string.weight), editText, onClickListener);
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(getResources().getString(R.string.me));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void showInputDialog(String title, EditText editText, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(editText)
                .setPositiveButton(getString(R.string.ok), onClickListener)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
