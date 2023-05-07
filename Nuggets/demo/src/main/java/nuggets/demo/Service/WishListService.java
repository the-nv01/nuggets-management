package nuggets.demo.Service;

import nuggets.demo.Model.Product;

import java.util.List;

public interface WishListService {
    List<Product> getWishListByUser();
}
