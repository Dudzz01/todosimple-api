package com.eduardomury.todosimple.services;

import com.eduardomury.todosimple.models.Task;
import com.eduardomury.todosimple.models.User;
import com.eduardomury.todosimple.respositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findTaskById(Long id)
    {
        Optional<Task> task = taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException("Task nao encontrada"));
    }

    public List<Task> findAllTasksByUserId(Long id)
    {
        List<Task> tasksOfUser = taskRepository.findByUser_Id(id); // essa querie personalizada do repository utiliza o JPA para procurar quais tasks estao atreladas ao um id de um usuario, e entao retorna uma lista de todas as tasks que tem o atributo user com o mesmo id. ou seja, ela ve que a parte escrita da assinatura User é referencia ao user (que utilizando @JoinColumn esta associado ao user_id no banco de dados), e damos _(para indicar que dentro do user) Id tem o atributo id que tem um valor, e entao pegamos todas as tasks que possuem o id igual, retornando uma lista de tasks
        return tasksOfUser;
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
        findTaskById(id); //ve se existe a task que deseja deletar, caso n exista é lancada uma excessao
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
