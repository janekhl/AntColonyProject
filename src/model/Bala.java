package model;

import dataStructures.ArrayList;

public class Bala extends Ant {

    public Bala(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        super(id, antType, age, alive, behaviorMode, xSquare, ySquare, movedThisTurn);
    }

    public static void BalaMode(ColonyNode[][] colonyNodes, ArrayList antList, Ant currentAnt) {
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
            oldNode.setBalaCount(oldNode.getBalaCount()-1);
            oldNode.nodeAntList.remove(currentAnt);
        }
        if (!currentAnt.hasMovedThisTurn() && currentAnt.getAlive()) {
                //setting up new node info
            if (currentAnt.getBehaviorMode().equals("random")) {
                //getting old node info


                //updating ant location and turn over
                currentAnt.setXSquare(newX);
                currentAnt.setYSquare(newY);

                //updating old node Bala Count
                int oldNodeCount = oldNode.getBalaCount();
                oldNode.setBalaCount(oldNodeCount - 1);
                oldNode.nodeAntList.remove(currentAnt);


                //updating new node Bala count
                int newNodeCount = newNode.getBalaCount();
                newNode.setBalaCount(newNodeCount + 1);
                newNode.nodeAntList.add(currentAnt);


                if (newNode.getForagerCount() > 0 || newNode.getScoutCount() > 0 || newNode.getSoldierCount() > 0 || newNode.isQueenNode()) {
                    currentAnt.setBehaviorMode("attack");
                    Ant.balaAttackEnemy(currentAnt, antList, newNode);

                }

                currentAnt.setMovedThisTurn(true);

                //            System.out.println("ant" + currentAnt.getId()+ " " +"ant has moved " + currentAnt.hasMovedThisTurn()+ " " + newNode.getX() + ", " + newNode.getY());

            }

            if (currentAnt.getBehaviorMode().equals("attack")) {
                if (oldNode.getForagerCount() > 0 || oldNode.getScoutCount() > 0 || oldNode.getSoldierCount() > 0 || oldNode.isQueenNode()) {

//                    Ant.balaAttackEnemy(currentAnt, antList, oldNode);
                    currentAnt.setMovedThisTurn(true);

                }
                else{
                    currentAnt.setBehaviorMode("random");
                }

            }
        }

    }
}
