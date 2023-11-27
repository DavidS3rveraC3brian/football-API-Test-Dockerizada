package es.soincon.proyecto;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "es.soincon.proyecto" })
public class ProyectoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		// Setting the default TimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		new SpringApplicationBuilder(ProyectoApplication.class).run(args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(ProyectoApplication.class);
	}

}