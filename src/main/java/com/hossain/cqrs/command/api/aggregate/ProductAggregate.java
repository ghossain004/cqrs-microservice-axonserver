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

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand) {
        ProductUpdateEvent productUpdateEvent = new ProductUpdateEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdateEvent);
        AggregateLifecycle.apply(productUpdateEvent);
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand) {
        ProductDeleteEvent productDeleteEvent = new ProductDeleteEvent();
        BeanUtils.copyProperties(deleteProductCommand, productDeleteEvent);
        AggregateLifecycle.apply(productDeleteEvent);
    }

    public ProductAggregate() {
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.name = productCreatedEvent.getName();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductUpdateEvent event){
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductDeleteEvent event){
        markDeleted();
    }
}
