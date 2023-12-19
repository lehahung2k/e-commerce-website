package com.hunglh.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();

		String port = dotenv.get("PORT");
		System.setProperty("server.port", port);

		String dbHost = dotenv.get("DB_HOST");
		String dbPort = dotenv.get("DB_PORT");
		String dbName = dotenv.get("DB_DATABASE");
		System.setProperty("spring.datasource.url", "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false");

		String dbUser = dotenv.get("DB_USERNAME");
		System.setProperty("spring.datasource.username", dbUser);
		String dbPass = dotenv.get("DB_PASSWORD");
		System.setProperty("spring.datasource.password", dbPass);

		String jwtSecret = dotenv.get("JWT_SECRET");
		System.setProperty("jwt.secret", jwtSecret);

		String hostName = dotenv.get("HOSTNAME");
		System.setProperty("hostname", hostName);

		SpringApplication.run(BackEndApplication.class, args);
	}

}
