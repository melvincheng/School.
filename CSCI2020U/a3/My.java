import java.util.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;


public class My extends JFrame {
    private static String filename;
    public My(){
        super("My");

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
        new My();
    }
    
    public void paint(Graphics g) {

        Map<String,Float> map = new HashMap<String,Float>();
        Map<String,Float> mapCount = new HashMap<String,Float>();
        

        List<Record> list = new ArrayList<Record>();
        RecordLoader file = new ParseFile();

        List<String> sectorList = new ArrayList<String>();
        
        list = file.load(filename);
        float temp = 0.0f, count = 0.0f;

        for(Record data : list){
            if(map.containsKey(data.sector)){
                temp = (float)(map.get(data.sector));
                temp = temp + data.salary;
                map.put(data.sector,temp);
                count = mapCount.get(data.sector);
                count++;
                mapCount.put(data.sector,count);
            } else {
                sectorList.add(data.sector);
                map.put(data.sector,data.salary);
                mapCount.put(data.sector,0.0f);
            }
        }

        float result = 0.0f, max = 0.0f;
        int color1 = 255, color2 = 255, color3 = 255, pos = 250;


        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawString("Average salary per person over different sectors",400,50);


        g2.drawLine(200,50,200,900);
        g2.drawLine(200,900,700,900);
        for(String data:sectorList){
            result = map.get(data)/mapCount.get(data);
            if(max<result)
                max = result;
            g2.fill(new Rectangle2D.Double(pos,900 - (result/250),10,(result/250)));
            g2.fill(new Rectangle2D.Double(620,pos-10,10,10));
            

            g2.setColor(new Color(0,0,0));
            g2.drawString(data,650,pos);
            g2.draw(new Rectangle2D.Double(pos,900 - (result/250),10,(result/250)));
            
            g2.setColor(new Color(color1,color2,color3));
            color1 = color1 - 30;
            color2 = color2 - 20;
            color3 = color3 - 10;
            pos = pos + 25;
        }
        
        max = (max/250)/32;
        g2.setColor(new Color(0,0,0));
        g2.drawString("Average salary",25,500);
        g2.drawString("Sectors",500,950);
        for(int z=900,y=0;z>100;z=z-25,y=y+3){
            g2.drawString(Float.toString(((float)y)*max),120,z);
        }
    }
}   