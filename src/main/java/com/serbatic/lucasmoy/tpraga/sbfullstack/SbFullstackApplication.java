package com.serbatic.lucasmoy.tpraga.sbfullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbFullstackApplication {

	public static void main(String[] args) {
		byte[] secret = "h8hAjiN2!8IAHA9bgh48gh4298gh4NAUU29gh422355gh249gh492".getBytes();
		System.out.println();
		SpringApplication.run(SbFullstackApplication.class, args);
	}

}
