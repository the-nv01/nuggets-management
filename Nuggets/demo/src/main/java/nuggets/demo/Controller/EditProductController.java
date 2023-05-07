package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EditProductController {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @GetMapping("/edit-product.html")
    public ModelAndView editProduct(@RequestParam(value = "product_id", required = true) Integer productId) {
        ModelAndView modelAndView = new ModelAndView("edit-product");

        modelAndView.addObject("product", productRepository.findAllByProductId(productId));
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());

        return modelAndView;
    }

    @PostMapping("/update-product")
    public ModelAndView updateProduct(@ModelAttribute Product product) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products.html");

        productRepository.save(product);

        return modelAndView;
    }
}
