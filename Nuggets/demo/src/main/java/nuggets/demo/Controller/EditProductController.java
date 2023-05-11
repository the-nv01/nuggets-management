package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CategoryRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EditProductController {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SessionOperatorDetails sessionOperatorDetails;


    @GetMapping("/add-product.html")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("add-product");
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        if (!Objects.equals(user.getRole(), "admin")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());
        return modelAndView;
    }

    @GetMapping("/edit-product.html")
    public ModelAndView editProduct(@RequestParam(value = "product_id", required = true) Integer productId) {
        ModelAndView modelAndView = new ModelAndView("edit-product");
        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        if (!Objects.equals(user.getRole(), "admin")) {
            return new ModelAndView("redirect:/index-2.html");
        }

        modelAndView.addObject("product", productRepository.findAllByProductId(productId));
        modelAndView.addObject("categories", categoryRepository.findAllByOrderByCategoryIdAsc());

        return modelAndView;
    }

    @PostMapping("/update-product")
    @Transactional
    public ModelAndView updateProduct(@ModelAttribute Product product) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products.html");

        productRepository.save(product);

        return modelAndView;
    }
}
