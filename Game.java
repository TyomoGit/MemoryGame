import java.util.Optional;
import java.util.Scanner;

public class Game {

    private Game(){}
    /*
     * n行上に戻る方法
     * \033[nA
     */
    public static void printFirstScreen(){
        System.out.print("\033[H\033[2J");
        System.out.print("""
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
            ##       ## ###### ##       ## ###### ###### ###   ###
            # #     # # #      # #     # # #    # #    #   ## ##
            #  #   #  # ###### #  #   #  # #    # ######    ###
            #   # #   # #      #   # #   # #    # #  #       #
            #    #    # ###### #    #    # ###### #   ##     #
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

            CUI上で遊べる神経衰弱ゲームです。

            """); //11段
            showTimeProgressBar(3500);
    }

    public static void showTimeProgressBar(double millis){
        double width = 1 + Field.NUMBER_DISPLAY_IN_LINE * 6;
        for (int i = 0; i <= width; i++) {
            System.out.print("\r");
            for (int j = 0; j < width; j++) {
                if(j > i){
                    System.out.print("□");
                }else{
                    System.out.print("■");
                }
            }
            sleep((long)(millis/width));
        }
        System.out.print("\r");
    }

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void showScreen(Field field, String message){
        // System.out.print("\033[11A");
        // showEmptyScreen();
        System.out.print("\033[H\033[2J");
        // System.out.print("\033[11A");
        field.show();
        System.out.println();
        System.out.println("\r" + message);
    }

    private void turnOver(){
        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Field field = new Field();
        System.out.println();
        printFirstScreen();

        while(true){
            showScreen(field, "どのカードをめくりますか？");

            System.out.print("🃏: ");
            String input = scanner.nextLine();
            Optional<Position> position = Optional.empty();

            if(input.length() == 2){
                position = Position.valueOf(input);
            }else{
                continue;
            }

            System.out.println("Debug: " + position.orElse(new Position(0, 0)));
            sleep(1000);
            if(!position.isPresent()){
                continue;
            }
            field.placeFaceUp(position.orElse(new Position(1, 1)));
        }
    }
}
