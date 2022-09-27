import java.util.Optional;

/**
 * 位置を表します。
 */
public class Position {
    private int line;
    private int column;

    public Position(int line, int colunm){
        this.line = line;
        this.column = colunm;
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }

    /*
     * "a1", "4B"などの表に対応する文字列を数字に変換します。
     */
    public static Optional<Position> valueOf(String input){
        final int LINE_MAX = Field.NUMBER_OF_CARDS / Field.NUMBER_DISPLAY_IN_LINE + 1;
        final int COLUMN_MAX = Field.NUMBER_DISPLAY_IN_LINE;
        final Position[] exceptions = {new Position(6, 1), new Position(6, 9)};

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
            return Optional.empty();
        }
        if((0x0041 + COLUMN_MAX) < columnAlphabet){
            return Optional.empty();
        }
        
        int columnNumber = columnAlphabet - 0x0041 + 1;

        Position result = new Position(Integer.parseInt(String.valueOf(lineDigit)), columnNumber);

        for (Position position : exceptions) {
            if(result.equals(position)){
                return Optional.empty();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public String toString(){
        return String.format("line: %d, Colunm: %d", line, column);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if( !(obj instanceof Position)) return false;
        Position position = (Position)obj;
        return this.getLine() == position.getLine() && this.getColumn() == position.getColumn();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + Integer.valueOf(line).hashCode();
        result = prime * result + Integer.valueOf(column).hashCode();
        return result;
    }
}
