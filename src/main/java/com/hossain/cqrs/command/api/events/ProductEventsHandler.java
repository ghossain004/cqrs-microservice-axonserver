package com.hossain.cqrs.command.api.events;

import com.hossain.cqrs.command.api.commands.DeleteProductCommand;
import com.hossain.cqrs.command.api.commands.UpdateProductCommand;
import com.hossain.cqrs.command.api.data.Product;
import com.hossain.cqrs.command.api.data.ProductRepository;
import com.hossain.cqrs.command.api.model.ProductRestModel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("products")
public class ProductEventsHandler {

    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);

        throw new RuntimeException("There is some error to save the product.");
    }

    @CommandHandler
    public void handle(UpdateProductCommand command) {
        String id = command.getProductId();
        ProductRestModel productRestModel = command.getProductRestModel();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Update information can't be done with the id " + id + "."));

        product.setName(productRestModel.getName());
        product.setPrice(productRestModel.getPrice());
        product.setQuantity(productRestModel.getQuantity());

        productRepository.save(product);
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        String id = command.getProductId();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delete user with id " + id + " failed."));

        productRepository.delete(product);
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
