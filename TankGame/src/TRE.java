import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import static java.awt.Color.*;


/**
 *
 * @author Alan Nguyen
 */
public class TRE extends JPanel  {

    public static final int WORLDSIZE_X = 1920;
    public static final int WORLDSIZE_Y = 1480;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 1080;


    private TRE tankgame;

    private BufferedImage world,LEFT_SIDE, RIGHT_SIDE,backgroundMap,life,breakableWall, unbreakableWall,bulletImg;
    private Image miniMap;
    private Graphics2D buffer;
    private JFrame jf;
    public Tank t1, t2,tank;
    private int tankLives = 3;
    private int health = 20;
    private Bullet b;
    private Wall w;
    private String line;
    private int position;


     private static ArrayList<Bullet> bullets = new ArrayList<>();

     private static ArrayList<Wall> wall = new ArrayList<>();


     private int p1WindowBoundX, p1WindowBoundY, p2WindowBoundX, p2WindowBoundY,p1HealthBar,p2HealthBar;


    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (true) {

                trex.t1.update();
                trex.t2.update();
                trex.b.update();
                trex.repaint();
                System.out.println(trex.t1);
                System.out.println(trex.t2);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }



    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");



        BufferedImage t1img = null, t2img = null;
        this.world = new BufferedImage(TRE.WORLDSIZE_X, TRE.WORLDSIZE_Y, BufferedImage.TYPE_INT_ARGB);

        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
          t1img = ImageIO.read(this.getClass().getClassLoader().getResource("Tank1.png"));
          t2img = ImageIO.read(this.getClass().getClassLoader().getResource("tank1.png"));



          backgroundMap = ImageIO.read(this.getClass().getClassLoader().getResource("Background.bmp"));

          bulletImg = ImageIO.read(this.getClass().getClassLoader().getResource("bullet.png"));

          life = ImageIO.read(this.getClass().getClassLoader().getResource("Heart.png"));

          breakableWall = ImageIO.read(this.getClass().getClassLoader().getResource("WoodBox.gif"));
          unbreakableWall = ImageIO.read(this.getClass().getClassLoader().getResource("Wall.gif"));


          BufferedReader in = new BufferedReader(new FileReader("MapLayout.txt"));

          position = 0;

