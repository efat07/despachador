package com.almundo.despachador.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.almundo.despachador.constantes.Constante;
import com.almundo.despachador.payload.DirectorResponse;
import com.almundo.despachador.payload.OperadorResponse;
import com.almundo.despachador.payload.SupervisorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author https://github.com/efat07 - Eyner Arias - efat07@gmail.com
 *
 */

@Component
public class Dispatcher {
	
	private OperadorResponse operadorResponse = null;
	private SupervisorResponse supervisorResponse = null;
	private DirectorResponse directorResponse = null;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = Constante.nombreColaDispatcher)
	public void onMessage( final Message msgLlamadaIn ) throws JMSException {
		
		System.out.println("Llamada en Dispatcher: ");
		TextMessage msgLlamada = (TextMessage) msgLlamadaIn;
		System.out.println( msgLlamada.getText() );
		
		dispatchCall(msgLlamada);
	}

	private void dispatchCall(TextMessage msgLlamada) {
		
		try {
			operadorResponse = mapper.readValue(llamarServicio(Constante.urlOperador), OperadorResponse.class);
			if (operadorResponse.getDuracionAtendiendo() == 0 && operadorResponse.getCantidadEnCola() == 0){
				enviarMensajeALaCola(Constante.nombreColaOperador,msgLlamada);
			}else {
				supervisorResponse = mapper.readValue(llamarServicio(Constante.urlSupervisor), SupervisorResponse.class);
				if (supervisorResponse.getDuracionAtendiendo() == 0 && supervisorResponse.getCantidadEnCola() == 0){
					enviarMensajeALaCola(Constante.nombreColaSupervisor,msgLlamada);
				}else {
					directorResponse = mapper.readValue(llamarServicio(Constante.urlDirector), DirectorResponse.class);
					if(directorResponse.getDuracionAtendiendo() == 0 && directorResponse.getCantidadEnCola() == 0){
						enviarMensajeALaCola(Constante.nombreColaDirector,msgLlamada);
					}else {
						if(operadorResponse.getCantidadEnCola() <= supervisorResponse.getCantidadEnCola()) {
							enviarMensajeALaCola(Constante.nombreColaOperador,msgLlamada);
						}else {
							if(supervisorResponse.getCantidadEnCola() <= directorResponse.getCantidadEnCola()) {
								enviarMensajeALaCola(Constante.nombreColaSupervisor,msgLlamada);
							}else {
								enviarMensajeALaCola(Constante.nombreColaDirector,msgLlamada);
							}
						}
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private String llamarServicio(String strUrl) {
		String output = null; 
				
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setConnectTimeout(10000);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			output = br.readLine();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	private void enviarMensajeALaCola(String nombreCola, TextMessage msgLlamada) {
		jmsTemplate.send(nombreCola,new MessageCreator() {
	          @Override
	          public Message createMessage(Session session) throws JMSException {
	              return session.createTextMessage(msgLlamada.getText());
	          }
	      });
	}
}
