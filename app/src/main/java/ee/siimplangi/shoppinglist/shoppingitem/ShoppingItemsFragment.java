package ee.siimplangi.shoppinglist.shoppingitem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ee.siimplangi.shoppinglist.listitem.ListFragment;
import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCart;

/**
 * Created by Siim on 20.04.2015.
 */
public class ShoppingItemsFragment extends ListFragment<ShoppingItemViewAdapter, ShoppingItem, ShoppingItemService> {

    public static final String PARENT_LIST_ID_KEY = "parentListId";
    public static final String PARENT_LIST_TEXT_KEY = "parentListText";

    private ShoppingItemService service;
    private String title;
    private long parentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        title = args.getString(PARENT_LIST_TEXT_KEY);
        parentId = args.getLong(PARENT_LIST_ID_KEY);
        getMainActivity().showBackButtonOnActionBar(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected List<ShoppingItem> getData() {
        return getService().getAllItemsWithParentId(parentId);
    }

    @Override
    protected ShoppingItemViewAdapter getNewAdapter(List<ShoppingItem> data) {
        return new ShoppingItemViewAdapter(data, this);
    }

    @Override
    protected ShoppingItemService getService() {
        if (service == null){
            service = new ShoppingItemService(getDatabaseInstance());
        }
        return service;
    }

    @Override
    protected ShoppingItem getNewDataItem(String text) {
        ShoppingItem item = new ShoppingItem();
        item.setText(text);
        item.setListId(parentId);
        return item;
    }

    @Override
    public void onToDoListClicked(ShoppingCart listItem) {
        // do nothing
    }
}
