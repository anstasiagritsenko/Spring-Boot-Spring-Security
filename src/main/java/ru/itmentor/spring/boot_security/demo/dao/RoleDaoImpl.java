package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository; // Импорт аннотации @Repository из пакета org.springframework.stereotype
import org.springframework.transaction.annotation.Transactional; // Импорт аннотации @Transactional из пакета org.springframework.transaction.annotation
import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role из пакета ru.itmentor.spring.boot_security.demo.model

import javax.persistence.EntityManager; // Импорт интерфейса EntityManager из пакета javax.persistence
import javax.persistence.PersistenceContext; // Импорт аннотации @PersistenceContext из пакета javax.persistence
import javax.persistence.TypedQuery; // Импорт интерфейса TypedQuery из пакета javax.persistence
import java.util.List; // Импорт интерфейса List из пакета java.util

@Repository // Аннотация @Repository для обозначения класса как компонента доступа к данным
@Transactional // Аннотация @Transactional для управления транзакциями
public class RoleDaoImpl implements RoleDao { // Объявление класса RoleDaoImpl, реализующего интерфейс RoleDao

    @PersistenceContext // Внедрение EntityManager, управляющего жизненным циклом сущностей JPA
    private EntityManager entityManager;

    @Override // Переопределение метода из интерфейса RoleDao
    public List<Role> getAllRoles() { // Объявление метода getAllRoles для получения списка всех ролей
        // Создание запроса на выборку всех объектов типа Role из базы данных
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override // Переопределение метода из интерфейса RoleDao
    public Role readRole(long id) { // Объявление метода readRole с параметром id для чтения роли по идентификатору
        // Поиск роли в базе данных по указанному идентификатору
        return entityManager.find(Role.class, id);
    }

    @Override // Переопределение метода из интерфейса RoleDao
    public Role deleteRole(long id) { // Объявление метода deleteRole с параметром id для удаления роли
        Role role = readRole(id); // Чтение роли по идентификатору
        if (role != null) { // Если роль найдена
            entityManager.remove(role); // Удаление роли из базы данных
        }
        return role; // Возврат удаленной роли
    }

    @Override // Переопределение метода из интерфейса RoleDao
    public void createRole(Role role) { // Объявление метода createRole с параметром role для создания роли
        entityManager.persist(role); // Сохранение новой роли в базе данных
    }

    @Override // Переопределение метода из интерфейса RoleDao
    public void updateRole(Role role) { // Объявление метода updateRole с параметром role для обновления роли
        entityManager.merge(role); // Обновление существующей роли в базе данных
    }

    @Override // Переопределение метода из интерфейса RoleDao
    public Role findByRoleName(String roleName) { // Объявление метода findByRoleName с параметром roleName для поиска роли по имени
        // Создание запроса на выборку объекта типа Role по имени роли
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class);
        query.setParameter("roleName", roleName); // Установка параметра запроса roleName
        return query.getSingleResult(); // Возврат единственного результата запроса (ожидается одна роль с указанным именем)
    }
}
