package Chat_Texto;

import java.io.IOException;
import java.net.Socket;
import juego.Pacman;

public class Cliente_Chat implements Runnable {
  public static final int puerto = 1 + Integer.valueOf(Pacman.puerto);
  public static final String IP = Pacman.ip;
  Socket socket;

  public void Cliente_Chat(String[] args)throws IOException {
        socket = new Socket( IP , puerto );
  }

  public void Cerrar_Sesion()
  {
       try { socket.close(); }
       catch(IOException e){ e.printStackTrace(); }

       System.exit(0);
  }

  public void run()
  {
      Talk talk = new Talk(socket, "Cliente_Chat >> " + Pacman.Nombre );
      talk.hablar();
      Cerrar_Sesion();
  }
}