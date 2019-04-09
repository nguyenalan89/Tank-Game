package TankGame;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {



    private Tank tank;
    private TRE tankGame;



    private int x;
    private int y;
    private int vx;
    private int vy;
    private int width;
    private int height;
    private int angle;
    private int damage;
    private boolean visible;
    private final int R = 2;
    private int speed;

    private BufferedImage img;



    Bullet(TRE tankGame,Tank tank,int speed, BufferedImage img,int damage) {

        this.tankGame = tankGame;
        this.speed = speed;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        this.img = img;
        this.visible = true;
        this.tank = tank;
        this.damage = damage;


    }



    public void update(){

        moveForwards();


    }


    private void moveForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

    }



    void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);



    }


    public boolean isVisible() {
        return visible;
    }


}
