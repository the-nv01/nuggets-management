package nuggets.demo.Repository;

import nuggets.demo.Model.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Integer> {
    List<WishList> findAllByUsername(String username);

    Integer deleteWishListByUsernameAndProductId (String username, Integer productId);
}
