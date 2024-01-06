package com.ku.quizzical.common.util.function;

public interface QuadriConsumer<T, U, V, W> {
    public void accept(T item1, U item2, V item3, W item4);
}
