package none.healthaide.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.model.Case;

public class RecentCaseViewHolder extends RecyclerView.ViewHolder {

    private final Callback callback;

    @BindView(R.id.title_text_view)
    TextView titleView;
    @BindView(R.id.describe_text_view)
    TextView describeView;
    @BindView(R.id.hospital_text_view)
    TextView hospitalView;
    @BindView(R.id.doctor_text_view)
    TextView doctorView;
    @BindView(R.id.start_date_text_view)
    TextView startDateView;

    public RecentCaseViewHolder(View view, Callback callback) {
        super(view);
        this.callback = callback;
        ButterKnife.bind(this, view);
    }

    public void populate(final Case caseItem) {
        titleView.setText(caseItem.getTitle());
        describeView.setText(caseItem.getCaseDescribe());
        hospitalView.setText(caseItem.getHospital());
        doctorView.setText(caseItem.getDoctor());
        startDateView.setText(caseItem.getStartDate());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onCaseSelected(caseItem.getId());
            }
        });
    }

    public interface Callback {
        void onCaseSelected(Integer caseId);
    }
}
