package com.sv.enviafacil.package_delivery_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PackageDeliverySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackageDeliverySystemApplication.class, args);
	}

}
