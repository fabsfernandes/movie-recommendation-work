package br.com.ufu.lsi.recommendation.statistics;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatisticalSignificanceCalculator {

    //private static final String PATH_RESULTS = "/home/fabiola/Desktop/Doutorado/DataMining/Trabalho-Recomendacao/notas_results.csv";
    private static final String PATH_RESULTS = "/home/fabiola/Desktop/Doutorado/DataMining/Trabalho-Recomendacao/bool_results.csv";

    private int significancePresence = 0;

    private int significanceAbsent = 0;

    private Map< String, Item > items = new HashMap< String, Item >();


    public static void main( String... args ) {

        StatisticalSignificanceCalculator calculator = new StatisticalSignificanceCalculator();
        calculator.readFile();

        calculator.calculateSignificance();

        for ( Map.Entry< String, Item > itemMap : calculator.items.entrySet() ) {
            
            Item i = itemMap.getValue();
            if ( i.getFloorLimit() <= 0.0 && i.getCeilingLimit() >= 0 )
                calculator.significanceAbsent++;
            else
                calculator.significancePresence++;
        }
        
        System.out.println( "#significancePresence = " + calculator.significancePresence );
        System.out.println( "#significanceAbsent = " + calculator.significanceAbsent );

    }


    public void calculateSignificance() {

        for ( Map.Entry< String, Item > itemMap : items.entrySet() ) {

            List< Accuracy > accuracies = itemMap.getValue().getAccuracies();

            double ac11 = accuracies.get( 0 ).getModel1();
            double ac21 = accuracies.get( 0 ).getModel2();
            double ac12 = accuracies.get( 1 ).getModel1();
            double ac22 = accuracies.get( 1 ).getModel2();
            double ac13 = accuracies.get( 2 ).getModel1();
            double ac23 = accuracies.get( 2 ).getModel2();
            double ac14 = accuracies.get( 3 ).getModel1();
            double ac24 = accuracies.get( 3 ).getModel2();
            double ac15 = accuracies.get( 4 ).getModel1();
            double ac25 = accuracies.get( 4 ).getModel2();

            double d1 = ( 1 - ac11 ) - ( 1 - ac21 );
            double d2 = ( 1 - ac12 ) - ( 1 - ac22 );
            double d3 = ( 1 - ac13 ) - ( 1 - ac23 );
            double d4 = ( 1 - ac14 ) - ( 1 - ac24 );
            double d5 = ( 1 - ac15 ) - ( 1 - ac25 );

            double dbar = ( d1 + d2 + d3 + d4 + d5 ) / 5;

            double variance = ( Math.pow( d1 - dbar, 2.0 ) + Math.pow( d2 - dbar, 2.0 ) + Math.pow( d3 - dbar, 2.0 )
                + Math.pow( d4 - dbar, 2.0 ) + Math.pow( d5 - dbar, 2.0 ) ) / 20.0;

            double deviation = Math.sqrt( variance );

            double floorLimit = dbar - ( 2.77 * deviation );
            double ceilingLimit = dbar + ( 2.77 * deviation );

            if ( floorLimit < ceilingLimit ) {
                itemMap.getValue().setFloorLimit( floorLimit );
                itemMap.getValue().setCeilingLimit( ceilingLimit );
            }
            else {
                itemMap.getValue().setFloorLimit( ceilingLimit );
                itemMap.getValue().setCeilingLimit( floorLimit );
            }
        }

    }


    public void handleLine( String currentLine ) {

        String[] tokens = currentLine.split( "," );

        String itemName = tokens[ 0 ];

        Item item = items.get( itemName );
        if ( item == null ) {
            Item it = new Item( itemName );
            items.put( itemName, it );
            item = it;
        }

        Accuracy acc = new Accuracy();
        acc.setModel1( Double.parseDouble( tokens[ 2 ] ) );
        acc.setModel2( Double.parseDouble( tokens[ 3 ] ) );
        item.getAccuracies().add( acc );
    }


    public void readFile() {

        BufferedReader br = null;

        try {
            String sCurrentLine;

            br = new BufferedReader( new FileReader( PATH_RESULTS ) );

            while ( ( sCurrentLine = br.readLine() ) != null ) {
                handleLine( sCurrentLine );
            }
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( br != null )
                    br.close();
            }
            catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }
    }
}
