package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Model.OrderMember;
import nuggets.demo.Model.User;
import nuggets.demo.Repository.*;
import nuggets.demo.Service.CartService;
import nuggets.demo.Session.SessionOperatorDetails;
import nuggets.demo.VNPay.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("")
public class PaymentController {
    @Autowired
    OrderMemberRepository orderMemberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    SessionOperatorDetails sessionOperatorDetails;

    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";

    public PaymentController() {
    }

    @GetMapping("/create-payment")
    @Transactional
    public ModelAndView createPayment(@ModelAttribute OrderMember orderMember) throws UnsupportedEncodingException {

        if (!sessionOperatorDetails.existsForm("account")) {
            return new ModelAndView("redirect:/index-2.html");
        }
        User user = sessionOperatorDetails.getForm("account", User.class);
        if (!orderMember.getIsNotMemberAddress()) {
            User newUser = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .firstName(orderMember.getFirstName())
                    .lastName(orderMember.getLastName())
                    .email(orderMember.getEmail())
                    .role(user.getRole())
                    .country(orderMember.getCountry())
                    .company(orderMember.getCompany())
                    .address(orderMember.getAddress())
                    .apartment(orderMember.getApartment())
                    .city(orderMember.getCity())
                    .state(orderMember.getState())
                    .phone(orderMember.getPhone())
                    .build();
            userRepository.save(newUser);
        }
        cartRepository.deleteCartByUsername(user.getUsername());
        orderMember.setOrderStatus(0);
        orderMemberRepository.save(orderMember);

        long amount = (long) (orderMember.getTotal() * 100 * 20000);

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = "redirect:" + Config.vnp_PayUrl + "?" + queryUrl;

        return new ModelAndView(paymentUrl);
    }

}
