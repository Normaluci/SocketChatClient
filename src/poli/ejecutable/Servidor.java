package poli.ejecutable;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import poli.Constantes;
import poli.controlador.ControladorServidor;
import poli.modelo.ListaClientes;
import poli.modelo.HiloServidor;
import poli.vista.VistaServidor;

//Clase Servidor
public class Servidor {
	
	private static JFrame ventana;
	private static VistaServidor vista;
	private static ControladorServidor controlador;
	public static ServerSocket servidor;
	private static ListaClientes clientes;
	
	//Principal
	public static void main(String[] args) {
		
		configurarVentana();
        lanzarVentana();
        
        try {
		    iniciarServidor();
		    
		    do {
	    		handleClient();
		    }while(!servidor.isClosed());
        } catch (BindException e) {
			vista.addText("Ya existe una instancia");
		} catch(IOException e) {
        	vista.addText("<SERVER FATAL ERROR> No fue posible iniciar el servidor (already running bruh?).");
        }
        
        //Se deja escuchando
        while(true) {}
    }
    
    //Imprimir consola
	public static void imprimirConsola(String msg) {
		vista.addText(msg);
	}
	
	//Imprimir a todos
	public static void imprimirTodos(String msg) {
		imprimirConsola(msg);
		clientes.emitirATodos(msg);
	}
	
	//Obtiene lista de clientes
	public static ListaClientes getClientes() {
		return clientes;
	}
	
	//Configurar ventana
	private static void configurarVentana() {
        ventana = new JFrame("Servidor de chat");
        vista = new VistaServidor();
        controlador = new ControladorServidor(vista);
        
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
	}
	
    private static void lanzarVentana(){
        ventana.pack();
        ventana.setVisible(true);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //Inicia Servidor
    private static void iniciarServidor() throws IOException {
		servidor = new ServerSocket(Constantes.PUERTO_SERVIDOR);
		clientes = new ListaClientes();
		controlador.setServidor(servidor);
		servidor.getInetAddress();
		vista.addText("<SERVER> Servidor iniciado en "+InetAddress.getLocalHost().getHostAddress());
    }
    
    //Manejo de hilos
    private static void handleClient(){
    	try {
    		//Aprobamos cliente.
	    	Socket cliente = servidor.accept();
	    	HiloServidor thread = new HiloServidor(vista, cliente);
			thread.start();
			
			//si esta lleno el server se rechaza.
	    	if(clientes.getClientesConectados() >= Constantes.MAX_CONEXIONES) {
				thread.enviarTCP("lleno");
				cliente.close();
				thread = null;
	    	}else 
	    		thread.enviarTCP("aceptado");
    	}catch(IOException e) {  }
    }
    
    //Agregar Cliente
    public static void meterCliente(HiloServidor thread) {
    	clientes.add(thread.getNombre(), thread);
    	clientes.actualizarConectados();
    	vista.setClientesConectados(clientes.getClientesConectados());
    }
    
    //Elimina cliente
    public static void sacarCliente(String nombre) {
    	clientes.remove(nombre);
    	clientes.actualizarConectados();
    	vista.setClientesConectados(clientes.getClientesConectados());
    }
}
