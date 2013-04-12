/*
 * MazeData.java
 *
 * Created on 22 de mayo de 2009, 17:12
 *
 */

package juego;


import java.awt.Graphics2D;
import java.awt.Color;

/**
 *
 * @author a064892
 */
public class MazeData {
    
    public final static int NROFBLOCKS  = 15;
    public final static int BLOCKHEIGHT = Stage.PLAY_HEIGHT / NROFBLOCKS;
    public final static int BLOCKWIDTH  = Stage.WIDTH / NROFBLOCKS;
    public final static int EMPTY       = 0;
    public final static int BLOCK       = 1;
    public final static int COCO        = 2;
    public final static int SUPERCOCO   = 3;
    public final static int CAGE        = 4;
    public final static int PACMAN      = 5;

    public final static int DERECHA     = 1;
    public final static int ABAJO       = 2;
    public final static int IZQUIERDA   = 3;
    public final static int ARRIBA      = 0;
        
    public static int       DOT_TOTAL   = 0;
    private boolean         initial     = true;
    public static int       thisLevel   = 1;
      
    final static int level1data[][] = 
        { { COCO,      COCO,  COCO,  COCO,  BLOCK, BLOCK, COCO,   COCO,   COCO,   BLOCK, BLOCK, COCO,  COCO,  COCO,  COCO      },
          { SUPERCOCO, BLOCK, BLOCK, COCO,  COCO,  COCO,  COCO,   BLOCK,  COCO,   COCO,  COCO,  COCO,  BLOCK, BLOCK, SUPERCOCO },
          { COCO,      COCO,  COCO,  COCO,  BLOCK, BLOCK, COCO,   COCO,   COCO,   BLOCK, BLOCK, COCO,  COCO,  COCO,  COCO      },
          { COCO,      BLOCK, BLOCK, COCO,  COCO,  BLOCK, COCO,   BLOCK,  COCO,   BLOCK, COCO,  COCO,  BLOCK, BLOCK, COCO      },
          { COCO,      BLOCK, BLOCK, BLOCK, COCO,  BLOCK, COCO,   BLOCK,  COCO,   BLOCK, COCO,  BLOCK, BLOCK, BLOCK, COCO      },
          { COCO,      COCO,  COCO , COCO,  COCO,  COCO,  COCO,   BLOCK,  COCO,   COCO,  COCO,  COCO,  COCO,  COCO,  COCO      },
          { BLOCK,     COCO,  BLOCK, COCO,  BLOCK, COCO,  BLOCK,  BLOCK,  BLOCK,  COCO,  BLOCK, COCO,  BLOCK, COCO,  BLOCK     },
          { BLOCK,     COCO,  BLOCK, COCO,  BLOCK, COCO,  CAGE,   CAGE,   CAGE,   COCO,  BLOCK, COCO,  BLOCK, COCO,  BLOCK     },
          { BLOCK,     COCO,  BLOCK, COCO,  BLOCK, COCO,  BLOCK,  BLOCK,  BLOCK,  COCO,  BLOCK, COCO,  BLOCK, COCO,  BLOCK     },
          { COCO,      COCO,  COCO,  COCO,  COCO,  COCO,  COCO,   COCO,   COCO,   COCO,  COCO,  COCO,  COCO,  COCO,  COCO,     },
          { COCO,      BLOCK, BLOCK, BLOCK, BLOCK, COCO,  BLOCK,  COCO,   BLOCK,  COCO,  BLOCK, BLOCK, BLOCK, BLOCK, COCO      },
          { COCO,      BLOCK, BLOCK, BLOCK, BLOCK, COCO,  PACMAN, EMPTY,  PACMAN, COCO,  BLOCK, BLOCK, BLOCK, BLOCK, COCO      },
          { COCO,      COCO,  COCO,  COCO,  BLOCK, COCO,  BLOCK,  BLOCK,  BLOCK,  COCO,  BLOCK, COCO,  COCO,  COCO,  COCO      },
          { SUPERCOCO, BLOCK, BLOCK, COCO,  COCO,  COCO,  COCO,   BLOCK,  COCO,   COCO,  COCO,  COCO,  BLOCK, BLOCK, SUPERCOCO },
          { COCO,      COCO,  COCO,  COCO,  BLOCK, BLOCK, COCO,   COCO,   COCO,   BLOCK, BLOCK, COCO,  COCO,  COCO,  COCO      }
        };

    
    /** Creates a new instance of MazeData */
    public MazeData() {
    }

    public void paint(Graphics2D g) {
        if(thisLevel==1)
        {
            for(int i = 0; i < NROFBLOCKS; i++)
            {
                for(int j = 0; j < NROFBLOCKS; j++)
                {
                    switch (level1data[i][j])
                    {
                        case COCO:
                            g.setColor(Color.yellow);
                            g.fillOval( j * BLOCKWIDTH  + BLOCKWIDTH  / 3,
                                        i * BLOCKHEIGHT + BLOCKHEIGHT / 3,
                                        BLOCKWIDTH / 5,   BLOCKHEIGHT / 5 );
                            if ( initial ) DOT_TOTAL++;
                            
                            break;

                        case SUPERCOCO:
                            g.setColor(Color.red);
                            g.fillOval( j * BLOCKWIDTH  + BLOCKWIDTH  / 3,
                                        i * BLOCKHEIGHT + BLOCKHEIGHT / 3,
                                        BLOCKWIDTH / 3,   BLOCKHEIGHT / 3 );
                            if ( initial ) DOT_TOTAL++;
                            break;

                    }
                }
            }

        }
        initial = false;
    }

    public void setLevel(int x){ thisLevel = x;  }

    public static int getLevel1Data( int x, int y ){ 
        if( ( (x >= 0) && (x < NROFBLOCKS) ) && ( (y >= 0) && (y < NROFBLOCKS) ) )
        return level1data[x][y];
        else return BLOCK;
    }

   
    
}
