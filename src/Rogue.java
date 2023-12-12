public class Rogue extends Unit {
    public Rogue() {
        fullHp = hp = 8;
        attack = 5;
        className = "разбойника";
    }
    @Override
    public void attack (Unit hero, Unit enemy){
        ranTemp = ran.nextInt(6);
        if (ranTemp == 2 && hero.knockedOut) {
            ranTemp = ran.nextInt(2);
        }
        switch (ranTemp) {
            case 0 -> {
                System.out.println("Разбойник проводит силовую атаку рапирой. ");
                hero.hp -= attack;
            }
            case 1 -> {
                System.out.println("Разбойник запускает снаряд из пращи в сторону героя. ");
                hero.hp -= attack / (ran.nextInt(2) + 2);
            }
            case 2 -> {
                System.out.println("Разбойник атакует выпадом с круговой подсечкой. ");
                if (hero.className.equals("палладин")){
                    System.out.println("Палладин неуязвим к атакам данного типа.");
                }
                else if (ranTemp == 0){
                hero.knockedOut = true;}
                else System.out.println("Герою удаётся увернуться от подсечки.");
            }
            case 3 -> {
                System.out.println("Разбойник совершает обманный маневр и атакует исподтишка. ");
                if (hero.hp < (hero.fullHp/2)){
                    System.out.println("Поскольку у Героя осталось <50% очков здоровья, ему нанесен крит. урон.");
                    hero.hp -= attack / (ran.nextInt(2) + 2);
                }
                hero.hp -= attack / (ran.nextInt(2) + 2);
            }
            case 4 -> {
                System.out.println("Разбойник входит в клинч, изматывая Героя. Теперь "+ hero.name+" наносит на 1 ед. меньше урона.");
                hero.hp--;
                hero.attack--;
            }
            case 5 -> {
                System.out.println("Разбойник дразнит Героя пинком ноги.");
                hero.hp--;
            }
        }
    }
    @Override
    public void defence(Unit hero, Unit enemy){
        System.out.println("Разбойник меняет стойку и переводит дух.");
        hp++;
    }
}