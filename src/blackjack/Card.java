package blackjack;

public class Card {
    
    private String name;
    private int value;
    private boolean hidden;
    private boolean ace;
    
    public Card(String name, int value){
        this.name = name;
        this.value = value;
        this.ace = false;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }
    
    public boolean getHidden(){
        return this.hidden;
    }
    
    public boolean getAce(){
        return this.ace;
    }
    
    public void setAce(boolean ace){
        this.ace = ace;
    }
    
}
