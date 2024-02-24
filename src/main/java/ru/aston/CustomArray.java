package ru.aston;

import java.util.Collection;
import java.util.Comparator;

/**
 * The {@code CustomList} interface represents a custom list that allows
 * manipulation of elements at specified positions, addition and removal
 * of elements, and sorting.
 *
 * @param <E> the type of elements in the list
 */
public interface CustomArray<E> {

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param index   the index at which the specified element is to be inserted
     * @param element the element to be inserted
     */
    void add(int index, E element);

    /**
     * Adds all the elements in the specified collection to this list.
     *
     * @param c the collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Removes all the elements from this list.
     */
    void clear();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     */
    E get(int index);

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    boolean isEmpty();

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
    E remove(int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * @param o the element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    boolean remove(Object o);

    /**
     * Sorts this list according to the order induced by the specified comparator.
     *
     * @param c the comparator to determine the order of this list
     */
    void sort(Comparator<? super E> c);
}

