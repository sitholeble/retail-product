package rt.service.product.retailproduct.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rt.service.product.retailproduct.api.ProductServiceApi;
import rt.service.product.retailproduct.entity.ProductEntity;

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
}
