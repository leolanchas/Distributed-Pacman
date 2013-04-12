/*
 * Actor.java
 *
 * Created on 21 de mayo de 2009, 13:07
 *
 */

package juego;

    import java.awt.Graphics2D;
    import java.awt.image.BufferedImage;
    import java.awt.Rectangle;
    
    public class Actor {
      boolean up,  down,  left,  right;

      protected int 	 	x, y;
      protected int 	 	width;
      protected int 	 	height;
      protected String[] 	spriteNames;
      protected int 	 	currentFrame;
      protected int 	 	sigY, sigX;
      
      protected int 	 	frameSpeed;
      protected int 	 	t;

      protected Stage 	 	stage;
      protected SpriteCache spriteCache;
      
      protected boolean 	markedForRemoval;
      
      public Actor(Stage stage) {
        this.stage = stage;
        spriteCache = stage.getSpriteCache();
        currentFrame = 0;
        frameSpeed = 1;
        t = 0;
      }
      
      public Rectangle getBounds(){
          return new Rectangle(x,y,width,height);
      }
      
      public void remove() {
          markedForRemoval = true;
      }
      
      public boolean isMarkedForRemoval(){
          return markedForRemoval;
      }
      
      public void paint(Graphics2D g){
        g.drawImage( spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage );
      }
      
      public void collision(Actor a){
          
      }
      
      public int getX()  { return x; }
      public void setX(int i) { x = i; }
    
      public int getY() { return y; }
      public void setY(int i) { y = i; }
      
      public void setSigY( int i ) { sigY = i; }
      public void setSigX( int i ) { sigX = i; }
      
      public int getFrameSpeed() { return frameSpeed; }
      public void setFrameSpeed(int i) { frameSpeed = i; }

      
      public void setSpriteNames(String[] names) { 
        spriteNames = names;
        height = 0;
        width = 0;
        for (int i = 0; i < names.length; i++ ) {
          BufferedImage image = spriteCache.getSprite(spriteNames[i]);
          height = Math.max(height,image.getHeight());
          width = Math.max(width,image.getWidth());
        }
      }     
    
     
      public int getHeight() { return height; }
      public int getWidth() { return width; }
      public void setHeight(int i) { height = i; }
      public void setWidth(int i) { width = i;  }
    
    
      public void act()
      { 
          t++;

          if ( t % frameSpeed == 0 )
          {
            t = 0;
            currentFrame = (( currentFrame + 1 ) % spriteNames.length);
          }
      }
    }

