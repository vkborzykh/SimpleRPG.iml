import java.util.Random;
import java.util.Scanner;

public abstract class Unit {
    Scanner sc = new Scanner(System.in);
    // БОЕВЫЕ ХАРАКТЕРИСТИКИ
    int hp; // Текущее здоровье (статистика показывает hp/fullHp)
    int fullHp; // Здоровье в начале битвы
    int attack; // Урон, наносимый персонажем при атаке
    boolean knockedOut = false; // Персонаж в нокауте (пропускает 1 ход)
    boolean classActionUsed; // Специальное действие класса использовано (можно применить 1 раз за игру)

    // ОПИСАНИЕ ПЕРСОНАЖА ДЛЯ ВЫВОДА В КОНСОЛЬ
    String name; // Имя (только для Героя)
    String className; // Для Героя - название класса, для противника - его тип
    String classDescription; // Информация о классе Героя (только для Героя)

    int ranTemp;
    String temp = "";
    String line = "________________________________________________\n";
    Random ran = new Random();

    public void setName(String name) {
        this.name = name;
    }

    public String printDescription() {
        return classDescription;
    }

    public void attack(Unit hero, Unit enemy) {
    }

    public void defence(Unit hero, Unit enemy) {
    }

    public void skip(Unit hero, Unit enemy) {
        System.out.println("Герой замирает в ожидании.");
    }

    public void takeDamage(int amount) {
        hp -= amount;
    }

    public void heroesDecision(Unit hero, Unit enemy) {
        if (hero.hp < 1) {
            System.out.println("Битва окончена. " + hero.name + " пал в бою от руки " + enemy.className + ".");
            System.exit(0);
        }
    }

    public void enemyDecision(Unit hero, Unit enemy) {
        if (enemy.hp < 1) {
            System.out.println("Битва окончена. " + hero.name + " одолел " + enemy.className + " в честном бою.");
            System.exit(0);
        }
        if (enemy.knockedOut) {
            System.out.println("Противник обездвижен и пропускает свой ход");
            enemy.knockedOut = false;
            hero.heroesDecision(hero, enemy);
        }
        if (enemy.hp < enemy.fullHp && ran.nextInt(10) < 7) {
            defence(hero, enemy);
        } else attack(hero, enemy);

        System.out.println(line + hero.currentState(hero, enemy) + "\n" + line);
        pause();
        hero.heroesDecision(hero, enemy);
    }

    public String currentState(Unit hero, Unit enemy) {
        if (hero.hp <= 2 || enemy.hp <= 2) {
            System.out.println("Битва близится к развязке! ");
        }
        if (hero.hp < 0) {
            hero.hp = 0;
        }
        if (enemy.hp < 0) {
            enemy.hp = 0;
        }
        return "Здоровье Героя: " + hero.hp + "/" + hero.fullHp +
                "\nЗдоровье " + enemy.className + ": " + enemy.hp + "/" + enemy.fullHp;
    }

    public void pause() {
        System.out.println("[Введите в консоль любой символ для продолжения]");
        while (temp.equals("")) {
            temp = sc.nextLine();
        }
        temp = "";
    }
}