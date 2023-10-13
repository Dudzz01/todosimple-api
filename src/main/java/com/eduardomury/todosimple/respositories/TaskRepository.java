package com.eduardomury.todosimple.respositories;

import com.eduardomury.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> // Os parametros sao a entidade/classe que vc vai usar para ser acessada no banco de dados, e o tipo da sua chave primária/id
{
    List<Task> findByUser_Id(Long id); // Criamos aqui uma consulta/query personalizada, que ela, através da classe/tabela Task, vai procurar através do atributo/chave estrangeira User (lembrando que na clase task, o atributo esta User, mas na tabela a coluna se chama user_id, logo o spring boot criou uma forma que faz com que criamos um metodo findBy(iso seria o select no banco de dados)User(vai procurar os registros da tabela task atraves do user, porém na tabela não há p user, apenas o user_id, e esse user_id está dentro do User, logo...)_Id(esse User_Id faz com que o spring entenda que o Id está dentro do modelo/tabela User)

   // @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
   // List<Task> findByUserId(@Param("id") Long id);   //JPQL

   // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> findByUserId(@Param("id") Long id);  //SQL
}
