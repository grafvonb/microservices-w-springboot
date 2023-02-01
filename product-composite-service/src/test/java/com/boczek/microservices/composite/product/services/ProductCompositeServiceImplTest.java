package com.boczek.microservices.composite.product.services;

import com.boczek.microservices.api.core.product.Product;
import com.boczek.microservices.api.core.recommendation.Recommendation;
import com.boczek.microservices.api.core.review.Review;
import com.boczek.microservices.api.exceptions.InvalidInputException;
import com.boczek.microservices.api.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCompositeServiceImplTest {

    public static final int PROD_ID_OK = 1;
    public static final int PROD_ID_NOT_OK = 13;
    public static final int PROD_ID_INVALID = -1;

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private ProductCompositeIntegration integration;

    @BeforeEach
    void setUp() {
        when(integration.getProduct(PROD_ID_OK))
                .thenReturn(new Product(PROD_ID_OK, "test", 123, "mock-address"));

        when(integration.getRecommendations(PROD_ID_OK)).
                thenReturn(singletonList(new Recommendation(PROD_ID_OK, 1,
                        "author", 1, "content", "mock address")));

        when(integration.getReviews(PROD_ID_OK)).
                thenReturn(singletonList(new Review(PROD_ID_OK, 1, "author",
                        "subject", "content", "mock address")));

        when(integration.getProduct(PROD_ID_NOT_OK)).
                thenThrow(new NotFoundException("No product found for productId: " +
                        PROD_ID_NOT_OK));

        when(integration.getProduct(PROD_ID_INVALID)).
                thenThrow(new InvalidInputException("INVALID: " +
                        PROD_ID_INVALID));
    }

    @Test
    void getProductByIdShouldReturnValidProduct() {
        testClient.get()
                .uri("/product-composite/" + PROD_ID_OK)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PROD_ID_OK)
                .jsonPath("$.recommendations.length()").isEqualTo(1)
                .jsonPath("$.reviews.length()").isEqualTo(1);
    }

    @Test
    void getNonExistingProductByIdShouldReturnNotFoundStatus() {
        testClient.get()
                .uri("/product-composite/" + PROD_ID_NOT_OK)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/product-composite/" +
                        PROD_ID_NOT_OK)
                .jsonPath("$.message").isEqualTo("No product found for productId: " +
                        PROD_ID_NOT_OK);
    }

    @Test
    void getProductByWrongIdShouldReturnUnprocessableEntity() {
        testClient.get()
                .uri("/product-composite/" + PROD_ID_INVALID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/product-composite/" +
                        PROD_ID_INVALID)
                .jsonPath("$.message").isEqualTo("INVALID: " +
                        PROD_ID_INVALID);
    }
}