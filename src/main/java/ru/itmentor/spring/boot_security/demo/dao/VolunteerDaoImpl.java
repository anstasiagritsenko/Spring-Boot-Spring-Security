package ru.itmentor.spring.boot_security.demo.dao; // Объявление пакета ru.itmentor.spring.boot_security.demo.dao

import org.springframework.stereotype.Repository; // Импорт аннотации @Repository из пакета org.springframework.stereotype
import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role из пакета ru.itmentor.spring.boot_security.demo.model
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model

import javax.persistence.EntityManager; // Импорт класса EntityManager из пакета javax.persistence
import javax.persistence.PersistenceContext; // Импорт аннотации @PersistenceContext из пакета javax.persistence
import java.util.List; // Импорт интерфейса List из пакета java.util
import java.util.Optional; // Импорт класса Optional из пакета java.util

@Repository // Аннотация @Repository для указания, что класс является компонентом репозитория
public class VolunteerDaoImpl implements VolunteerDao { // Объявление класса VolunteerDaoImpl, реализующего интерфейс VolunteerDao

    @PersistenceContext // Аннотация @PersistenceContext для внедрения EntityManager
    private EntityManager entityManager; // Поле entityManager для управления сущностями JPA

    @Override // Переопределение метода из интерфейса VolunteerDao
    public List<Volunteer> getAllVolunteers() { // Объявление метода getAllVolunteers для получения списка всех волонтеров
        return entityManager.createQuery("from Volunteer", Volunteer.class).getResultList(); // Возврат списка всех волонтеров из базы данных
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public void createVolunteer(Volunteer volunteer) { // Объявление метода createVolunteer с параметром volunteer
        entityManager.persist(volunteer); // Сохранение волонтера в базе данных
        entityManager.flush(); // Принудительная запись изменений в базу данных
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public void updateVolunteer(Volunteer volunteer) { // Объявление метода updateVolunteer с параметром volunteer
        entityManager.merge(volunteer); // Обновление данных волонтера в базе данных
        entityManager.flush(); // Принудительная запись изменений в базу данных
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public Volunteer readVolunteer(long id) { // Объявление метода readVolunteer с параметром id
        return entityManager.find(Volunteer.class, id); // Поиск и возврат волонтера по его идентификатору
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public Volunteer deleteVolunteer(long id) { // Объявление метода deleteVolunteer с параметром id
        Volunteer volunteer = readVolunteer(id); // Получение волонтера по его идентификатору
        if (volunteer != null) { // Проверка наличия волонтера
            entityManager.remove(volunteer); // Удаление волонтера из базы данных
            entityManager.flush(); // Принудительная запись изменений в базу данных
        }
        return volunteer; // Возврат удаленного волонтера
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public Optional<Volunteer> findByUsername(String username) { // Объявление метода findByUsername с параметром username
        List<Volunteer> volunteers = entityManager.createQuery("from Volunteer where username = :username", Volunteer.class) // Выполнение JPQL запроса
                .setParameter("username", username) // Установка параметра запроса
                .getResultList(); // Получение списка волонтеров
        return volunteers.stream().findFirst(); // Возврат Optional с первым найденным волонтером
    }

    @Override // Переопределение метода из интерфейса VolunteerDao
    public List<Role> getAllRoles() { // Объявление метода getAllRoles для получения списка всех ролей
        return List.of(); // Возврат пустого списка ролей
    }
}
