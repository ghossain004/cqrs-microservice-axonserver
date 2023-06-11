package com.hossain.cqrs.command.api.controller;

import com.hossain.cqrs.command.api.commands.CreateProductCommand;
import com.hossain.cqrs.command.api.commands.DeleteProductCommand;
import com.hossain.cqrs.command.api.commands.UpdateProductCommand;
import com.hossain.cqrs.command.api.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/command/product")
public class ProductCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel){
        CreateProductCommand createProductCommand =
                CreateProductCommand.builder()
                        .productId(UUID.randomUUID().toString())
                        .name(productRestModel.getName())
                        .price(productRestModel.getPrice())
                        .quantity(productRestModel.getQuantity())
                        .build();
        String result = commandGateway.sendAndWait(createProductCommand);
        return result;
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable("productId") String productId, @RequestBody ProductRestModel productRestModel) {
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(productId)
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();

        commandGateway.sendAndWait(updateProductCommand);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) {
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productId);

        commandGateway.sendAndWait(deleteProductCommand);
    }
}
