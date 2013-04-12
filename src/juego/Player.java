/*
 * Player.java
 *
 * Created on 21 de mayo de 2009, 13:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package juego;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Actor {

    protected int PLAYER_SPEED = 3;
    protected int vx;
    protected int vy;
    int numPlayers;
    private ArrayList actors;
    public int sentido;
    private int score;
    private int ybloque, xbloque, xbloqueinf, ybloqueinf, xinf, yinf;
    public int vidas = 3;

    public Player(Stage stage)
    {
        super(stage);
        setSpriteNames (new String[]{
            "PacMan1up.gif", "PacMan2up.gif", "PacMan3up.gif", "PacMan4up.gif",
            "PacMan5up.gif", "PacMan6up.gif", "PacMan7up.gif", "PacMan8up.gif",

            "PacMan1right.gif", "PacMan2right.gif", "PacMan3right.gif","PacMan4right.gif",
            "PacMan5right.gif", "PacMan6right.gif", "PacMan7right.gif","PacMan8right.gif",

            "PacMan1down.gif", "PacMan2down.gif", "PacMan3down.gif","PacMan4down.gif",
            "PacMan5down.gif", "PacMan6down.gif", "PacMan7down.gif","PacMan8down.gif",

            "PacMan1left.gif", "PacMan2left.gif", "PacMan3left.gif","PacMan4left.gif",
            "PacMan5left.gif", "PacMan6left.gif", "PacMan7left.gif","PacMan8left.gif",
        });
        setFrameSpeed(6);
        score = 0;
    }

    public void act()
    {
        t++;
        if ( t % frameSpeed == 0 )
        {
            t = 0;
            currentFrame = 8*this.sentido+(( currentFrame + 1 ) % 8) ;
        }
       // super.act();
        /* Hay que controlar que nos podemos mover a esa dirección;
        si no es así, nos quedamos donde estabamos               */

        x += vx;
        y += vy;
        xinf = x + this.width-5;
        yinf = y + this.height-5;

        xbloque = x / MazeData.BLOCKWIDTH;
        ybloque = y / MazeData.BLOCKHEIGHT;

        xbloqueinf = xinf / MazeData.BLOCKWIDTH;
        ybloqueinf = yinf / MazeData.BLOCKHEIGHT;
        
        

        switch(checkCollision()){
            case MazeData.BLOCK: x -= vx; y -= vy; break;

            case MazeData.COCO:
                this.addScore(10);
                MazeData.DOT_TOTAL--;
                stage.getSoundCache().playSound("comecocos.wav");
                if((sentido == MazeData.ARRIBA) || (sentido == MazeData.IZQUIERDA))
                    MazeData.level1data[ybloque][xbloque] = MazeData.EMPTY;
                else
                    MazeData.level1data[ybloqueinf][xbloqueinf] = MazeData.EMPTY;
            break;

            case MazeData.SUPERCOCO:
                this.addScore(50);
                Pacman.SCARED = true;
                Pacman.time_scared = 1000;
                MazeData.DOT_TOTAL--;
                stage.getSoundCache().playSound("comefantasmas.wav");
                if((sentido == MazeData.ARRIBA) || (sentido == MazeData.IZQUIERDA))
                    MazeData.level1data[ybloque][xbloque] = MazeData.EMPTY;
                else
                    MazeData.level1data[ybloqueinf][xbloqueinf] = MazeData.EMPTY;
            break;
        }


        xbloque = getWidth() / MazeData.BLOCKWIDTH;
        ybloque = getHeight() / MazeData.BLOCKHEIGHT;

        
        if (x < 0) x = 0;
        
        if (x > Stage.WIDTH - getWidth()) x = Stage.WIDTH - getWidth();

        if (y < 0) y = 0;
        
        if (y > Stage.PLAY_HEIGHT - getHeight())
            y = Stage.PLAY_HEIGHT - getHeight();
     
    }

    private int checkCollision(/*int x,int y,int sentido*/){

        switch(sentido){
            case MazeData.DERECHA:
                if((MazeData.getLevel1Data( ybloque, xbloqueinf )==MazeData.BLOCK)
                 ||(MazeData.getLevel1Data(ybloqueinf, xbloqueinf)==MazeData.BLOCK)) return MazeData.BLOCK;
                else return MazeData.getLevel1Data(ybloque, xbloqueinf);

            case MazeData.ABAJO:
                 if((MazeData.getLevel1Data( ybloqueinf, xbloque )==MazeData.BLOCK)
                 ||(MazeData.getLevel1Data(ybloqueinf, xbloqueinf)==MazeData.BLOCK)) return MazeData.BLOCK;
                else return MazeData.getLevel1Data(ybloqueinf, xbloque);

            case MazeData.IZQUIERDA:
                 if((MazeData.getLevel1Data( ybloque, xbloque )==MazeData.BLOCK)
                 ||(MazeData.getLevel1Data(ybloqueinf, xbloque)==MazeData.BLOCK)) return MazeData.BLOCK;
                else return MazeData.getLevel1Data(ybloqueinf, xbloque);

            case MazeData.ARRIBA:
                 if((MazeData.getLevel1Data( ybloque, xbloque )==MazeData.BLOCK)
                 ||(MazeData.getLevel1Data(ybloque, xbloqueinf)==MazeData.BLOCK)) return MazeData.BLOCK;
                else return MazeData.getLevel1Data(ybloque, xbloque);
        }
         return 0;
    }

    public int getVx() { return vx; }

    public void setVx(int i) { vx = i; }

    public int getVy() { return vy; }

    public void setVy(int i) { vy = i; }


    protected void updateSpeed()
    {
        vx = 0;
        vy = 0;
        
        if (  down  ) { vy =  PLAYER_SPEED;/* vx = 0;*/ }
        if (   up   ) { vy = -PLAYER_SPEED;/* vx = 0;*/ }
        if (  left  ) { vx = -PLAYER_SPEED;/* vy = 0;*/ }
        if (  right ) { vx =  PLAYER_SPEED;/* vy = 0;*/ }
    }

    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN  : down  = false; break;
            case KeyEvent.VK_UP    : up    = false; break;
            case KeyEvent.VK_LEFT  : left  = false; break;
            case KeyEvent.VK_RIGHT : right = false; break;
        }
        updateSpeed();
    }

    public void keyPressed(KeyEvent e)
    {
        up   = false;
        left = false;
        right = false;
        down = false;
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP    : up    = true; sentido = MazeData.ARRIBA; break;
            case KeyEvent.VK_LEFT  : left  = true; sentido = MazeData.IZQUIERDA; break;
            case KeyEvent.VK_RIGHT : right = true; sentido = MazeData.DERECHA; break;
            case KeyEvent.VK_DOWN  : down  = true; sentido = MazeData.ABAJO; break;
        }
        updateSpeed();
    }

    public void keyTyped(KeyEvent e)
    {
        //if( ( e.getKeyCode() == e.VK_S ) )  ;
    }

    public int getScore() { return score; }

    public void setScore(int i) { score = i; }

    public void addScore(int i) { score += i; }

    public int getVidas() {return vidas; }

    public void setVidas(int i) { vidas = i; }

    public void subVida() { vidas -= 1; }

    public int getPLAYER_SPEED() { return PLAYER_SPEED; }
    
  
    public void collision(Actor a)
    {
        if ((a instanceof Monster)&&(Pacman.SCARED == false))
        {
            subVida();
            if(getVidas()==0)
            {
                markedForRemoval = true;
                if (stage.getNumPlayers() == 2)
                {    
                    if (stage.getPlayer().isMarkedForRemoval() && stage.getPlayer2().isMarkedForRemoval())
                        Pacman.gameOver();
                }else
                    Pacman.gameOver();
            }else{
                setX( Stage.WIDTH / 2 - 10 );
                setY( Stage.PLAY_HEIGHT/2 +150 );
                for (int i = 0; i < Pacman.actors.size(); i++) {
                    Actor a1 = (Actor)Pacman.actors.get(i);
                    if (a1 instanceof Monster){
                        a1.setX ( (int) ( 15 * MazeData.BLOCKWIDTH / 2 - this.getWidth()/2  ) );
                        a1.setY ( (int) ( 15 * MazeData.BLOCKHEIGHT / 2 - this.getHeight()/2 ) );
                        a1.setSigX( a1.getX()+a1.getWidth()/2);
                        a1.setSigY( a1.getY()+a1.getHeight()/2);
                    }
                }
            }
        }
        if ((a instanceof Monster)&&(Pacman.SCARED == true))
          {
                  Pacman.muertes++;  
                  this.addScore(100*Pacman.muertes);
                  
                  
                  a.setX ( (int) ( 15 * MazeData.BLOCKWIDTH / 2 - a.getWidth()/2 ) );
                  a.setY ( (int) ( 15 * MazeData.BLOCKHEIGHT / 2 - a.getHeight()/2  ) );
                  a.setSigX( a.getX()+a.getWidth()/2);
                  a.setSigY( a.getY()+a.getHeight()/2);
          }
    }
}
