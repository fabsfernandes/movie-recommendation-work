package br.com.ufu.lsi.recommendation;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.ufu.lsi.recommendation.model.Movie;


public class UserUtilityMatrixGeneration {

    private static final String PATH_MOVIES = "/home/fabiola/Desktop/Doutorado/DataMining/Trabalho-Recomendacao/dadosFilmes.txt";

    List< Movie > movies = new ArrayList< Movie >();
    
    Set<Long> moviesIds = new HashSet<Long>();
    Set<String> genders = new HashSet<String>();
    Set<String> directors = new HashSet<String>();
    Set<String> starrings = new HashSet<String>();
    Set<String> languages = new HashSet<String>();

    public static String handleName( String value ) {

        value = value.replaceAll( "\"", "" );
        if ( "null".equals( value ) )
            return null;
        value += "'";
        value = "'" + value;
        return value;
    }


    public static void handleLine( String currentLine, UserUtilityMatrixGeneration userUtilityMatrixGeneration ) {

        String[] tokens = currentLine.split( "\t" );

        Long movieId = Long.parseLong( tokens[ 0 ] );
        userUtilityMatrixGeneration.moviesIds.add( movieId );

        List< String > genders = new ArrayList< String >();
        String g1 = handleName( tokens[ 1 ] );
        if ( g1 != null )
            genders.add( g1 );
        String g2 = handleName( tokens[ 2 ] );
        if ( g2 != null )
            genders.add( g2 );
        String g3 = handleName( tokens[ 3 ] );
        if ( g3 != null )
            genders.add( g3 );
        String g4 = handleName( tokens[ 4 ] );
        if ( g4 != null )
            genders.add( g4 );
        String g5 = handleName( tokens[ 5 ] );
        if ( g5 != null )
            genders.add( g5 );

        String director = handleName( tokens[ 6 ] );
        if( director == null )
            director = "?";
        else userUtilityMatrixGeneration.directors.add( director );
        

        List< String > starrings = new ArrayList< String >();
        String s1 = handleName( tokens[ 7 ] );
        if ( s1 != null )
            starrings.add( s1 );
        String s2 = handleName( tokens[ 8 ] );
        if ( s2 != null )
            starrings.add( s2 );
        String s3 = handleName( tokens[ 9 ] );
        if ( s3 != null )
            starrings.add( s3 );
        String s4 = handleName( tokens[ 10 ] );
        if ( s4 != null )
            starrings.add( s4 );

        String language = handleName( tokens[ 11 ] );
        if( language == null )
            language = "?";        
        else userUtilityMatrixGeneration.languages.add( language );

        for ( int i = 0; i < 3 && i < genders.size(); i++ ) {
            userUtilityMatrixGeneration.genders.add( genders.get( i ) );
            for ( int j = 0; j < 3 && j < starrings.size(); j++ ) {
                userUtilityMatrixGeneration.starrings.add( starrings.get( j ) );
                Movie m = new Movie( movieId );
                m.setDirector( director );
                m.setGender( genders.get( i ) );
                m.setStarring( starrings.get( j ) );
                m.setLanguage( language );
                
                userUtilityMatrixGeneration.movies.add( m );
            }
        }
    }


    public static void readFile( UserUtilityMatrixGeneration userUtilityMatrixGeneration ) {

        BufferedReader br = null;

        try {
            String sCurrentLine;

            br = new BufferedReader( new FileReader( PATH_MOVIES ) );

            // exclude first line
            br.readLine();

            while ( ( sCurrentLine = br.readLine() ) != null ) {
                handleLine( sCurrentLine, userUtilityMatrixGeneration );
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


    public static void main( String... args ) {

        UserUtilityMatrixGeneration userUtilityMatrixGeneration = new UserUtilityMatrixGeneration();
        readFile( userUtilityMatrixGeneration );
        
        for( Movie m : userUtilityMatrixGeneration.movies ){
            System.out.println( m.toStringFull() );
        }
        System.out.println( userUtilityMatrixGeneration.moviesIds );
        System.out.println( userUtilityMatrixGeneration.genders );
        System.out.println( userUtilityMatrixGeneration.directors );
        System.out.println( userUtilityMatrixGeneration.starrings );
        System.out.println( userUtilityMatrixGeneration.languages );
        
    }

}
