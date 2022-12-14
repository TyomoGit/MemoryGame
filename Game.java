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

    public static void showEndScreen(int score){
        System.out.print("\033[H\033[2J");
        System.out.printf("""
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
                    ###### #      ###### ###### ######  ##         
                    #      #      #      #    # #    #  ##         
                    #      #      ###### ###### ######  ##         
                    #      #      #      #    # # #                
                    ###### ###### ###### #    # #  ###  ##         
            ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

            ゲームクリア！ score: %3d

            """, score); //11段
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

        while(!field.isEmpty()){
            String message = (isFirstCard ? "一" : "二") + "枚目のカードをめくってください。         " + String.format("score: %3d", score);
            showScreen(field, message);

            System.out.print("🃏: ");
            String input = scanner.nextLine();
            Optional<Position> position = Optional.empty();

            if(input.length() == 2){
                position = Position.valueOf(input);
            }else{
                continue;
            }

            if(!position.isPresent()){
                continue;
            }
            //ここでpositionはnull以外確定

            field.placeFaceUp(position.orElse(new Position(0, 0)));

            if(isFirstCard){
                firstCard = field.getCard(position.get());
                if(!firstCard.isPresent()) continue;
            }else{
                score++;
                Optional<Card> secondCard = field.getCard(position.get());
                if(!secondCard.isPresent()) continue;
                
                if(firstCard.get().numberEquals(secondCard.get())){
                    System.out.println("当たり！");
                    field.remove(field.positionOf(firstCard.get()).get());// 1枚目
                    field.remove(position.get()); // 2枚目
                }else{
                    showScreen(field, "残念！");
                    sleep(1000);
                    field.placeFaceDown(field.positionOf(firstCard.get()).get());
                    field.placeFaceDown(position.get());
                }
            }
            isFirstCard = !isFirstCard;
            sleep(1500);

        }
        showEndScreen(score);
        scanner.close();
    }
}