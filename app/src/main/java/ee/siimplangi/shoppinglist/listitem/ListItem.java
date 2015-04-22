package ee.siimplangi.shoppinglist.listitem;

/**
 * Created by Siim on 18.04.2015.
 */
public interface ListItem {

    public long getId();
    public void setId(long id);

    public String getText();
    public void setText(String text);

    public boolean isCompleted();
    public void setCompleted(boolean completed);

}
