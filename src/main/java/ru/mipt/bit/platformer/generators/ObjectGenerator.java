package ru.mipt.bit.platformer.generators;

import java.util.Collection;

public interface ObjectGenerator<T> {
    Collection<? super T> generate(int n, Collection<? super T> destination);

    T generate();
}
