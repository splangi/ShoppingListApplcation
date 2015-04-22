package ee.siimplangi.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCart;
import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCartService;
import ee.siimplangi.shoppinglist.shoppingitem.ShoppingItem;
import ee.siimplangi.shoppinglist.shoppingitem.ShoppingItemService;

/**
 * Created by Siim on 18.04.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler dbInstance;

    private Context context;

    private static int VERSION = 10;

    private static final String DATABASE_NAME = "ToDoListDatabase";

    //Use autoincrement for sorting purposes (autoincrement wont reuse deleted ID-s)
    private static final String LIST_TABLE_CREATE = "CREATE TABLE " +
            ShoppingCart.TABLE_NAME +
            "(" +
                ShoppingCart.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShoppingCart.KEY_NAME + " TEXT NOT NULL" +
            ");";

    private static final String LIST_TABLE_DROP = "DROP TABLE IF EXISTS " + ShoppingCart.TABLE_NAME;

    //Use autoincrement for sorting purposes (autoincrement wont reuse ID-s)
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
        this.context = context;
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
        createExampleData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // This is ok for development
        sqLiteDatabase.execSQL(LIST_TABLE_DROP);
        sqLiteDatabase.execSQL(LIST_ITEM_TABLE_DROP);
        onCreate(sqLiteDatabase);
    }



    public void createExampleData(SQLiteDatabase sqLiteDatabase){
        ShoppingCartService shoppingCartService = new ShoppingCartService(this);
        ShoppingItemService shoppingItemService = new ShoppingItemService(this);

        ShoppingCart cartEmpty = createNewCart(context.getString(R.string.empty_shopping_cart));
        ShoppingCart cartFilledCompleted = createNewCart(context.getString(R.string.filled_completed_shopping_cart));
        ShoppingCart cartFilledNotCompleted = createNewCart(context.getString(R.string.filled_not_completed_shopping_cart));

        cartEmpty.setId(shoppingCartService.addItem(cartEmpty, sqLiteDatabase));
        cartFilledCompleted.setId(shoppingCartService.addItem(cartFilledCompleted, sqLiteDatabase));
        cartFilledNotCompleted.setId(shoppingCartService.addItem(cartFilledNotCompleted, sqLiteDatabase));

        ShoppingItem milk = createNewItem(context.getString(R.string.milk), cartFilledNotCompleted.getId(), true);
        ShoppingItem bread = createNewItem(context.getString(R.string.bread), cartFilledNotCompleted.getId(), false);
        ShoppingItem sugar = createNewItem(context.getString(R.string.sugar), cartFilledNotCompleted.getId(), false);

        shoppingItemService.addItem(milk, sqLiteDatabase);
        shoppingItemService.addItem(bread, sqLiteDatabase);
        shoppingItemService.addItem(sugar, sqLiteDatabase);

        ShoppingItem apples = createNewItem(context.getString(R.string.apples), cartFilledCompleted.getId(), true);
        ShoppingItem oranges = createNewItem(context.getString(R.string.oranges), cartFilledCompleted.getId(), true);

        shoppingItemService.addItem(apples, sqLiteDatabase);
        shoppingItemService.addItem(oranges, sqLiteDatabase);
    }

    public ShoppingCart createNewCart(String text){
        ShoppingCart cart = new ShoppingCart();
        cart.setText(text);
        return cart;
    }

    public ShoppingItem createNewItem(String text, long parentId, boolean completed){
        ShoppingItem item = new ShoppingItem();
        item.setText(text);
        item.setListId(parentId);
        item.setCompleted(completed);
        return item;
    }

}
