package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Category;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.ProductDTO;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AdminController {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @GetMapping("/products.html")
    public ModelAndView products() {
        ModelAndView modelAndView = new ModelAndView("products");

        List<Category> categories = categoryRepository.findAllByOrderByCategoryIdAsc();

        List<Product> products = productRepository.findAllByCategoryIdNotNullOrderByCategoryIdAsc()
                .stream().peek(product -> product.setCategoryName(categoryRepository.findByCategoryId(product.getCategoryId()).getName())).collect(Collectors.toList());

        modelAndView.addObject("products", products);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", new ProductDTO());
        return modelAndView;
    }

    @GetMapping("/delete-product")
    public ModelAndView deleteProduct(@RequestParam (value = "product_id") Integer productId) {
        productRepository.deleteProductByProductId(productId);
        return new ModelAndView("redirect:/products.html");
    }

    @PostMapping("/delete-products")
    public ModelAndView deleteProducts(@ModelAttribute ProductDTO request) {
        request.getSelectedProduct().stream()
                .map(productRepository::deleteProductByProductId);
        return new ModelAndView("redirect:/products.html");
    }
}
