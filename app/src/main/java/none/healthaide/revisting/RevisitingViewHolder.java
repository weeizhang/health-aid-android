package none.healthaide.revisting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;

import butterknife.BindView;
import butterknife.ButterKnife;
import none.healthaide.R;
import none.healthaide.model.MedicalRecords;

import static android.view.View.GONE;

public class RevisitingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.revisiting_date_text_view)
    TextView revisitingDateView;
    @BindView(R.id.title_text_view)
    TextView titleView;
    @BindView(R.id.describe_text_view)
    TextView describeView;
    @BindView(R.id.hospital_icon)
    ImageView hospitalIconView;
    @BindView(R.id.hospital_text_view)
    TextView hospitalView;
    @BindView(R.id.doctor_icon)
    ImageView doctorIconView;
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
        String medicalRecordsDescribe = medicalRecordsItem.getMedicalRecordsDescribe();
        if (Strings.isNullOrEmpty(medicalRecordsDescribe)) {
            describeView.setVisibility(GONE);
        } else {
            describeView.setText(medicalRecordsDescribe);
        }
        String hospital = medicalRecordsItem.getHospital();
        if (Strings.isNullOrEmpty(hospital)) {
            hospitalView.setVisibility(GONE);
            hospitalIconView.setVisibility(GONE);
        } else {
            hospitalView.setText(hospital);
        }
        String doctor = medicalRecordsItem.getDoctor();
        if (Strings.isNullOrEmpty(doctor)) {
            doctorView.setVisibility(GONE);
            doctorIconView.setVisibility(GONE);
        } else {
            doctorView.setText(doctor);
        }
        String startDate = medicalRecordsItem.getStartDate();
        if (Strings.isNullOrEmpty(startDate)) {
            startDateView.setVisibility(GONE);
        } else {
            startDateView.setText(startDate);
        }
    }
}
