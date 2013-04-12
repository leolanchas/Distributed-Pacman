/*
 * Main.java
 *
 * Created on 15 de mayo de 2009, 12:56
 *
 */


package juego;


import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.Semaphore;

import ClienteServidor.*;
import Chat_Texto.Servidor_Chat;
import Chat_Texto.Cliente_Chat;


public class Pacman extends Canvas implements Stage, KeyListener
{                
    public BufferStrategy   strategy;
    public long             usedTime;
    
    public static int       NROFMONSTERS = 5;
    public static boolean   SCARED       = false;
    public static int       time_scared  = 0;
    public static int       muertes      = 0;
    private SpriteCache     spriteCache;
    public static ArrayList actors;
    public static boolean   start        = false;
           
    private Player[]        players;
    public static int       numplayers;
    String                  txtventana;
    
    private MazeData        maze;
    private static boolean  gameEnded    = false;
    private SoundCache      soundCache;

    private BufferedImage   background, life;
    private int             backgroundY;
//    private int             t;
    public static boolean   serv         = true;

    public static String    puerto       = "3035";
    public static String    ip           = "localhost";
    public static String    Nombre       = "Tu Nombre";

    Data                    Envio        = new Data();
    Data                    Recibo       = new Data();
    Data2                   Envio2       = new Data2();
    Data2                   Recibo2      = new Data2();

    public static Semaphore begin;
    
