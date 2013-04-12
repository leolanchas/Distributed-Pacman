package Chat_Texto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;

import java.net.Socket;

import juego.Pacman;

import java.awt.Font;

class AccionEnviar implements ActionListener{
        private JTextField areaTexto;
        private PrintStream salida;

        public AccionEnviar(Socket s, JTextField at){
                areaTexto = at;
                try {
					//Conseguimos el canal de salida
                        salida = new PrintStream(s.getOutputStream());
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void actionPerformed(ActionEvent e){
			//Conseguido el canal, enviamos el texto y limpiamos el campo de escritura
                salida.println( Pacman.Nombre + " dijo :: " + areaTexto.getText() );
                areaTexto.setText("");
        }
}

public class Talk {
        private Socket socket;
        private String titulo;

        public Talk(Socket s, String t){
                socket = s;
                titulo = t;
        }

        public void hablar(){
                JFrame marco = new JFrame(titulo);
                marco.setLayout(new BorderLayout());
                JTextArea areaTexto = new JTextArea("");
                areaTexto.setEditable(false);
                marco.add(areaTexto, "Center");
                JPanel panel = new JPanel(new FlowLayout());
                marco.add(panel, "South");
                JTextField campoTexto = new JTextField(30);
                panel.add(campoTexto);
                JButton botonEnviar = new JButton("Enviar");
                AccionEnviar ae = new AccionEnviar(socket, campoTexto);
                botonEnviar.addActionListener(ae);
				//El boton de envio estará a la escucha de las acciones.
                panel.add(botonEnviar);
                marco.setSize(800,400);
                marco.setVisible(true);

                BufferedReader entrada;
                try {
					//Obtenemos el canal de entrada
                        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String mensaje;
						//Cuando recibimos un mensaje entrada, lo mostramos en la pantalla
                        if( Pacman.serv ) areaTexto.setFont( new Font("Calibri", Font.BOLD, 20 ));
                        else areaTexto.setFont( new Font("Maiandra", Font.BOLD, 20 ));
                        while( (mensaje = entrada.readLine()) != null){
                            areaTexto.setText( areaTexto.getText() + mensaje + "\n");
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
