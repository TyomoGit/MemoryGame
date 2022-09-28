import java.util.Optional;
import java.util.Random;

/**
 * Cardの集合を表します。
 */
public class Field {
    public static final int NUMBER_OF_CARDS = 52;
    public static final int NUMBER_OF_SYMBOLS = 4;
    public static final int NUMBER_DISPLAY_IN_LINE = 9;
    private Card[] field;

    public Field(){
        field = new Card[NUMBER_OF_CARDS];
        Random random = new Random();

        for (int i = 0; i < field.length; i++) {
            Card card;
            do{
                int randomCardNum = random.nextInt(NUMBER_OF_CARDS / NUMBER_OF_SYMBOLS) + 1;
                int randomNumForSymbol = random.nextInt(4);
                SymbolType randomCardSymbol;
                switch(randomNumForSymbol){
                    default:
                    case 0:
                        randomCardSymbol = SymbolType.SPADE;
                        break;
                    case 1:
                        randomCardSymbol = SymbolType.HEART;
                        break;
                    case 2:
                        randomCardSymbol = SymbolType.DIAMOND;
                        break;
                    case 3:
                        randomCardSymbol = SymbolType.CLOVER;
                        break;
                }
                card = new Card(randomCardSymbol, randomCardNum);
            }while(fieldContains(card));
            field[i] = card;
        }

        
    }

    private boolean fieldContains(Card card){
        boolean flag = false;

        for (int i = 0; i < field.length; i++) {
            if(card.equals(field[i])){
                flag = true;
                break;
            }
            
        }
        return flag;
    }

    public Optional<Card> getCard(Position position){
        int index = (position.getLine() -1 ) * NUMBER_DISPLAY_IN_LINE + (position.getColumn()-1);
        if(index == 46-1 || index == 54-1){
            return Optional.empty();
        }else if(index > 46-1){
            index--;
        }
        if(index > field.length - 1){
            return Optional.empty();
        }
        Card card = field[index];
        return Optional.ofNullable(card);
    }

    public void placeFaceUp(Position position){
        Optional<Card> card = getCard(position);
        if(card.isPresent()){
            card.get().placeFaceUp();
        }
    }

    public void show(){
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        String ret = "";

        char headerChar = 'A';
        for (int i = 0; i < NUMBER_DISPLAY_IN_LINE; i++) {
            if(i == 0){
                ret += " |  " + headerChar;
            }else{
                ret += "     " + headerChar;
            }
            headerChar++;

        }
        ret += "  " + "\n";

        for (int i = 0; i < NUMBER_DISPLAY_IN_LINE; i++) {
            if(i == 0){
                ret += "-+-----";
            }else{
                ret += "------";
            }
        }
        ret += "\n";

        int columnNumber = 1;
        for (int i = 0; i < field.length; i++) {
            if(i == 0){
                ret += columnNumber + "|";
            }
            if(i == 45){
                ret += "      ";
            }
            ret += field[i].toString() + " ";
            if((i+1) % NUMBER_DISPLAY_IN_LINE == 0){
                columnNumber++;
                ret += "\n";
                ret += columnNumber + "|";
            }
        }
        ret += "     ";

        return ret;
    }
}
