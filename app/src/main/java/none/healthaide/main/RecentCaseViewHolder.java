package none.healthaide.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.model.Case;

public class RecentCaseViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.title_text_view)
    TextView titleView;
    @BindView(R.id.describe_text_view)
    TextView describeView;
    @BindView(R.id.hospital_text_view)
    TextView hospitalView;
    @BindView(R.id.doctor_text_view)
    TextView doctorView;

    public RecentCaseViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void populate(Case caseItem) {
        titleView.setText(caseItem.getTitle());
        describeView.setText(caseItem.getCaseDescribe());
        hospitalView.setText(caseItem.getHospital());
        doctorView.setText(caseItem.getDoctor());
    }
}
