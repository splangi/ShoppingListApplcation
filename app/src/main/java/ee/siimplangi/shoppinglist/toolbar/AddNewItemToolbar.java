package ee.siimplangi.shoppinglist.toolbar;


import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ee.siimplangi.shoppinglist.R;

/**
 * Created by Siim on 20.04.2015.
 */
public class AddNewItemToolbar extends Toolbar {

    private AutoCompleteTextView editText;

    private ToolbarActionListener listener;

    private ArrayAdapter<String> autoCompleteAdapter;

    public AddNewItemToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateMenu(R.menu.menu_add_new);
        setOnMenuItemClickListener(new OnAddNewMenuItemClicked());
    }

    @Override
    protected void onFinishInflate() {
        editText = (AutoCompleteTextView) findViewById(R.id.addNewEditText);
        editText.setOnEditorActionListener(new OnKeyboardActionKeyClicked());
        super.onFinishInflate();
    }

    public void setAutoCompleteData(List<String> data) {
        autoCompleteAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, data);
        autoCompleteAdapter.setNotifyOnChange(true);
        editText.setAdapter(autoCompleteAdapter);
    }

    private class OnKeyboardActionKeyClicked implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == EditorInfo.IME_ACTION_DONE){
                return addItem(editText.getText().toString());
            }
            return false;
        }
    }

    private class OnAddNewMenuItemClicked implements OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.addNew){
                addItem(editText.getText().toString());
                return true;
            }
            return false;
        }
    }


    private boolean addItem(String text){
        if (text.isEmpty()){
            Toast.makeText(getContext(), R.string.enter_name, Toast.LENGTH_SHORT).show();
            return true;
        } else{
            listener.onAddNewItem(editText.getText().toString());
            editText.setText("");
            autoCompleteAdapter.add(text);
            return false;
        }
    }

    public void setListener(ToolbarActionListener listener) {
        this.listener = listener;
    }

}
