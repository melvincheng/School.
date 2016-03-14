import java.util.Random;

public class TimeBoundedPi{
    static public void main(String[] args) throws Exception{
        Random R = new Random();
        long total = 0;
        long inCircle = 0;
        double x   = 0;
        double y   = 0;
        double pi = 0;

        final long startTime = System.currentTimeMillis();
        long currTime = System.currentTimeMillis();

        int iteration = 0;
        while((currTime - startTime)<Long.parseLong(args[0])) {

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
        System.out.println("Total iteration: " + iteration);
        System.out.println("Estimation: " + pi);
        System.out.println("Error: " + Math.abs(Math.PI - pi));
    }

}
