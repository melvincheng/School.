import java.util.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;


public class Pie extends JFrame {
    private static String filename;
    public Pie(){
        super("Pie");

        setSize(1000,1000);
        setVisible(true);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose(); System.exit(0);
            }
        }
        );
    }
    public static void main(String[] args) {
        filename = args[0];
        new Pie();
    }
    
    public void paint(Graphics g) {

        Map<String,Float> map;
        Set<Map.Entry<String,Float>> set;
        Iterator<Map.Entry<String,Float>> iterator;

        List<Record> list = new ArrayList<Record>();
        RecordLoader file = new ParseFile();
        
        GroupBy group = new GroupBy();

        String key;

        list = file.load(filename);
        group.setData(list);
        group.groupby(0);

        set = group.set;
        map = group.map;
        iterator = group.iterator;

        float total = 0.0f, result = 0.0f, angle = 0.0f;
        int color = 255, pos = 300;

        while(iterator.hasNext()){
            total = total + iterator.next().getValue();
        }

        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawString("Salary over different sectors",400,50);
        
        iterator = set.iterator();
        
        while(iterator.hasNext()){
            key = iterator.next().getKey();
            result = map.get(key) / total;
            result = result*360.0f;
            g2.fill(new Arc2D.Double(100.0d, 100.0d, 500.0d,500.0d, angle,result, Arc2D.PIE));
            g2.fill(new Rectangle2D.Double(630,pos-10,10,10));
            g2.setColor(new Color(0,0,0));
            g2.drawString(key,650,pos);
            angle = angle + result;
            g2.setColor(new Color(color,color,color));
            color = color - 30;
            pos = pos + 20;
        }
    }
}   