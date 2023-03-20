package com.ria.process.api.config;

public interface DataConverter<I, O> {

    O convert(I input);
}
