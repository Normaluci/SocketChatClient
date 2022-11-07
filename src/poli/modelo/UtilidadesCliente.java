package poli.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import poli.Constantes;
import poli.controlador.ControladorCliente;
import poli.vista.VistaCliente;

//Utilidades Clientes
public class UtilidadesCliente {

	private VistaCliente vista;
	private poli.controlador.ControladorCliente controlador;
	private Socket cliente;
	
	private BufferedReader entrada;
	private PrintWriter salida;
	//Constructor
	public UtilidadesCliente(Socket cliente, VistaCliente vista, ControladorCliente controlador) throws IOException {
		this.cliente = cliente;
		this.vista = vista;
		this.controlador = controlador;
		entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		salida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	//Recibe Mensaje
	public String recibirTCP() throws IOException {
		String cadenaRecibida = null;
		do {
				cadenaRecibida = entrada.readLine();
		} while(cadenaRecibida==null);
			
		return cadenaRecibida;
	}
	
	//Envía Dato
	public void enviarTCP(String cadena) {
			salida.println(cadena );
	}
	
	//Cierra conexión
	public void cerrarConexion() {
		try {
			entrada.close();
			salida.close();
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Manejador de mensajes
    public void handleMessage() {
		try {
			String msg = recibirTCP();
		
			switch(msg.trim()){
				case Constantes.CODIGO_SALIDA:					
					controlador.salir();
					vista.addText("<CLIENT> El servidor se ha apagado");
					break;
				// Recibimos actualizar numero clientes
				case Constantes.CODIGO_ACTUALIZAR_CONECTADOS:
					vista.setClientes(recibirTCP());
					break;
				default: // Recibimos un mensaje normal y corriente
					vista.addText(msg);
					break;
			}
		} catch (IOException e) {
			controlador.salir();
			vista.addText("<CLIENT> Servidor desconectado.");
		}
    }
}
