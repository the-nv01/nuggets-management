package nuggets.demo.Repository;

import nuggets.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllBy();

    List<Product> findByProductIdAfter(Integer productId);

    List<Product> findByProductIdBefore(Integer productId);
}
