package com.pandama.top.core.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Seq<T> {
    void consume(Consumer<T> consumer);

    default <E> Seq<E> map(Function<T, E> function) {
        return c -> consume(t -> c.accept(function.apply(t)));
    }

    default <E> Seq<E> flatMap(Function<T, Seq<E>> function) {
        return c -> consume(t -> function.apply(t).consume(c));
    }
}
