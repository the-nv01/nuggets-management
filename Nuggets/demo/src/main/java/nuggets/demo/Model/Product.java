package nuggets.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer productId;

    @Column
    private String name;

    @Column
    private String image;

    @Column (name = "category_id")
    private Integer categoryId;

    @Column
    private Double price;

    @Column
    private Integer discount;

    @Column
    private Integer quantity;

    @Column
    private String detail;

    private Double newPrice;

    private String categoryName;

    private Integer quantityProductCart;

    public Double getNewPrice() {
        return price * (100-discount)/100;
    }
}
