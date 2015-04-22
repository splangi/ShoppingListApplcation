package ee.siimplangi.shoppinglist.listitem;

import ee.siimplangi.shoppinglist.shoppingcart.ShoppingCart;

/**
 * Created by Siim on 21.04.2015.
 */
public interface ListUserActionListener<D extends ListItem> {

    public void onShoppingCartClicked(ShoppingCart shoppingCartItem);

    public void onDeleteItemClicked(D listItem);

    public void onItemEdited(D listItem);

}
