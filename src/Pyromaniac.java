public class Pyromaniac extends Unit{
    public Pyromaniac(){
        fullHp = hp = 9;
        attack = 4;
        className="культиста-пиромана";
    }
    @Override
    public void attack(Unit hero, Unit enemy) {
        ranTemp = ran.nextInt(6);
        if (ranTemp == 2 && hero.knockedOut){
            ranTemp = ran.nextInt(2);
        }
        switch (ranTemp) {
            case 0 -> {
                System.out.println("""
            Пироман небрежно швыряет в героя огненный сгусток, получая 1 ед. урона от ожога.
            """);
                hp--;
                hero.hp -= attack;
            }
            case 1 -> {
                System.out.println("Пироман подрывает пороховой снаряд, получая 1 ед. урона от осколка. ");
                hp--;
                hero.hp -= attack/(ran.nextInt(2)+2);
            }
            case 2 -> {
                System.out.println("Пироман возводит вокруг Героя непроницаемый огненный купол. ");
                hero.knockedOut = true;
            }
            case 3 -> {
                System.out.println("Пироман обращается в пламенную форму и бросается в яростную атаку. ");
                hp-=3;
                attack--;
                System.out.println("[Бросок кубика]");
                if (ran.nextInt(2) == 0) {
                    System.out.println("Промах. " + hero.name + " успевает увернуться. ");
                } else {
                    System.out.println("Языки пламени поглощают Героя. " + hero.name + " получает тяжелые ожоги. ");
                    hero.hp -= 5;
                }
                System.out.println("Изможденный собственным приёмом, пироман возвращается в человеческое обличие.");
            }
            case 4 -> {
                System.out.println("Пироман изматывает Героя, вызывая у него тяжелый жар. ");
                hero.hp-=2;
                hero.attack-=2;
            }
            case 5 -> {
                System.out.println("Пироман стреляет в Героя разрывной стрелой из лёгкого арбалета.");
                System.out.println("[Бросок кубика]");
                if (ran.nextInt(2) == 0) {
                    System.out.println("Промах. " + hero.name + " успевает спрятаться от выстрела. ");
                } else {
                    System.out.println("Стрела попадает в цель.");
                    hero.hp -= attack + (ran.nextInt(2) + 2);
                }
            }
        }
    }
    public void defence(Unit hero, Unit enemy){
        System.out.println("Пироман возносит молитву Покровителю, чтобы тот придал ему новых сил.");
        hp++;
        attack++;
    }
}
