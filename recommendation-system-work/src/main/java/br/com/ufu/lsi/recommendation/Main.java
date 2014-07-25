package br.com.ufu.lsi.recommendation;


import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.recommendation.model.Movie;
import br.com.ufu.lsi.recommendation.model.User;
import br.com.ufu.lsi.recommendation.model.UtilityMatrix;


public class Main {

    /*private static final String DIS_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/dis/d";
    private static final String DIS_BOOL_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/dis-bool/d";
    private static final String PATH_RATE_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/MU_rates.txt";
    private static final String PATH_BOOL_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/treino/MU_bool.txt";*/
    
    private static final String DIS_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/dis/d";
    private static final String DIS_BOOL_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/dis-bool/d";
    private static final String PATH_RATE_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/MU_rates.txt";
    private static final String PATH_BOOL_MATRIX = "/home/fabiola/Desktop/Doutorado/DataMining/Projeto-Recomendacao/teste/MU_bool.txt";
    
    UserUtilityMatrixGeneration userUtilityMatrixGeneration = new UserUtilityMatrixGeneration();

    UtilityMatrix utilityMatrix = new UtilityMatrix();


    public static void main( String... args ) throws Exception {
        //generateConsolidatedFile();
        //generateConsolidatedFileBoolean();

        Main main = new Main();

        UserUtilityMatrixGeneration.readFile( main.userUtilityMatrixGeneration );

        /*// generate rating Di files
        UtilityMatrixGenerator.readFile( main.utilityMatrix, false );
        
        UtilityMatrixGenerator.printMatrix( main.utilityMatrix, PATH_RATE_MATRIX );

        HashMap< Long, User > users = main.utilityMatrix.getUsers();
        
        Collections.sort(main.utilityMatrix.getUsersSparsity(), new Comparator<User>() {
            
            public int compare(User  u1, User  u2)
            {

                return  u2.getSparsityDegree().compareTo( u1.getSparsityDegree() );
            }
        });
        for( User u : main.utilityMatrix.getUsersSparsity() ){
        System.out.println( "User: " + u.getId() + "#movies: " + u.getRatings().size() + " Sparsity: " + u.getSparsityDegree() );
        }

        int i=0;
        //for ( Map.Entry< Long, User > ratingForMovieMap : users.entrySet() ) {
        for( User user : main.utilityMatrix.getUsersSparsity() ){
            
            i++;
            PrintWriter writer = new PrintWriter( DIS_PATH + i + ".arff", "UTF-8");
            
            //User user = ratingForMovieMap.getValue();
            StringBuilder di = new StringBuilder();
            di.append( main.generateArffFileRelationName( Integer.parseInt(user.getId().toString()), false ) );
            di.append( main.generateArffFileHeader() );
            di.append( main.generateArffFileData( user ) );            
            writer.print( di );
            writer.close();
        }*/

        // generate bool Di files
        UtilityMatrixGenerator.readFile( main.utilityMatrix, true );
        
        UtilityMatrixGenerator.printMatrix( main.utilityMatrix, PATH_BOOL_MATRIX );

        HashMap< Long, User > users = main.utilityMatrix.getUsers();
        
        Collections.sort(main.utilityMatrix.getUsersSparsity(), new Comparator<User>() {
            
            public int compare(User  u1, User  u2)
            {

                return  u2.getSparsityDegree().compareTo( u1.getSparsityDegree() );
            }
        });
        for( User u : main.utilityMatrix.getUsersSparsity() ){
        System.out.println( "User: " + u.getId() + "#movies: " + u.getRatings().size() + " Sparsity: " + u.getSparsityDegree() );
        }
        
        int i=0;
        //for ( Map.Entry< Long, User > ratingForMovieMap : users.entrySet() ) {
        for( User user : main.utilityMatrix.getUsersSparsity() ){
        
            i++;
            PrintWriter writer = new PrintWriter( DIS_BOOL_PATH + i + "-bool.arff", "UTF-8");
            
            //User user = ratingForMovieMap.getValue();
            StringBuilder di = new StringBuilder();
            di.append( main.generateArffFileRelationName( Integer.parseInt(user.getId().toString() ), true ) );
            di.append( main.generateArffFileHeader() );
            di.append( main.generateArffFileData( user ) );            
            writer.print( di );
            writer.close();
        }

    }
    
