package org.xhtmlrenderer.js.web_idl;

import org.jsoup.nodes.Node;
import org.xhtmlrenderer.js.impl.DOMStringImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface Attribute<T> {
    T get();
    void set(T t);
    
    static <T> AtBuilder<T> receive(Consumer<T> consumer){
        return new AtBuilder<T>(consumer);
    }
    
    static <T> AtBuilder<T> readOnly(){
        return new AtBuilder<T>(null);
    }
        
    static <T> Attribute<T> readOnly(T value){
        return new Attribute<T>() {
            @Override
            public T get() {
                return value;
            }

            @Override
            public void set(T t) {
            }
        };
    }

    static Attribute<DOMString> forNode(Node node, String attributeName){
        return Attribute.<DOMString>receive((domString) -> node.attr(attributeName)).give(() -> DOMStringImpl.of(node.attr(attributeName)));
    }
    
    class AtBuilder<T> {
        private Consumer<T> consumer;

        private AtBuilder(Consumer<T> consumer) {
            this.consumer = consumer;
        }

        public Attribute<T> give(Supplier<T> supplier){
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
        };
    }
}
