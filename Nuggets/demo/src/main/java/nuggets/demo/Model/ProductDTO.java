package nuggets.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private Integer productId;

    private String name;

    private String image;

    private Integer categoryId;

    private String detail;

    private Double price;

    private Integer discount;

    private List<Integer> selectedProduct = new ArrayList<>();

}
