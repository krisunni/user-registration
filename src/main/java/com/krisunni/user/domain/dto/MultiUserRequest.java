package com.krisunni.user.domain.dto;

import java.util.List;

public class MultiUserRequest {
    private List<String> columns;
    private int size;
    private int page;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MultiUserRequest)) {
            return false;
        }
        return columns != null && columns.equals(((MultiUserRequest) o).columns)
                && size == ((MultiUserRequest) o).size
                && page == ((MultiUserRequest) o).page;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MultiUserRequest{" +
                "columns=" + getColumns().toString() +
                ", size='" + getSize() + "'" +
                ", page='" + getPage() + "'" +
                "}";
    }
}
