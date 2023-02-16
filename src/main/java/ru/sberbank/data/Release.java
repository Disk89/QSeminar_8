package ru.sberbank.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Release {

    private final int id;
    private final String summary;
    private List<Task> tasks;

    private Status status;

    public Release(int id, String summary) {
        this.id = id;
        this.summary = summary;
        this.tasks = new ArrayList<>();
        this.status = Status.CREATED;
    }

    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Status getStatus() {
        return status;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Release release = (Release) o;
        return id == release.id && Objects.equals(summary, release.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, summary);
    }
}
