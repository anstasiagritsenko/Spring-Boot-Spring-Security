// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.configs
package ru.itmentor.spring.boot_security.demo.configs;

// Импортируем необходимые классы и аннотации из Spring для настройки безопасности
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// Импортируем класс VolunteerDetailsService из вашего пакета, который используется для загрузки данных пользователей
import ru.itmentor.spring.boot_security.demo.service.VolunteerDetailsService;

@Configuration // Объявляем класс как конфигурационный для Spring
@EnableWebSecurity // Включаем поддержку настройки безопасности веб-приложения
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final VolunteerDetailsService volunteerDetailsService; // Поле для хранения экземпляра VolunteerDetailsService
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler; // Поле для хранения экземпляра AuthenticationSuccessHandler

    @Autowired // Аннотация для автоматического внедрения зависимостей через конструктор
    public WebSecurityConfig(VolunteerDetailsService volunteerDetailsService, AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.volunteerDetailsService = volunteerDetailsService; // Инициализация поля volunteerDetailsService
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler; // Инициализация поля customAuthenticationSuccessHandler
    }

    @Override // Переопределяем метод для настройки HTTP-безопасности
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // Начинаем конфигурацию прав доступа
                // Указываем, что доступ ко всем адресам требует наличие одной из ролей: ADMIN или USER
                .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and() // Заканчиваем конфигурацию прав доступа
                .formLogin() // Настраиваем форму входа в систему
                .successHandler(customAuthenticationSuccessHandler) // Устанавливаем кастомный обработчик успешного входа
                .permitAll() // Разрешаем всем пользователям доступ к форме входа
                .and() // Заканчиваем конфигурацию формы входа
                .logout() // Настраиваем выход из системы
                .permitAll(); // Разрешаем всем пользователям доступ к странице выхода
    }

    @Autowired // Аннотация для автоматической настройки аутентификации
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(volunteerDetailsService); // Устанавливаем VolunteerDetailsService для загрузки данных о пользователях
    }

    @Bean // Объявляем метод как спринговый бин для создания PasswordEncoder
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Возвращаем NoOpPasswordEncoder для отключенного шифрования паролей (используется для учебных целей)
    }
}
