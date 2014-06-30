package br.com.ufu.lsi.recommendation.statistics;


public class Accuracy {
    
    Double model1;
    
    Double model2;

    
    public Double getModel1() {
    
        return model1;
    }

    
    public void setModel1( Double model1 ) {
    
        this.model1 = model1;
    }

    
    public Double getModel2() {
    
        return model2;
    }

    
    public void setModel2( Double model2 ) {
    
        this.model2 = model2;
    }


    @Override
    public String toString() {

        return "Accuracy [model1=" + model1 + ", model2=" + model2 + "]";
    }

}
