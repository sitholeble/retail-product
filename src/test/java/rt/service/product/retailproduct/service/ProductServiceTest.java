package rt.service.product.retailproduct.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rt.service.product.retailproduct.entity.ProductEntity;
import rt.service.product.retailproduct.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final String DESCRIPTION = "vegetable";
    private static final String CATEGORY = "perishable";
    private static final String NAME = "cabbage";
    private static final BigDecimal PRICE = BigDecimal.TEN;

    private static final int INVOKE_ONCE = 1;

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Test
    void whenCreateProduct_thenCallRepository() {
        var product = ProductEntity.builder()
                .build();

        service.createProduct(product);

        verify(repository, times(INVOKE_ONCE))
                .save(product);
    }

    @Test
    void whenCreateProduct_returnProductEntity() {
        var product = ProductEntity.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .category(CATEGORY)
                .price(PRICE)
                .build();

        when(repository.save(any(ProductEntity.class)))
                .thenReturn(product);

        var response = service.createProduct(product);

        assertNotNull(response);
    }

    @Test
    void whenGetProduct_returnProduct() {
        var productId = UUID.randomUUID();

        when(repository.findById(any()))
                .thenReturn(Optional.ofNullable(mock(ProductEntity.class)));

        var response = service.getProduct(productId);

        verify(repository, times(INVOKE_ONCE))
                .findById(productId);

        assertNotNull(response);
    }
}
