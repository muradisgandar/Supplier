package main;

import main.tcpconnection.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplierApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(SupplierApplication.class, args);
                TCPServer.startConnection();
	}

}
