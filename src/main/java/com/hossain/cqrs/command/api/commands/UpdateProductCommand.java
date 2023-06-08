package com.hossain.cqrs.command.api.commands;

import com.hossain.cqrs.command.api.model.ProductRestModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {

    @Autowired
    private ProductRestModel productRestModel;

    @TargetAggregateIdentifier
    private String productId;

//    private final String id;
//    private final ProductRestModel productRestModel;
//
//    public UpdateProductCommand(String id, ProductRestModel productRestModel) {
//        this.id = id;
//        this.productRestModel = productRestModel;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public ProductRestModel getProductRestModel() {
//        return productRestModel;
//    }
}
