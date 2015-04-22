
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


    private long addOrEditItem(ShoppingCart item, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(ShoppingCart.KEY_NAME, item.getText());
        long listId;
        if (item.getId() == -1){
            listId = db.insert(ShoppingCart.TABLE_NAME, null, values);
            item.setId(listId);
        } else{
            values.put(ShoppingCart.KEY_ID, item.getId());
            listId = db.replace(ShoppingCart.TABLE_NAME, null, values);
        }
        return listId;
    }

    @Override
    public long addItem(ShoppingCart item, SQLiteDatabase db){
        return addOrEditItem(item, db);
    }

    @Override
    public long addItem(ShoppingCart item) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return addOrEditItem(item, db);
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
        return getAllItems();
    }

    @Override
    public List<ShoppingCart> getAllItems() {
        final String TOTAL_TASKS = "total";
        final String COMPLETED_TASKS = "completed";
        final String SHOPPING_CART_KEY_ID = ShoppingCart.TABLE_NAME + "." + ShoppingCart.KEY_ID;
        final String SHOPPING_CART_KEY_NAME = ShoppingCart.TABLE_NAME + "." + ShoppingCart.KEY_NAME;
        final String SHOPPING_ITEM_KEY_COMPLETED = ShoppingItem.TABLE_NAME + "." + ShoppingItem.KEY_COMPLETED;
        final String SHOPPING_ITEM_KEY_LIST_ID = ShoppingItem.TABLE_NAME + "." + ShoppingItem.KEY_LIST_ID;
        String select = "SELECT " +
                SHOPPING_CART_KEY_ID+ ", " +
                SHOPPING_CART_KEY_NAME + "," +
                " (IFNULL (SUM(" + SHOPPING_ITEM_KEY_COMPLETED + "), 0)) AS " + COMPLETED_TASKS + "," +
                " count("+SHOPPING_ITEM_KEY_LIST_ID+") AS " + TOTAL_TASKS +
                " FROM " + ShoppingCart.TABLE_NAME +
                " LEFT OUTER JOIN " + ShoppingItem.TABLE_NAME + " ON "+ SHOPPING_CART_KEY_ID + " = "+ SHOPPING_ITEM_KEY_LIST_ID +
                " GROUP BY " + SHOPPING_CART_KEY_ID + " " +
                " ORDER BY " + SHOPPING_CART_KEY_ID + " DESC;";

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
    public void editItem(ShoppingCart item) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        addOrEditItem(item, db);
    }


}
