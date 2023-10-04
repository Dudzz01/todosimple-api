package com.eduardomury.todosimple.respositories;

import com.eduardomury.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Anotacao para indicar que essa interface é um repositório, ou seja, para indicar que ela é uma acesso ao banco de dados utilizando uma comunicacao de codigo java para acessar o banco de dados
public interface UserRepository extends JpaRepository<User,Long> //O meu UserRepository está herdando o meu JpaRepository, que é uma interface que possui diversos metodos de acoes que fazemos no banco de dados, só que escritos em códigos java, temos a funcao select em codigo java com suas diversas variacoes, delete em codigo java com diversas variacoes, acessando diretamente o meu banco de dados, o update, save
{
}
