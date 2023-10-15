package com.eduardomury.todosimple.controllers;

import com.eduardomury.todosimple.models.Task;
import com.eduardomury.todosimple.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    private TaskService taskService;
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id)
    {
        Task task = taskService.findTaskById(id);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllTasksByUserId(Long userId)
    {
        List<Task> tasksOfUser = taskService.findAllTasksByUserId(userId);
        return ResponseEntity.ok().body(tasksOfUser);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Task task)
    {
        Task newTask = taskService.createTask(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTask.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Task task, @PathVariable Long id)
    {
        task.setId(id); // garanto que a minha task tenha o mesmo ID da URL
        taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
