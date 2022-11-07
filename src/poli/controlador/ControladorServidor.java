package poli.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import poli.ejecutable.Servidor;
import poli.vista.VistaServidor;

//Controlador Servidor
public class ControladorServidor implements ActionListener {

	private VistaServidor vista;
	private ServerSocket servidor;
	
	//Constructor
	public ControladorServidor(VistaServidor vista) {
		this.vista = vista;
	}
	
	//Crea Servidor
	public void setServidor(ServerSocket servidor) {
		this.servidor = servidor;
	}
	
	//Acciones del Servidor
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch(ae.getActionCommand()){
			case "apagar":
				try {
					do {
						Servidor.getClientes().desconectarTodos();
						servidor.close();
						vista.addText("<SERVER> Se ha apagado el servidor voluntariamente.");
						vista.apagar();
					}while(!servidor.isClosed());
				} catch (IOException e) {	vista.addText("<SERVER FATAL ERROR> Ya estaba apagado."); }
			break;
			case "scroll":
				vista.alternarAutoScroll();
			break;
			default:
				
			break;
		}
	}

}
