package ee.siimplangi.shoppinglist.listitem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ee.siimplangi.shoppinglist.DatabaseHandler;
import ee.siimplangi.shoppinglist.MainActivity;
import ee.siimplangi.shoppinglist.R;
import ee.siimplangi.shoppinglist.toolbar.ToolbarActionListener;

/**
 * Created by Siim on 18.04.2015.
 */
public abstract class ListFragment<A extends ListViewAdapter, D extends ListItem, S extends ListItemService>
        extends Fragment implements ToolbarActionListener, ListUserActionListener<D> {

    private RecyclerView recyclerView;

    private A mAdapter;

    private List<D> data;

    private S service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.toDoItemContentWindow);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(mLayoutManager);

        service = getService();

        data = getData();

        mAdapter = getNewAdapter(data);

        recyclerView.setAdapter(mAdapter);

        getMainActivity().setToolBarActionListener(this);

        getActivity().setTitle(getTitle());

        getMainActivity().setAutoCompleteData(getAutoCompleteData());

        getMainActivity().setMenuItemsToRegularMode();

        return rootView;
    }

    @Override
    public void onAddNewItem(String text) {
        D newItem = getNewDataItem(text);
        long id = service.addItem(newItem);
        newItem.setId(id);
        data.add(0, newItem);
        mAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onDeleteItemClicked(D listItem) {
        service.deleteItem(listItem.getId());
        int indexInData = data.indexOf(listItem);
        if (indexInData > -1){
            data.remove(indexInData);
            mAdapter.notifyItemRemoved(indexInData);
        }
    }

    @Override
    public void onItemEdited(D listItem) {
        service.editItem(listItem);
    }

    @Override
    public void toEditMode() {
        mAdapter.setEditMode();
    }

    @Override
    public void exitEditMode() {
        mAdapter.setRegularMode();
    }

    protected abstract List<D> getData();

    protected abstract A getNewAdapter(List<D> data);

    protected abstract S getService();

    protected abstract D getNewDataItem(String text);

    protected abstract String getTitle();

    protected DatabaseHandler getDatabaseInstance() {
        return DatabaseHandler.getInstance(getActivity());
    }

    protected abstract List<String> getAutoCompleteData();

    protected MainActivity getMainActivity(){
        return (MainActivity) getActivity();
    }

}
