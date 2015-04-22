
package ee.siimplangi.shoppinglist.shoppingcart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ee.siimplangi.shoppinglist.DatabaseHandler;
import ee.siimplangi.shoppinglist.listitem.ListItemService;
import ee.siimplangi.shoppinglist.shoppingitem.ShoppingItem;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingCartService implements ListItemService<ShoppingCart> {

    private DatabaseHandler dbHandler;

    public ShoppingCartService(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public long addItem(ShoppingCart item) {
        return addOrEditItem(item);
    }

    private long addOrEditItem(ShoppingCart item){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingCart.KEY_NAME, item.getText());
        if (item.getId() == -1){
            long newListId = db.insert(ShoppingCart.TABLE_NAME, null, values);
            item.setId(newListId);
            return newListId;
        } else{
            values.put(ShoppingCart.KEY_ID, item.getId());
            long oldListId = db.replace(ShoppingCart.TABLE_NAME, null, values);
            return oldListId;
        }
    }

    @Override
    public void deleteItem(long id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(ShoppingCart.TABLE_NAME, ShoppingCart.KEY_ID + "= ?",  new String[] { String.valueOf(id) });
        //We have to delete seperately, because foreign key actions were added in API 16.
        db.delete(ShoppingItem.TABLE_NAME, ShoppingItem.KEY_LIST_ID + "= ?", new String[] {String.valueOf(id)});
    }

    @Override
    public List<ShoppingCart> getAllItemsWithParentId(long parentId) {
        // We don't use the parentId, because List does not have a parent;
        final String TOTAL_TASKS = "total";
        final String COMPLETED_TASKS = "completed";
        String select = "SELECT " +
                "list."+ ShoppingCart.KEY_ID + ", " +
                "list."+ ShoppingCart.KEY_NAME + "," +
                " (IFNULL (SUM(item."+ ShoppingItem.KEY_COMPLETED+"), 0)) AS " + COMPLETED_TASKS + "," +
                " count(*) AS " + TOTAL_TASKS +
                " FROM " + ShoppingCart.TABLE_NAME + " list" +
                " LEFT OUTER JOIN " + ShoppingItem.TABLE_NAME + " item ON list."+ ShoppingCart.KEY_ID + " = item."+ ShoppingItem.KEY_LIST_ID +
                " GROUP BY list." + ShoppingCart.KEY_ID + " " +
                " ORDER BY list." + ShoppingCart.KEY_ID + " DESC;";

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(select, new String[0]);
        List<ShoppingCart> lists = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setId(c.getInt(c.getColumnIndexOrThrow(ShoppingCart.KEY_ID)));
                shoppingCart.setText(c.getString(c.getColumnIndexOrThrow(ShoppingCart.KEY_NAME)));
                shoppingCart.setCompletedTasks(c.getInt(c.getColumnIndexOrThrow(COMPLETED_TASKS)));
                shoppingCart.setTasksTotal(c.getInt(c.getColumnIndexOrThrow(TOTAL_TASKS)));
                lists.add(shoppingCart);
            } while (c.moveToNext());
        }
        c.close();
        return lists;
    }

    @Override
    public List<ShoppingCart> getAllItems() {
        return getAllItemsWithParentId(-1);
    }

    @Override
    public void editItem(ShoppingCart item) {
        addOrEditItem(item);
    }


}
