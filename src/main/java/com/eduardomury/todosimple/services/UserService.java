package com.eduardomury.todosimple.services;

import com.eduardomury.todosimple.models.User;
import com.eduardomury.todosimple.respositories.TaskRepository;
import com.eduardomury.todosimple.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id)
    {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado... Id: "+id));
    }

    @Transactional // Ele faz o famoso "tudo ou nada", ou ele salva tudo pois tudo foi salvo corretamente, ou caso 1 dado nao foi salvo corretamente, ele reverte toda a operacao e o banco de dados volta a seu estado anterior, impedindo que tenha dados salvos e outros nao, ou salva tudo ou salva nada
    public User createUser(User obj)
    {
        obj.setId(null); //zerando o id pois quando criar um user, termos a certeza que ele criara automatico e sem conflitos um novo user com um ID unico
        obj = userRepository.save(obj); //esse save é um insert de um registro na tabela do banco de dados, estou salvando meu objeto com todos os campos preenchidos no banco de dados, ou seja, estou salvando um registro com todas as colunas preenchidas no meu banco de dados
        taskRepository.saveAll(obj.getTaskList());
        return obj;

    }

    @Transactional
    public User updatdeUser(User obj)
    {
        User newObj = findById(obj.getId()); //pegando o objeto que quero atualizar, lembrando que o parametro que queremos atualizar pode ja ter sido alterado o valor de sua variavel, porém essa alteracao não foi atualizada no banco de dados, entao iremos atualizar no banco de dados para haver conscistencia no banco de dados
        newObj.setPassword(obj.getPassword()); //unico parametro alteravel do usuario na regra de negocios é a senha
        newObj = userRepository.save(newObj); //estou retornando o meu usuário atualizado
        return newObj; //estou retornando o meu usuário atualizado
    }

    public void deleteUser(Long id)
    {
        findById(id);

        try
        {
            userRepository.deleteById(id);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Não é possivel excluir esse user pois ele está relacionado com outras entidades");
        }
    }



}
