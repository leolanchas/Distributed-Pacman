/*
 * Data.java
 *
 * Created on 2 de junio de 2009, 9:33
 *
 */

package ClienteServidor;


import java.io.Serializable;
import juego.Pacman;

public class Data implements Serializable
{    
    public int []XMonsters;
    public int []YMonsters;
    public int Player1Sentido;
    public int XPlayer1;
    public int YPlayer1;
    public boolean scared;
    
    /** Creates a new instance of Data */
    public Data()
    {
        XMonsters = new int[Pacman.NROFMONSTERS];
        YMonsters = new int[Pacman.NROFMONSTERS];
    }
}
