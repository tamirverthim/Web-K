package com.earnix.webk.script.web_idl;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Responsible for defining writable and readable properties in WebIDL interface.
 * Should be used as return value of Java interface method with name matching WebIDL property name.
 *
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Attribute<T> {
    T get();

    void set(T t);

    /**
     * Todo remove and refactor to simple instantiation of {@link Attribute}
     */
    static <T> AtBuilder<T> receive(Consumer<T> consumer) {
        return new AtBuilder<T>(consumer);
    }

    class AtBuilder<T> {
        private Consumer<T> consumer;

        private AtBuilder(Consumer<T> consumer) {
            this.consumer = consumer;
        }

        public Attribute<T> give(Supplier<T> supplier) {
            return new Attribute<T>() {
                @Override
                public T get() {
                    return supplier.get();
                }

                @Override
                public void set(T o) {
                    consumer.accept(o);
                }
            };
        }

        ;
    }
}
