package nuggets.demo.Repository;

import nuggets.demo.Model.Category;
import nuggets.demo.Model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAllByOrderByCategoryIdAsc();

    Category findByCategoryId(Integer id);
}
