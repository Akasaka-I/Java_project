import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BossPanel extends JPanel {
    private BufferedImage backgroundImage;
    public Boss boss;
    private int bossX;
    private int bossY;
    private int deltaX;
    private int deltaY;
    private BufferedImage[] bossImages;
    private int currentFrame;

    public BossPanel() {
        boss = new Boss(10);
        setPreferredSize(new Dimension(996, 600)); // BossPanel size
        //loadImage
        bossImages = new BufferedImage[3];
        try {
            backgroundImage = ImageIO.read(new File("picture/boss.png"));
            bossImages[0] = ImageIO.read(new File("picture/boss.png"));
            bossImages[1] = ImageIO.read(new File("picture/boss_2.png"));
            bossImages[2] = ImageIO.read(new File("picture/boss_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set initial position and movement deltas
        bossX = 0;
        bossY = 0;
        deltaX = 5;
        deltaY = 5;
        // Start a timer to update Boss position every 100 milliseconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                updateBossPosition();
                updateBossAnimation();
                repaint(); // Trigger repainting of the BossPanel
            }
        }, 100, 200);
    }
    
    private void updateBossAnimation() {
        // Increment current frame index
        currentFrame++;
        if (currentFrame >= bossImages.length) {
            currentFrame = 0;
        }
    }

    private void updateBossPosition() {
        // Update Boss position based on deltas
        bossX += deltaX;
        bossY += deltaY;

        // Check boundaries and reverse direction if needed
        if (bossX <= 0 || bossX + backgroundImage.getWidth() >= getWidth()) {
            deltaX = -deltaX;
        }
        if (bossY <= 0 || bossY + backgroundImage.getHeight() >= getHeight()) {
            deltaY = -deltaY;
        }
    }
    /*
    public void setBossHealth(int health) {
        boss.setHealth(health);
        repaint(); // Update Boss health
    }
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (backgroundImage != null) {
            // g.drawImage(backgroundImage, bossX, bossY, getWidth(), getHeight(), this);
            g.drawImage(bossImages[currentFrame], bossX, bossY,getWidth(), getHeight(), this);
        }

        // Draw Boss health bar
        g.setColor(Color.RED);
        g.fillRect(10, 10,boss.getHealth() * 20, 30);
        g.setColor(Color.BLACK);
        // Set the stroke to bold
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3)); // Set the desired thickness
        g2d.drawRect(10, 10, boss.getHealth()* 20, 30);
        if(boss.getHealth() > 10){
            g2d.drawRect(11, 11, boss.getHealth()* 20, 30);            
        }
        else{
            g2d.drawRect(11, 11, 10*20, 30);
        }
        g2d.setStroke(oldStroke); // Restore the old stroke
        Font font = new Font("Arial", Font.BOLD, 26);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Boss health: " + boss.getHealth(), 10, 70);

    }

    public void update() {
        System.out.println("Boss Panel update!");
        repaint();
    }
}
