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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import none.healthaide.MainActivity;
import none.healthaide.R;
import none.healthaide.common.WebViewFragment;
import none.healthaide.utils.PreferencesUtil;

public class MeFragment extends Fragment {
    public static final String TAG = MeFragment.class.getSimpleName();
    private static final String suggest_jinshuju_url = "https://jinshuju.net/f/5woEEh";

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
                int age = 0;
                try {
                    age = Integer.parseInt(editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.age_error_message, Toast.LENGTH_LONG).show();
                }
                if (age > 0) {
                    PreferencesUtil.setUserAge(age);
                    ageTextView.setText(age);
                }
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
                int height = 0;
                try {
                    height = Integer.parseInt(editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.height_error_message, Toast.LENGTH_LONG).show();
                }
                if (height > 0) {
                    PreferencesUtil.setUserHeight(height);
                    heightTextView.setText(height);
                }
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
                int weight = 0;
                try {
                    weight = Integer.parseInt(editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.weight_error_message, Toast.LENGTH_LONG).show();
                }
                if (weight > 0) {
                    PreferencesUtil.setUserWeight(weight);
                    weightTextView.setText(weight);
                }
            }
        };
        showInputDialog(getString(R.string.weight), editText, onClickListener);
    }

    @OnClick(R.id.suggest_view)
    public void onSuggestViewClick() {
        ((MainActivity) MeFragment.this.getActivity()).replaceFragment(
                WebViewFragment.createInstance(getString(R.string.suggest), suggest_jinshuju_url),
                WebViewFragment.TAG);

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
