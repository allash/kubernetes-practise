package com.architect.messaging;

public interface DomainEventHandler<T> {
    void process(T command);
}
