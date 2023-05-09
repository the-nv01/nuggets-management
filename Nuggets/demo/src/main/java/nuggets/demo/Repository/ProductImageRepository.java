package nuggets.demo.Repository;

import nuggets.demo.Model.Product;
import nuggets.demo.Model.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductId(Integer productId);
}
