package none.healthaide.revisting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import none.healthaide.R;
import none.healthaide.model.MedicalRecords;

public class RevisitingListViewAdapter extends RecyclerView.Adapter<RevisitingViewHolder> {

    private Context context;
    private List<MedicalRecords> medicalRecordsList;

    public RevisitingListViewAdapter(Context context, List<MedicalRecords> medicalRecordsList) {
        this.context = context;
        this.medicalRecordsList = medicalRecordsList;
    }

    @Override
    public RevisitingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RevisitingViewHolder(LayoutInflater.from(context).inflate(R.layout.revisiting_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RevisitingViewHolder holder, int position) {
        holder.populate(medicalRecordsList.get(position));
    }

    @Override
    public int getItemCount() {
        return medicalRecordsList.size();
    }

    public void setMedicalRecordsList(List<MedicalRecords> medicalRecordsList) {
        this.medicalRecordsList = medicalRecordsList;
    }
}
