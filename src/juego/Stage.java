/*
 * Stage.java
 *
 * Created on 21 de mayo de 2009, 13:06
 *
 */

package juego;

    
    import java.awt.image.ImageObserver;
    
    public interface Stage extends ImageObserver
    {
      public static final int WIDTH         = 600;
      public static final int HEIGHT        = 700;
      public static final int PLAY_HEIGHT   = 600;
      public static final int SPEED         = 10;
      
      public SpriteCache getSpriteCache();
      public void addActor( Actor a );
      public Player getPlayer();
      public Player getPlayer2();
      public SoundCache getSoundCache();
      public int getNumPlayers();
      
    }