package com.eduardomury.todosimple.controllers;

import com.eduardomury.todosimple.models.User;
import com.eduardomury.todosimple.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user") //a anotacao @RequestMapping está indicando que para solicitar alguma rota do nosso mapa da nossa API, é necessário que utilize-se o /user e após ele, o que deseja procurar(a sua rota). Ex: Facebook.com/user/suaRotaQueVoceDesejaProcurar. o /user simboliza a controller na URL
@Validated // Anotacao que serve para validar informacoes que chegam do frontend para a aplicacao (lembrando que o sistema de validacao tem que ser feitas nos modelos)
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id)
    {
        User user = userService.findById(id); //busca o user no banco de dados atraves do service
        return ResponseEntity.ok().body(user); //para retornar o corpo do tipo user, primeiro checamos se a solicitacao HTTP do tipo Get( solicitacao de pegar/buscar uma informacao existente) foi ok, ou seja, se é de status 200, mostrando que foi processada com sucesso, e depois selecionamos o corpo da resposta que será retornada ao usuario
    }

    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User user)
    {
        User obj = userService.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id)
    {
        user.setId(id);
        userService.updatdeUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }





}
