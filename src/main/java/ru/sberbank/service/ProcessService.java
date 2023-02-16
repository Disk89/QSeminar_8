package ru.sberbank.service;

import org.springframework.stereotype.Service;
import ru.sberbank.data.*;

import java.util.List;

@Service
public class ProcessService {

    private final TaskService taskService;
    private final DeveloperService developerService;
    private final TesterService testerService;
    private final ReleaseService releaseService;

    public ProcessService(
            TaskService taskService,
            DeveloperService developerService,
            TesterService testerService,
            ReleaseService releaseService) {
        this.taskService = taskService;
        this.developerService = developerService;
        this.testerService = testerService;
        this.releaseService = releaseService;
    }

    public int createTask(String summary) throws IllegalStateException {
        int id = taskService.getTasks().size();
        Task createdTask = taskService.createTask(id, summary);
        return createdTask.getId();
    }

    public Task pushStatusTask(int id){
        Task task = taskService.getTask(id);
        if(task.isDeveloped() && task.isTested()){
            throw new IllegalStateException("Задача уже в финальном статусе!");
        }

        if(!task.isDeveloped()){
            List<Developer> developers = developerService.getListOfFree();
            if(developers.isEmpty()){
                throw new IllegalStateException("Нет свободных разработчиков!");
            }
            Developer developer = developers.get(0);
            developer.addTask(task);
            task = developer.makeTask();
        }

        if(!task.isTested()){
            List<Tester> testers = testerService.getListOfFree();
            if(testers.isEmpty()){
                throw new IllegalStateException("Нет свободных разработчиков!");
            }
            Tester tester = testers.get(0);
            tester.addTask(task);
            task = tester.checkTask();
        }

        return task;
    }

    public Release pushStatusRelease(int releaseId){
        Release release = releaseService.getRelease(releaseId);

        if(release.getStatus() == Status.CREATED){
            release.setStatus(Status.IN_PROGRESS);
        }

        if(release.getStatus() == Status.IN_PROGRESS){
            for(Task task: release.getTasks()){
                if(!task.isDeveloped()){
                    throw new IllegalStateException(
                            "Нельзя перевести релиз в статус Тестируемый, т.к. не все задачи разработаны"
                    );
                }
            }
            release.setStatus(Status.IN_TESTING);
        }

        if(release.getStatus() == Status.IN_TESTING){
            release.setStatus(Status.DEPLOYED);
        }

        return release;
    }
}
