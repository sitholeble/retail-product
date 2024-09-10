package rt.service.product.retailproduct.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rt.service.product.retailproduct.api.ProductServiceApi;
import rt.service.product.retailproduct.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value = "/products")
@RestController
@Validated
public class ProductController {

    private final ProductServiceApi serviceApi;

    public ProductController(ProductServiceApi service) {
        this.serviceApi = service;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UUID> createProduct(@RequestBody @Valid ProductEntity product) {
        serviceApi.createProduct(product);

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(product.getProductId());
    }

    @GetMapping(value = "/{product_id}/product")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable("product_id") UUID productId) {

        var product =
                Optional.ofNullable(serviceApi.getProduct(productId));

        return product.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                );
    }

    @GetMapping()
    List<ProductEntity> getAllProducts() {
        return serviceApi.getAllProducts();
    }

    @DeleteMapping(value = "/{product_id}/product")
    void deleteProduct(@PathVariable("product_id") UUID productId) {
        serviceApi.deleteProduct(productId);
    }
}
