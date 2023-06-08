package com.hossain.cqrs.command.api.commands;

import com.hossain.cqrs.command.api.model.ProductRestModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductCommand {

    @Autowired
    private ProductRestModel productRestModel;

    @TargetAggregateIdentifier
    private String productId;

    public DeleteProductCommand(String productId) {
        this.productId = productId;
    }

//    private final String id;
//
//    public DeleteProductCommand(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
}
