package com.hossain.cqrs.query.api.controller;

import com.hossain.cqrs.command.api.data.Product;
import com.hossain.cqrs.command.api.model.ProductRestModel;
import com.hossain.cqrs.query.api.projection.ProductProjection;
import com.hossain.cqrs.query.api.queries.GetProductByIdQuery;
import com.hossain.cqrs.query.api.queries.GetProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/query/product")
public class ProductQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private ProductProjection projection;

    @GetMapping
    public List<ProductRestModel> getAllProducts(){
        GetProductsQuery getProductsQuery = new GetProductsQuery();

        List<ProductRestModel> productRestModels =
        queryGateway.query(getProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
        return productRestModels;
    }

    @GetMapping("/{id}")
    public ProductRestModel getProductById(@PathVariable("id") String id) {
        GetProductByIdQuery getProductByIdQuery = new GetProductByIdQuery(id);

        ProductRestModel productRestModel = queryGateway.query(getProductByIdQuery, ResponseTypes.instanceOf(ProductRestModel.class)).join();
        return productRestModel;
    }

}
