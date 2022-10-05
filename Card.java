import java.util.Objects;

/**
 * トランプのカードを表します。
 * 今のところジョーカーは表せない。
 */
public class Card {

    private SymbolType symbol = SymbolType.SPADE;
    private int number = 1;
    private boolean placedFaceUp = false;

    /**
     * placedFaceUpの初期値はfalse（裏返し）。
     * @param symbol
     * @param number
     */
    public Card(SymbolType symbol, int number){
        if(1<= number && number <= 13){
            this.number = number;
        }
        if(symbol != null){
            this.symbol = symbol;
        }
    }

    public void placeFaceUp(){
        placedFaceUp = true;
    }

    public void placeFaceDown(){
        placedFaceUp = false;
    }

    public boolean isPlacedFaceUp(){
        return placedFaceUp;
    }

    public SymbolType getSymbol(){
        return symbol;
    }

    public int getNumber(){
        return number;
    }

    public boolean symbolEquals(Card card){
        return card != null && this.getSymbol() == card.getSymbol();
    }

    public boolean numberEquals(Card card){
        return card != null && this.getNumber() == card.getNumber();
    }

    @Override
    public String toString(){
        String ret = "";
        if(this.placedFaceUp){
            ret = String.format("[%s%s%d]", symbol.toString(), number >=10 ? "" : " ", number);
        }else{
            ret = "[■■■]";
        }
        return ret;
    }

    @Override
    public boolean equals(Object object){
        boolean ret = false;
        if(object instanceof Card){
            Card card = (Card) object;
            ret = card.getSymbol() == this.getSymbol() && card.getNumber() == this.getNumber();
        }
        return ret;
    }

    @Override
    public int hashCode(){
        final int prime = 31; //resultには31をかけるのが主流(計算の都合)
        int result = 1;

        result = prime * result + Integer.valueOf(number).hashCode();
        result = prime * result + Objects.hashCode(symbol);

        return result;
    }
}