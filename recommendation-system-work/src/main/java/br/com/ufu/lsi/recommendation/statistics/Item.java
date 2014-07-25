package br.com.ufu.lsi.recommendation.statistics;

import java.util.ArrayList;
import java.util.List;


public class Item {
    
    String name;
    
    List<Accuracy> accuracies;
    
    Double floorLimit;
    
    Double ceilingLimit;
    
    boolean significance;
    
    
    public Item( String name ){
        this.name = name;
        accuracies = new ArrayList< Accuracy >();
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {

        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Item other = (Item) obj;
        if ( name == null ) {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        return true;
    }

    
    public String getName() {
    
        return name;
    }

    
    public void setName( String name ) {
    
        this.name = name;
    }

    
    public List< Accuracy > getAccuracies() {
    
        return accuracies;
    }

    
    public void setAccuracies( List< Accuracy > accuracies ) {
    
        this.accuracies = accuracies;
    }

    @Override
    public String toString() {

        return "Item [name=" + name + ": [" + floorLimit + " , " + ceilingLimit + "]";
    }

    
    public Double getFloorLimit() {
    
        return floorLimit;
    }

    
    public void setFloorLimit( Double floorLimit ) {
    
        this.floorLimit = floorLimit;
    }

    
    public Double getCeilingLimit() {
    
        return ceilingLimit;
    }

    
    public void setCeilingLimit( Double ceilingLimit ) {
    
        this.ceilingLimit = ceilingLimit;
    }

    
    public boolean isSignificance() {
    
        return significance;
    }

    
    public void setSignificance( boolean significance ) {
    
        this.significance = significance;
    }

}
