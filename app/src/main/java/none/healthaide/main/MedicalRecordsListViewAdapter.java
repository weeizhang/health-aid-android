package none.healthaide.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import none.healthaide.R;
import none.healthaide.model.MedicalRecords;

public class MedicalRecordsListViewAdapter extends RecyclerView.Adapter<RecentMedicalRecordsViewHolder> {

    private List<MedicalRecords> medicalRecordsList;
    private Context context;
    private  RecentMedicalRecordsViewHolder.Callback callback;

    public MedicalRecordsListViewAdapter(Context context, List<MedicalRecords> medicalRecordsList, RecentMedicalRecordsViewHolder.Callback callback) {
        this.context = context;
        this.medicalRecordsList = medicalRecordsList;
        this.callback = callback;
    }

    public void setMedicalRecordsList(List<MedicalRecords> medicalRecordsList) {
        this.medicalRecordsList = medicalRecordsList;
    }

    @Override
    public RecentMedicalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentMedicalRecordsViewHolder(LayoutInflater.from(context).inflate(R.layout.medical_records_item_view, parent, false), callback);
    }

    @Override
    public void onBindViewHolder(RecentMedicalRecordsViewHolder holder, int position) {
        holder.populate(medicalRecordsList.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return medicalRecordsList.size();
    }
}
