package ee.siimplangi.shoppinglist.listitem;

import java.util.List;

/**
 * Created by Siim on 20.04.2015.
 */
public interface ListItemService<T extends ListItem> {

    public long addItem(T item);

    public void deleteItem(long id);

    public List<T> getAllItemsWithParentId(long parentId);

    public List<T> getAllItems();

    public void editItem(T item);

}
