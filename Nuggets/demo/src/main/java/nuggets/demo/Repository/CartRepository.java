package nuggets.demo.Repository;

import nuggets.demo.Model.Cart;
import nuggets.demo.Model.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findAllByUsername(String username);

    Cart findCartByUsernameAndProductId(String name, Integer productId);

    Integer deleteCartByUsernameAndProductId(String username, Integer productId);

    Cart save(Cart cart);
}
