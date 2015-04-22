package ee.siimplangi.shoppinglist.shoppingcart;

import android.widget.LinearLayout;

import java.util.List;

import ee.siimplangi.shoppinglist.listitem.ListUserActionListener;
import ee.siimplangi.shoppinglist.listitem.ListViewAdapter;

/**
 * Created by Siim on 20.04.2015.
 */
public class ShoppingCartViewAdapter extends ListViewAdapter<ShoppingCart, ShoppingCartViewHolder> {

    public ShoppingCartViewAdapter(List<ShoppingCart> items, ListUserActionListener listener) {
        super(items, listener);
    }

    @Override
    protected ShoppingCartViewHolder createNewViewHolder(LinearLayout layout, ListUserActionListener listener) {
        return new ShoppingCartViewHolder(layout, listener);
    }
}
