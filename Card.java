/**
 * Card
 */
public class Card {

    private SymbolType symbol;
    private int number;

    public Card(SymbolType symbol, int number){
        if(1<= number && number <= 13){
            this.symbol = symbol;
            this.number = number;
        }else{
            this.symbol = symbol;
            this.number = 1;
        }
    }

    public String toString(){
        return "[" + symbol.toString() + (number >=10 ? "" : " ") + String.valueOf(number) + "]";
    }

    public SymbolType getSymbol(){
        return symbol;
    }

    public int getNumber(){
        return number;
    }

    public boolean symbolEquals(Card card){
        return card != null && this.getNumber() == card.getNumber();
    }

    public boolean numberEquals(Card card){
        return card != null && this.getNumber() == card.getNumber();
    }

    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }
        if(!(object instanceof Card)){
            return false;
        }
        Card card = (Card) object;
        return card.getSymbol() == this.getSymbol() && card.getNumber() == this.getNumber();
    }

    @Override
    public int hashCode(){
        final int prime = 31; //resultには31をかけるのが主流(計算の都合)
        int result = 1;

        result = prime * result + number;
        result = prime * result + (symbol != null ? symbol.hashCode() : 0);

        return result;
    }
}