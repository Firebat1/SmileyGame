import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Enemy {
    private int size;
    private int isHappy;
    private int xloc;
    private int yloc;
    private int speed;
    private int x = 0;
    public Enemy(int sped) {
        size = (int)(Math.random() * 200) + 1;
        xloc = (int)(Math.random() * 1600) + 1;
        yloc = (int)(Math.random() * 1600) + 1;
        speed = sped;
    }
    public Enemy(int tSize, int sped, int x, int y) {
        speed = sped;
        size = tSize;
        xloc = x;
        yloc = y;
    }
    public int getXLoc() {
        return xloc;
    }
    public int getYLoc() {
        return yloc;
    }
    public int getSize() {
        return size;
    }
    public void modifySize(int i) {
        size += i;
    }
    public void move() {
        int rand = (int)(Math.random() * 4);
        x ++;
        if (rand == 0) {
            xloc += speed;
        }
        if (rand == 1) {
            xloc -= speed;
        }
        if (rand == 2) {
            yloc += speed;
        }
        if (rand == 3) {
            yloc -= speed;
        }
        if (yloc > 1600 - size) {
            yloc -= speed;
        }
        if (xloc > 1600 - size) {
            xloc -= speed;
        }
        if (yloc < 0) {
            yloc += speed;
        }
        if (xloc < 0) {
            xloc += speed;
        }
        if (x > 250 && size > 10) {
            x = 0;
            size -= 1;
        }
    }
    public Enemy split() {
        if (Math.random() * 1000 == 1 && size > 40) {
            int tempsize = size;
            size *= .75;
            yloc -= size/2;
            xloc -= size/2;
            return new Enemy((int)(tempsize * .25), speed, xloc + size/2, yloc + size/2);
        }
        return null;
    }
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(xloc, yloc, size, size);
        g.setColor(Color.black);
        g.fillArc(xloc + size/4, yloc + (size/10)*5,size/2,size/2, 180 * isHappy, 180);
        g.fillOval(xloc+ size/10, yloc + (size / 50) + size/5 , size/4, size/4);
        g.fillOval(xloc+ size/10 + size/2, yloc + (size / 50) + size/5 , size/4, size/4);


    }
}
