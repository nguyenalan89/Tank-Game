import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet {


    private int x,y,vx,vy,width,height,angle;
    private BufferedImage img;
    private int R = 2;
    private boolean visible;
    private TRE tankgame;
    private Tank tank;



    public Bullet(TRE tankgame, Tank tank, BufferedImage img,int angle,int x, int y){

        this.tankgame = tankgame;
        this.tank = tank;
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        this.angle = angle;
        this.visible = true;




    }


    public void update(){

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;


    }

    public Rectangle getRect(){
        return new Rectangle(x,y,getWidth(),getHeight());

    }

    public void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);




    }



    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tank getTank() {
        return tank;
    }

    public TRE getTankgame() {
        return tankgame;
    }
}
