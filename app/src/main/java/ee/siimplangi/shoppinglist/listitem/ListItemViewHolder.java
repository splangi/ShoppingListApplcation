package ee.siimplangi.shoppinglist.listitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import ee.siimplangi.shoppinglist.R;

/**
 * Created by Siim on 18.04.2015.
 */
public abstract class ListItemViewHolder<T extends ListItem> extends RecyclerView.ViewHolder{

    protected CheckBox completedCheckBox;
    protected ViewSwitcher viewSwitcher;
    protected TextView taskText;
    protected EditText taskEditText;
    protected Button deleteButton;
    protected LinearLayout layout;
    protected T item;
    protected ListUserActionListener listener;

    public ListItemViewHolder(LinearLayout listItemView, ListUserActionListener listener) {
        super(listItemView);
        this.listener = listener;
        this.layout = listItemView;
        viewSwitcher = (ViewSwitcher) listItemView.findViewById(R.id.viewSwitcher);
        completedCheckBox = (CheckBox) listItemView.findViewById(R.id.completedCheckBox);
        taskText = (TextView) listItemView.findViewById(R.id.taskText);
        taskEditText = (EditText) listItemView.findViewById(R.id.taskEditText);
        deleteButton = (Button) listItemView.findViewById(R.id.deleteButton);

        completedCheckBox.setBackgroundResource(getCheckboxDrawableId());

        deleteButton.setOnClickListener(new OnDeleteButtonClicked());
        taskEditText.setOnFocusChangeListener(new OnTextEditedListener());
    }

    public void setData(T item, boolean inEditMode){
        this.item = item;
        taskText.setText(item.getText());
        taskEditText.setText(item.getText());
        completedCheckBox.setChecked(item.isCompleted());
        if (inEditMode){
            inEditMode();
        } else{
            inRegularMode();
        }

    }

    public void inRegularMode(){
        completedCheckBox.setVisibility(View.VISIBLE);
        taskEditText.setClickable(false);
        deleteButton.setVisibility(View.GONE);
        deleteButton.setClickable(false);
        layout.setClickable(true);
        viewSwitcher.setDisplayedChild(0);
    };

    public void inEditMode(){
        completedCheckBox.setVisibility(View.GONE);
        taskEditText.setClickable(true);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setClickable(true);
        layout.setClickable(false);
        viewSwitcher.setDisplayedChild(1);
    };

    public abstract int getCheckboxDrawableId();

    public CheckBox getCompletedCheckBox() {
        return completedCheckBox;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public T getItem() {
        return item;
    }

    public ListUserActionListener getListener() {
        return listener;
    }

    private class OnDeleteButtonClicked implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            listener.onDeleteItemClicked(item);
        }
    }

    private class OnTextEditedListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus){
                item.setText(taskEditText.getText().toString());
                listener.onItemEdited(item);
            }
        }
    }



}
