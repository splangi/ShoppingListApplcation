package ee.siimplangi.shoppinglist.shoppingitem;

import android.widget.CompoundButton;
import android.widget.LinearLayout;

import ee.siimplangi.shoppinglist.R;
import ee.siimplangi.shoppinglist.listitem.ListItemViewHolder;
import ee.siimplangi.shoppinglist.listitem.ListUserActionListener;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingItemViewHolder extends ListItemViewHolder {

    public ShoppingItemViewHolder(LinearLayout toDoItemView, ListUserActionListener listener) {
        super(toDoItemView, listener);
        getCompletedCheckBox().setOnCheckedChangeListener(new CompletedCheckBoxChangeListener());
        getCompletedCheckBox().setClickable(true);
    }

    @Override
    public void inEditMode() {
        super.inEditMode();
        getCompletedCheckBox().setClickable(false);
    }

    @Override
    public void inRegularMode() {
        super.inRegularMode();
        getCompletedCheckBox().setClickable(true);
    }

    @Override
    public int getCheckboxDrawableId() {
        return R.drawable.checkbox_drawable;
    }

    private class CompletedCheckBoxChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            getItem().setCompleted(b);
            getListener().onItemEdited(getItem());
        }
    }
}
