package nuggets.demo.Repository;

import nuggets.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    final Integer ZERO = 0;

    List<Product> findAllBy();

    Product findAllByProductId(Integer productId);

    List<Product> findByProductIdAfter(Integer productId);

    List<Product> findByProductIdBefore(Integer productId);

    List<Product> findAllByCategoryId(Integer categoryId);

    List<Product> findAllByCategoryIdNotNullOrderByCategoryIdAsc();

    Product save(Product product);

    Integer deleteProductByProductId(Integer productId);

    //search
    List<Product> findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameAsc
        (String name, Integer categoryId, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameDesc
        (String name, Integer categoryId, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceAsc
            (String name, Integer categoryId, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceDesc
            (String name, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameAsc
            (String name, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByNameDesc
            (String name, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceAsc
            (String name, Double minPrice, Double maxPrice, Integer discount, Integer quantity);

    List<Product> findAllByNameContainingAndCategoryIdAndPriceBetweenAndDiscountIsAfterAndQuantityIsAfterOrderByPriceDesc
            (String name, Integer categoryId, Double minPrice, Double maxPrice, Integer discount, Integer quantity);
}
