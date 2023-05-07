package nuggets.demo.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Service.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAllBy();
    }

    public List<Product> getLargeProducts() {
        Integer largeProduct = 14;
        return productRepository.findByProductIdBefore(largeProduct);
    }

    public List<Product> getSmallProducts() {
        Integer smallProduct = 13;
        return productRepository.findByProductIdAfter(smallProduct);
    }

}
