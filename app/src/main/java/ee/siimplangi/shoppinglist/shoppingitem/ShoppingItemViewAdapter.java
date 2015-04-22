package ee.siimplangi.shoppinglist.shoppingitem;

import android.widget.LinearLayout;

import java.util.List;

import ee.siimplangi.shoppinglist.listitem.ListUserActionListener;
import ee.siimplangi.shoppinglist.listitem.ListViewAdapter;

/**
 * Created by Siim on 20.04.2015.
 */
public class ShoppingItemViewAdapter extends ListViewAdapter<ShoppingItem, ShoppingItemViewHolder> {

    public ShoppingItemViewAdapter(List<ShoppingItem> items, ListUserActionListener listener) {
        super(items, listener);
    }

    @Override
    protected ShoppingItemViewHolder createNewViewHolder(LinearLayout layout, ListUserActionListener listener) {
        return new ShoppingItemViewHolder(layout, listener);
    }
}
