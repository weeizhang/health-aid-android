package none.healthaide.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.model.MedicalRecords;

public class RecentMedicalRecordsViewHolder extends RecyclerView.ViewHolder {

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

    public RecentMedicalRecordsViewHolder(View view, Callback callback) {
        super(view);
        this.callback = callback;
        ButterKnife.bind(this, view);
    }

    public void populate(final MedicalRecords medicalRecordsItem) {
        titleView.setText(medicalRecordsItem.getTitle());
        describeView.setText(medicalRecordsItem.getMedicalRecordsDescribe());
        hospitalView.setText(medicalRecordsItem.getHospital());
        doctorView.setText(medicalRecordsItem.getDoctor());
        startDateView.setText(medicalRecordsItem.getStartDate());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onMedicalRecordsSelected(medicalRecordsItem.getId());
            }
        });
    }

    public interface Callback {
        void onMedicalRecordsSelected(Integer caseId);
    }
}
