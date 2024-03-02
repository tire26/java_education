package ru.aston.hw1;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.RandomAccess;

public class CustomArrayImpl<T> implements CustomArray<T>, RandomAccess, Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 6132455678419482762L;

    private static final int DEFAULT_SIZE = 10;

    private static final Object[] EMPTY_ARRAY = {};
    private int currentElementsCount;

    private Object[] array;

    public CustomArrayImpl(int size) {
        if (size > 0) {
            this.array = new Object[size];
        } else if (size == 0) {
            this.array = EMPTY_ARRAY;
        } else {
            throw new IllegalArgumentException("start size of ArrayList should be greater or equals 0");
        }
    }

    public CustomArrayImpl() {
        this(DEFAULT_SIZE);
    }

    public CustomArrayImpl(Collection<T> tCollection) {
        if (tCollection != null) {
            Object[] collectionArray = tCollection.toArray();
            if (collectionArray.length != 0) {
                currentElementsCount = collectionArray.length;
                array = collectionArray;
            } else {
                array = EMPTY_ARRAY;
            }
        } else {
            throw new IllegalArgumentException("collection pointer is null");
        }
    }

    @Override
    public void add(int index, T element) {
        checkIndexForAdd(index);
        if (index == currentElementsCount) {
            addElementInTheEnd(element);
        } else {
            addElementInCurrentPos(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c != null) {
            for (T t : c) {
                addElementInTheEnd(t);
            }
            return true;
        } else {
            throw new IllegalArgumentException("collection pointer is null");
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < currentElementsCount; i++) {
            array[i] = null;
        }
        currentElementsCount = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) array[index];
    }

    @Override
    public boolean isEmpty() {
        return currentElementsCount == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        Object removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, currentElementsCount - index);
        currentElementsCount--;
        return (T) removedElement;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < currentElementsCount; i++) {
            Object currentElement = array[i];
            if ((currentElement == o && o == null) || (currentElement != null && currentElement.equals(o))) {
                System.arraycopy(array, i + 1, array, i, currentElementsCount - i);
                currentElementsCount--;
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> c) {
        quickSort((T[]) array, 0, currentElementsCount - 1, c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CustomArrayImpl<T> clone() {
        try {
            CustomArrayImpl<T> clone = (CustomArrayImpl<T>) super.clone();
            if (this.array != null) {
                clone.array = Arrays.copyOf(this.array, this.array.length);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void quickSort(T[] arr, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high, comparator);

            quickSort(arr, low, pi - 1, comparator);
            quickSort(arr, pi + 1, high, comparator);
        }
    }

    private int partition(T[] arr, int low, int high, Comparator<? super T> comparator) {
        int middle = low + (high - low) / 2;
        T pivot = arr[middle];
        swap(arr, middle, high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void checkIndex(int index) {
        if (index >= currentElementsCount || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void checkIndexForAdd(int index) {
        if (index > currentElementsCount || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + currentElementsCount;
    }

    private void addElementInTheEnd(T element) {

        if (currentElementsCount + 1 >= array.length) {
            Object[] bufferArray = new Object[array.length * 2];
            System.arraycopy(array, 0, bufferArray, 0, currentElementsCount);
            array = bufferArray;
        }
        array[currentElementsCount] = element;
        currentElementsCount++;
    }

    private void addElementInCurrentPos(int index, T element) {

        Object[] bufferArray;
        if (currentElementsCount + 1 >= array.length) {
            bufferArray = new Object[array.length * 2];
        } else {
            bufferArray = new Object[array.length];
        }

        System.arraycopy(array, 0, bufferArray, 0, index);
        System.arraycopy(array, index, bufferArray, index + 1, currentElementsCount - index);
        array = bufferArray;

        array[index] = element;
        currentElementsCount++;
    }
}
