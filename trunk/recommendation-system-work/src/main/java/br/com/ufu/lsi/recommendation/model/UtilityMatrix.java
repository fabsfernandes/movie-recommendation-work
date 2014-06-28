package br.com.ufu.lsi.recommendation.model;

import java.util.HashMap;


public class UtilityMatrix {

    HashMap< Long,User > users = new HashMap< Long,User >();

    HashMap< Long,Movie > movies = new HashMap< Long,Movie >();

    
    public HashMap< Long,User > getUsers() {
    
        return users;
    }

    
    public void setUsers( HashMap< Long,User > users ) {
    
        this.users = users;
    }

    
    public HashMap< Long,Movie > getMovies() {
    
        return movies;
    }

    
    public void setMovies( HashMap< Long,Movie > movies ) {
    
        this.movies = movies;
    }

}
