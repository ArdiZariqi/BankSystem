package com.banksystem.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankappApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BankappApplication.class, args);

		ConsoleApp consoleApp = new ConsoleApp();
		consoleApp.run(args);
	}

}
