package chewyt.Freelancr.io;

// import java.util.HashMap;
// import java.util.Map;
// import static chewyt.Freelancr.io.Constants.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// SpringApplication app = new SpringApplication(Application.class);
		// Map<String, Object> defaults = new HashMap<String, Object>();
		// 	defaults.put("spring.datasource.username", DATASOURCE_USERNAME);
		// 	defaults.put("spring.datasource.password", DATASOURCE_PASSWORD);
		// 	defaults.put("spring.datasource.url", DATASOURCE_URL);
		// app.setDefaultProperties(defaults);
		// app.run(args);
	}

}
