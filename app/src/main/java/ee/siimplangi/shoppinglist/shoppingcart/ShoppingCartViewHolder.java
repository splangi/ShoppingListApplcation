package ee.siimplangi.shoppinglist.shoppingcart;

import android.view.View;
import android.widget.LinearLayout;

import ee.siimplangi.shoppinglist.R;
import ee.siimplangi.shoppinglist.listitem.ListItemViewHolder;
import ee.siimplangi.shoppinglist.listitem.ListUserActionListener;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingCartViewHolder extends ListItemViewHolder<ShoppingCart> {

    public ShoppingCartViewHolder(LinearLayout layout, ListUserActionListener listener) {
        super(layout, listener);
        getCompletedCheckBox().setClickable(false);
        getLayout().setOnClickListener(new ToDoListOnClickListener());
    }

    @Override
    public int getCheckboxDrawableId() {
        return R.drawable.checkbox_all_drawable;
    }

    private class ToDoListOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            getListener().onShoppingCartClicked(getItem());
        }
    }

}
