package nuggets.demo.Repository;

import nuggets.demo.Model.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Integer> {
    List<WishList> findAllByUsernameOrderByDateDesc(String username);

    WishList findWishListByProductIdAndUsernameOrderByDateDesc(Integer productId, String username);

    Integer deleteWishListByUsernameAndProductId (String username, Integer productId);

    WishList save(WishList wishList);
}
