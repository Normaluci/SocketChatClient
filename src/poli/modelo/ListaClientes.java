package poli.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import poli.Constantes;

import java.util.Set;

//Lista CLientes Conectados
public class ListaClientes {

	private static HashMap<String, HiloServidor> mapaClientes;
	
	public ListaClientes(){
		mapaClientes = new HashMap<String, HiloServidor>();
	}
	
	//Obtiene clientes conectados
	public int getClientesConectados() {
		return mapaClientes.size();
	}
	
	//Crea Cliente
	public void add(String nombre, HiloServidor cliente) {
		mapaClientes.put(nombre, cliente);
	}
	
	//Elimina Cliente
	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}
	
	//Retorna verdadero si el cliente existe
	public boolean yaEstaDentro(String nombre) {
		return mapaClientes.containsKey(nombre);
	}
	
	//Lista de clientes
	public String getListaClientes() {
		StringBuilder clientes = new StringBuilder(250);
		
		Set<String> claves = mapaClientes.keySet();
		for (String clave : claves) {
		   clientes.append(clave + ", ");
		} 
		
		clientes.setLength(clientes.length()-2);
		clientes.append(".");
				
		return clientes.toString().trim();
	}
	
	//Actualiza Conectados
	public void actualizarConectados() {
    	emitirATodos(Constantes.CODIGO_ACTUALIZAR_CONECTADOS);
    	emitirATodos(getClientesConectados() + "/" + Constantes.MAX_CONEXIONES);
    }
	
	//desconecta todos
	public void desconectarTodos() {
		Set<Map.Entry<String, HiloServidor>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
			((HiloServidor) entry.getValue()).cerrarConexion();
		}
	}
	
	//envia mensaje a todos los clientes
	public void emitirATodos(String msg) {
		Set<Map.Entry<String, HiloServidor>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
		   ((HiloServidor) entry.getValue()).enviarTCP(msg);
		}
	}
}
