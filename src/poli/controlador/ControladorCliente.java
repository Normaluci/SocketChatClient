package poli.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import poli.Constantes;
import poli.modelo.UtilidadesCliente;
import poli.vista.VistaCliente;

//Controlador del Cliente 
public class ControladorCliente implements ActionListener {

	private UtilidadesCliente cliente;
	private VistaCliente vista;
	
	//Constructor
	public ControladorCliente(VistaCliente vista) {
		this.vista = vista;
	}

	//Crea Cliente
	public void setCliente(UtilidadesCliente cliente) {
		this.cliente = cliente;
	}
	
	//Acciones del cliente
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "salir":
				salir();
			break;
			case "enviar":
				if(vista.getTextoCampo().equals("chao")){
					salir();
				}
				cliente.enviarTCP(vista.getTextoCampo());
				vista.vaciarTextoCampo();
			break;
			case "listado":
				cliente.enviarTCP(Constantes.CODIGO_LISTAR);
			break;
			case "limpiar":
				vista.limpiarChat();
			break;
			case "scroll":
				vista.alternarAutoScroll();
			break;
			default:
			break;
		}
	}
	
	//Metodo que desconecta un cliente
	public int salir() {
		cliente.enviarTCP(Constantes.CODIGO_SALIDA);
		cliente.cerrarConexion();
		vista.setClientes("Unknown");
		vista.addText("<CLIENT> Has abandonado la sala de chat.");
		vista.setEnabled(false);
		return 0;
	}
}
