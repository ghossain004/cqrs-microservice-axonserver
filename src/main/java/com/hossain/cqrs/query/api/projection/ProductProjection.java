package com.hossain.cqrs.query.api.projection;

import com.hossain.cqrs.command.api.data.Product;
import com.hossain.cqrs.command.api.data.ProductRepository;
import com.hossain.cqrs.command.api.model.ProductRestModel;
import com.hossain.cqrs.query.api.queries.GetProductByIdQuery;
import com.hossain.cqrs.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    @Autowired
    private ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery){
        List<Product> products = productRepository.findAll();

        List<ProductRestModel> productRestModels =
                products.stream()
                        .map(product -> ProductRestModel
                                .builder()
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .name(product.getName())
                                .build())
                        .collect(Collectors.toList());
        return productRestModels;
    }

    @QueryHandler
    public ProductRestModel handle(GetProductByIdQuery getProductByIdQuery) {
        Product product = productRepository.findById(getProductByIdQuery.getId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        return ProductRestModel.builder()
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }
}
