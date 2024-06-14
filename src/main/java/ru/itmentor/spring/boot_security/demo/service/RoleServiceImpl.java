package ru.itmentor.spring.boot_security.demo.service; // Объявление пакета ru.itmentor.spring.boot_security.demo.service

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации @Autowired из пакета org.springframework.beans.factory.annotation
import org.springframework.stereotype.Service; // Импорт аннотации @Service из пакета org.springframework.stereotype
import org.springframework.transaction.annotation.Transactional; // Импорт аннотации @Transactional из пакета org.springframework.transaction.annotation
import ru.itmentor.spring.boot_security.demo.dao.RoleDao; // Импорт класса RoleDao из пакета ru.itmentor.spring.boot_security.demo.dao
import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт интерфейса List из пакета java.util

@Service // Аннотация @Service для обозначения класса как сервисного компонента Spring
@Transactional // Аннотация @Transactional для управления транзакциями
public class RoleServiceImpl implements RoleService { // Объявление класса RoleServiceImpl, реализующего интерфейс RoleService

    private final RoleDao roleDao; // Поле roleDao типа RoleDao для доступа к данным о ролях

    @Autowired // Аннотация @Autowired для внедрения зависимости через конструктор
    public RoleServiceImpl(RoleDao roleDao) { // Конструктор класса RoleServiceImpl с параметром roleDao
        this.roleDao = roleDao; // Присвоение значения полю roleDao
    }

    @Override // Переопределение метода из интерфейса RoleService
    public List<Role> getAllRoles() { // Объявление метода getAllRoles для получения списка всех ролей
        return roleDao.getAllRoles(); // Возврат списка всех ролей из roleDao
    }

    @Override // Переопределение метода из интерфейса RoleService
    public Role readRole(long id) { // Объявление метода readRole с параметром id
        return roleDao.readRole(id); // Возврат роли по ее идентификатору из roleDao
    }

    @Override // Переопределение метода из интерфейса RoleService
    public Role deleteRole(long id) { // Объявление метода deleteRole с параметром id
        return roleDao.deleteRole(id); // Удаление роли по ее идентификатору из roleDao и возврат удаленной роли
    }

    @Override // Переопределение метода из интерфейса RoleService
    public void createOrUpdateRole(Role role) { // Объявление метода createOrUpdateRole с параметром role
        if (role.getId() == null || roleDao.readRole(role.getId()) == null) { // Если идентификатор роли не установлен или роль не найдена в базе данных
            roleDao.createRole(role); // Создание новой роли в базе данных
        } else { // В противном случае
            roleDao.updateRole(role); // Обновление существующей роли в базе данных
        }
    }

    @Override // Переопределение метода из интерфейса RoleService
    public Role findByRoleName(String roleName) { // Объявление метода findByRoleName с параметром roleName
        return roleDao.findByRoleName(roleName); // Поиск роли по ее имени в базе данных и возврат найденной роли
    }

    @Override // Переопределение метода из интерфейса RoleService
    public Role saveRole(Role role) { // Объявление метода saveRole с параметром role
        if (role.getId() == null || roleDao.readRole(role.getId()) == null) { // Если идентификатор роли не установлен или роль не найдена в базе данных
            roleDao.createRole(role); // Создание новой роли в базе данных
            return role; // Возврат созданной роли
        } else { // В противном случае
            roleDao.updateRole(role); // Обновление существующей роли в базе данных
            return roleDao.readRole(role.getId()); // Возврат обновленной роли
        }
    }
}
