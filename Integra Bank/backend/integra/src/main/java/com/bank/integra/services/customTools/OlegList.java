package com.bank.integra.services.customTools;

import javax.management.ObjectName;
import java.util.*;

public class OlegList implements List {
    private Object array[];
    private int size = 0;

    public OlegList() {
        array = new Object[2];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return new Iterator<Object>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[currentIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        try{
            if(array.length <= size+1) {
                Object tempArray[] = new Object[(int)(array.length*1.5)];
                for (int i = 0; i < array.length; i++) {
                    tempArray[i] = array[i];
                }
                array = tempArray.clone();
            }
            array[size] = o;
            size++;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            int indexToRemove = -1;
            for (int i = 0; i < array.length; i++) {
                if (array[i] == o) {
                    indexToRemove = i;
                    break;
                }
            }
            Object[] firstArr = Arrays.copyOfRange(array, 0, indexToRemove);
            Object[] secondArr = Arrays.copyOfRange(array, indexToRemove + 1, array.length);

            Object[] newArray = new Object[firstArr.length + secondArr.length];
            System.arraycopy(firstArr, 0, newArray, 0, firstArr.length);
            System.arraycopy(secondArr, 0, newArray, firstArr.length, secondArr.length);
            array = newArray.clone();
            size--;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
        array = new Object[2];
    }

    @Override
    public Object get(int index) {
        if(array[index] == null) throw new NullPointerException("That's cringe.");
        return array[index];
    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {
        if(array.length <= size+1) {
            Object tempArray[] = new Object[(int)(array.length*1.5)];
            for (int i = 0; i < array.length; i++) {
                tempArray[i] = array[i];
            }
            array = tempArray;
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public Object remove(int index) {
        Object o = array[index];
        try {
            Object[] firstArr = Arrays.copyOfRange(array, 0, index);
            Object[] secondArr = Arrays.copyOfRange(array, index + 1, array.length);

            Object[] newArray = new Object[firstArr.length + secondArr.length];
            System.arraycopy(firstArr, 0, newArray, 0, firstArr.length);
            System.arraycopy(secondArr, 0, newArray, firstArr.length, secondArr.length);
            array = newArray.clone();
            size--;
        } catch (Exception e) {
            return false;
        }
        return o;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == o) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
