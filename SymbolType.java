public enum SymbolType{
    SPADE("\u2660"),
    HEART("\u2665"),
    DIAMOND("\u2666"),
    CLOVER("\u2663");

    String symbol;
    SymbolType(String symbol){
        this.symbol = symbol;
    }

    public String toString(){
        return symbol;
    }
}