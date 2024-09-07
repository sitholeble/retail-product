package rt.service.product.retailproduct.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rt.service.product.retailproduct.api.ProductServiceApi;
import rt.service.product.retailproduct.entity.ProductEntity;
import rt.service.product.retailproduct.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceApi {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity getProduct(UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
