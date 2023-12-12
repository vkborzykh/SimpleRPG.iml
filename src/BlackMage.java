public class BlackMage extends Unit {
    public BlackMage() {
        fullHp = hp = 8;
        attack = 3;
        className = "чёрного мага";
    }

    @Override
    public void attack(Unit hero, Unit enemy) {
        ranTemp = ran.nextInt(6);
        if (ranTemp == 2 && hero.knockedOut) {
            ranTemp = ran.nextInt(2);
        }
        switch (ranTemp) {
            case 0 -> {
                System.out.println("Маг заклинает Героя чернокнижным проклятием. ");
                hero.hp -= attack;
            }
            case 1 -> {
                System.out.println("Маг направляет в Героя магическую стрелу. ");
                hero.hp -= attack / (ran.nextInt(2) + 2);
            }
            case 2 -> {
                System.out.println("Маг удерживает Героя в неподвижности магическими путами. ");
                hero.knockedOut = true;
            }
            case 3 -> {
                System.out.println("Маг размахивается боевым посохом и наносит Герою слабый удар. ");
                hero.hp--;
            }
            case 4 -> {
                System.out.println("Маг перевоплощается гигантскую летучую мышь и пытается укусить героя. ");
                System.out.println("[Бросок кубика]");
                if (ran.nextInt(10) >= 4) {
                    System.out.println("Крылатое чудовище истощает Героя и насыщается его кровью.");
                    hero.hp -= 2;
                    hero.attack -= 2;
                    enemy.hp++;
                } else System.out.println("Герой не подпускает к себе назойливое чудовище и готовится к контратаке.");
            }
            case 5 -> {
                System.out.println("Маг поднимает в воздух каменную глыбу и наносит телекинетический удар по Герою. ");
                System.out.println("[Бросок кубика]");
                if (ran.nextInt(2) == 0) {
                    System.out.println("Промах. " + hero.name + " успевает увернуться. ");
                } else {
                    System.out.println("Тяжелый осколок задевает цель. " + hero.name + " получает увечия. ");
                    hero.hp -= 5;
                }
            }
        }
    }

    public void defence(Unit hero, Unit enemy) {
        System.out.println("Маг совершает ритуал крови, чтобы полностью восстановить очки здоровья. ");
        hp = fullHp;
    }
}
