package com.architect.messaging;

public interface DomainCommandHandler<T> {
    void process(T command);
}
