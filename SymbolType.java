/**
 * トランプの模様の種類を表します。
 */
public enum SymbolType{
    SPADE("\u2660"),
    HEART("\u2665"),
    DIAMOND("\u2666"),
    CLOVER("\u2663");

    String symbolString;
    SymbolType(String symbolString){
        this.symbolString = symbolString;
    }

    @Override
    public String toString(){
        return symbolString;
    }
}