package ee.siimplangi.shoppinglist.listitem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import ee.siimplangi.shoppinglist.R;

/**
 * Created by Siim on 18.04.2015.
 */
public abstract class ListViewAdapter<D extends ListItem, VH extends ListItemViewHolder> extends RecyclerView.Adapter<VH> {

    private List<D> dataSet;
    private ListUserActionListener listener;
    private boolean editMode = false;

    public ListViewAdapter(List<D> items, ListUserActionListener listener) {
        this.dataSet = items;
        this.listener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.list_item, viewGroup, false);
        return createNewViewHolder(linearLayout, listener);
    }

    @Override
    public void onBindViewHolder(VH listItemViewHolder, int i) {
        listItemViewHolder.setData(dataSet.get(i), editMode);

    }

    public void setEditMode(){
        editMode = true;
        notifyDataSetChanged();
    }

    public void setRegularMode(){
        editMode = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    protected abstract VH createNewViewHolder(LinearLayout layout, ListUserActionListener listener);

}
