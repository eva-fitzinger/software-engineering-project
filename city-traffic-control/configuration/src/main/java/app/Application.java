package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import restservice.ControlSystemController;
import restservice.DetectionController;
import restservice.MaintenanceController;
import restservice.ParticipantsController;

@SpringBootApplication(scanBasePackageClasses = {
        ControlSystemController.class,
        MaintenanceController.class,
        ParticipantsController.class,
        DetectionController.class
})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
