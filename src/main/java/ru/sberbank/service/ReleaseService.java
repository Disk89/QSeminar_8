package ru.sberbank.service;

import org.springframework.stereotype.Service;
import ru.sberbank.data.Release;
import ru.sberbank.data.Task;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleaseService {

    private List<Release> releases;
    private TaskService taskService;

    public ReleaseService(TaskService taskService) {
        this.releases = new ArrayList<>();
        this.taskService = taskService;
    }

    public Release create(int id, String summary){
        if(summary == null || summary.isEmpty()){
            throw new IllegalStateException("Название релиза не задано");
        }
        Release release = new Release(id, summary);
        releases.add(release);
        return release;
    }

    public Task getTaskFromRelease(int releaseId, int taskId){
        Release release = getRelease(releaseId);
        Task task = getTaskFromRelease(release, taskId);
        return task;
    }

    public void addTaskToRelease(int releaseId, int taskId){
        Release release = getRelease(releaseId);
        Task task = taskService.getTask(taskId);
        release.getTasks().add(task);
    }

    public Release getRelease(int releaseId){
        Release release = null;
        for(Release r: releases){
            if(r.getId() == releaseId){
                release = r;
            }
        }
        if(release == null){
            throw new IllegalStateException(String.format("Релиз с %s не найден", releaseId));
        }
        return release;
    }

    private Task getTaskFromRelease(Release release, int taskId){
        Task targetTask = null;
        for(Task task: release.getTasks()){
            if(task.getId() == taskId){
                targetTask = task;
            }
        }
        if(targetTask == null){
            throw new IllegalStateException(String.format("Задача(id=%s) не найдена в релизе(id=%s)", taskId, release.getId()));
        }

        return targetTask;
    }
}
