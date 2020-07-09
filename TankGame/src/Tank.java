import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 *
 * @author Alan Nguyen
 */
public class Tank{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int width,height,health;
    private int tankLives;

    private final int R = 2;
    private final int ROTATIONSPEED = 4;



    private BufferedImage img,bulletImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean visible;


    private TRE tankgame = new TRE();



    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, int health, int tankLives) {

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.health = health;
        this.tankLives = tankLives;
        this.visible = true;


        try {
            bulletImg = ImageIO.read(this.getClass().getClassLoader().getResource("bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }


    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed(){
        this.ShootPressed = false;
    }



    public void update() {

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed){
            this.shootBullet(this);
        }




    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void shootBullet(Tank tank){

        this.tankgame.addBullets(new Bullet(this.tankgame.getTankgame(),tank,bulletImg,angle,x,y));
        checkBorder();

    }


    public int getTankCenterX() {
        return x + img.getWidth(null) / 2;
    }

    public int getTankCenterY() {
        return y + img.getHeight(null) / 2;
    }




    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.SCREEN_WIDTH - 88) {
            x = TRE.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.SCREEN_HEIGHT - 80) {
            y = TRE.SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);


    }

    public Rectangle getRect(){
        return new Rectangle(x,y,getWidth(),getHeight());

    }




    public int getHealth() {
        return health;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTankLives() {
        return tankLives;
    }

    public void setTankLives(int tankLives) {
        this.tankLives = tankLives; }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
