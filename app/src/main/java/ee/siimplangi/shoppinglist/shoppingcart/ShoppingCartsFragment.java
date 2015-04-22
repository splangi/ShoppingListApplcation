package ee.siimplangi.shoppinglist.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ee.siimplangi.shoppinglist.R;
import ee.siimplangi.shoppinglist.listitem.ListFragment;

/**
 * Created by Siim on 20.04.2015.
 */
public class ShoppingCartsFragment extends ListFragment<ShoppingCartViewAdapter, ShoppingCart, ShoppingCartService> {

    private ShoppingCartService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getMainActivity().showBackButtonOnActionBar(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected List<ShoppingCart> getData() {
        return getService().getAllItems();
    }

    @Override
    protected ShoppingCartViewAdapter getNewAdapter(List<ShoppingCart> data) {
        return new ShoppingCartViewAdapter(data, this);
    }

    @Override
    protected ShoppingCartService getService() {
        if (service == null){
            service = new ShoppingCartService(getDatabaseInstance());
        }
        return service;
    }

    @Override
    protected ShoppingCart getNewDataItem(String text) {
        ShoppingCart listItem = new ShoppingCart();
        listItem.setText(text);
        return listItem;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.my_lists);
    }

    @Override
    protected List<String> getAutoCompleteData() {
        List<String> autoCompleteData = new ArrayList<>();
        for (ShoppingCart cart : getService().getAllItems()){
            autoCompleteData.add(cart.getText());
        }
        return autoCompleteData;
    }

    @Override
    public void onShoppingCartClicked(ShoppingCart shoppingCart) {
        getMainActivity().onShoppingCartViewClicked(shoppingCart);
    }
}
