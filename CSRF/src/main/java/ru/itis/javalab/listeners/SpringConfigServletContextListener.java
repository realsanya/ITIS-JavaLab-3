package ru.itis.javalab.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;
import ru.itis.javalab.services.LoginService;
import ru.itis.javalab.services.LoginServiceImpl;
import ru.itis.javalab.services.UsersService;
import ru.itis.javalab.services.UsersServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class SpringConfigServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.jdbc.url"));
        config.setUsername(properties.getProperty("db.jdbc.username"));
        config.setPassword(properties.getProperty("db.jdbc.password"));
        config.setDriverClassName(properties.getProperty("db.jdbc.driver.classname"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.jdbc.hikari.max-pool-size")));

        HikariDataSource dataSource = new HikariDataSource(config);

        UsersRepository userRepository = new UsersRepositoryJdbcImpl(dataSource);
        UsersService userService = new UsersServiceImpl(userRepository);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LoginService loginService = new LoginServiceImpl(userRepository, passwordEncoder);
        servletContextEvent.getServletContext().setAttribute("userService", userService);
        servletContextEvent.getServletContext().setAttribute("passwordEncoder", passwordEncoder);
        servletContextEvent.getServletContext().setAttribute("loginService", loginService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

