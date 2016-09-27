package none.healthaide.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import none.healthaide.R;
import none.healthaide.model.Case;

public class CaseListViewAdapter extends RecyclerView.Adapter<RecentCaseViewHolder> {

    private List<Case> caseList;
    private Context context;
    private  RecentCaseViewHolder.Callback callback;

    public CaseListViewAdapter(Context context, List<Case> caseList, RecentCaseViewHolder.Callback callback) {
        this.context = context;
        this.caseList = caseList;
        this.callback = callback;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }

    @Override
    public RecentCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentCaseViewHolder(LayoutInflater.from(context).inflate(R.layout.case_item_view, parent, false), callback);
    }

    @Override
    public void onBindViewHolder(RecentCaseViewHolder holder, int position) {
        holder.populate(caseList.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return caseList.size();
    }
}
