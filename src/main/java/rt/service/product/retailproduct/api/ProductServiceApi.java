package rt.service.product.retailproduct.api;

import rt.service.product.retailproduct.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductServiceApi {

    ProductEntity createProduct(ProductEntity product);
    ProductEntity getProduct(UUID productId);
    List<ProductEntity> getAllProducts();
    void deleteProduct(UUID productId);
}