    public static void generateConsolidatedFileBoolean() throws Exception {
        Main main = new Main();

        UserUtilityMatrixGeneration.readFile( main.userUtilityMatrixGeneration );

        UtilityMatrixGenerator.readFile( main.utilityMatrix, true );
        
        UtilityMatrixGenerator.printMatrix( main.utilityMatrix, PATH_BOOL_MATRIX );

        HashMap< Long, User > users = main.utilityMatrix.getUsers();
        
        Collections.sort(main.utilityMatrix.getUsersSparsity(), new Comparator<User>() {
            
            public int compare(User  u1, User  u2)
            {

                return  u2.getSparsityDegree().compareTo( u1.getSparsityDegree() );
            }
        });
        for( User u : main.utilityMatrix.getUsersSparsity() ){
        System.out.println( "User: " + u.getId() + "#movies: " + u.getRatings().size() + " Sparsity: " + u.getSparsityDegree() );
        }
        
        PrintWriter writer = new PrintWriter( DIS_BOOL_PATH + "consolidated-bool.arff", "UTF-8");
        
        StringBuilder di = new StringBuilder();
        di.append( main.generateArffFileRelationName( 1, true ) );
        di.append( main.generateArffFileHeader() );
        for( User user : main.utilityMatrix.getUsersSparsity() ){
            
            di.append( main.generateArffFileData( user ) );            
        }
        writer.print( di );
        writer.close();
    }
    

    public static void generateConsolidatedFile() throws Exception {
        Main main = new Main();

        UserUtilityMatrixGeneration.readFile( main.userUtilityMatrixGeneration );

        UtilityMatrixGenerator.readFile( main.utilityMatrix, false );
        
        UtilityMatrixGenerator.printMatrix( main.utilityMatrix, PATH_RATE_MATRIX );

        HashMap< Long, User > users = main.utilityMatrix.getUsers();
        
        Collections.sort(main.utilityMatrix.getUsersSparsity(), new Comparator<User>() {
            
            public int compare(User  u1, User  u2)
            {

                return  u2.getSparsityDegree().compareTo( u1.getSparsityDegree() );
            }
        });
        for( User u : main.utilityMatrix.getUsersSparsity() ){
        System.out.println( "User: " + u.getId() + "#movies: " + u.getRatings().size() + " Sparsity: " + u.getSparsityDegree() );
        }

        
        PrintWriter writer = new PrintWriter( DIS_PATH + "consolidated.arff", "UTF-8");
        StringBuilder di = new StringBuilder();
        di.append( main.generateArffFileRelationName( 1, false ) );
        di.append( main.generateArffFileHeader() );
        for( User user : main.utilityMatrix.getUsersSparsity() ){            
            
            di.append( main.generateArffFileData( user ) );            
            
        }
        writer.print( di );
        writer.close();
    }

    public String generateArffFileRelationName( int di, boolean booleanRates ) {

        String bool = booleanRates ? "bool" : "rates";
        String relation = ( "@relation um-" + di + "-" + bool + "\n\n" );

        return relation;
    }


    public String generateArffFileHeader() {

        StringBuilder header = new StringBuilder();

        //header.append( "@attribute movie_id numeric\n" );
        header.append( "@attribute gender {" );
        for ( String gender : userUtilityMatrixGeneration.genders ) {
            header.append( gender + ", " );
        }
        int index = header.lastIndexOf( ", " );
        header.replace( index, header.toString().length(), "}\n" );

        header.append( "@attribute director {" );
        for ( String director : userUtilityMatrixGeneration.directors ) {
            header.append( director + ", " );
        }
        index = header.lastIndexOf( ", " );
        header.replace( index, header.toString().length(), "}\n" );

        header.append( "@attribute starring {" );
        for ( String starring : userUtilityMatrixGeneration.starrings ) {
            header.append( starring + ", " );
        }
        index = header.lastIndexOf( ", " );
        header.replace( index, header.toString().length(), "}\n" );

        header.append( "@attribute language {" );
        for ( String language : userUtilityMatrixGeneration.languages ) {
            header.append( language + ", " );
        }
        index = header.lastIndexOf( ", " );
        header.replace( index, header.toString().length(), "}\n" );

        header.append( "@attribute class {" );
        for ( Double rating : utilityMatrix.getRatings() ) {
            header.append( rating + ", " );
        }
        index = header.lastIndexOf( ", " );
        header.replace( index, header.toString().length(), "}\n" );

        header.append( "@data\n" );
        return header.toString();
    }


    public String generateArffFileData( User user ) {

        StringBuilder data = new StringBuilder();

        HashMap< Long, Double > ratingsForMovies = user.getRatings();
        for ( Map.Entry< Long, Double > ratingForMovieMap : ratingsForMovies.entrySet() ) {
            Long movieId = ratingForMovieMap.getKey();
            Double movieRating = ratingForMovieMap.getValue();

            List< Movie > movies = userUtilityMatrixGeneration.movies;
            for ( Movie movie : movies ) {
                if ( movie.getId().equals( movieId ) ) {
                    //data.append( movieId + "," );
                    data.append( movie.getGender() + "," );
                    data.append( movie.getDirector() + "," );
                    data.append( movie.getStarring() + "," );
                    data.append( movie.getLanguage() + "," );
                    data.append( movieRating + "\n" );
                }
            }
        }

        return data.toString();
    }

}
