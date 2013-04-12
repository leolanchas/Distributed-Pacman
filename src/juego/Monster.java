/*
 * Monster.java
 *
 * Created on 21 de mayo de 2009, 13:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package juego;

 
    import java.awt.image.BufferStrategy;

    public class Monster extends Actor {
      protected int vx, vy;
      protected int [] dir = {1, 1, 1, 1 };
      protected int speed, rand, possib;
      private int ybloque, xbloque, ybloquesig, xbloquesig, bloquesig, direccion;
      public int sentido;
      private int xCentral, yCentral;
      public int PersigoA = -1; 
      public BufferStrategy strategy;
      
      public Monster(Stage stage) {
        super(stage);
        setSpriteNames(new String[] { "Ghost1.gif", "Ghost2.gif", "Ghost3.gif",
                                      "Ghost4.gif", "GhostScared1.gif",
                                      "GhostScared2.gif","GhostScared3.gif",
                                      "GhostScared4.gif"
                                    }
                      );
        setFrameSpeed(50);
      }
      
      public void act() {
          
        super.act();
          t++;

          if ( t % frameSpeed == 0 ){
              t = 0;
              if(Pacman.SCARED == false){
                  currentFrame = (( currentFrame + 1 ) % 4);
              }else{
                  currentFrame = 4+(( currentFrame + 1 ) % 4);
              }
         }
          
         if(Pacman.serv){
         xCentral = x+this.getWidth()/2;
         yCentral = y+this.getHeight()/2;
                 
         if(xCentral <= sigX+2 && xCentral >= sigX-2 && yCentral <= sigY+2 && yCentral >= sigY-2)
         {
              //IA-------------------------------------------------------------------
            if (PersigoA!=-1)
            direccion = getSmartWay(xCentral, yCentral, PersigoA);
            else direccion = getRandomWay(xCentral, yCentral);
            sentido = direccion;
            switch(direccion){
                case MazeData.ARRIBA: sigY -= MazeData.BLOCKHEIGHT; break;
                case MazeData.ABAJO: sigY += MazeData.BLOCKHEIGHT; break;
                case MazeData.IZQUIERDA: sigX -= MazeData.BLOCKWIDTH; break;
                case MazeData.DERECHA: sigX += MazeData.BLOCKWIDTH; break;
            }

             //------------------------------------------------------------------
         }else{
              if(xCentral < sigX-2) x+=speed;
              else if(xCentral > sigX+2) x-=speed;
              if(yCentral<sigY-2) y+=speed;
              else if(yCentral > sigY+2) y-=speed;
         } 
        }
 
      }

      int getRandomWay(int x, int y){
           int [] dire =  {0, 0, 0, 0 };

          int xB = x / MazeData.BLOCKWIDTH;
          int yB = y / MazeData.BLOCKHEIGHT;

          dire[MazeData.ARRIBA] = MazeData.getLevel1Data(yB-1,xB);
          dire[MazeData.ABAJO] = MazeData.getLevel1Data(yB+1,xB);
          dire[MazeData.IZQUIERDA] = MazeData.getLevel1Data(yB,xB-1);
          dire[MazeData.DERECHA] = MazeData.getLevel1Data(yB,xB+1);

          dire[(sentido+2)%4] = MazeData.BLOCK;

          possib = 0;

          for(int i = 0; i < 4; i++) if(dire[i] != MazeData.BLOCK) possib++;

          rand = (int)((Math.random() *100) % (possib+1) );

          for(int i = 0; i<4; i++)
              if (dire[i]!=MazeData.BLOCK)
              {
                rand--;
                if (rand <= 0) return i;
              }

          return -1;

      }

      int getSmartWay(int x, int y, int jugador){
          int [] dire =  {0, 0, 0, 0 };
    
          int xB = x / MazeData.BLOCKWIDTH;
          int yB = y / MazeData.BLOCKHEIGHT;

          int res = -1;
           
         
         int bloqueJugadorY;
         int bloqueJugadorX;
          
         if( jugador == 0 )
         {
              bloqueJugadorX = stage.getPlayer().getX() / MazeData.BLOCKWIDTH;
              bloqueJugadorY = stage.getPlayer().getY() / MazeData.BLOCKHEIGHT;
         }
         else
         {
              bloqueJugadorX = stage.getPlayer2().getX() / MazeData.BLOCKWIDTH;
              bloqueJugadorY = stage.getPlayer2().getY() / MazeData.BLOCKHEIGHT;
         }
          
          dire[MazeData.ARRIBA] = MazeData.getLevel1Data(yB-1,xB);
          dire[MazeData.ABAJO] = MazeData.getLevel1Data(yB+1,xB);
          dire[MazeData.IZQUIERDA] = MazeData.getLevel1Data(yB,xB-1);
          dire[MazeData.DERECHA] = MazeData.getLevel1Data(yB,xB+1);
          if(Pacman.SCARED == true){
            if((dire[MazeData.ARRIBA] != MazeData.BLOCK) && (bloqueJugadorY>yB)) return MazeData.ARRIBA;
            if((dire[MazeData.ABAJO] != MazeData.BLOCK) && (bloqueJugadorY<yB)) return MazeData.ABAJO;
            if((dire[MazeData.IZQUIERDA] != MazeData.BLOCK) && (bloqueJugadorX>xB)) return MazeData.IZQUIERDA;
            if((dire[MazeData.DERECHA] != MazeData.BLOCK) && (bloqueJugadorX<xB)) return MazeData.DERECHA;
          }else{
            if((dire[MazeData.ARRIBA] != MazeData.BLOCK) && (bloqueJugadorY<yB)) return MazeData.ARRIBA;
            if((dire[MazeData.ABAJO] != MazeData.BLOCK) && (bloqueJugadorY>yB)) return MazeData.ABAJO;
            if((dire[MazeData.IZQUIERDA] != MazeData.BLOCK) && (bloqueJugadorX<xB)) return MazeData.IZQUIERDA;
            if((dire[MazeData.DERECHA] != MazeData.BLOCK) && (bloqueJugadorX>xB)) return MazeData.DERECHA;
          }
          
          
          possib = 0;
          
          for(int i = 0; i < 4; i++) if(dire[i] != MazeData.BLOCK) possib++;
          
          rand = (int)((Math.random() *100) % possib );

          for(int i = 0; i<4; i++)
              if (dire[i]!=MazeData.BLOCK)
              { 
                rand--;
                if (rand <= 0) return i; 
              }
        
          return -1;
      }
    
      public int getVx()        { return vx; }
      public void setVx(int i)  { vx = i; }
      public int getVy()          { return vy; }
      public void setVy(int i)    {vy = i; }
      public void setSpeed(int i) { speed = i+1; vx = i; vy = 0; }
      public int getSpeed(){ return speed; }
      public void setPersigoA(int i) { PersigoA = i; }
      public void setSentido(int i) {
          if (i == 0) sentido = MazeData.DERECHA;
          else sentido = MazeData.IZQUIERDA; }
      
 
    
    }

