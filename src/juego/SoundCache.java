package juego;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import java.awt.event.KeyEvent;


/**
 *
 * @author Leo
 */
public class SoundCache extends ResourceCache
{
    private boolean loopOn = false;

    protected Object loadResource( URL url ){ return Applet.newAudioClip(url); }

    public AudioClip getAudioClip( String Nombre )
                                { return ( AudioClip ) getResource( Nombre ) ; }

    public void playSound( final String Nombre )
    {
        new Thread( new Runnable() 
                        { public void run() { getAudioClip( Nombre ).play(); } } 
                  ).start();
    }

    public void loopSound( final String Nombre )
    {
        new Thread( new Runnable()
                        { public void run() { getAudioClip( Nombre ).loop(); } }
                  ).start();
        loopOn = true;
    }

    public void keyPressed( KeyEvent e )
    {
        if( e.getKeyCode() == KeyEvent.VK_M )
        {
            if( loopOn ) 
            {
                getAudioClip( "musica.wav" ).stop();
                loopOn = false;
            }
            else
            {
                getAudioClip( "musica.wav" ).loop();
                loopOn = true;
            }
        }
    }
    

}
