package com.hossain.cqrs.command.api.exception;

import com.hossain.cqrs.command.api.events.ProductCreatedEvent;
import com.hossain.cqrs.command.api.events.ProductDeleteEvent;
import com.hossain.cqrs.command.api.events.ProductUpdateEvent;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
//    @Override
//    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
//        throw exception;
//    }

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        if (exception instanceof RuntimeException) {
            handleRuntimeException((RuntimeException) exception, event, eventHandler);
        } else {
            // Handle other types of exceptions as needed
            // For example, log the exception or perform specific error handling
            throw exception;
        }
    }

    private void handleRuntimeException(RuntimeException exception, EventMessage<?> event, EventMessageHandler eventHandler) {
        // Perform specific error handling for RuntimeException
        if (event instanceof ProductCreatedEvent) {
            handleProductCreatedError((ProductCreatedEvent) event, eventHandler, exception);
        } else if (event instanceof ProductUpdateEvent) {
            handleProductUpdateError((ProductUpdateEvent) event, eventHandler, exception);
        } else if (event instanceof ProductDeleteEvent) {
            handleProductDeleteError((ProductDeleteEvent) event, eventHandler, exception);
        } else {
            // Handle other events or throw the exception
            throw exception;
        }
    }

    private void handleProductCreatedError(ProductCreatedEvent event, EventMessageHandler eventHandler, RuntimeException exception) {
        // Handle the error for ProductCreatedEvent
        // For example, log the error or perform specific recovery actions
        System.out.println("Error occurred while handling ProductCreatedEvent: " + exception.getMessage());
    }

    private void handleProductUpdateError(ProductUpdateEvent event, EventMessageHandler eventHandler, RuntimeException exception) {
        // Handle the error for ProductUpdateEvent
        // For example, log the error or perform specific recovery actions
        System.out.println("Error occurred while handling ProductUpdateEvent: " + exception.getMessage());
    }

    private void handleProductDeleteError(ProductDeleteEvent event, EventMessageHandler eventHandler, RuntimeException exception) {
        // Handle the error for ProductDeleteEvent
        // For example, log the error or perform specific recovery actions
        System.out.println("Error occurred while handling ProductDeleteEvent: " + exception.getMessage());
    }
}