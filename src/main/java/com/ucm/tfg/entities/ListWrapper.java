package com.ucm.tfg.entities;

import java.util.ArrayList;
import java.util.List;

public class ListWrapper<T> {

    private List<T> list;

    public ListWrapper() {
        list = new ArrayList<T>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
