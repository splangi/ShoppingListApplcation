package ee.siimplangi.shoppinglist.shoppingcart;

import ee.siimplangi.shoppinglist.listitem.ListItem;

/**
 * Created by Siim on 18.04.2015.
 */
public class ShoppingCart implements ListItem {

    public static final String TABLE_NAME = "toDoList";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    private long id;
    private String text;
    private int tasksTotal;
    private int completedTasks;

    public ShoppingCart(){
        this.id = -1;
        tasksTotal = 0;
        completedTasks = 0;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isCompleted() {
        return tasksTotal == completedTasks;
    }

    public void setTasksTotal(int tasksTotal) {
        this.tasksTotal = tasksTotal;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    @Override
    public void setCompleted(boolean completed) {
        throw new UnsupportedOperationException("Cannot set list completed!");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ShoppingCart){
            ShoppingCart anotherShoppingCart = (ShoppingCart) o;
            return getId() == anotherShoppingCart.getId();
        }
        return super.equals(o);
    }
}
