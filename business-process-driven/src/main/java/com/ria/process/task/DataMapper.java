package com.ria.process.task;

import com.ria.process.ProcessRequest;

/**
 * 
 * @param <T>
 */
public abstract class DataMapper<T> {
    public abstract T extract(ProcessRequest request);
}
