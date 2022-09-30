import java.util.Optional;
import java.util.Scanner;

public class Game {

    private Game(){}

    /*
     * n行上に戻る方法
     * \033[nA
     */
    public static void showTitleScreen(){
        System.out.print("\033[H\033[2J");
        System.out.print("""
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
            ##       ## ###### ##       ## ###### ###### ###   ###
            # #     # # #      # #     # # #    # #    #   ## ##
            #  #   #  # ###### #  #   #  # #    # ######    ###
            #   # #   # #      #   # #   # #    # #  #       #
            #    #    # ###### #    #    # ###### #   ##     #
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

            神経衰弱ゲームです。

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
        System.out.print("\033[H\033[2J");
        field.show();
        System.out.println();
        System.out.println("\r" + message);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Field field = new Field();
        System.out.println();
        showTitleScreen();

        boolean isFirstCard = true;
        Optional<Card> firstCard = Optional.empty();
        int score = 0;
        while(true){
            String message = (isFirstCard ? "一" : "二") + "枚目のカードをめくってください。         " + String.format("score: %3d", score);
            showScreen(field, message);

            System.out.print("🃏: ");
            String input = scanner.nextLine();
            Optional<Position> position = Optional.empty();

            if(input.length() == 2){
                position = Position.valueOf(input);
                // System.out.println(position.get());
            }else{
                continue;
            }

            if(!position.isPresent()){
                continue;
            }
            //ここでpositionはnull以外確定

            if(isFirstCard){
                firstCard = field.getCard(position.get());
                if(!firstCard.isPresent()) continue;
            }else{
                score++;
                Optional<Card> secondCard = field.getCard(position.get());
                if(!secondCard.isPresent()) continue;

                if(firstCard.get().numberEquals(secondCard.get())){
                    field.remove(field.positionOf(firstCard.get()).get());// 1枚目
                    field.remove(position.get()); // 2枚目
                    System.out.println("当たり！");
                }else{
                    System.out.println("残念！");
                }
            }
            if(field.isEmpty()){
                break;
            }
            field.placeFaceUp(position.orElse(new Position(0, 0)));
            isFirstCard = !isFirstCard;
            sleep(1500);

        }
        System.out.print("\033[H\033[2J");
        
        
        
    }
}