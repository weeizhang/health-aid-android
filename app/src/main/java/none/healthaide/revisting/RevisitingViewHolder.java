package none.healthaide.revisting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.model.MedicalRecords;

public class RevisitingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.revisiting_date_text_view)
    TextView revisitingDateView;
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

    public RevisitingViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void populate(MedicalRecords medicalRecordsItem) {
        revisitingDateView.setText(medicalRecordsItem.getRevisitingDate());
        titleView.setText(medicalRecordsItem.getTitle());
        describeView.setText(medicalRecordsItem.getMedicalRecordsDescribe());
        hospitalView.setText(medicalRecordsItem.getHospital());
        doctorView.setText(medicalRecordsItem.getDoctor());
        startDateView.setText(medicalRecordsItem.getStartDate());
    }
}
