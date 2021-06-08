package model;

public class Scout extends Ant{

    public Scout(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        super(id, antType, age, alive, behaviorMode, xSquare, ySquare, movedThisTurn);
    }

    public static void ScoutMode(ColonyNode[][] colonyNodes, Ant currentAnt){
            int oldX = currentAnt.getXSquare();
            int oldY = currentAnt.getYSquare();
            ColonyNode oldNode = colonyNodes[oldX][oldY];

        if (currentAnt.getAge()>3650){
            currentAnt.setAlive(false);
            oldNode.setScoutCount(oldNode.getScoutCount()-1);
            oldNode.nodeAntList.remove(currentAnt);
        }

        if (!currentAnt.hasMovedThisTurn() && currentAnt.getAlive()){
            //getting old node info

            //setting up new node info
            ColonyNode newNode;
            newNode = randomMove(colonyNodes, currentAnt);
            int newX = newNode.getX();
            int newY = newNode.getY();

            //updating ant location and turn over
            currentAnt.setXSquare(newX);
            currentAnt.setYSquare(newY);

            //updating old node scout Count
            int oldNodeCount = oldNode.getScoutCount();
            oldNode.setScoutCount(oldNodeCount-1);
            oldNode.nodeAntList.remove(currentAnt);

            //updating new node scout count
            int newNodeCount = newNode.getScoutCount();
            newNode.setScoutCount(newNodeCount+1);
            newNode.nodeAntList.add(currentAnt);



            //if the node hasn't been discovered yet we want to make it visible
            if (!newNode.isNodeDiscovered()){
                newNode.setNodeDiscovered(true);
            }
            currentAnt.setMovedThisTurn(true);

            System.out.println("ant" + currentAnt.getId()+ " " +"ant has moved " + currentAnt.hasMovedThisTurn()+ " " + newNode.getX() + ", " + newNode.getY());

        }

    }
}