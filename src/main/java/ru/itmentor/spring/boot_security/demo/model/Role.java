package ru.itmentor.spring.boot_security.demo.model; // Объявление пакета ru.itmentor.spring.boot_security.demo.model

import org.springframework.security.core.GrantedAuthority; // Импорт интерфейса GrantedAuthority из пакета org.springframework.security.core

import javax.persistence.*; // Импорт пакета javax.persistence для работы с JPA
import java.util.Set; // Импорт интерфейса Set из пакета java.util для работы с коллекциями

@Entity // Аннотация @Entity для указания, что класс является сущностью JPA
public class Role implements GrantedAuthority { // Объявление класса Role, который реализует интерфейс GrantedAuthority

    @Id // Аннотация @Id для указания, что поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Аннотация @GeneratedValue для автоматической генерации значения
    private Long id; // Поле для хранения идентификатора роли

    @Column(unique = true, nullable = false) // Аннотация @Column для указания свойств столбца в БД
    private String name; // Поле для хранения названия роли

    @ManyToMany(mappedBy = "userRoles") // Аннотация @ManyToMany для указания множественной связи между таблицами
    private Set<Volunteer> volunteers; // Поле для хранения списка волонтеров с данной ролью

    public Role() { // Объявление конструктора по умолчанию
    }

    public Role(String name) { // Объявление конструктора с параметром name
        this.name = name; // Присвоение значения полю name
    }

    public Long getId() { // Объявление метода getId для получения значения поля id
        return id; // Возврат значения поля id
    }

    public void setId(Long id) { // Объявление метода setId с параметром id
        this.id = id; // Присвоение значения полю id
    }

    public String getName() { // Объявление метода getName для получения значения поля name
        return name; // Возврат значения поля name
    }

    public void setName(String name) { // Объявление метода setName с параметром name
        this.name = name; // Присвоение значения полю name
    }

    public Set<Volunteer> getVolunteers() { // Объявление метода getVolunteers для получения списка волонтеров
        return volunteers; // Возврат списка волонтеров
    }

    public void setVolunteers(Set<Volunteer> volunteers) { // Объявление метода setVolunteers с параметром volunteers
        this.volunteers = volunteers; // Присвоение значения полю volunteers
    }

    @Override // Переопределение метода интерфейса GrantedAuthority
    public String getAuthority() { // Объявление метода getAuthority
        return name; // Возврат названия роли в виде authority
    }

    @Override // Переопределение метода toString для преобразования объекта в строку
    public String toString() { // Объявление метода toString
        return name; // Возврат названия роли в виде строки
    }
}
