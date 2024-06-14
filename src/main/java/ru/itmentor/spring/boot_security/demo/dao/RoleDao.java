package ru.itmentor.spring.boot_security.demo.dao;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;

// Интерфейс для доступа к данным о ролях
public interface RoleDao {

    // Получение списка всех ролей
    List<Role> getAllRoles();

    // Чтение роли по ее идентификатору
    Role readRole(long id);

    // Удаление роли по ее идентификатору и возврат удаленной роли
    Role deleteRole(long id);

    // Создание новой роли
    void createRole(Role role);

    // Обновление существующей роли
    void updateRole(Role role);

    // Поиск роли по ее имени
    Role findByRoleName(String roleName); // Новый метод для поиска роли по имени
}
