import java.util.*;
import java.util.Random;

public class ThreadedTimeBoundedPi{
    static public void main(String[] args) throws Exception{
        List<MonteCarlo> threads = new ArrayList<MonteCarlo>();

        int iteration = 0;
        long total = 0;
        long inCircle = 0;
        double pi;

        int count = Integer.parseInt(args[1]);
        for(int x=0;x<count;x++){
            threads.add(new MonteCarlo(args[0]));
            
        }
        for(int x=0;x<count;x++){
            threads.get(x).start();
        }
        for(int x=0;x<count;x++){
            threads.get(x).join();
        }
        for(int x=0;x<count;x++){
            total += threads.get(x).total;
            inCircle += threads.get(x).inCircle;
            iteration += threads.get(x).iteration;
        }
        pi = (double)inCircle / total * 4.0;
        System.out.println("Total iteration: " + (iteration));
        System.out.println("Estimation: " + pi);
        System.out.println("Error: " + Math.abs(Math.PI - pi));
    }
}


class MonteCarlo extends Thread{

    String time;
    long total = 0;
    long inCircle = 0;
    int iteration = 0;

    public MonteCarlo(String time){
        this.time = time;
    }
    public void run(){
        Random R = new Random();
        double x   = 0;
        double y   = 0;
        double pi = 0;

        final long startTime = System.currentTimeMillis();
        long currTime = System.currentTimeMillis();

        
        while((currTime - startTime)<Long.parseLong(time)) {

            x = R.nextDouble();
            y = R.nextDouble();
            if(x*x + y*y <= 1) {
                inCircle += 1;
            }
            total += 1;
            iteration += 1;

            pi = (double)inCircle / total * 4.0;
            currTime = System.currentTimeMillis();
        }
    }
}
