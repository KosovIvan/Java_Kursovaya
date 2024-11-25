package Kosov.MAI.Kursovaya.controllers;

import Kosov.MAI.Kursovaya.dto.TaskInputDTO;
import Kosov.MAI.Kursovaya.dto.TaskOutputDTO;
import Kosov.MAI.Kursovaya.models.Task;
import Kosov.MAI.Kursovaya.services.TasksService;
import Kosov.MAI.Kursovaya.util.TaskErrorResponse;
import Kosov.MAI.Kursovaya.util.TaskNotCreatedException;
import Kosov.MAI.Kursovaya.util.TaskNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TasksService tasksService;
    private final ModelMapper modelMapper;

    @Autowired
    public TasksController(TasksService tasksService, ModelMapper modelMapper) {
        this.tasksService = tasksService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<TaskOutputDTO> getTasks() {
        return tasksService.getTasks().stream().map(this::convertToTaskOutputDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskOutputDTO getTask(@PathVariable int id) {
        return convertToTaskOutputDTO(tasksService.getTask(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> saveTask(@RequestBody @Valid TaskInputDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append("; ");
            }
            throw new TaskNotCreatedException(sb.toString().trim());
        }
        tasksService.saveTask(convertToTask(taskDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id) {
        tasksService.deleteTask(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> exceptionHandler(TaskNotFoundException e) {
        TaskErrorResponse response = new TaskErrorResponse("Задачи с таким id не найдено");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> exceptionHandler(TaskNotCreatedException e) {
        TaskErrorResponse response = new TaskErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Task convertToTask(TaskInputDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        if ((taskDTO.getCities() == null)||(taskDTO.getCities().isEmpty())) throw new TaskNotCreatedException("Должно быть задано не менее 1 города");
        else task.setCities(taskDTO.getCities());
        return task;
    }

    private TaskOutputDTO convertToTaskOutputDTO(Task task) {
        return modelMapper.map(task, TaskOutputDTO.class);
    }
}