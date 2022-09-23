/*
 * 
 * 
 */
public class Game {

    private static void turnOver(){
        
    }
    public static void main(String[] args) {
        Field field = new Field();

        System.out.println();
        field.show();
        System.out.println(Field.convertIntoCoorfinate(args[0]).orElse("null"));
    }
}
