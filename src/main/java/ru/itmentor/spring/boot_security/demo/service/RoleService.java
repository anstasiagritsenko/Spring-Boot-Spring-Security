package ru.itmentor.spring.boot_security.demo.service; // Объявление пакета

import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт класса List из пакета java.util

public interface RoleService { // Объявление интерфейса RoleService

    List<Role> getAllRoles(); // Метод для получения списка всех ролей

    Role readRole(long id); // Метод для чтения информации о роли по её идентификатору

    Role deleteRole(long id); // Метод для удаления роли по её идентификатору

    void createOrUpdateRole(Role role); // Метод для создания или обновления информации о роли

    Role findByRoleName(String roleName);

    Role saveRole(Role role);
}