          while((line = in.readLine()) != null){
             for(int i = 0; i < line.length(); i++){

                if(line.charAt(i) == '1'){
                     this.addWall(new Wall(this,unbreakableWall,(position % 48) * 32, position/ 48 * 32,false));
                }else
                if(line.charAt(i) == '2'){
                     this.addWall(new Wall(this,breakableWall,(position % 48) * 32, position/ 48 * 32,true));

                 }

                 position++;
             }


          }







        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        t1 = new Tank(100, 100, 0, 0, 0, t1img,health,tankLives);
        t2 = new Tank(700,700,0,0,0,t2img,health,tankLives);
        b = new Bullet(this,t1,bulletImg,0,t1.getWidth(),t1.getHeight()/2);


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);


        drawWorld();


        g2.drawImage(LEFT_SIDE, 0, 0, this); //draws the left side of the screen
        g2.drawImage(RIGHT_SIDE, TRE.SCREEN_WIDTH / 2, 0, this); //draws the right side of the screen
        g2.drawImage(miniMap,(SCREEN_WIDTH/2) - 85,(SCREEN_HEIGHT/2) + 100 ,this);


        g2.setColor(BLACK); // gives a border for the split screen
        g2.draw3DRect(0, 0, (TRE.SCREEN_WIDTH/2)-1, TRE.SCREEN_HEIGHT-22, true);
        g2.draw3DRect(TRE.SCREEN_WIDTH/2, 0, (TRE.SCREEN_WIDTH/2)-1, TRE.SCREEN_HEIGHT-2, true);

        drawHealthBar(g2);



    }






    public void drawBackgroundTile(Graphics g){

        int imageWidth = backgroundMap.getWidth(this);
        int imageHeight = backgroundMap.getHeight(this);

        int widthX =  (TRE.SCREEN_WIDTH / imageWidth);
        int heightY =  (TRE.SCREEN_HEIGHT / imageHeight);


        /* nested for loop that tiles the background image starting from the top left corner and finishes at the
          end of the frame */
        for(int x = 0; x <= widthX; x++ ){
            for(int y = 0; y <= heightY; y++){
                g.drawImage(backgroundMap, x * imageWidth, y * imageHeight, this);

            }
        }


    }





    public void drawWorld(){

        drawBackgroundTile(buffer);


        for(int i = 0; i < wall.size(); i++){

           w = wall.get(i);
           w.drawImage(buffer);

        }


        //FixME add conditionals to check whether bullets hit border
        for(int i = 0; i < bullets.size(); i++){
            b = bullets.get(i);
            if(b.getX() < 0){
                bullets.remove(i);
            }

            b.drawImage(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        playerViewBoundChecker();

        LEFT_SIDE = world.getSubimage(this.p1WindowBoundX, this.p1WindowBoundY, TRE.SCREEN_WIDTH/2, TRE.SCREEN_HEIGHT);
        RIGHT_SIDE = world.getSubimage(this.p2WindowBoundX, this.p2WindowBoundY, TRE.SCREEN_WIDTH/2, TRE.SCREEN_HEIGHT);

        miniMap =  world.getScaledInstance(200,200,Image.SCALE_FAST); //FIXME change the specific size of minimap

        this.checkCollisions();





    }



    private void drawHealthBar(Graphics g){


        p1HealthBar = this.t1.getHealth();
        p2HealthBar = this.t2.getHealth();

        int t2Position = t1.getHeight();

        int p1Offset = 25;
        int p2Offset = 25;



        g.setColor(white);
        g.draw3DRect(t1.getWidth(),t1.getHeight(),200,20,true); //t1 health bar
//        g.drawString("HEALTH",t1.getWidth() ,t1.getHeight());
        g.draw3DRect(1020,t2Position,200,20,true); //t2 health bar
//        g.drawString("HEALTH",1020 ,t2Position);


        g.setColor(green);
        g.fill3DRect(t1.getWidth(),t1.getHeight(),200,20,true);
        g.fill3DRect(1020,t2Position,200,20,true);

        //fills in the color for lives for both t1 and t2
//        for(int i = 0; i < tankLives; i++){
//
//            g.setColor(white);
//            g.drawOval(t1.getWidth() + p1Offset * i ,t1.getHeight() + 20,15,15);
//            g.drawOval(1205 - (p2Offset * i),t2Position+ 20,15,15);
//
//            g.setColor(red);
//            g.fillOval(t1.getWidth() + p1Offset * i, t1.getHeight() + 20, 15, 15);
//            g.fillOval(1205-(p2Offset * i),t2Position + 20,15,15);
//
//        }


        //draws the tank lives for each player

        for(int i = 0; i < tankLives; i++) {

            g.drawImage(life, t1.getWidth() + (i * p1Offset), t1.getHeight() + 20, this);//t1's live count
            g.drawImage(life, 1020 + (i * p2Offset), t2Position + 20, this); //t2's live count

        }



    }

    public void checkCollisions(){













    }







    //credit SuhanKoh
    private void playerViewBoundChecker() {

        if ((this.p1WindowBoundX = t1.getTankCenterX() - SCREEN_WIDTH / 4) < 0) {

            this.p1WindowBoundX = 0;

        } else if (this.p1WindowBoundX >= WORLDSIZE_X - SCREEN_WIDTH / 2) {

            this.p1WindowBoundX = (WORLDSIZE_X - SCREEN_WIDTH/ 2);
        }

        if ((this.p1WindowBoundY = t1.getTankCenterY() - SCREEN_HEIGHT / 2) < 0) {

            this.p1WindowBoundY = 0;
        } else if (this.p1WindowBoundY >= WORLDSIZE_Y - SCREEN_HEIGHT) {

            this.p1WindowBoundY = (WORLDSIZE_Y - SCREEN_HEIGHT);
        }

        if ((this.p2WindowBoundX = t2.getTankCenterX() - SCREEN_WIDTH/ 4) < 0) {

            this.p2WindowBoundX = 0;
        } else if (this.p2WindowBoundX >= WORLDSIZE_X- SCREEN_WIDTH / 2) {

            this.p2WindowBoundX = (WORLDSIZE_X - SCREEN_WIDTH / 2);
        }

        if ((this.p2WindowBoundY = t2.getTankCenterY() - SCREEN_HEIGHT / 2) < 0) {

            this.p2WindowBoundY = 0;

        } else if (this.p2WindowBoundY >= WORLDSIZE_Y - SCREEN_HEIGHT) {

            this.p2WindowBoundY = (WORLDSIZE_Y - SCREEN_HEIGHT);
        }

    }





    public Tank getT1(){
        return t1;
    }

    public Tank getT2(){
        return t2;
    }



    public void addWall(Wall walls){
        wall.add(walls);
    }


    public ArrayList<Wall> getWall() {
        return wall;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void addBullets(Bullet projectile){
        bullets.add(projectile);

    }
    public void removeBullets(Bullet projectile){
        bullets.remove(projectile);
    }


    public TRE getTankgame() {

        return tankgame;
    }



}
