package nuggets.demo.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.Cart;
import nuggets.demo.Model.CartDTO;
import nuggets.demo.Model.Product;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.CartRepository;
import nuggets.demo.Repository.ProductRepository;
import nuggets.demo.Repository.UserRepository;
import nuggets.demo.Repository.WishListRepository;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final WishListRepository wishListRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final SessionOperatorDetails sessionOperatorDetails;

    @Override
    public List<Product> getProductsByUser() {
        User user = new User();
        if (sessionOperatorDetails.existsForm("account")) {
            user = sessionOperatorDetails.getForm("account", User.class);
        }
        List<Cart> carts = cartRepository.findAllByUsernameOrderByDateDesc(user.getUsername());
        if (ObjectUtils.isEmpty(carts)) return new ArrayList<>();

        return carts.stream().map(cart -> {
            Product product = productRepository.findAllByProductId(cart.getProductId());
            product.setQuantityProductCart(cart.getQuantity());

            return product;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer deleteMemberProduct(Integer productId) {
        User user = sessionOperatorDetails.getForm("account", User.class);

        return cartRepository.deleteCartByUsernameAndProductId(user.getUsername(), productId);
    }

    @Override
    public void updateCart(CartDTO cartDTO) {
        User user = sessionOperatorDetails.getForm("account", User.class);

        for (int i = 0; i<cartDTO.getProductIds().size(); i++) {
            if (!Objects.equals(cartDTO.getProductIds().get(i), null)) {
                Cart cart = cartRepository.findCartByUsernameAndProductIdOrderByDateDesc(user.getUsername(), cartDTO.getProductIds().get(i));
                cart.setQuantity(cartDTO.getQuantities().get(i));
                cartRepository.save(cart);
            }
        }
    }
}
