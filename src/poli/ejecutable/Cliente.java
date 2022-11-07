package poli.ejecutable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import poli.controlador.ControladorCliente;
import poli.modelo.UtilidadesCliente;
import poli.vista.VistaCliente;

//Clase Cliente
public class Cliente {
	
	private static JFrame ventana;
	private static VistaCliente vista;
	private static ControladorCliente controlador;
	private static Socket cliente;
	private static UtilidadesCliente utilidades;
	
	//Principal
	public static void main(String[] args) {
		
		configurarVentana();
		
		try {
			
			iniciarCliente();
			
			while(!cliente.isClosed()) {
				utilidades.handleMessage();
			}
			
			while(true) {}
		} catch (SocketTimeoutException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Conexi贸n perdida (connection timeout)", "Error de conexi贸n", JOptionPane.ERROR_MESSAGE);
		} catch (SocketException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Servidor no alcanzado. Apagado o fuera de covertura.", "Error de conexi贸n", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error de conexi贸n", JOptionPane.ERROR_MESSAGE);
		} 
	}
	//Configiurar Ventana
	private static void configurarVentana() {

        ventana = new JFrame("Cliente de chat");
        vista = new VistaCliente(ventana);
        controlador = new ControladorCliente(vista);
        
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
        ventana.pack();
        ventana.setResizable(false);
	}
    
	///Crea la ventana del cliente
    private static void iniciarCliente() throws NumberFormatException, IOException {
    	String host = JOptionPane.showInputDialog(ventana, "IP Servidor", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Puerto de la conecci髇", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String nickname = JOptionPane.showInputDialog(ventana, "Escriba su nombre", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	
    	if(puerto.equals(""))
    		puerto = "5001";
    	if(host.equals(""))
    		host = "localhost";
		
    	try {
    		if(nickname.equals(""))
    			throw new IOException("Nombre no v醠ido.");

    		cliente = new Socket();
    		cliente.connect(new InetSocketAddress(host, Integer.parseInt(puerto)), 5000);
    		utilidades = new UtilidadesCliente(cliente, vista, controlador);
    		
    		// Si tiene datos agregamos el cliente sino generamos error
    		if(utilidades.recibirTCP().trim().equals("aceptado")) {
    			iniciarChat(nickname);
    		}else {
    			utilidades = null;
    			throw new IOException("Servidor lleno");
    		}
    	}catch(NumberFormatException e) {
    		JOptionPane.showMessageDialog(ventana, "Debes introducir un n煤mero de puerto v谩lido.", "Error de conexi贸n", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    //Inicia Chat
    private static void iniciarChat(String nick) throws IOException {
    	ventana.setVisible(true);
		vista.setEnabled(true);
		controlador.setCliente(utilidades);
		utilidades.enviarTCP(nick);
    }
}
