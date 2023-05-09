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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
        // default session
        sessionOperatorDetails.setForm("account", userRepository.getUserByUsername("the"));
        sessionOperatorDetails.setForm("memberWishlist", wishListRepository.findAllByUsername("the"));
        sessionOperatorDetails.setForm("memberCarts", cartRepository.findAllByUsername("the"));

        User account = sessionOperatorDetails.getForm("account", User.class);
        List<Cart> carts = cartRepository.findAllByUsername(account.getUsername());

        return carts.stream().map(cart -> {
            Product product = productRepository.findAllByProductId(cart.getProductId());
            product.setQuantityProductCart(cart.getQuantity());

            return product;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer deleteMemberProduct(Integer productId) {
        User account = sessionOperatorDetails.getForm("account", User.class);

        return cartRepository.deleteCartByUsernameAndProductId(account.getUsername(), productId);
    }

    @Override
    public void updateCart(CartDTO cartDTO) {
        User account = sessionOperatorDetails.getForm("account", User.class);

        for (int i = 0; i<cartDTO.getProductIds().size(); i++) {
            Cart cart = cartRepository.findCartByUsernameAndProductId(account.getUsername(), cartDTO.getProductIds().get(i));
            cart.setQuantity(cartDTO.getQuantities().get(i));
            cartRepository.save(cart);
        }
    }
}
