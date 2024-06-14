package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itmentor.spring.boot_security.demo.model.Volunteer;
import ru.itmentor.spring.boot_security.demo.security.VolunteerDetails;
import ru.itmentor.spring.boot_security.demo.service.VolunteerService;

import javax.validation.Valid;
import java.util.List;

@Controller // Объявляем класс как контроллер Spring MVC
@RequestMapping("/volunteers") // Указываем базовый путь для всех запросов, обрабатываемых этим контроллером
public class VolunteerController {

    private final VolunteerService volunteerService; // Поле для хранения сервиса VolunteerService

    // Конструктор контроллера, используемый для внедрения зависимости VolunteerService
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    // Обработчик GET запроса для получения списка всех волонтеров
    @GetMapping
    public String listVolunteers(Model model) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers(); // Получаем список всех волонтеров из сервиса

        // Получаем текущего аутентифицированного пользователя
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VolunteerDetails currentVolunteerDetails = null;
        if (principal instanceof UserDetails) {
            currentVolunteerDetails = (VolunteerDetails) principal; // Приводим к типу VolunteerDetails, если текущий пользователь аутентифицирован
        }

        // Передаем данные в шаблон volunteers_edit
        model.addAttribute("volunteers", volunteers); // Список волонтеров
        model.addAttribute("currentVolunteer", currentVolunteerDetails != null ? currentVolunteerDetails.getVolunteer() : null); // Текущий волонтер
        model.addAttribute("currentVolunteerDetails", currentVolunteerDetails); // Детали текущего волонтера
        return "volunteers_edit"; // Возвращаем имя шаблона, который будет отображаться
    }

    // Обработчик GET запроса для отображения формы добавления волонтера
    @GetMapping("/add")
    public String addVolunteerForm(Model model) {
        model.addAttribute("volunteer", new Volunteer()); // Передаем пустой объект Volunteer в модель для заполнения формы
        return "form_v"; // Возвращаем имя шаблона формы добавления волонтера
    }

    // Обработчик GET запроса для отображения формы редактирования волонтера по его ID
    @GetMapping("/{id}/edit")
    public String editVolunteerForm(@PathVariable("id") long id, Model model) {
        Volunteer volunteer = volunteerService.readVolunteer(id); // Получаем волонтера по его ID из сервиса
        if (volunteer == null) {
            return "redirect:/"; // Если волонтер не найден, выполняем перенаправление на главную страницу
        }
        model.addAttribute("volunteer", volunteer); // Передаем найденного волонтера в модель для заполнения формы редактирования
        return "form_v"; // Возвращаем имя шаблона формы редактирования волонтера
    }

    // Обработчик POST запроса для сохранения волонтера
    @PostMapping
    public String saveVolunteer(@ModelAttribute("volunteer") @Valid Volunteer volunteer, BindingResult bindingResult,
                                RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "form_v"; // Если есть ошибки валидации, возвращаем форму добавления/редактирования снова
        }
        volunteerService.createOrUpdateVolunteer(volunteer); // Сохраняем или обновляем волонтера через сервис
        attributes.addFlashAttribute("flashMessage",
                "Волонтер " + volunteer.getFirstName() + " " + volunteer.getLastName() + " успешно добавлен!"); // Добавляем сообщение об успешном сохранении во flash-атрибут
        return "redirect:/"; // Перенаправляем на страницу со списком волонтеров после успешного сохранения
    }

    // Обработчик GET запроса для удаления волонтера по его ID
    @GetMapping("/delete/{id}")
    public String deleteVolunteer(@PathVariable("id") long id, RedirectAttributes attributes) {
        Volunteer volunteer = volunteerService.deleteVolunteer(id); // Удаляем волонтера по его ID через сервис
        if (volunteer == null) {
            attributes.addFlashAttribute("flashMessage", "Волонтер не найден!"); // Если волонтер не найден, добавляем сообщение об ошибке
        } else {
            attributes.addFlashAttribute("flashMessage",
                    "Волонтер " + volunteer.getFirstName() + " " + volunteer.getLastName() + " успешно удален!"); // Иначе добавляем сообщение об успешном удалении
        }
        return "redirect:/volunteers"; // Перенаправляем на страницу со списком волонтеров после удаления
    }

    // Обработчик GET запроса для отображения страницы редактирования волонтеров (требует роль ADMIN)
    @GetMapping("/volunteers_edit")
    @PreAuthorize("hasRole('ADMIN')") // Указываем, что доступ к этому методу разрешен только пользователям с ролью ADMIN
    public String showVolunteersEditPage(Model model) {
        model.addAttribute("volunteers", volunteerService.getAllVolunteers()); // Получаем список всех волонтеров и передаем его в модель
        return "volunteers_edit"; // Возвращаем имя шаблона страницы редактирования волонтеров
    }
}
