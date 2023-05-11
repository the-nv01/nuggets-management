package nuggets.demo.Repository;

import nuggets.demo.Model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findAllByUsernameOrderByDateDesc(String username);

    Cart findCartByUsernameAndProductIdOrderByDateDesc(String name, Integer productId);

    Integer deleteCartByUsernameAndProductId(String username, Integer productId);

    Integer deleteCartByUsername(String username);

    Cart save(Cart cart);
}
