package br.com.ufu.lsi.recommendation;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.recommendation.model.Movie;
import br.com.ufu.lsi.recommendation.model.User;
import br.com.ufu.lsi.recommendation.model.UtilityMatrix;


public class Main {

    private static final String DIS_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Trabalho-Recomendacao/dis/di";
    private static final String DIS_BOOL_PATH = "/home/fabiola/Desktop/Doutorado/DataMining/Trabalho-Recomendacao/dis-bool/di";
    
    UserUtilityMatrixGeneration userUtilityMatrixGeneration = new UserUtilityMatrixGeneration();

    UtilityMatrix utilityMatrix = new UtilityMatrix();


    public static void main( String... args ) throws Exception {

        Main main = new Main();

        UserUtilityMatrixGeneration.readFile( main.userUtilityMatrixGeneration );

        // generate rating Di files
        UtilityMatrixGenerator.readFile( main.utilityMatrix, false );

        HashMap< Long, User > users = main.utilityMatrix.getUsers();

        for ( Map.Entry< Long, User > ratingForMovieMap : users.entrySet() ) {
            
            PrintWriter writer = new PrintWriter( DIS_PATH + "-" + ratingForMovieMap.getKey() + ".arff", "UTF-8");
            
            User user = ratingForMovieMap.getValue();
            StringBuilder di = new StringBuilder();
            di.append( main.generateArffFileRelationName( Integer.parseInt(ratingForMovieMap.getKey().toString()), false ) );
            di.append( main.generateArffFileHeader() );
            di.append( main.generateArffFileData( user ) );            
            writer.print( di );
            writer.close();
        }

        // generate bool Di files
        UtilityMatrixGenerator.readFile( main.utilityMatrix, true );

        users = main.utilityMatrix.getUsers();
        
        for ( Map.Entry< Long, User > ratingForMovieMap : users.entrySet() ) {            
        
            PrintWriter writer = new PrintWriter( DIS_BOOL_PATH + "-" + ratingForMovieMap.getKey() + ".arff", "UTF-8");
            
            User user = ratingForMovieMap.getValue();
            StringBuilder di = new StringBuilder();
            di.append( main.generateArffFileRelationName( Integer.parseInt(ratingForMovieMap.getKey().toString()), true ) );
            di.append( main.generateArffFileHeader() );
            di.append( main.generateArffFileData( user ) );            
            writer.print( di );
            writer.close();
        }

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
