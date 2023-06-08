package com.hossain.cqrs.command.api.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        throw exception;
        // Handle the exception, e.g., log the error
        // You can use a logging framework like SLF4J to log the exception details
        // Example: logger.error("Error occurred while handling event: " + event.getPayload(), exception);

        // You can choose to perform additional error handling or recovery logic here
    }
}