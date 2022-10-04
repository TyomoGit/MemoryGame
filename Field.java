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

    // debug ONLY!!
    @Deprecated
    public Card[] getField(){
        return field;
    }

    private boolean checkPosition(Position position){
        if(position == null) return false;

        int index = (position.getLine() -1 ) * NUMBER_DISPLAY_IN_LINE + (position.getColumn()-1);
        if(index == 46-1 || index == 54-1){
            return false;
        }else if(index > 46-1){
            index--;
        }
        if(index > field.length - 1){
            return false;
        }

        return true;
    }

    public boolean isEmpty(){
        for (int i = 0; i < field.length; i++) {
            if(field[i] != null){
                return false;
            }
        }
        return true;
    }

    public Optional<Position> positionOf(Card card){
        if(card == null)return Optional.empty();
        int index = -1;
        for (int i = 0; i < field.length; i++) {
            try {
                if(field[i].equals(card)){
                    index = i;
                    break;
                }
            } catch (NullPointerException e) {}
        }
        if(index == -1){
            return Optional.empty();
        }else{
            if(index + 1 >= 46) index++; 
            int line = ((index) / NUMBER_DISPLAY_IN_LINE) + 1;
            int column = ((index) % NUMBER_DISPLAY_IN_LINE) + 1;
            return Optional.ofNullable(new Position(line, column));
        }
    }

    public void remove(Position position){
        if(position == null)return;
        if(!checkPosition(position)) return;
        int index = (position.getLine() -1 ) * NUMBER_DISPLAY_IN_LINE + (position.getColumn()-1);
        if(index >= 46)index--;
        field[index] = null;
    }

    // public Optional<Card> getCard(Position position){
    //     Optional<Card> ret = Optional.empty();

    //     if(position == null)return Optional.empty();
    //     if(checkPosition(position)){
    //         int index = (position.getLine() -1 ) * NUMBER_DISPLAY_IN_LINE + (position.getColumn()-1);
    //         if(index >= 46)index--;
    //         Card card = field[index];
    //         return Optional.ofNullable(card);
    //     }else{
    //         return Optional.empty();
    //     }
    // }

    public Optional<Card> getCard(Position position){
        Optional<Card> ret = Optional.empty();

        if(position != null && checkPosition(position)){
            int index = (position.getLine() -1 ) * NUMBER_DISPLAY_IN_LINE + (position.getColumn()-1);
            if(index >= 46)index--;
            Card card = field[index];
            return Optional.ofNullable(card);
        }
        return ret;
    }

    public void placeFaceUp(Position position){
        place(position, true);
    }

    public void placeFaceDown(Position position){
        place(position, false);
    }

    public void place(Position position, boolean faceUp){
        Optional<Card> card = getCard(position);
        if(card.isPresent()) return;
        if(faceUp){
            card.get().placeFaceUp();
        }else{
            card.get().placeFaceDown();
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
            if(field[i] == null){
                ret += "     " + " ";
            }else{
                ret += field[i].toString() + " ";
            }
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
