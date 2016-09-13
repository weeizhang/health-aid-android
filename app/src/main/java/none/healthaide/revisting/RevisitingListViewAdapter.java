package none.healthaide.revisting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import none.healthaide.R;
import none.healthaide.model.Case;

public class RevisitingListViewAdapter extends RecyclerView.Adapter<RevisitingViewHolder> {

    private Context context;
    private List<Case> caseList;

    public RevisitingListViewAdapter(Context context, List<Case> caseList) {
        this.context = context;
        this.caseList = caseList;
    }

    @Override
    public RevisitingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RevisitingViewHolder(LayoutInflater.from(context).inflate(R.layout.revisiting_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RevisitingViewHolder holder, int position) {
        holder.populate(caseList.get(position));
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }
}
