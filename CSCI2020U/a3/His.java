import java.util.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;


public class His extends JFrame {
    private static String filename;
    public His(){
        super("His");

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
        new His();
    }
    
    public void paint(Graphics g) {
        Map<String,Float> map;
        Set<Map.Entry<String,Float>> set;
        Iterator<Map.Entry<String,Float>> iterator;

        List<Record> list = new ArrayList<Record>();
        RecordLoader file = new ParseFile();
        
        GroupBy group = new GroupBy();

        String key = "";

        Map<Integer,Integer> bin = new HashMap<Integer,Integer>();

        list = file.load(filename);
        group.setData(list);
        group.groupby(3);

        set = group.set;
        map = group.map;
        iterator = group.iterator;

        float interval = 0.0f, start = 100000.0f, max = 0.0f, min = 0.0f, range = 0.0f, next = 0.0f;
        int pos = 900, counter = 0, loop = 99;
        max = iterator.next().getValue();
        while(iterator.hasNext()){
            min=iterator.next().getValue();
        }

        range = max - min;
        
        interval = range/100;
        
        start = max - interval;
        

        iterator = set.iterator();
        key = iterator.next().getKey();
        

        while(loop>=0){

            if(start <= map.get(key) && iterator.hasNext()){
                counter++;
                key = iterator.next().getKey();
            }
            
            else{
                start = start - interval;
                bin.put(loop,counter);
                loop--;
                counter = 0;
            }
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("Salary of people sorted into bins of intervals of " + interval,300,100);
        for(int z=955,y=0;z>100;z=z-25,y=y+3){
            g2.drawString(Float.toString(100000.0f+interval*((float)y)),120,z);
        }
        for(int z=900,y=100;z>200;z=z-35,y=y-5){
            g2.drawString(Integer.toString(y),z,970);
        }
        for(int x = 99;x>=0;x--){   
            g2.draw(new Rectangle2D.Double(pos,(955-((bin.get(x))/25)),7,((bin.get(x))/25)));
            
            
            g2.drawString("Salary",25,500);
            g2.drawString("Bins",500,985);
            g2.setColor(new Color(0,0,0));
            pos = pos - 7;
            counter = 0;
        }
    }
}   