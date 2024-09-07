package rt.service.product.retailproduct.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Table("products")
public class ProductEntity {

    @Id
    private UUID productId;

    private @NotNull String name;
    private @NotNull String description;
    private @NotNull String category;
    private @NotNull BigDecimal price;
}
