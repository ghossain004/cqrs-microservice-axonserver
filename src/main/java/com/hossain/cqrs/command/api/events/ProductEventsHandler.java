package com.hossain.cqrs.command.api.events;

import com.hossain.cqrs.command.api.data.Product;
import com.hossain.cqrs.command.api.data.ProductRepository;
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
        System.out.println("create eventhandler");
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);

//        try {
//            Product product = new Product();
//            BeanUtils.copyProperties(event, product);
//            productRepository.save(product);
//        }catch (Exception exception){
//            throw new RuntimeException("There is some error to save the product.");
//        }

    }

    @EventHandler
    public void on(ProductUpdateEvent event) {
        System.out.println("update eventhandler");

        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);

//        try {
//            Product product = new Product();
//            BeanUtils.copyProperties(event, product);
//            productRepository.save(product);
//        }catch (Exception exception){
//            throw new RuntimeException("Product didn't updated due to id not found.");
//        }
    }

    @EventHandler
    public void on(ProductDeleteEvent event) {
        System.out.println("delete eventhandler");
//        String productId = event.getProductId();
//        productRepository.deleteById(productId);

        try {
            String productId = event.getProductId();
            productRepository.deleteById(productId);
        }catch (Exception exception){
            throw new RuntimeException("No match found for delete.");
        }

    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw new RuntimeException("There is some error");
    }
}
