package Chat_Texto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import juego.Pacman;

public class Servidor_Chat implements Runnable{
  public static final int puerto = 1 + Integer.valueOf( Pacman.puerto );
  ServerSocket socketEscucha;
  Socket socket;



  public void Servidor_Chat(String[] args)throws IOException {

        socketEscucha = new ServerSocket(puerto);
        socket = socketEscucha.accept();
  }

  public void Cerrar_Sesion()
  {
       try {
           socket.close();
           socketEscucha.close();
       }
       catch(IOException e){ e.printStackTrace(); }

       System.exit(0);
  }

  public void run()
  {
      Talk talk = new Talk(socket, "Servidor_Chat >> " + Pacman.Nombre );
      talk.hablar();
      Cerrar_Sesion();
  }
}
