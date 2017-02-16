package none.healthaide.main;

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

public class RecentMedicalRecordsViewHolder extends RecyclerView.ViewHolder {

    private final Callback callback;

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

    public RecentMedicalRecordsViewHolder(View view, Callback callback) {
        super(view);
        this.callback = callback;
        ButterKnife.bind(this, view);
    }

    public void populate(final MedicalRecords medicalRecordsItem) {
        titleView.setText(medicalRecordsItem.getTitle());
        String medicalRecordsDescribe = medicalRecordsItem.getMedicalRecordsDescribe();
        if (Strings.isNullOrEmpty(medicalRecordsDescribe)) {
            describeView.setVisibility(GONE);
        } else {
            describeView.setText(medicalRecordsDescribe);
        }
        String hospital = medicalRecordsItem.getHospital();
        if (Strings.isNullOrEmpty(hospital)) {
            hospitalIconView.setVisibility(GONE);
            hospitalView.setVisibility(GONE);
        } else {
            hospitalView.setText(hospital);
        }
        String doctor = medicalRecordsItem.getDoctor();
        if (Strings.isNullOrEmpty(doctor)) {
            doctorIconView.setVisibility(GONE);
            doctorView.setVisibility(GONE);
        } else {
            doctorView.setText(doctor);
        }
        String startDate = medicalRecordsItem.getStartDate();
        if (Strings.isNullOrEmpty(startDate)) {
            startDateView.setVisibility(GONE);
        } else {
            startDateView.setText(startDate);
        }

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
