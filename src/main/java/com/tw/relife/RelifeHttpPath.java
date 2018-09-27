package com.tw.relife;

import java.util.Objects;

public class RelifeHttpPath {
    private String path;
    private RelifeMethod method;

    public RelifeHttpPath(String path, RelifeMethod method) {
        this.path = path;
        this.method = method;
    }

    public RelifeHttpPath() {
    }

    public String getPath() {
        return path;
    }

    public RelifeHttpPath setPath(String path) {
        this.path = path;
        return this;
    }

    public RelifeMethod getMethod() {
        return method;
    }

    public RelifeHttpPath setMethod(RelifeMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        RelifeHttpPath that = (RelifeHttpPath) o;
        return Objects.equals(path, that.path) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
