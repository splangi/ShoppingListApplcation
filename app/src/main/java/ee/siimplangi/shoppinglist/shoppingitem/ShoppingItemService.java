package ee.siimplangi.shoppinglist.shoppingitem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ee.siimplangi.shoppinglist.DatabaseHandler;
import ee.siimplangi.shoppinglist.listitem.ListItemService;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingItemService implements ListItemService<ShoppingItem> {

    private DatabaseHandler dbHandler;

    public ShoppingItemService(DatabaseHandler databaseHandler){
        this.dbHandler = databaseHandler;
    }

    @Override
    public long addItem(ShoppingItem item) {
        return addOrEditItem(item);
    }

    private long addOrEditItem(ShoppingItem item){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ShoppingItem.KEY_TEXT, item.getText());
        cv.put(ShoppingItem.KEY_COMPLETED, item.isCompleted());
        cv.put(ShoppingItem.KEY_LIST_ID, item.getListId());
        if (item.getId() == -1){
            long newId = db.insert(ShoppingItem.TABLE_NAME, null, cv);
            item.setId(newId);
            return newId;
        } else {
            cv.put(ShoppingItem.KEY_ID, item.getId());
            long oldId = db.replace(ShoppingItem.TABLE_NAME, null, cv);
            return oldId;
        }
    }

    @Override
    public void deleteItem(long id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(ShoppingItem.TABLE_NAME, ShoppingItem.KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    @Override
    public List<ShoppingItem> getAllItemsWithParentId(long parentId) {
        String WHERE_CLAUSE = "";
        if (parentId != -1){
            WHERE_CLAUSE = " WHERE " + ShoppingItem.KEY_LIST_ID + "= ?";
        }
        String sql = "SELECT " +
                ShoppingItem.KEY_ID + "," +
                ShoppingItem.KEY_TEXT + "," +
                ShoppingItem.KEY_COMPLETED + "," +
                ShoppingItem.KEY_LIST_ID +
                " FROM " + ShoppingItem.TABLE_NAME +
                WHERE_CLAUSE +
                " ORDER BY " + ShoppingItem.KEY_ID + " DESC;";
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] args;
        if (parentId != -1){
            args = new String [] {String.valueOf(parentId)};
        } else {
            args = new String[0];
        }
        Cursor c = db.rawQuery(sql, args);
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                ShoppingItem item = new ShoppingItem();
                item.setId(c.getLong(c.getColumnIndexOrThrow(ShoppingItem.KEY_ID)));
                item.setText(c.getString(c.getColumnIndexOrThrow(ShoppingItem.KEY_TEXT)));
                item.setCompleted(c.getInt(c.getColumnIndexOrThrow(ShoppingItem.KEY_COMPLETED))!=0);
                item.setListId(parentId);
                shoppingItems.add(item);
            } while(c.moveToNext());
        }
        c.close();
        return shoppingItems;
    }

    @Override
    public List<ShoppingItem> getAllItems() {
        return getAllItemsWithParentId(-1);
    }

    @Override
    public void editItem(ShoppingItem item) {
        addOrEditItem(item);
    }

}
