package com.almundo.despachador.constantes;

/**
 * @author https://github.com/efat07 - Eyner Arias - efat07@gmail.com
 *
 */

public class Constante {

	public final static String nombreColaDispatcher = "jms/almundo/queueDispatcher";
	
	public final static String nombreColaOperador   = "jms/almundo/queueOperador";
	public final static String nombreColaSupervisor = "jms/almundo/queueSupervisor";
	public final static String nombreColaDirector   = "jms/almundo/queueDirector";
	
	public final static String urlOperador          = "http://localhost:8082/validarDispOperador";
	public final static String urlSupervisor        = "http://localhost:8083/validarDispSupervisor";
	public final static String urlDirector          = "http://localhost:8084/validarDispDirector";
}
