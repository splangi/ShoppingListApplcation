package ee.siimplangi.shoppinglist.toolbar;

import ee.siimplangi.shoppinglist.listitem.ListItem;

/**
 * Created by Siim on 20.04.2015.
 */
public interface ToolbarActionListener<D extends ListItem> {

    public void onAddNewItem(String text);

    public void toEditMode();

    public void exitEditMode();

}
