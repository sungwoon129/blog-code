package com.blog.composit;

import java.util.ArrayList;

public class Directory extends Entry {
    private final String name;
    private final ArrayList<Object> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSize() {
        int size = 0;
        for (Object o : directory) {
            Entry entry = (Entry) o;
            size += entry.getSize();
        }
        return size;
    }

    @Override
    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Object o : directory) {
            Entry entry = (Entry) o;
            entry.printList(prefix + "/" + name);
        }
    }
}
