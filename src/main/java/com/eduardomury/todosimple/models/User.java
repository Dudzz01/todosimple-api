package com.eduardomury.todosimple.models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity //Usamos essa anotacao para definir uma classe como entidade de um banco de dados, e GERALMENTE, essa entidade é uma tabela, no banco de dados temos entidades como: tabelas, views, indexs, fucntions
@Table (name = User.TABLE_NAME)
public class User
{
    public interface CreateUser{} //Sao apenas marcadores de validacoes, que sao chamados de grupos, quando usamos a anotacao @Valited para validar, podemos especificar os grupos que vamos utilizar na validacao, fazendo que a validacao seja valida somente para as anotacoes que estejam associadas a esse grupo
    public interface UpdateUser{}
    public final static String TABLE_NAME = "user";

    //Atributos(Colunas) do user

    @Id //Usamos essa anotacao para representar/mapear o ID, ou seja, esse atributo representa a coluna chave primária do banco de dados
    @Column(name = "id",unique = true,nullable = false) //Usamos essa anotacao para mapear o atributo como uma coluna chamada "id" no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Usamos essa anotacao para gerar um valor automatico para a nossa coluna, e definimos a estrategia de como gerar esse valor automaticamente, decidimos usar o tipo de geracao chamado IDENTITY, que basicamente gera automaticamente o valor pra cada coluna (esse valor é único e n se repete) utilizando o recurso do banco de dados para fazer isso (Um exemplo é o  AUTO INCREMENT do MySQL)
    private Long id;

    //Na regra de negocio, não haverá atualizacoes no nome, por isso não precisamos validar nenhuma anotacao com o grupo UpdateUser.class
    @Column(name = "username",length = 100,unique = true,nullable = false)
    @NotNull(groups = CreateUser.class) //É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna nao pode ser nula
    @NotEmpty(groups = CreateUser.class) //É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna nao pode ter uma string vazia
    @Size(groups = CreateUser.class, min = 2,max = 100) // É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna tem que ter no minimo 2 digitos e no max 100
    private String username;


    @Column(name = "password",length = 64,unique = false,nullable = false)
    @NotNull(groups = {CreateUser.class,UpdateUser.class}) //É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna nao pode ser nula
    @NotEmpty(groups = {CreateUser.class,UpdateUser.class}) //É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna nao pode ter uma string vazia
    @Size(groups = {CreateUser.class,UpdateUser.class},min = 2,max = 100) // É uma anotacao de uma dependencia do spring de validacao, para validar dentro do programa(e nao no banco de dados) que a coluna tem que ter no minimo 2 digitos e no max 100
    private String password;

    @OneToMany (mappedBy = "user") // esse parametro da anotacao OneToMany indica que o "Many", ou seja, os muitos objetos, que nesse exemplo, sao as tasks, sao mapeadas por one, ou seja, por 1 entidade, visto que 1 entidade tem muitas entidades de um tipo, e muitas entidades podem pertencer/serem mapeadas por 1 entidade, que seria? 
    private List<Task> taskList = new ArrayList<Task>();

    public User()
    {

    }

    public User(Long id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
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
        if(!(obj instanceof User ))
        {
            return  false;
        }

        User other = (User) obj;

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

        return Objects.equals(this.id,other.id) && Objects.equals(this.username,other.username) && Objects.equals(this.password, other.password);
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
