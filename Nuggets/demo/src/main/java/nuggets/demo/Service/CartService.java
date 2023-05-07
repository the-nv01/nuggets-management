package nuggets.demo.Service;

import nuggets.demo.Model.Cart;
import nuggets.demo.Model.CartDTO;
import nuggets.demo.Model.Product;

import java.util.List;

public interface CartService {
    List<Product> getProductsByUser();

    Integer deleteMemberProduct(Integer productId);

    void updateCart(CartDTO cartDTO);
}
