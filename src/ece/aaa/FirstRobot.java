package ece.aaa;

import robocode.*;
import robocode.RobocodeFileWriter;

import java.io.*;


/**
 * MyFirstRobot - a sample robot by Mathew Nelson.
 * <p>
 * Moves in a seesaw motion, and spins the gun around at each end.
 *
 * @author Mathew A. Nelson (original)
 */
public class FirstRobot extends AdvancedRobot {
    String path = "winningRate.txt";;
    /**
     * MyFirstRobot's run method - Seesaw
     */
    public void run() {
        load();
        //emptyFile();
        while (true) {
            ahead(100); // Move ahead 100
            turnGunRight(360); // Spin gun around
            back(100); // Move back 100
            turnGunRight(360); // Spin gun around
        }
    }

    /**
     * Fire when we see a robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }

    /**
     * We were hit!  Turn perpendicular to the bullet,
     * so our seesaw might avoid a future shot.
     */
    public void onHitByBullet(HitByBulletEvent e) {
        turnLeft(90 - e.getBearing());
    }

    public void onWin(WinEvent e){
        try{
            System.out.println("print file");
            File file = getDataFile(path);
            RobocodeFileWriter fileWriter = new RobocodeFileWriter(file.getAbsolutePath(), true);
            fileWriter.write("Robot win 123!\n");
            fileWriter.close();
        }
        catch(Exception m){
            System.out.println(m);
        }
    }

    public void onDeath(DeathEvent event) {
        PrintStream w = null;
        try
        {
            File file = getDataFile(path);
            w = new PrintStream(new RobocodeFileOutputStream(file.getAbsolutePath(), true));
            w.println("Robot fails  ==");
            w.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException trying to write: " + e);
        }
        finally
        {
            try
            {
                if (w != null)
                    w.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception trying to close witer: " + e);
            }
        }
    }

    public void emptyFile() {
        try {
            System.out.println("empty file");
            File file = getDataFile(path);
            RobocodeFileWriter fw = new RobocodeFileWriter(file.getAbsolutePath(), true);
            fw.write("");
            fw.flush();
            fw.close();
        }
        catch(Exception m){
            System.out.println(m);
        }
    }

    public void load() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(getDataFile(path).getAbsoluteFile()));
            try {
                String str = in.readLine();
                System.out.println(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

