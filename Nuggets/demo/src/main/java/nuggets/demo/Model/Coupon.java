package nuggets.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Coupon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name = "coupon_id")
    private Integer couponId;

    @Column (name = "coupon_name")
    private String couponName;

    @Column (name = "coupon_code")
    private String couponCode;

    @Column
    private Integer discount;

    @Column (name = "reserve_from")
    private String reserveFrom;

    @Column (name = "reserve_to")
    private String reserveTo;

}
