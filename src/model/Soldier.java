package model;

import dataStructures.ArrayList;

public class Soldier extends Ant{
    public Soldier(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        super(id, antType, age, alive, behaviorMode, xSquare, ySquare, movedThisTurn);
    }

    public static void SoldierMode(ColonyNode[][] colonyNodes, ArrayList antList, Ant currentAnt, int turnCount){
        ColonyNode newNode;
        newNode = randomMove(colonyNodes, currentAnt);
        int newX = newNode.getX();
        int newY = newNode.getY();
        newNode = colonyNodes[newX][newY];
        int oldX = currentAnt.getXSquare();
        int oldY = currentAnt.getYSquare();
        ColonyNode oldNode = colonyNodes[oldX][oldY];

        if (currentAnt.getAge()>3650){
            currentAnt.setAlive(false);
            oldNode.setSoldierCount(oldNode.getSoldierCount()-1);
            oldNode.nodeAntList.remove(currentAnt);
        }
        if (currentAnt.getAlive() ) {

            if (currentAnt.getBehaviorMode() != "attack") {
//getting old node info


                //updating ant location and turn over
                currentAnt.setXSquare(newX);
                currentAnt.setYSquare(newY);

                //updating old node Soldier Count
                int oldNodeCount = oldNode.getSoldierCount();
                oldNode.setSoldierCount(oldNodeCount - 1);
                oldNode.nodeAntList.remove(currentAnt);


                //updating new node Soldier count
                int newNodeCount = newNode.getSoldierCount();
                newNode.setSoldierCount(newNodeCount + 1);
                newNode.nodeAntList.add(currentAnt);


                if (newNode.getSoldierCount() > 0) {
                    currentAnt.setBehaviorMode("attack");
                    Ant.AttackEnemy(currentAnt, antList, newNode);
                }

                currentAnt.setMovedThisTurn(true);


            }
            else if (currentAnt.getBehaviorMode().equals("attack")) {
                if (oldNode.getSoldierCount() > 0) {
                    Ant.AttackEnemy(currentAnt, antList, oldNode);

                } else {
                    currentAnt.setBehaviorMode("soldier");

                }
                currentAnt.setMovedThisTurn(true);
            }
        }
    }
}
