package TankGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {

    private TRE tgame;
    private BufferedImage img;
    private int x,y,width,height;
    private boolean breakable;


    public Wall(TRE tgame, BufferedImage img, int x, int y, boolean breakable){

        this.tgame = tgame;
        this.img = img;
        this.x = x;
        this.y = y;
        this.breakable = true;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);



    }


    public void drawImage(Graphics g){

        g.drawImage(this.img,x,y,null);



    }






}
