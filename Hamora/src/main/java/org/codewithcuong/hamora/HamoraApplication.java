package org.codewithcuong.hamora;

import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class HamoraApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HamoraApplication.class, args);
        // Check JDBC connection
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
                System.out.println("Database URL: " + connection.getMetaData().getURL());
                System.out.println("JDBC connection is successful!");
            }
        } catch (SQLException e) {
            System.err.println("Error while testing JDBC connection: " + e.getMessage());
        }

        //display all users
         UserRepo userRepository = context.getBean(UserRepo.class);
         List<User> users = userRepository.getAllUser();
         users.forEach(user -> System.out.println("User: " + user.getEmail() + ", Password: " + user.getPassword()));

    }
}
