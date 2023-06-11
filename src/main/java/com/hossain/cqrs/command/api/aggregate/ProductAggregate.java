package com.hossain.cqrs.command.api.aggregate;

import com.hossain.cqrs.command.api.commands.CreateProductCommand;
import com.hossain.cqrs.command.api.commands.DeleteProductCommand;
import com.hossain.cqrs.command.api.commands.UpdateProductCommand;
import com.hossain.cqrs.command.api.events.ProductCreatedEvent;
import com.hossain.cqrs.command.api.events.ProductDeleteEvent;
import com.hossain.cqrs.command.api.events.ProductUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static net.bytebuddy.implementation.attribute.AnnotationAppender.Default.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        System.out.println("create command");
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        System.out.println("create eventsource");
        this.name = productCreatedEvent.getName();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand) {
        System.out.println("update command");
        ProductUpdateEvent productUpdateEvent = new ProductUpdateEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdateEvent);
        AggregateLifecycle.apply(productUpdateEvent);
    }

    @EventSourcingHandler
    public void on(ProductUpdateEvent event){
        System.out.println("update eventsource");
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand) {
        System.out.println("delete command");
        ProductDeleteEvent productDeleteEvent = new ProductDeleteEvent();
        BeanUtils.copyProperties(deleteProductCommand, productDeleteEvent);
        AggregateLifecycle.apply(productDeleteEvent);
    }

    @EventSourcingHandler
    public void on(ProductDeleteEvent event){
        System.out.println("delete eventsource");
        markDeleted();
    }
}
