package model;

import dataStructures.ArrayList;

import java.util.Stack;

public class Ant {
    private int id;
    private String antType;
    private int age;
    private boolean alive;
    private boolean movedThisTurn;
    private String behaviorMode;
    private int xSquare;
    Stack MovementHistory;
    private int ySquare;


    public Ant(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        this.id = id;
        this.antType = antType;
        this.age = age;
        this.alive = alive;
        this.behaviorMode = behaviorMode;
        this.xSquare = xSquare;
        this.ySquare = ySquare;
        this.movedThisTurn = movedThisTurn;
        MovementHistory = new Stack();

    }

    public static void balaAttackEnemy(Ant ant, ArrayList antList, ColonyNode currentNode){
        Ant enemyAnt;
        ArrayList enemyList = currentNode.getNodeAntList();
        int testIndex = antToAttack(enemyList);
           Ant test = (Ant) enemyList.get(testIndex);
            if (test.getAntType() != "Bala"){
                enemyAnt = test;
            }
            else{
                return;
            }
        int enemyAntId = enemyAnt.getId();
        System.out.println("THIS IS THE ENEMY ANT ID" + enemyAntId);
        enemyAnt= (Ant) antList.get(enemyAntId);

        int chances=AntSim.randomize(100,1);
            if (chances<51) {
                System.out.println("enemy ant " + enemyAnt.getAntType() + "has been killed");
                currentNode.nodeAntList.remove(enemyAnt);
                enemyAnt.setAlive(false);
                if (enemyAnt.getAntType().equals("Soldier")) {
                    currentNode.setSoldierCount(currentNode.getSoldierCount() - 1);

                }
                if (enemyAnt.getAntType().equals("Forager")) {
                    currentNode.setForagerCount(currentNode.getForagerCount() - 1);

                }
                if (enemyAnt.getAntType().equals("Scout")) {
                    currentNode.setScoutCount(currentNode.getScoutCount() - 1);

                }
                if (enemyAnt.getAntType().equals("Queen")) {
                    System.out.println("Game Over!");
                    AntSim.setQueenAlive(false);
                }
                System.out.println("ant down!");
                if (currentNode.getForagerCount() == 0 && currentNode.getScoutCount() == 0 && currentNode.getSoldierCount() == 0 && !currentNode.isQueenNode()) {
                    {
                        ant.setBehaviorMode("random");
                    }
                }
            }

    }

    public static int antToAttack(ArrayList enemyList) {
        int testIndex;
        if (enemyList.size() > 1){
            testIndex = AntSim.randomize(enemyList.size() - 1, 0);
         }
             else {
                 testIndex=0;
        }
         return testIndex;
    }

    public static void AttackEnemy(Ant ant, ArrayList antList, ColonyNode currentNode){
        Ant enemyAnt;
        ArrayList enemyList = currentNode.getNodeAntList();
        int testIndex = antToAttack(enemyList);
        Ant test = (Ant) enemyList.get(testIndex);
            if (test.getAntType().equals("Bala")){
                enemyAnt = test;
            }
            else{
                return;
            }
            int enemyAntId = enemyAnt.getId();
            enemyAnt= (Ant) antList.get(enemyAntId);

        int chances=AntSim.randomize(100,1);
        if (chances<51){
            System.out.println("ant down! bala is killed");
            enemyAnt.setAlive(false);
            currentNode.nodeAntList.remove(enemyAnt);
            currentNode.setBalaCount(currentNode.getBalaCount()-1);
            if (currentNode.getBalaCount() == 0){
                ant.setBehaviorMode("soldier");
            }
        }
    }



    public void addMovementHistory(ColonyNode[][] colonyNodes,int x, int y){
        this.MovementHistory.add(colonyNodes[x][y]);
    }


    public Stack getMovementHistory() {
        return MovementHistory;
    }

//    public static ColonyNode randomMoveDiscovered(ColonyNode[][] colonyNodes, Ant currentAnt){
//        ColonyNode newNode;
//        newNode = randomMove(colonyNodes, currentAnt);
//        if (!newNode.isNodeDiscovered()){
//            randomMoveDiscovered(colonyNodes, currentAnt);
//        }
//        return newNode;
//    }

    public static ColonyNode randomMove(ColonyNode[][] colonyNodes, Ant currentAnt) {
        int direction = AntSim.randomize(8, 1);
        ColonyNode newNode;
        int x = currentAnt.getXSquare();
        int y = currentAnt.getYSquare();

        newNode = randomMoveSwitch(direction, colonyNodes,x,y);
            return newNode;
    }


    public static ColonyNode randomMoveSwitch(int direction, ColonyNode[][] colonyNodes, int x, int y){
        ColonyNode newNode;
        switch (direction) {
            case 0:
                if (y!=26) //these statements are so the ants don't go out of bounds! Recursive solution!!!

                    newNode = colonyNodes[x][y + 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 1:
                if (y!=0)
                    newNode = colonyNodes[x][y - 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 2:
                if (x!=26)
                    newNode = colonyNodes[x + 1][y];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 3:
                if(x!=0)
                    newNode = colonyNodes[x - 1][y];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 4:
                if (x!=26 && y!=26)
                    newNode = colonyNodes[x + 1][y + 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 5:
                if (x!=26 && y!=0)
                    newNode = colonyNodes[x + 1][y - 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 6:
                if (x!=0 && y!=26)
                    newNode = colonyNodes[x - 1][y + 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            case 7:
                if (x!=0 && y!=0)
                    newNode = colonyNodes[x - 1][y - 1];
                else {
                    direction = AntSim.randomize(8, 1);
                    newNode = randomMoveSwitch(direction, colonyNodes, x, y);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        return newNode;
    }

    public boolean hasMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getAntType() {
        return antType;
    }

    public void setAntType(String antType) {
        this.antType = antType;
    }

    public String getBehaviorMode() {
        return behaviorMode;
    }

    public void setBehaviorMode(String behaviorMode) {
        this.behaviorMode = behaviorMode;
    }

    public int getXSquare() {
        return xSquare;
    }

    public void setXSquare(int xSquare) {
        this.xSquare = xSquare;
    }

    public int getYSquare() {
        return ySquare;
    }

    public void setYSquare(int ySquare) {
        this.ySquare = ySquare;
    }


}