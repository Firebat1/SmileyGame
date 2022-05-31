import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Smiley {
    private int size;
    private int isHappy;
    private double xloc;
    private double yloc;
    private double speed;
    private String splitDirection;
    private int suprised;
    private boolean isSplitting;
    private int boundaryX;
    private int boundaryY;
    int x = 0;
    public Smiley(int s, int happy, int x, int y, String sDirection, int width, int height) {
        size = s;
        isHappy = happy;
        xloc = x;
        yloc = y;
        speed = 100/size;
        splitDirection = sDirection;
        isSplitting = false;
        boundaryX = width;
        boundaryY = height;
        if (happy >= 2) {
            suprised = 360;
        }
        else {
            suprised = 180;
        }
    }
    public int getXLoc() {
        return (int)xloc;
    }
    public int getYLoc() {
        return (int)yloc;
    }
    public int getSize() {
        return size;
    }
    public void modifySize(int i) {
        size += i/2;
    }
     private double getSpeed() {
        return speed;
    }
    public void modifySpeed(int i) {
        speed = i;
    }
    public void changeSplit() {
        if (splitDirection.equals("SIDEWAYS")) {
            splitDirection = "VERTICAL";
        }
        else {
            splitDirection = "SIDEWAYS";
        }
    }
    public void move(String direction) {
        if (!isSplitting) {
            speed = 100.0/size;
            x ++;
        }
        if (direction.equals("RIGHT")) {
            xloc += speed;
        }
        if (direction.equals("LEFT")) {
            xloc -= speed;
        }
        if (direction.equals("UP")) {
            yloc -= speed;
        }
        if (direction.equals("DOWN")) {
            yloc += speed;
        }
        if (yloc + size/2> boundaryY) {
            yloc -= speed;
        }
        if (xloc + size/2> boundaryX) {
            xloc -= speed;
        }
        if (yloc + size/2 < 0) {
            yloc += speed;
        }
        if (xloc + size/2 < 0) {
            xloc += speed;
        }
        if (x > 250 && size > 10) {
            x = 0;
            size --;
        }
    }

    public Smiley split(Graphics g, Smiley original) {
        x = 0;
        isSplitting = true;
        ArrayList<Smiley> temp = new ArrayList<>();
        yloc += size/4;
        xloc += size/4;
        size /= 2;
        temp.add(original);
        if (splitDirection.equals("SIDEWAYS")) {
            xloc += size/2.0;
            temp.add(new Smiley(size, (isHappy + 1)%3, (int)xloc - size, (int)yloc, splitDirection, boundaryX, boundaryY));
        }
        else {
            yloc += size/2.0;
            temp.add(new Smiley(size, isHappy + 1, (int)xloc, (int)yloc - size, splitDirection, boundaryX, boundaryY));
        }
        temp.get(1).modifySpeed(-100/temp.get(1).getSize());
        temp.get(1).changeSplit();
        changeSplit();
        isSplitting = false;
        return temp.get(1);
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval((int)xloc, (int)yloc, size, size);
        g.setColor(Color.red);
        g.fillArc((int)xloc + size/4, (int)yloc + (size/10)*4,size/2,size/2, 180 * isHappy, suprised);
        g.setColor(Color.black);
        g.fillOval((int)xloc+ size/10, (int)yloc + (size / 50) + size/5 , size/4, size/4);
        g.fillOval((int)xloc+ size/10 + size/2, (int)yloc + (size / 50) + size/5 , size/4, size/4);
    }

}
