package com.hossain.cqrs.query.api.queries;

public class GetProductByIdQuery {

    private final String id;

    public GetProductByIdQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
