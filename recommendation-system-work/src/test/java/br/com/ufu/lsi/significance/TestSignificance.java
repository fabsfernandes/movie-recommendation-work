package br.com.ufu.lsi.significance;

import org.junit.Test;


public class TestSignificance {
    
    double ac11 = 0.8696;
    double ac21 = 0.8609;
    double ac12 = 0.8261;
    double ac22 = 0.8812;
    double ac13 = 0.8430;
    double ac23 = 0.8808;
    double ac14 = 0.8227;
    double ac24 = 0.8227;
    double ac15 = 0.8634;
    double ac25 = 0.8808;
    
    @Test
    public void test(){
        
        double d1 = (1-ac11) - (1-ac21); 
        double d2 = (1-ac12) - (1-ac22);
        double d3 = (1-ac13) - (1-ac23);
        double d4 = (1-ac14) - (1-ac24);
        double d5 = (1-ac15) - (1-ac25);
        
        /*double d1 = (1-ac21) - (1-ac11); 
        double d2 = (1-ac22) - (1-ac12);
        double d3 = (1-ac23) - (1-ac13);
        double d4 = (1-ac24) - (1-ac14);
        double d5 = (1-ac25) - (1-ac15);*/
        
        double dbar = (d1+d2+d3+d4+d5)/5;
        
        double variance = (Math.pow(d1-dbar,2.0) + Math.pow(d2-dbar,2.0) + Math.pow(d3-dbar,2.0) + Math.pow(d4-dbar,2.0) + Math.pow(d5-dbar,2.0) )/20.0;
        
        double deviation = Math.sqrt( variance );
        
        double floorLimit = dbar - (2.77 * deviation);
        double ceilingLimit = dbar + (2.77 * deviation);
        
        System.out.println( "[" + floorLimit + "," + ceilingLimit + "]");
        
    }

    @Test
    public void generateFileName(){
        for( int i = 200; i<=296; i++ ){
            System.out.print("\"d" + i + "-bool.arff\" ");
        }
        
    }
}
