package poli.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import poli.controlador.ControladorCliente;

//Vista Cliente
public class VistaCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	WindowListener exitListener;
	
	private JLabel labelClientes;
	private JTextArea chat;
	private JTextField campo;
	private JButton botonEnviar, botonSalir, botonLimpiar, botonListado, botonScroll;
	DefaultCaret caret;

	//Constructor
	public VistaCliente(JFrame ventana) {
		this.ventana = ventana;
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel(new FlowLayout());
		JPanel panelSur = new JPanel(new GridLayout(1,3));
		
		labelClientes = new JLabel("Clientes en el chat: 0/0");
		chat = new JTextArea();
		campo = new JTextField();
		botonListado = new JButton("Listado de clientes");
		botonSalir = new JButton("Salir");
		botonEnviar = new JButton("Enviar");
		botonLimpiar = new JButton("Limpiar chat");
		botonScroll = new JButton("Auto-scroll");
		JScrollPane scroll = new JScrollPane(chat);
		
		panelNorte.add(labelClientes);
		panelNorte.add(botonListado);
		panelNorte.add(botonScroll);
		panelNorte.add(botonSalir);
		panelSur.add(botonLimpiar);
		panelSur.add(campo);
		panelSur.add(botonEnviar);
		
		add(panelNorte, BorderLayout.NORTH);
		add(panelSur, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret = (DefaultCaret)chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setPreferredSize(new Dimension(480, 360));
		chat.setLineWrap(true);
		
		chat.setEditable(false);
		setEnabled(false);
	}
	//Obtiene texto del campo
	public String getTextoCampo() {
		return campo.getText().toString();
	}
	//Limpia textos
	public void vaciarTextoCampo() {
		campo.setText("");
	}
	//Crea cliente
	public void setClientes(String clientes) {
		labelClientes.setText("Clientes en el chat: "+ clientes);
	}
	//Agrega texto
	public void addText(String linea) {
		chat.append(linea+"\n");
	}
	//Limpia Chat
	public void limpiarChat() {
		chat.setText("");
	}
	//Scroll
	public void alternarAutoScroll() {
		if(caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE)
			caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		else {
			chat.setCaretPosition(chat.getDocument().getLength() );
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}
	//Habilita o deshabilita usuario
	public void setEnabled(boolean activado) {
		campo.setEnabled(activado);
		chat.setEnabled(activado);
		botonEnviar.setEnabled(activado);
		botonLimpiar.setEnabled(activado);
		botonListado.setEnabled(activado);
		botonSalir.setEnabled(activado);
		botonScroll.setEnabled(activado);
	}
	//Crea control
	public void setControlador(final ControladorCliente l) {
		botonEnviar.setActionCommand("enviar");
		campo.setActionCommand("enviar");
		botonSalir.setActionCommand("salir");
		botonLimpiar.setActionCommand("limpiar");
		botonListado.setActionCommand("listado");
		botonScroll.setActionCommand("scroll");
		
		botonEnviar.addActionListener(l);
		campo.addActionListener(l);
		botonSalir.addActionListener(l);
		botonLimpiar.addActionListener(l);
		botonListado.addActionListener(l);
		botonScroll.addActionListener(l);
		
		exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        l.salir();
		        System.exit(0);
		    }
		};
		ventana.addWindowListener(exitListener);
	}
}
