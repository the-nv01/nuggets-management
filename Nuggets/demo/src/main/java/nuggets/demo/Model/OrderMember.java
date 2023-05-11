package nuggets.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class  OrderMember{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Integer orderId;

    @Column
    private Integer cartId;

    @Column
    private String username;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String country;

    @Column
    private String company;

    @Column
    private String address;

    @Column
    private String apartment;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private Integer phone;

    @Column
    private String note;

    @Column
    private String date;

    @Column
    private Double subtotal;

    @Column
    private Double discount;

    @Column
    private Double total;

    @Column
    private Integer orderStatus;

    private Boolean isNotMemberAddress = false;
}
