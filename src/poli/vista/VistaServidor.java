package poli.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import poli.Constantes;

//Vista Servidor
public class VistaServidor extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@Deprecated
	private int clientesConectados;
	
	private JLabel labelConexiones, labelPuerto;
	private JButton botonSalir, botonScroll;
	private JTextArea texto;
	
	DefaultCaret caret;
	//Constructor
	public VistaServidor() {
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel(new GridLayout(1, 2));
		JPanel panelSur = new JPanel(new GridLayout(1, 2));
		
		clientesConectados = 0;
		labelConexiones = new JLabel("Clientes conectados: 0/"+Constantes.MAX_CONEXIONES);
		labelPuerto = new JLabel("Puerto: " + Constantes.PUERTO_SERVIDOR);
		botonSalir = new JButton("Apagar servidor");
		botonScroll = new JButton("Alternar Auto-scroll");
		texto = new JTextArea();
		texto.setEditable(false);
		JScrollPane scroll = new JScrollPane(texto);
		
		panelNorte.add(labelConexiones);
		panelNorte.add(labelPuerto);
		panelSur.add(botonScroll);
		panelSur.add(botonSalir);
		this.add(panelNorte, BorderLayout.NORTH);
		this.add(panelSur, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret = (DefaultCaret)texto.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		texto.setLineWrap(true);
		
		setPreferredSize(new Dimension(480, 360));
	}
	
	//Setea Controlador
	public void setControlador(ActionListener l) {
		botonSalir.setActionCommand("apagar");
		botonScroll.setActionCommand("scroll");
		botonSalir.addActionListener(l);
		botonScroll.addActionListener(l);
	}
	//Setea Cliente conectado
	public void setClientesConectados(int clientesConectados) {
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Constantes.MAX_CONEXIONES);
	}
	//Agrrega clientes
	public void addText(String linea) {
		texto.append(linea+ "\n");
	}
	//Apagar servidor
	public void apagar() {
		botonSalir.setEnabled(false);
		texto.setEnabled(false);
		botonScroll.setEnabled(false);
		labelConexiones.setText("Servidor apagado.");
		labelPuerto.setText("Puerto: -");
	}
	//Manejo de scroll
	public void alternarAutoScroll() {
		if(caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE)
			caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		else {
			texto.setCaretPosition(texto.getDocument().getLength() );
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}
}
