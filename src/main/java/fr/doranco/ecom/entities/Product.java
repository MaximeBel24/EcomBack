package fr.doranco.ecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.doranco.ecom.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

//    @Lob
//    private String description;
//
//    @Lob
//    @Column(columnDefinition = "longblob")
//    private byte[] img;

    @Lob
    @Column(columnDefinition = "TEXT") // Utilisation de TEXT pour la description dans PostgreSQL
    private String description;

    @Lob
    @Column(columnDefinition = "bytea") // Utilisation de bytea pour les images binaires dans PostgreSQL
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto(){
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setByteImg(img);
        productDto.setCategoryId(category.getId());
        return productDto;
    }
}
