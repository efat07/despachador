package com.almundo.despachador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author https://github.com/efat07 - Eyner Arias - efat07@gmail.com
 *
 */

@ComponentScan(basePackages="com.almundo.despachador.listener")
@SpringBootApplication
public class DespachadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DespachadorApplication.class, args);
	}

}
