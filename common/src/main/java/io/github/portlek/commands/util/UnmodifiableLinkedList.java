/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.portlek.commands.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class UnmodifiableLinkedList<T> extends LinkedList<T> {

    private boolean finished = false;

    public UnmodifiableLinkedList(@NotNull final Collection<? extends T> c) {
        super(c);
        this.finished = true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeAll(c);
    }

    @Override
    public boolean removeIf(final Predicate<? super T> filter) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeIf(filter);
    }

    @Override
    public T removeFirst() {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeFirst();
    }

    @Override
    public T removeLast() {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeLast();
    }

    @Override
    public void addFirst(final T t) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        super.addFirst(t);
    }

    @Override
    public void addLast(final T t) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        super.addLast(t);
    }

    @Override
    public boolean add(final T t) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.add(t);
    }

    @Override
    public boolean remove(final Object o) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.remove(o);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> c) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.addAll(index, c);
    }

    @Override
    public void add(final int index, final T element) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        super.add(index, element);
    }

    @Override
    public T remove(final int index) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.remove(index);
    }

    @Override
    public T remove() {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.remove();
    }

    @Override
    public boolean removeFirstOccurrence(final Object o) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(final Object o) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        return super.removeLastOccurrence(o);
    }

    @Override
    protected void removeRange(final int fromIndex, final int toIndex) {
        if (this.finished) {
            throw new UnsupportedOperationException("You can't edit the list that's unmodifiable!");
        }
        super.removeRange(fromIndex, toIndex);
    }

}
