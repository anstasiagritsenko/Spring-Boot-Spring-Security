package ru.itmentor.spring.boot_security.demo.model; // Объявление пакета ru.itmentor.spring.boot_security.demo.model

import javax.persistence.*; // Импорт пакета javax.persistence для работы с JPA
import javax.validation.constraints.NotEmpty; // Импорт аннотации NotEmpty из javax.validation.constraints для валидации данных
import java.util.HashSet; // Импорт класса HashSet из пакета java.util для работы с коллекциями
import java.util.Set; // Импорт интерфейса Set из пакета java.util для работы с коллекциями

@Entity // Аннотация @Entity для указания, что класс является сущностью JPA
@Table(name = "volunteers") // Аннотация @Table для указания имени таблицы в базе данных
public class Volunteer { // Объявление класса Volunteer

    @Id // Аннотация @Id для указания, что поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Аннотация @GeneratedValue для автоматической генерации значения
    private long id; // Поле для хранения идентификатора волонтера

    @NotEmpty(message = "First name should not be empty") // Аннотация @NotEmpty для проверки, что поле не пустое
    private String firstName; // Поле для хранения имени волонтера

    @NotEmpty(message = "Last name should not be empty") // Аннотация @NotEmpty для проверки, что поле не пустое
    private String lastName; // Поле для хранения фамилии волонтера

    @NotEmpty(message = "Position should not be empty") // Аннотация @NotEmpty для проверки, что поле не пустое
    private String position; // Поле для хранения должности волонтера

    @NotEmpty(message = "Username should not be empty") // Аннотация @NotEmpty для проверки, что поле не пустое
    private String username; // Поле для хранения имени пользователя

    @NotEmpty(message = "Password should not be empty") // Аннотация @NotEmpty для проверки, что поле не пустое
    private String password; // Поле для хранения пароля волонтера

    @ManyToMany(fetch = FetchType.EAGER) // Аннотация @ManyToMany для указания множественной связи между таблицами
    @JoinTable( // Аннотация @JoinTable для указания параметров соединительной таблицы
            name = "volunteers_roles", // Имя соединительной таблицы
            joinColumns = @JoinColumn(name = "volunteer_id"), // Столбец с ссылкой на таблицу Volunteer
            inverseJoinColumns = @JoinColumn(name = "role_id")) // Столбец с ссылкой на таблицу Role
    private Set<Role> userRoles = new HashSet<>(); // Поле для хранения ролей волонтера

    public Volunteer() { // Объявление конструктора по умолчанию
    }

    public Volunteer(String firstName, String lastName, String position, String username, String password) { // Объявление конструктора с параметрами
        this.firstName = firstName; // Присвоение значения полю firstName
        this.lastName = lastName; // Присвоение значения полю lastName
        this.position = position; // Присвоение значения полю position
        this.username = username; // Присвоение значения полю username
        this.password = password; // Присвоение значения полю password
    }

    public long getId() { // Объявление метода getId для получения значения поля id
        return id; // Возврат значения поля id
    }

    public void setId(long id) { // Объявление метода setId с параметром id
        this.id = id; // Присвоение значения полю id
    }

    public String getFirstName() { // Объявление метода getFirstName для получения значения поля firstName
        return firstName; // Возврат значения поля firstName
    }

    public void setFirstName(String firstName) { // Объявление метода setFirstName с параметром firstName
        this.firstName = firstName; // Присвоение значения полю firstName
    }

    public String getLastName() { // Объявление метода getLastName для получения значения поля lastName
        return lastName; // Возврат значения поля lastName
    }

    public void setLastName(String lastName) { // Объявление метода setLastName с параметром lastName
        this.lastName = lastName; // Присвоение значения полю lastName
    }

    public String getPosition() { // Объявление метода getPosition для получения значения поля position
        return position; // Возврат значения поля position
    }

    public void setPosition(String position) { // Объявление метода setPosition с параметром position
        this.position = position; // Присвоение значения полю position
    }

    public String getUsername() { // Объявление метода getUsername для получения значения поля username
        return
                username; // Возврат значения поля username
    }

    public void setUsername(String username) { // Объявление метода setUsername с параметром username
        this.username = username; // Присвоение значения полю username
    }

    public String getPassword() { // Объявление метода getPassword для получения значения поля password
        return password; // Возврат значения поля password
    }

    public void setPassword(String password) { // Объявление метода setPassword с параметром password
        this.password = password; // Присвоение значения полю password
    }

    public Set<Role> getUserRoles() { // Объявление метода getUserRoles для получения списка ролей волонтера
        return userRoles; // Возврат списка ролей волонтера
    }

    public void setUserRoles(Set<Role> userRoles) { // Объявление метода setUserRoles с параметром userRoles
        this.userRoles = userRoles; // Присвоение значения полю userRoles
    }

    public void addRole(Role role) { // Объявление метода addRole для добавления роли в волонтера
        this.userRoles.add(role); // Добавление роли в множество ролей волонтера
        role.getVolunteers().add(this); // Добавление волонтера к множеству волонтеров у роли
    }

    public void removeRole(Role role) { // Объявление метода removeRole для удаления роли из волонтера
        this.userRoles.remove(role); // Удаление роли из множества ролей волонтера
        role.getVolunteers().remove(this); // Удаление волонтера из множества волонтеров у роли
    }
}
