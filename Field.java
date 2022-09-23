import java.util.Optional;
import java.util.Random;

public class Field {
    private static final int NUMBER_OF_CARDS = 52;
    private static final int NUMBER_OF_SYMBOLS = 4;
    private static final int NUMBER_DISPLAY_IN_LINE = 9;
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

    public Card[] getField(){
        return field;
    }

    public void show(){
        char headerChar = 'A';
        for (int i = 0; i < NUMBER_DISPLAY_IN_LINE; i++) {
            if(i == 0){
                System.out.print(" |  " + headerChar);
            }else{
                System.out.print("     " + headerChar);
            }
            headerChar++;

        }
        System.out.println();

        for (int i = 0; i < NUMBER_DISPLAY_IN_LINE; i++) {
            if(i == 0){
                System.out.print("-+-----");
            }else{
                System.out.print("------");
            }
        }
        System.out.println();

        int columnNumber = 1;
        for (int i = 0; i < field.length; i++) {
            if(i == 0){
                System.out.print(columnNumber + "|");
            }
            if(i == 45){
                System.out.print("      ");
            }
            System.out.print(field[i].toString() + " ");
            if((i+1) % NUMBER_DISPLAY_IN_LINE == 0){
                columnNumber++;
                System.out.println();
                System.out.print(columnNumber + "|");
            }
        }
        System.out.println();
    }

    /*
     * "a1", "4B"などの表に対応する文字列を数字に変換します。
     */
    public static Optional<String> convertIntoCoorfinate(String input){
        final int LINE_MAX = NUMBER_OF_CARDS / NUMBER_DISPLAY_IN_LINE + 1;
        final int COLUMN_MAX = NUMBER_DISPLAY_IN_LINE;

        input = input.replaceAll(" ", "");

        if(input.length() != 2) return Optional.empty();
        
        char lineDigit = 0;
        char columnAlphabet = 0;
        for (int i = 0; i < 2; i++) {
            if(Character.isDigit(input.charAt(i))){
                lineDigit = input.charAt(i);
            }else{
                columnAlphabet = input.charAt(i);
            }
        }
        if(lineDigit < 0x0030 || LINE_MAX < Integer.parseInt(String.valueOf(lineDigit))){
            return Optional.empty();
        }
        if(0x0061 <= columnAlphabet){
            ///アルファベット小文字から0x0020を引くとアルファベット大文字になる。
            columnAlphabet -= 0x0020;
        }
        if(columnAlphabet < 0x0041 || 0x005A < columnAlphabet){
            System.out.printf("line: %c, col: %c\n", lineDigit, columnAlphabet);
            return Optional.empty();
        }
        if((0x0041 + COLUMN_MAX) < columnAlphabet){
            return Optional.empty();
        }
        
        int columnNumber = columnAlphabet - 0x0041 + 1;

        return Optional.ofNullable(columnNumber + "," + lineDigit);
    }
}
