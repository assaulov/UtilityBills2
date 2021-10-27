package ru.assaulov.utilitybills2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class PropertiesSetter  {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesSetter.class);

	@Value("${spring.datasource.url}")
	private  String dbUrl;

	@Value("${spring.datasource.username}")
	private  String dbUser;

	@Value("${spring.datasource.password}")
	private  String dbPassword;



	@PostConstruct
	public void liquibasePropertiesSet() throws IOException {
		FileInputStream in = new FileInputStream("src/main/resources/liquibase.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();
		props.setProperty("url", dbUrl);
		props.setProperty("username", dbUser);
		props.setProperty("password", dbPassword);
		FileOutputStream out = new FileOutputStream("src/main/resources/liquibase.properties");
		props.store(out, null);
		out.close();

		LOGGER.info("\n\nliquibase.properties: url, username, password was recorded\n");
	}

	@PreDestroy
	public void liquibasePropertiesClear() throws IOException {
		FileInputStream in = new FileInputStream("src/main/resources/liquibase.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		props.setProperty("url", "path to DB");
		props.setProperty("username", "DB User");
		props.setProperty("password", "DB User Password");

		FileOutputStream out = new FileOutputStream("src/main/resources/liquibase.properties");
		props.store(out, null);
		out.close();

		LOGGER.info("\n\nliquibase.properties: url, username, password was rewritten\n");

	}

}
