public class Assassin extends Unit{
    public Assassin() {
        fullHp = hp = 9;
        attack = 4;
        className="ассассин";
        classDescription= "Ассассины - гильдия скрытных убийц:\n" +
                "они склонны держаться в тени и не привычны к открытым сражениям ("+attack+ " DMG),\n"+
                "однако их клинки с точностью поражают уязвимые места противника (CRT DMG х2).\n" +
                "Герой-ассассин может питаться тёмной энергией, получая по единице к здоровью и силе атаки.\n"+
                "Удары Героя никогда не промахиваются по врагу, а однажды за игру он может обратиться\n" +
                "в ядовитое облако, нанося врагу урон и заставляя последнего пропустить свой ход.\n";
    }

    @Override
    public String printDescription() {
        return name+" - ассассин. "+classDescription;
    }

    @Override
    public void heroesDecision(Unit hero, Unit enemy) {
        super.heroesDecision(hero, enemy);
        System.out.println("""
                Выберите действие:
                1. Атаковать врага -> Выбор атаки
                2. Напитаться потусторонней энергией
                3. Бездействовать""");

        if (!classActionUsed) { // Предложить классовую способность, если она ещё не использована
            System.out.println("4. [Действие класса] Обратиться в ядовитый туман");
        }
        switch (sc.nextInt()) {
            case 1 -> attack(hero, enemy);
            case 2 -> defence();
            case 3 -> skip(hero, enemy);
            case 4 -> { // Способность можно выбрать, если она не была использована
                if (!classActionUsed) {
                    poisonousFog(hero, enemy);
                } else { // Невалидный ввод возвращает к выбору
                    System.out.println("Необходимо сделать выбор.");
                    heroesDecision(hero, enemy);
                }
            }
            default -> { // Невалидный ввод возвращает к выбору
                System.out.println("Необходимо сделать выбор.");
                heroesDecision(hero, enemy);
            }
        }
        System.out.println(line+hero.currentState(hero,enemy)+"\n"+line);
        pause();
        enemy.enemyDecision(hero, enemy);
    }
    public void attack (Unit hero, Unit enemy){
        System.out.println("""
                    Выберите тип атаки:
                    1. Пронзить врага ядовитым клинком
                    2. Одурманить врага пугающей иллюзией
                    3. [Вернуться назад]""");
        ranTemp = ran.nextInt(10);
        switch (sc.nextInt()) {
            case 1 -> {
                if(ranTemp <= 4) {
                    System.out.println("Ассассин "+hero.name+" попадает по уязвимому месту "+ enemy.className+"\n" +
                            "и наносит ему критический урон.");
                    enemy.takeDamage(attack+2);
                }
                else {
                    enemy.takeDamage(attack);
                }
            }
            case 2 -> {
                if (ranTemp >= 4){
                    System.out.println("Враг замирает, парализованный ужасом.");
                    enemy.knockedOut = true;
                }
                else System.out.println("Врагу удается сфокусируется на битве. Иллюзия исчезает.");
            }
            case 3 -> heroesDecision(hero, enemy);
            default -> { // Невалидный ввод возвращает к выбору
                System.out.println("Необходимо сделать выбор.");
                attack(hero, enemy);
            }
        }
    }
    public void defence () {
        if (hp<fullHp){
            System.out.println("Герой насыщается потусторонней энергией, получая прибавку к здоровью и силе.");
            hp ++;
        }
        else System.out.println("Герой насыщается потусторонней энергией, получая только прибавку к силе.");
        attack++;
    }
    @Override
    public void skip (Unit hero, Unit enemy){
        super.skip(hero, enemy);
        enemy.enemyDecision(hero, enemy);
    }

    public void poisonousFog(Unit hero, Unit enemy) {
        System.out.println(hero.name+" принимает форму газообразного облака и заставляет "+enemy.className+" задыхаться. Враг парализован.");
        enemy.hp -= ran.nextInt(4)+1; // враг получает 1-5 ед. урона
        enemy.knockedOut = true;
        classActionUsed = true;
    }
}