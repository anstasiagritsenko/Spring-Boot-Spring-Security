package ru.itmentor.spring.boot_security.demo.controller; // Объявление пакета

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации Autowired
import org.springframework.web.bind.annotation.*; // Импорт аннотаций RestController, RequestMapping, GetMapping, PostMapping, PutMapping, DeleteMapping, PathVariable, RequestBody
import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role
import ru.itmentor.spring.boot_security.demo.service.RoleService; // Импорт интерфейса RoleService

import java.util.List; // Импорт класса List из пакета java.util

@RestController // Аннотация, указывающая, что данный класс является контроллером REST
@RequestMapping("/roles") // Указание базового URL для всех методов данного контроллера
public class RoleController { // Объявление класса RoleController

    private final RoleService roleService; // Объявление поля для RoleService

    @Autowired // Аннотация для автоматического внедрения зависимости RoleService
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping // Обработка GET-запросов по базовому URL
    public List<Role> getAllRoles() {
        return roleService.getAllRoles(); // Получение всех ролей
    }

    @PostMapping // Обработка POST-запросов по базовому URL
    public void createRole(@RequestBody Role role) {
        roleService.createOrUpdateRole(role); // Создание новой роли
    }

    @PutMapping // Обработка PUT-запросов по базовому URL
    public void updateRole(@RequestBody Role role) {
        roleService.createOrUpdateRole(role); // Обновление существующей роли
    }

    @GetMapping("/{id}") // Обработка GET-запросов по URL /roles/{id}
    public Role readRole(@PathVariable long id) {
        return roleService.readRole(id); // Получение роли по идентификатору
    }

    @DeleteMapping("/{id}") // Обработка DELETE-запросов по URL /roles/{id}
    public void deleteRole(@PathVariable long id) {
        roleService.deleteRole(id); // Удаление роли по идентификатору
    }
}
