package Kosov.MAI.Kursovaya.services;

import Kosov.MAI.Kursovaya.models.Task;
import Kosov.MAI.Kursovaya.repositories.TasksRepository;
import Kosov.MAI.Kursovaya.util.GeneticalAlgorithm;
import Kosov.MAI.Kursovaya.util.Result;
import Kosov.MAI.Kursovaya.util.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TasksService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> getTasks() {
        return tasksRepository.findAll();
    }

    public Task getTask(int id) {
        return tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public void deleteTask(int id) {
        tasksRepository.deleteById(id);
    }

    @Transactional
    public void saveTask(Task task) {
        GeneticalAlgorithm geneticalAlgorithm = new GeneticalAlgorithm(task.getPopulation(), task.getIter(),
                task.getSurvived(), task.getMutated(), task.getCities());
        Result result = geneticalAlgorithm.solve();
        enrichTask(task, result);
        tasksRepository.save(task);
    }

    private void enrichTask(Task task, Result result) {
        int k = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < task.getCities().size(); i++) {
            ++k;
            task.getCities().get(i).setNumber(k);
            sb.append("{Гор").append(k).append(", x:").append(task.getCities().get(i).getX()).
                    append(", y:").append(task.getCities().get(i).getY()).append("} ");
        }
        task.setConditions(sb.toString().trim());
        sb = new StringBuilder();
        if (result.getLength() == 0) {
            sb.append("Кратчайший путь: ").append(result.getLength()).append(", Гор1");
            task.setSolution(sb.toString());
        }
        else {
            sb.append("Кратчайший путь: ").append(result.getLength()).append(", Гор1 -> ");
            for (int i = 0; i < result.getCities().size(); i++) {
                sb.append("Гор").append(result.getCities().get(i).getNumber()).append(" -> ");
            }
            sb.append("Гор1");
            task.setSolution(sb.toString());
        }
    }
}