    public Pacman()
    {
        spriteCache = new SpriteCache();
        
        soundCache  = new SoundCache();
        
        if( numplayers >= 2 )
        {
            if ( serv ) txtventana = "Pacman : Servidor - © Leo Lanchas y Aday López";
            else txtventana = "Pacman : Cliente - © Leo Lanchas y Aday López";
        }
        else txtventana = "Pacman - © Leo Lanchas y Aday López";
             
        
        JFrame ventana = new JFrame(txtventana);
        JPanel panel = (JPanel)ventana.getContentPane();
        setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
        panel.setPreferredSize(new Dimension(Stage.WIDTH,Stage.HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        ventana.setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
        ventana.setVisible(true);
        ventana.addWindowListener( new WindowAdapter()
        { @Override public void windowClosing(WindowEvent e){ System.exit(0); } } );
        
        ventana.setResizable(false);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
    }

    @Override public Player getPlayer()  { return players[0]; }
    @Override public Player getPlayer2() { return players[1]; }
    @Override public int getNumPlayers() { return numplayers; }

    public static void gameOver()
    {
        gameEnded = true;
        if ( numplayers == 1 ) return;
        if ( serv ) Start.server.finalizar();
        else Start.client.finalizar();
    }
    
    public void initWorld()
    {
        players = new Player[2];
        actors = new ArrayList();
        for (int i = 0; i < NROFMONSTERS; i++)
        {
            Monster m = new Monster(this);
            m.setX ( (int) ( 15 * MazeData.BLOCKWIDTH / 2 - m.getWidth()/2 ) );
            m.setY ( (int) ( 15 * MazeData.BLOCKHEIGHT / 2 - m.getHeight()/2  ) );
            m.setSigX( m.getX() + m.getWidth()  / 2 );
            m.setSigY( m.getY() + m.getHeight() / 2 );
            if( serv )
            {
                m.setSpeed( (int)( (Math.random() * 100) % 5 ) - 2 );
                while ( m.getSpeed() == 0 )  m.setSpeed( (int)( (Math.random() * 100) % 2 ) );
            }
            if(i<numplayers) m.setPersigoA(i);
            else
            {
                m.setSentido( i % 2 );
                m.setPersigoA( -1 );
            }
            actors.add( m );
        }
        /*
        if( numplayers == 1 ) players[1] = new Player (this);
        else for(int i = 0; i < numplayers; i++) players[i] = new Player (this);
        */
        for(int i = 0; i < 2/*numplayers*/; i++)
        {
            players[i] = new Player (this);
            players[i].setX( Stage.WIDTH / 2 - 50 + i * 80 );
            players[i].setY( Stage.PLAY_HEIGHT / 2 + 150 );
        }
        
        if(serv)players[1].PLAYER_SPEED = 0;
        else players[0].PLAYER_SPEED = 0;

        maze = new MazeData();

        soundCache.loopSound("musica.wav");
        
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        background = spriteCache.getSprite("Mapa.gif");
      
        life = spriteCache.getSprite("Life.gif");
    }
    public void addActor(Actor a) { actors.add(a); }
    
    public void updateWorld()
    {
        if(SCARED) time_scared--;
        if(time_scared == 0 )
        {
            SCARED  = false;
            muertes = 0;
        }
            ////////////Servidor/////////////Jugador1/////////////////
        if(serv == true)
        {
            Envio = new Data();
            for (int i = 0; i < actors.size(); i++)
            {
                Actor m = (Actor)actors.get(i);
                m.act();
                Envio.XMonsters[i]=m.getX();
                Envio.YMonsters[i]=m.getY();
            } 
            if(!players[0].isMarkedForRemoval())
            {
                players[0].act();
                Envio.XPlayer1 = players[0].getX();
                Envio.YPlayer1 = players[0].getY();
                Envio.Player1Sentido = players[0].sentido;
            }
            if(Recibo2 != null)
            {
                if(!players[1].isMarkedForRemoval())
                {
                    players[1].setX(Recibo2.XPlayer2);
                    players[1].setY(Recibo2.YPlayer2);
                    players[1].sentido=Recibo2.Player2Sentido;
                    players[1].act();
                }
            }
        }
        else
        {
            ///////////////////Cliente//////Jugador 2///////////////////////////
            for (int i = 0; i < actors.size(); i++)
            {
               if(Recibo == null) break;
               Actor m = (Actor)actors.get(i);
               m.setX(Recibo.XMonsters[i]);
               m.setY(Recibo.YMonsters[i]);
               m.act();
            }

            if(Recibo != null)
            {
                if(!players[0].isMarkedForRemoval())
                {
                    players[0].setX(Recibo.XPlayer1);
                    players[0].setY(Recibo.YPlayer1);
                    players[0].sentido=Recibo.Player1Sentido;
                    players[0].act();
                }
                if(!players[1].isMarkedForRemoval())
                {
                    players[1].act();
                    Envio2.XPlayer2 = players[1].getX();
                    Envio2.YPlayer2 = players[1].getY();
                    Envio2.Player2Sentido = players[1].sentido;
                }
            }
        }
     }
    
    public void checkCollisions()
    {
        for(int j = 0;j<numplayers;j++)
        {
            if(!players[j].isMarkedForRemoval())
            {
                Rectangle playerBounds = players[j].getBounds();
                for (int i = 0; i < actors.size(); i++)
                {
                    Actor a1 = (Actor)actors.get(i);
                    Rectangle r1 = a1.getBounds();
                    if (r1.intersects(playerBounds))
                    {
                        a1.collision(players[j]);
                        players[j].collision(a1);
                    }
                }
            }
        }
     }
        
    public void paintScore(Graphics2D g)
    {
       g.setFont(new Font("Calibri", Font.BOLD, 20));
       g.setPaint(Color.white);
       g.drawString("Score:",20,Stage.PLAY_HEIGHT + 20);
       g.drawString("Score:",300,Stage.PLAY_HEIGHT + 20);
       g.setPaint(Color.red);
       for(int i = 0;i<numplayers;i++)
            g.drawString(players[i].getScore()+"",(i*280)+100,Stage.PLAY_HEIGHT  + 20);
    }
    
    public void paintfps(Graphics2D g)
    {
       g.setFont( new Font("Arial",Font.BOLD,12));
       g.setColor(Color.white);

       if (usedTime > 0)
         g.drawString( String.valueOf( 1000 / usedTime ) +
                             " fps", Stage.WIDTH - 50, Stage.PLAY_HEIGHT + 20 );
       else g.drawString( "-- fps", Stage.WIDTH - 50 , Stage.PLAY_HEIGHT + 20 );
     }
    
    public void paintlives(Graphics2D g)
    {
        for(int i = 0;i<numplayers;i++)
            for(int j = 0; j<players[i].getVidas(); j++)
                g.drawImage( life, (i*285)+10+j*40, Stage.PLAY_HEIGHT + 30, this);
                                                            
    }
    
     public void paintStatus(Graphics2D g)
     {
       g.setColor(Color.BLACK);
       g.fillRect(0,Stage.PLAY_HEIGHT, Stage.WIDTH, Stage.HEIGHT-Stage.PLAY_HEIGHT);
       paintScore(g);
       paintfps(g); 
       paintlives(g);
     }

     
    public void paintWorld()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.drawImage( background,
                    0,0,Stage.WIDTH,Stage.PLAY_HEIGHT,
                    0,backgroundY,Stage.WIDTH,backgroundY+Stage.PLAY_HEIGHT,this);
        maze.paint(g);
        
        for (int i = 0; i < actors.size(); i++)
        {
            Actor m = (Actor)actors.get(i);
            m.paint(g);
        }

        for(int i = 0;i<numplayers;i++)
            if(!players[i].isMarkedForRemoval()) players[i].paint(g);

        paintStatus(g);
        strategy.show();
    }

    public void paintYouWin()
    {
        start = false;
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Maiandra", Font.BOLD, 20));
        g.drawString("HAS GANADO!!!", Stage.WIDTH / 2 - 59, Stage.HEIGHT / 2 );
        strategy.show();
    }
    
    public void paintGameOver()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("GAME OVER", Stage.WIDTH / 2 - 59, Stage.HEIGHT / 2 );
        strategy.show();
    }
    
