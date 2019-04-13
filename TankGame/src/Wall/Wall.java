package Wall;

import TankGame.TRE;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Wall {

    private TRE tankgame;
    private BufferedImage img;
    private int x,y,width,height;
    private boolean breakable,visible;



    public Wall(TRE tankgame,BufferedImage img, int x, int y, boolean breakable){

        this.tankgame = tankgame;
        this.img = img;
        this.x = x;
        this.y = y;
        this.visible = true;
        this.breakable = true;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);



    }

    public void update(){



    }


    public void drawImage(Graphics g){

        if(visible) {
            g.drawImage(this.img, x, y, null);

        }

    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public TRE getTankgame() {
        return tankgame;
    }
}
