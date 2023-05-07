package nuggets.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product {
    @Id
    @Column (name = "id")
    private Integer productId;

    @Column
    private String name;

    @Column
    private String image;

    @Column (name = "category_id")
    private Integer categoryId;

    @Column
    private String detail;

    @Column
    private Double price;

    @Column
    private Integer discount;

    private Double newPrice;

    private String categoryName;

    public Double getNewPrice() {
        return price * (100-discount)/100;
    }
}
