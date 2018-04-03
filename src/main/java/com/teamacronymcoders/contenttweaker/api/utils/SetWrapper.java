package com.teamacronymcoders.contenttweaker.api.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Wraps around a Set and forwards all calls to the delegate.
 * Can methods can be overwritten to perform special actions upon method calls
 */

public class SetWrapper<E> implements Set<E> {

    private final Set<E> delegate;

    public SetWrapper(Set<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return delegate.removeIf(filter);
    }

    @Override
    public Stream stream() {
        return delegate.stream();
    }

    @Override
    public Stream parallelStream() {
        return delegate.parallelStream();
    }

    @Override
    public void forEach(Consumer action) {
        delegate.forEach(action);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public boolean add(E o) {
        return delegate.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return delegate.addAll(c);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Spliterator spliterator() {
        return delegate.spliterator();
    }

    @Override
    public boolean removeAll(Collection c) {
        return delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return delegate.retainAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return delegate.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return delegate.toArray(a);
    }
}
