package nuggets.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Category {
    @Id
    @Column(name = "id")
    private Integer categoryId;

    @Column
    private String name;

    @Column
    private String detail;
}
