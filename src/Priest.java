public class Priest extends Unit{
    public Priest() {
        fullHp = hp = 7;
        attack = 3;
        className="жрец";
        classDescription= "Жрецы - адепты магии света:\n" +
                "уязвимые в бою ("+hp+" HP, "+attack+" DMG), они искуссно владеют чарами исцеления, что\n" +
                "позволяет полностью восстанавливать здоровье за 1 ход. Накладывая чары увядания, Герой\n" +
                "также увеличивает на 1 ед. максимальный уровень здоровья. Однажды за игру Герой может\n" +
                "бросить кубик, чтобы призвать энергию хаоса и нанести случайный урон по кому-либо из\n" +
                "персонажей (в том числе по самому заклинателю).";
    }

    @Override
    public String printDescription() {
        return name+" - жрец Великого Культа. "+classDescription;
    }

    @Override
    public void heroesDecision(Unit hero, Unit enemy) {
        super.heroesDecision(hero, enemy);
        System.out.println("""
                Выберите действие:
                1. Наложить чары на врага -> Выбор зачарования
                2. Излечить себя магией света
                3. Бездействовать""");
        if (!classActionUsed) { // Предложить классовую способность, если она ещё не использована
            System.out.println("4. [Действие класса] Бросить жребий рискованного заклятия");
        }
        switch (sc.nextInt()) {
            case 1 -> attack(hero, enemy);
            case 2 -> defence();
            case 3 -> skip(hero, enemy);
            case 4 -> { // Способность можно выбрать, если она не была использована
                if (!classActionUsed) {
                    riskySpell(hero, enemy);
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
                    Выберите тип чар:
                    1. Наложить на врага чары увядания и заполучить 1 ед. здоровья
                    2. Опутать врага роем саранчи
                    3. [Вернуться назад]""");
        ranTemp = ran.nextInt(10);
        switch (sc.nextInt()) {
            case 1 -> {
                if(ranTemp >= 8) {
                    System.out.println("Увядание наносит критический удар. ");
                    enemy.takeDamage(attack+2);
                    hero.fullHp++;
                }
                else if(ranTemp == 7){
                    System.out.println("Заклинание дает промах по врагу.");
                    skip(hero, enemy);
                }
                else {
                    enemy.takeDamage(attack);
                    hero.fullHp++;
                }
            }
            case 2 -> {
                if (ranTemp >= 4){
                    System.out.println("Назойливый рой саранчи дезориентирует "+enemy.className+".");
                    enemy.knockedOut = true;
                }
                else System.out.println("Врагу удается разогнать саранчу и вернуться к бою.");
            }
            case 3 -> heroesDecision(hero, enemy);
            default -> { // Невалидный ввод возвращает к выбору
                System.out.println("Необходимо сделать выбор.");
                attack(hero, enemy);
            }
        }
    }
    public void defence () {
        hp = fullHp;
    }
    @Override
    public void skip (Unit hero, Unit enemy){
        super.skip(hero, enemy);
        enemy.enemyDecision(hero, enemy);
    }

    public void riskySpell(Unit hero, Unit enemy) {
        System.out.println("Земля содрогается от ревущей магии хаоса.\n[Бросок кубика]");
        ranTemp = ran.nextInt(10);
        if (ranTemp<=3){
            System.out.println("Заклятие наносит урон по врагу.");
            enemy.hp -= ran.nextInt(6)+1; // враг получает 1-6 ед. урона
        }
        else if (ranTemp<=7){
            System.out.println("Заклятие наносит урон по самому заклинателю.");
            hero.hp -= ran.nextInt(3)+1; // Герой получает 1-3 ед. урона
        }
        else { // И враг и Герой полчают 1-6 ед. урона
            System.out.println("Заклятие наносит урон по обоим персонажам.");
            hero.hp -= ran.nextInt(3)+1;
            enemy.hp -= ran.nextInt(6)+1;
        }
        classActionUsed = true;
    }
}
