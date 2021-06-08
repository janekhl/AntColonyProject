package model;

import dataStructures.ArrayList;

public class Queen extends Ant{
    public Queen(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        super(id, antType, age, alive, behaviorMode, xSquare, ySquare, movedThisTurn);
    }

    public static void QueenMode(ColonyNode[][] colonyNodes, ArrayList antList, int turnCount){
        ColonyNode queenNode;
        queenNode=colonyNodes[13][13];

        int currentFood = queenNode.getFood();
        System.out.println(currentFood);
        queenNode.setFood(currentFood-1);

        Ant queenAnt;
        queenAnt = (Ant) antList.get(0);

        if (currentFood==0||turnCount > 73000){
            queenAnt.setAlive(false);
            AntSim.setQueenAlive(false);
        }

        if (turnCount%10==0){
            int nextID= antList.size();
            int probability=AntSim.randomize(100,1);
            System.out.println(probability);
            if (probability<51){
                if (probability<26){
                    antList.add(new Scout(nextID,"Scout",0,true,"scout",13,13,false));
                    int currentScouts = queenNode.getScoutCount();
                    queenNode.setScoutCount(currentScouts+1);

                }
                else {
                    antList.add(new Soldier(nextID, "Soldier", 0, true, "soldier", 13, 13, false));
                    int currentSoldiers = queenNode.getSoldierCount();
                    queenNode.setSoldierCount(currentSoldiers + 1);
                }

            }
            else{
                Forager newForager = new Forager(nextID,"Forager",0,true,"forager",13,13,false);
                newForager.addMovementHistory(colonyNodes, 13, 13);
                antList.add(newForager);
                int currentForagers = queenNode.getForagerCount();
                queenNode.setForagerCount(currentForagers+1);
            }

        }
    }
}
