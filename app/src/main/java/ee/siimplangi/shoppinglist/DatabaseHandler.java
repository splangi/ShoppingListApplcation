package ee.siimplangi.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ee.siimplangi.shoppinglist.shoppingitem.ShoppingItem;
import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCart;

/**
 * Created by Siim on 18.04.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler dbInstance;

    private static int VERSION = 5;

    private static final String DATABASE_NAME = "ToDoListDatabase";

    // Use autoincrement for sorting purposes
    private static final String LIST_TABLE_CREATE = "CREATE TABLE " +
            ShoppingCart.TABLE_NAME +
            "(" +
                ShoppingCart.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShoppingCart.KEY_NAME + " TEXT NOT NULL" +
            ");";

    private static final String LIST_TABLE_DROP = "DROP TABLE IF EXISTS " + ShoppingCart.TABLE_NAME;

    // Use autoincrement for sorting purposes
    //No foreign constraints checks on < API 16
    private static final String LIST_ITEMS_TABLE_CREATE = "CREATE TABLE " +
            ShoppingItem.TABLE_NAME +
            "( " +
                ShoppingItem.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShoppingItem.KEY_TEXT + " TEXT NOT NULL, " +
                ShoppingItem.KEY_COMPLETED + " INTEGER NOT NULL, " +
                ShoppingItem.KEY_LIST_ID + " INTEGER NOT NULL );";

    private static final String LIST_ITEM_TABLE_DROP = "DROP TABLE IF EXISTS " + ShoppingItem.TABLE_NAME;

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public synchronized static DatabaseHandler getInstance(Context context) {
        if (dbInstance == null){
            dbInstance = new DatabaseHandler(context);
        }
        return dbInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LIST_TABLE_CREATE);
        sqLiteDatabase.execSQL(LIST_ITEMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // This is ok for development
        sqLiteDatabase.execSQL(LIST_TABLE_DROP);
        sqLiteDatabase.execSQL(LIST_ITEM_TABLE_DROP);
        onCreate(sqLiteDatabase);
    }


}
