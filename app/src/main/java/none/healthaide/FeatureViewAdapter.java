package none.healthaide;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeatureViewAdapter extends BaseAdapter {

    private Context context;
    private final TypedArray featureIcons;
    private final TypedArray featureNames;

    public FeatureViewAdapter(Context context, TypedArray featureIcons, TypedArray featureNames) {
        this.context = context;
        this.featureIcons = featureIcons;
        this.featureNames = featureNames;
    }

    @Override
    public int getCount() {
        return featureIcons.length();
    }

    @Override
    public Feature getItem(int i) {
        return new Feature(featureIcons.getResourceId(i, 0), featureNames.getString(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.feature_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.featureIconImageView.setImageResource(getItem(position).getIconRes());
        holder.featureNameTextView.setText(getItem(position).getName());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.feature_icon)
        ImageView featureIconImageView;
        @BindView(R.id.feature_name)
        TextView featureNameTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
