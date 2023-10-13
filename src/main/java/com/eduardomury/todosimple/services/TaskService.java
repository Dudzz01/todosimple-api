package com.eduardomury.todosimple.services;

import com.eduardomury.todosimple.models.Task;
import com.eduardomury.todosimple.models.User;
import com.eduardomury.todosimple.respositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService
{
    private TaskRepository taskRepository;

    private UserService userService;

    public Task findTaskById(Long id)
    {
        Optional<Task> task = taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException("Task nao encontrada"));
    }
    @Transactional
    public Task createTask(Task obj)
    {
        User user = userService.findById(obj.getUser().getId()); // aqui buscamos se o Usuario dessa tarefa existe, por isso usamos o userService para verificcar se o user dessa tarefa existe, pois uma tarefa necessita ter um usuario, nao pode existir uma tarefa sem usuario
        obj.setId(null);
        return taskRepository.save(obj);
    }
    @Transactional

    public Task updateTask(Task obj)
    {
        Task newTask = findTaskById(obj.getId());
        newTask.setDescription(obj.getDescription());
        newTask = taskRepository.save(newTask);
        return newTask;
    }

    public void deleteTask(Long id)
    {
        findTaskById(id);
        try
        {
            taskRepository.deleteById(id); // esse try/catch por enquanto é desnecessário, pois se deletarmos uma tarefa, ele não está sendo referenciada em nenhuma outra classe, ou seja, ela não é chave estrrangeira em nenhuma outra classe(ela n é referenciada em nenhuma outra classe, entao se deletarmos uma tarefa, ela n vai afetar a estrutura de nenhuma outra classe, pois nenhuma outra classe depende do relacionamento dela)
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Nao foi possivel deletar essa task pois ela esta relacionada com outras entidades");
        }

    }
}
