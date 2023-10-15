package com.eduardomury.todosimple.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task
{
    public final static String TABLE_NAME = "task";

    @Id
    @Column(name = "id",nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // A anotacao ManyToOne (Existe outras anotacoes de relacionamentos entre entidades) basicamente cria um relacionamento entre a entidade 1( que é a classe e tabela que estamos codando agora) e a entidade 2( a classe que esta sendo referenciada no atributo), o que essa anotacao faz é criar uma chave estrangeira dentro de tarefa que referencia a classe usuario, criando um relacionamento entre essas 2 entidades
    @JoinColumn(name = "user_id",nullable = false,updatable = false) // Essa anotacao basicamente mapeia que, ok a anotacao ManyToOne criou o relacionamento entre as 2 entidades, criou automaticamente a chave estrangeira, tudo certo, mas qual coluna da tabela tarefas será a chave estrangeira? atraves da anotacao @JoinColumn podemos definir o nome da coluna que sera a chave estrangeira, que nesse caso a coluna "user_id" sera a chave estrangeira
    private User user;



    @Column(name = "description",nullable = false,length = 255)
    @NotEmpty
    @NotNull
    @Size(min = 1,max = 255)
    private String description;
    public Task()
    {

    }

    public Task(Long id, User user, String description)
    {
        this.id = id;
        this.user = user;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
        {
            return true;
        }
        if(obj == null)
        {
            return  false;
        }
        if(!(obj instanceof Task ))
        {
            return  false;
        }

        Task other = (Task) obj;

        if(this.id == null)
        {
            if(other.id!=null)
            {
                return false;
            }
            else if(!(this.id.equals(other.id)))
            {
                return false;
            }

        }

        return Objects.equals(this.id,other.id) && Objects.equals(this.user,other.user) && Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode()
    {
        final int pararNumber = 31; //numero parametro definido como 31
        int hashCode = 1;

        hashCode = pararNumber * hashCode + (this.id == null ? 0 : this.id.hashCode()); //operador ternario

        return hashCode;

    }
}
