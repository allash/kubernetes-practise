package com.architect.messaging;

public interface DomainCommand<T> {
    void process(T command);
}
