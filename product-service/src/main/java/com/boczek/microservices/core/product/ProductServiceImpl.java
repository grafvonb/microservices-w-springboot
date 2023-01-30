package com.boczek.microservices.core.product;

import com.boczek.microservices.api.core.product.Product;
import com.boczek.microservices.api.core.product.ProductService;
import com.boczek.microservices.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    public Product getProduct(int productId) {
        return new Product(productId, "name-" + productId, 123,  serviceUtil.getServiceAddress());
    }
}
