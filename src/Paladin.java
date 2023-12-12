public class Paladin extends Unit {
    public Paladin() {
        classActionUsed = false;
        fullHp = hp = 15;
        attack = 5;
        className = "палладин";
        classDescription = "Палладины - войны на страже Королевства:\n" +
                "они славятся могучим здоровьем (" + hp + " HP), ловко управляются с мечом (" + attack + " DMG)\n" +
                "и не могут быть обездвижены. Готовые отдать жизнь в пылу сражения, палладины \n" +
                "не могут сыграть действие лечения, но однажды за игру они вправе выпить эликсир \n" +
                "неистовой ярости, чтобы на 2 ед. повысить как наносимый, так и получаемый урон.";
    }

    public String printDescription() {
        return name + " - рыцарь ордена палладинов. " + classDescription;
    }

    @Override
    public void heroesDecision(Unit hero, Unit enemy) {
        super.heroesDecision(hero, enemy);
        System.out.println("""
                Выберите действие:
                1. Аттаковать врага -> Выбор атаки
                2. Оглушить врага щитом
                3. Бездействовать""");
        if (!classActionUsed) { // Предложить классовую способность, если она ещё не использована
            System.out.println("4. [Действие класса] Выпить эликсир неистовой ярости");
        }
        switch (sc.nextInt()) {
            case 1 -> attack(hero, enemy);
            case 2 -> defence(hero, enemy);
            case 3 -> skip(hero, enemy);
            case 4 -> { // Способность можно выбрать, если она не была использована
                if (!classActionUsed) {
                    rage(hero, enemy);
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
        System.out.println(line + hero.currentState(hero, enemy) + "\n" + line);
        pause();
        enemy.enemyDecision(hero, enemy);
    }

    public void attack(Unit hero, Unit enemy) {
        System.out.println("""
                Выберите тип атаки:
                1. Нанести рубящий удар мечом
                2. Попытаться прижать врага к земле
                3. [Вернуться назад]""");
        ranTemp = ran.nextInt(10);
        switch (sc.nextInt()) {
            case 1 -> {
                if (ranTemp >= 8) {
                    System.out.println("Меч Героя наносит критический удар. ");
                    enemy.takeDamage(attack + 2);
                } else if (ranTemp == 7) {
                    System.out.println("Герой промахивается, ему не удается ранить " + enemy.className + ".");
                } else enemy.takeDamage(attack);
            }
            case 2 -> {
                if (ranTemp >= 4) {
                    System.out.println(hero.name + " успешно удерживает " + enemy.className + " на лопатках.");
                    enemy.knockedOut = true;
                } else System.out.println("Врагу удалось увернуться.");
            }
            case 3 -> heroesDecision(hero, enemy);
            default -> { // Невалидный ввод возвращает к выбору
                System.out.println("Необходимо сделать выбор.");
                attack(hero, enemy);
            }
        }
    }

    public void defence(Unit hero, Unit enemy) {
        if (enemy.attack > 0) {
            System.out.println("Тяжелый щит оглушает " + enemy.className + ". Его атаки наносят на 1 ед. меньше урона.");
            enemy.attack--;
        } else {
            System.out.println("Противник измотан до предела. Герою остаётся атаковать.");
            attack(hero, enemy);
        }
    }

    @Override
    public void skip(Unit hero, Unit enemy) {
        super.skip(hero, enemy);
        enemy.enemyDecision(hero, enemy);
    }

    public void rage(Unit hero, Unit enemy) {
        System.out.println("Глаза палладина застелает кровавая пелена.\n" +
                "В порыве ярости " + hero.name + " наносит на 2 ед. больше урона," +
                " но также становится на 2 ед. уязвимее к атакам " + enemy.className + ".");
        hero.attack += 2;
        enemy.attack += 2;
        classActionUsed = true;
    }

}