package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации @Autowired из пакета org.springframework.beans.factory.annotation
import org.springframework.stereotype.Service; // Импорт аннотации @Service из пакета org.springframework.stereotype
import org.springframework.transaction.annotation.Transactional; // Импорт аннотации @Transactional из пакета org.springframework.transaction.annotation
import ru.itmentor.spring.boot_security.demo.dao.VolunteerDao; // Импорт класса VolunteerDao из пакета ru.itmentor.spring.boot_security.demo.dao
import ru.itmentor.spring.boot_security.demo.model.Volunteer; // Импорт класса Volunteer из пакета ru.itmentor.spring.boot_security.demo.model
import ru.itmentor.spring.boot_security.demo.model.Role; // Импорт класса Role из пакета ru.itmentor.spring.boot_security.demo.model

import java.util.List; // Импорт интерфейса List из пакета java.util

@Service // Аннотация @Service для обозначения класса как сервисного компонента Spring
@Transactional // Аннотация @Transactional для управления транзакциями
public class VolunteerServiceImpl implements VolunteerService { // Объявление класса VolunteerServiceImpl, реализующего интерфейс VolunteerService

    private final VolunteerDao volunteerDao; // Поле volunteerDao типа VolunteerDao для доступа к данным о волонтерах
    private final RoleService roleService; // Поле roleService типа RoleService для доступа к данным о ролях

    @Autowired // Аннотация @Autowired для внедрения зависимости через конструктор
    public VolunteerServiceImpl(VolunteerDao volunteerDao, RoleService roleService) { // Конструктор класса VolunteerServiceImpl с параметрами volunteerDao и roleService
        this.volunteerDao = volunteerDao; // Инициализация поля volunteerDao
        this.roleService = roleService; // Инициализация поля roleService
    }

    @Override // Переопределение метода из интерфейса VolunteerService
    public List<Volunteer> getAllVolunteers() { // Объявление метода getAllVolunteers для получения списка всех волонтеров
        return volunteerDao.getAllVolunteers(); // Возврат списка всех волонтеров из volunteerDao
    }

    @Override // Переопределение метода из интерфейса VolunteerService
    public void createOrUpdateVolunteer(Volunteer volunteer) { // Объявление метода createOrUpdateVolunteer с параметром volunteer
        // Проверяем роли волонтера и сохраняем или обновляем их сначала
        for (Role role : volunteer.getUserRoles()) {
            roleService.createOrUpdateRole(role); // Использование RoleService для операций с ролями
        }

        if (volunteer.getId() == 0) { // Если идентификатор волонтера равен 0
            volunteerDao.createVolunteer(volunteer); // Вызов метода createVolunteer для создания волонтера
        } else { // В противном случае
            volunteerDao.updateVolunteer(volunteer); // Вызов метода updateVolunteer для обновления волонтера
        }
    }

    @Override // Переопределение метода из интерфейса VolunteerService
    public Volunteer readVolunteer(long id) { // Объявление метода readVolunteer с параметром id для получения волонтера по его идентификатору
        return volunteerDao.readVolunteer(id); // Возврат волонтера по его идентификатору из volunteerDao
    }

    @Override // Переопределение метода из интерфейса VolunteerService
    public Volunteer deleteVolunteer(long id) { // Объявление метода deleteVolunteer с параметром id для удаления волонтера
        Volunteer volunteer = volunteerDao.readVolunteer(id); // Получение волонтера по его идентификатору из volunteerDao
        if (volunteer != null) { // Если волонтер найден
            volunteerDao.deleteVolunteer(id); // Удаление волонтера из volunteerDao
        }
        return volunteer; // Возврат удаленного волонтера
    }
}
