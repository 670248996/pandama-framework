package com.pandama.top.core.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSeq {

    static <T> Seq<T> unit(T t) {
        return c -> c.accept(t);
    }

    public static void main(String[] args) {
        TestSeq.unit(1).consume(System.out::println);

        TestSeq.unit(1).map(k -> "新对象:" + k).consume(System.out::println);

        List<String> collect = Stream.of(1, 2, 3).map(k -> "新对象:" + k).collect(Collectors.toList());
        System.out.println(collect);

    }
}
