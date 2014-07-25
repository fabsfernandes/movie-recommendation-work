package br.com.ufu.lsi.recommendation;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import br.com.ufu.lsi.recommendation.model.Movie;
import br.com.ufu.lsi.recommendation.model.User;
import br.com.ufu.lsi.recommendation.model.UtilityMatrix;


public class UtilityMatrixGenerator {
    
    /*private static final String PATH_RATES = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/ratings_training.txt";
    private static final String PATH_RATE_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/MU_rates.txt";
    private static final String PATH_BOOL_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/MU_bool.txt";
    */
    private static final String PATH_RATES = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/ratings_test.txt";
    private static final String PATH_RATE_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/MU_rates.txt";
    private static final String PATH_BOOL_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/MU_bool.txt";

    
    public static void handleLine( String currentLine, UtilityMatrix utilityMatrix, boolean booleanRates ) {    
        
        HashMap<Long,User> users = utilityMatrix.getUsers();
        HashMap<Long,Movie> movies = utilityMatrix.getMovies();

        String tokens[] = currentLine.split( "\t" );
        Long userId = Long.parseLong( tokens[0] );
        Long movieId = Long.parseLong( tokens[1] );
        Double rating = Double.parseDouble( tokens[2] );
        
        if( booleanRates ){
            if( rating >= 3.0 )
                rating = 1.0;
            else rating = 0.0;
        }            
        
        User user = users.get( userId );
        if( user == null ){
            User u = new User( userId );
            users.put( userId, u );
            user = u;
        }
        
        Movie movie = movies.get( movieId );
        if( movie == null ){
            Movie m = new Movie( movieId );
            movies.put( movieId, m );
            movie = m;
        }
        
        user.getRatings().put( movieId, rating );
        movie.getRatings().put( userId, rating );
        
        utilityMatrix.getRatings().add( rating );
    }
    
    public static void readFile( UtilityMatrix utilityMatrix, boolean booleanRates ){
        
        BufferedReader br = null;

        try {
            String sCurrentLine;

            br = new BufferedReader( new FileReader( PATH_RATES ) );

            while ( ( sCurrentLine = br.readLine() ) != null ) {
                handleLine( sCurrentLine, utilityMatrix, booleanRates );
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
    
    public static void printMatrix( UtilityMatrix utilityMatrix, String path ) throws FileNotFoundException, UnsupportedEncodingException{
        
        HashMap<Long,Movie> movies = utilityMatrix.getMovies();
        HashMap<Long,User> users = utilityMatrix.getUsers();
        
        System.out.println( "#users = " + users.size() );
        System.out.println( "#movies = " + movies.size() );
        
        PrintWriter writer = new PrintWriter( path, "UTF-8");
                
        // print first line        
        for (Map.Entry<Long,Movie> movieMap : movies.entrySet()) {
            writer.print("\t" + movieMap.getKey());
        }
        writer.println();
        
        // print matrix
        for (Map.Entry<Long,User> userMap : users.entrySet()) {
            Long userId = userMap.getKey();
            writer.print( userId );
            for (Map.Entry<Long,Movie> movieMap : movies.entrySet()) {
                Movie movie = movieMap.getValue();
                HashMap<Long,Double> movieRatings = movie.getRatings();
                Double rating = movieRatings.get( userId );
                writer.print( "\t" + rating );
            }
            writer.println();
            
            utilityMatrix.getUsersSparsity().add( userMap.getValue() );
            
            
        }
        
        writer.close();
        
        // print sparsity order
        Collections.sort(utilityMatrix.getUsersSparsity(), new Comparator<User>() {
                
                public int compare(User  u1, User  u2)
                {

                    return  u2.getSparsityDegree().compareTo( u1.getSparsityDegree() );
                }
            });
        /*for( User u : usersSparsity ){
            System.out.println( "User: " + u.getId() + "#movies: " + u.getRatings().size() + " Sparsity: " + u.getSparsityDegree() );
        }*/
    }

    public static void main( String... args ) throws Exception {
        
        UtilityMatrix utilityMatrix = new UtilityMatrix();
        
        readFile( utilityMatrix, false );
        
        printMatrix( utilityMatrix, PATH_RATE_MATRIX );
        
        readFile( utilityMatrix, true );
        
        printMatrix( utilityMatrix, PATH_BOOL_MATRIX );
    }
}
