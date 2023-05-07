package nuggets.demo.Repository;

import nuggets.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllBy();

    Product findAllByProductId(Integer productId);

    List<Product> findByProductIdAfter(Integer productId);

    List<Product> findByProductIdBefore(Integer productId);

    List<Product> findAllByCategoryId(Integer categoryId);

    List<Product> findAllByCategoryIdNotNullOrderByCategoryIdAsc();

    Product save(Product product);

    Integer deleteProductByProductId(Integer productId);
}
