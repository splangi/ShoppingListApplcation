package ee.siimplangi.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCart;
import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCartsFragment;
import ee.siimplangi.shoppinglist.shoppingitem.ShoppingItemsFragment;
import ee.siimplangi.shoppinglist.toolbar.AddNewItemToolbar;
import ee.siimplangi.shoppinglist.toolbar.ToolbarActionListener;


public class MainActivity extends ActionBarActivity {

    private Toolbar mainToolbar;
    private AddNewItemToolbar addNewItemToolbar;

    private MenuItem toEditMode;
    private MenuItem acceptEdit;

    ToolbarActionListener listener;

    private static final String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        addNewItemToolbar = (AddNewItemToolbar) findViewById(R.id.addNewItemToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ShoppingCartsFragment(), LIST_FRAGMENT_TAG).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        toEditMode = menu.findItem(R.id.editMode);
        acceptEdit = menu.findItem(R.id.accept);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case(R.id.editMode):
                setMenuItemsToEditMode();
                listener.toEditMode();
                return true;
            case(R.id.accept):
                setMenuItemsToRegularMode();
                listener.exitEditMode();
                return true;
            case(android.R.id.home):
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void setMenuItemsToEditMode(){
        if (toEditMode != null && acceptEdit != null){
            toEditMode.setVisible(false);
            acceptEdit.setVisible(true);
        }

    }

    public void setMenuItemsToRegularMode(){
        if (toEditMode != null && acceptEdit != null){
            toEditMode.setVisible(true);
            acceptEdit.setVisible(false);
        }
    }

    public void onShoppingCartViewClicked(ShoppingCart item){
        Bundle args = new Bundle();
        args.putLong(ShoppingItemsFragment.PARENT_CART_ID_KEY, item.getId());
        args.putString(ShoppingItemsFragment.PARENT_CART_TEXT_KEY, item.getText());
        ShoppingItemsFragment fragment = new ShoppingItemsFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(LIST_FRAGMENT_TAG)
                .commit();
    }

    public void setAutoCompleteData(List<String> data){
        addNewItemToolbar.setAutoCompleteData(data);
    }

    public void showBackButtonOnActionBar(boolean show){
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    public void setToolBarActionListener(ToolbarActionListener listener) {
        this.listener = listener;
        addNewItemToolbar.setListener(listener);
    }

}
