package com.almundo.despachador.payload;

/**
 * @author https://github.com/efat07 - Eyner Arias - efat07@gmail.com
 *
 */

public class DirectorResponse {

	private int idLlamada = 0;
	private int duracionAtendiendo = 0;
	private int cantidadEnCola = 0;
	
	public DirectorResponse() {
	}
	
	public DirectorResponse(int idLlamada, int duracionAtendiendo, int cantidadEnCola) {
		this.idLlamada = idLlamada;
		this.duracionAtendiendo = duracionAtendiendo;
		this.cantidadEnCola = cantidadEnCola;
	}
	
	public int getIdLlamada() {
		return idLlamada;
	}
	public void setIdLlamada(int idLlamada) {
		this.idLlamada = idLlamada;
	}
	public int getDuracionAtendiendo() {
		return duracionAtendiendo;
	}
	public void setDuracionAtendiendo(int duracionAtendiendo) {
		this.duracionAtendiendo = duracionAtendiendo;
	}
	public int getCantidadEnCola() {
		return cantidadEnCola;
	}
	public void setCantidadEnCola(int cantidadEnCola) {
		this.cantidadEnCola = cantidadEnCola;
	}
}