   public SpriteCache getSpriteCache() { return spriteCache; }

   public SoundCache getSoundCache(){ return soundCache; }
   
   public void keyPressed(KeyEvent e)
   {
       for(int i = 0;i<numplayers;i++) players[i].keyPressed(e);
       soundCache.keyPressed( e );
   }

   public void keyReleased(KeyEvent e)
   {
       for(int i = 0;i<numplayers;i++) players[i].keyReleased(e);
   }

   public void keyTyped(KeyEvent e) { }
   
    
    public void game()
    {
        usedTime = 1000;
        initWorld();
        paintWorld();
        gameEnded = false;
        
        while ( isVisible() && !gameEnded && MazeData.DOT_TOTAL > 0 )
        {
            long startTime = System.currentTimeMillis();
            if( numplayers > 1 )
            {
                if(serv == false)
                {
                    Recibo = new Data();
                    Recibo = Start.client.recibirData( "queue1", 1 );
                    if ( Recibo != null ) SCARED = Recibo.scared;
                }
                else
                {
                    Recibo2 = new Data2();
                    Recibo2 = Start.server.recibirData2("queue2",1);
                }
            }
            
            updateWorld();
            if( numplayers > 1 )
            {
                if( serv == true )
                {
                    Envio.scared = SCARED;
                    Start.server.enviarData ( Envio, "queue1" );
                }
                else Start.client.enviarData2 ( Envio2, "queue2" );

            }
            checkCollisions();

            paintWorld();
            usedTime = System.currentTimeMillis() - startTime;            
            try{
                Thread.sleep(SPEED);
            } catch (InterruptedException e){}
        }
        
        if ( MazeData.DOT_TOTAL > 0 ) paintGameOver();
        else paintYouWin();
    }


    public static void main(String[] args)
    {
         begin = new Semaphore( 0, true );
         java.awt.EventQueue.invokeLater
                (
                 new Runnable()
                 {
                     public void run()
                     {
                         Start s = new Start();
                         s.setVisible(true);
                     }
                 }
                );
         try { begin.acquire(); }
         catch (InterruptedException e){}

         Servidor_Chat servidor_chat;
         Cliente_Chat cliente_chat;

         if( numplayers >= 2 )
         {
             if( serv )
             {
                  servidor_chat = new Servidor_Chat();
                  try { servidor_chat.Servidor_Chat(args); }
                  catch(java.io.IOException e){ e.printStackTrace(); }
                  Thread S = new Thread( servidor_chat );
                  S.start();
              }
              else
              {
                  cliente_chat = new Cliente_Chat();
                  try { cliente_chat.Cliente_Chat(args); }
                  catch(java.io.IOException e){ e.printStackTrace(); }
                  Thread C = new Thread( cliente_chat );
                  C.start();
              }
          }

          Pacman pac = new Pacman();
          pac.game();
    }
}
