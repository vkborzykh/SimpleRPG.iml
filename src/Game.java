import java.util.Random;
import java.util.Scanner;
// Вспомогательный класс
public class Game {
    String line = "________________________________________________\n";
    Scanner sc = new Scanner(System.in);
    Random ran = new Random();
    String temp="";
    public void pause() {
        System.out.println(line+"[Введите в консоль любой символ для продолжения]");
        while (temp.equals("")) {
            temp = sc.nextLine();
        }
        temp = "";
    }
    public int randomizer(int bound){
        return ran.nextInt(bound);
    }
    public void diceMessage(){
        System.out.println("[Бросок кубика]");
    }
}

