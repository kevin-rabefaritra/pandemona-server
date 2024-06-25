package studio.startapps.pandemona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class PandemonaApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PandemonaApplication.class);

		// Required for StartupEndpoint to record events
		application.setApplicationStartup(new BufferingApplicationStartup(1024));
		application.run(args);
	}
}
