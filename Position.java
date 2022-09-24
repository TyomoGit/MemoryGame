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

    public String toString(){
        return String.format("line: %d, Colunm: %d", line, column);
    }
}
