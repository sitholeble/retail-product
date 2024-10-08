package rt.service.product.retailproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rt.service.product.retailproduct.api.ProductServiceApi;
import rt.service.product.retailproduct.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    private static final String DESCRIPTION = "vegetable";
    private static final String CATEGORY = "perishable";
    private static final String NAME = "cabbage";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final int INVOKE_ONCE = 1;

    @MockBean
    private ProductServiceApi serviceApi;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreateProduct_andNameIsNull_throwException() throws Exception {
        //given
        var product = ProductEntity.builder()
                .name(null)
                .description(DESCRIPTION)
                .category(CATEGORY)
                .price(PRICE)
                .build();

        var serializedProduct = mapper.writeValueAsString(product);

        var request = post("/products")
                .contentType("application/json")
                .content(serializedProduct);

        //when
        var result = mockMvc.perform(request);

        //then
        result.andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenCreateProduct_andDescriptionIsNull_throwException() throws Exception{
        var product = ProductEntity.builder()
                .name(NAME)
                .description(null)
                .category(CATEGORY)
                .price(PRICE)
                .build();

        var serializedProduct = mapper.writeValueAsString(product);

        var request = post("/products")
                .contentType("application/json")
                .content(serializedProduct);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateProduct_andCategoryIsNull_throwException() throws Exception {
        var product = ProductEntity.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .category(null)
                .price(PRICE)
                .build();

        var serializedProduct = mapper.writeValueAsString(product);

        var request = post("/products")
                .contentType("application/json")
                .content(serializedProduct);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateProduct_andPriceIsNull_throwException() throws Exception {
        var product = ProductEntity.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .category(CATEGORY)
                .price(null)
                .build();

        var serializedProduct = mapper.writeValueAsString(product);

        var request = post("/products")
                .contentType("application/json")
                .content(serializedProduct);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateProduct_andInputsAreValid_returnStatusCreated() throws Exception {
        var product = ProductEntity.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .category(CATEGORY)
                .price(PRICE)
                .build();

        var serializedProduct = mapper.writeValueAsString(product);

        var request = post("/products")
                .contentType("application/json")
                .content(serializedProduct);

        when(serviceApi.createProduct(any(ProductEntity.class)))
                .thenReturn(ProductEntity.builder().productId(UUID.randomUUID()).build());

        var result = mockMvc.perform(request);

        result.andExpect(status().isCreated()).andReturn();
    }

    @Test
    void whenGetProduct_andProductIdIsNull_throwNotFoundError() throws Exception {
        //given
        UUID productId = null;

        var request = get("/products/{product_id}/product", productId)
                .contentType("application/json");

        //when
        when(serviceApi.getProduct(productId))
                .thenReturn(null);

        var result = mockMvc.perform(request);

        //then
        result.andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void whenGetProduct_andProductIdIsValid_returnProduct() throws Exception {
        //given
        UUID productId = UUID.randomUUID();

        var product = ProductEntity.builder()
                .productId(productId)
                .build();

        var request = get("/products/{product_id}/product", productId)
                .contentType("application/json");

        //when
        when(serviceApi.getProduct(productId))
                .thenReturn(product);

        var result = mockMvc.perform(request);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andReturn();
    }

    @Test
    void whenGetAllProducts_returnListOfProducts() throws Exception {
        var request = get("/products")
                .contentType("application/json");

        when(serviceApi.getAllProducts())
                .thenReturn(List.of(mock(ProductEntity.class)));

        var response = mockMvc.perform(request);

        response.andExpect(status().isOk()).andReturn();
    }

    @Test
    void whenDeleteProduct_confirmCorrectProductDeleted() throws Exception {
        var productId = UUID.randomUUID();

        mockMvc.perform(delete("/products/{product_id}/product", productId)
                .contentType("application/json"));

        verify(serviceApi, times(INVOKE_ONCE))
                .deleteProduct(productId);
    }
}
