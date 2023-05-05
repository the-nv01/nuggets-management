package nuggets.demo.Service;

import nuggets.demo.Model.Product;

import java.util.List;

public interface IndexService {
    List<Product> findAll();

    List<Product> getLargeProducts();

    List<Product> getSmallProducts();
}
