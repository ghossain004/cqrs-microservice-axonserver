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
        System.out.println("postmapping");
//        CreateProductCommand createProductCommand =
//                CreateProductCommand.builder()
//                        .productId(UUID.randomUUID().toString())
//                        .name(productRestModel.getName())
//                        .price(productRestModel.getPrice())
//                        .quantity(productRestModel.getQuantity())
//                        .build();
//        String result = commandGateway.sendAndWait(createProductCommand);
//        return result;

        try {
            CreateProductCommand createProductCommand =
                    CreateProductCommand.builder()
                            .productId(UUID.randomUUID().toString())
                            .name(productRestModel.getName())
                            .price(productRestModel.getPrice())
                            .quantity(productRestModel.getQuantity())
                            .build();
            String result = commandGateway.sendAndWait(createProductCommand);
            return result;
        }catch (Exception e){
            throw new RuntimeException("There is some error to save the product.");
        }
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable("productId") String productId, @RequestBody ProductRestModel productRestModel) {
        System.out.println("putmapping");
//        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
//                .productId(productId)
//                .name(productRestModel.getName())
//                .price(productRestModel.getPrice())
//                .quantity(productRestModel.getQuantity())
//                .build();
//
//        commandGateway.sendAndWait(updateProductCommand);

        try {
            UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                    .productId(productId)
                    .name(productRestModel.getName())
                    .price(productRestModel.getPrice())
                    .quantity(productRestModel.getQuantity())
                    .build();
            commandGateway.sendAndWait(updateProductCommand);
        }catch (Exception e){
            throw new RuntimeException("Product didn't updated due to id not found.");
        }
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) {
        System.out.println("deletemapping");
//        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productId);
//
//        commandGateway.sendAndWait(deleteProductCommand);

        try {
            DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productId);

            commandGateway.sendAndWait(deleteProductCommand);
        }catch (Exception e){
            throw new RuntimeException("No match found for delete.");
        }
    }
}
