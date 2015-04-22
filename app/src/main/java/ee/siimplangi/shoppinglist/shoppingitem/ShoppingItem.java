package ee.siimplangi.shoppinglist.shoppingitem;

import ee.siimplangi.shoppinglist.listitem.ListItem;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingItem implements ListItem{

    public static final String TABLE_NAME = "listItem";

    public static final String KEY_ID = "id";
    public static final String KEY_COMPLETED = "completed";
    public static final String KEY_DELETED = "deleted";
    public static final String KEY_TEXT = "text";
    public static final String KEY_LIST_ID = "listId";

    private long id;
    private boolean completed;
    private String text;
    private long listId;

    public ShoppingItem() {
        this.id = -1;
        this.completed = false;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof ShoppingItem){
            ShoppingItem anotherShoppingItem = (ShoppingItem) o;
            return getId() == anotherShoppingItem.getId();
        }
        return super.equals(o);
    }
}
