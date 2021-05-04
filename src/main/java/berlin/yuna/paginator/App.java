package berlin.yuna.paginator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static berlin.yuna.paginator.config.Constants.TIME_ZONE;

@EnableScheduling
@SpringBootApplication
public class App {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TIME_ZONE);
    }

    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }

}
