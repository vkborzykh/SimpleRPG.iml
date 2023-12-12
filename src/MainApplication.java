import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Unit hero, enemy;
        Scanner sc = new Scanner(System.in);
        Game game = new Game();

        // (1) ВЫБОР ПЕРСОНАЖА
        // ................................................................
        // Имя Героя
        System.out.println("Введите имя Героя:");
        String heroName=sc.nextLine();
        // Класс Героя
        System.out.println("""
                Введите класс Героя:
                1. Палладин
                2. Жрец
                3. Ассассин
                4. [Случайный выбор класса]
                """);
        int heroClass = sc.nextInt();
        while (heroClass<1 || heroClass >4) {
            System.out.println("Введите корректный номер (1-4).");
            heroClass = sc.nextInt();
        }
        if (heroClass == 4){
            game.diceMessage();
            heroClass = game.randomizer(3)+1;
            System.out.println("Результат случайного выбора:");
        }
        switch (heroClass) {
            case 1 -> { // Герой - палладин
                hero = new Paladin();
                hero.setName(heroName);
                System.out.println(hero.printDescription());
            }
            case 2 -> { // Герой - жрец
                hero = new Priest();
                hero.setName(heroName);
                System.out.println(hero.printDescription());
            }
            case 3 -> { // Герой - ассассин
                hero = new Assassin();
                hero.setName(heroName);
                System.out.println(hero.printDescription());
            }
            default -> throw new IllegalStateException("Unexpected value: " + heroClass);
        }
        game.pause();
        // ................................................................
        // (2) СЛУЧАЙНЫЙ ВЫБОР ПРОТИВНИКА
        switch (game.randomizer(3)){ // бросаем кубик для выбора класса противника
            case 0 -> enemy = new Rogue(); // Разбойник
            case 1 -> enemy = new BlackMage(); // Чёрный маг
            case 2 -> enemy = new Pyromaniac(); // Культист-пироман
            default -> throw new IllegalStateException("Unexpected value: " + game.randomizer(3));
        }
        System.out.println("Таинственный силуэт крадется во мраке подземелья." +
                "\nПриглядевшись, " + heroName + " различает очертания " +enemy.className+". Бой неизбежен.");
        game.pause();
        // ................................................................
        // (3) НАЧАЛО БОЯ
        game.diceMessage();
        int attacker = game.randomizer(2);

        switch (attacker){
            case 0-> {
                System.out.println("Волей судьбы "+ hero.name + " оказывается проворнее и ходит первым.");
                hero.heroesDecision(hero, enemy);
            }
            case 1 -> {
                System.out.println("Похоже, "+hero.name+" не смог перехватить инициативу. Ход "+enemy.className+".");
                enemy.enemyDecision(hero, enemy);

            }
        }
    }
}