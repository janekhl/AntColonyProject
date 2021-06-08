package model;

public class Forager extends Ant {
    private boolean hasFood;
    ColonyNode[][] colonyNodes;


    // Will use Stack for Movement history since the LIFO will help the ant get back to the queen

    public Forager(int id, String antType, int age, boolean alive, String behaviorMode, int xSquare, int ySquare, boolean movedThisTurn) {
        super(id, antType, age, alive, behaviorMode, xSquare, ySquare, movedThisTurn);
    }


    public static void foragerMovement(ColonyNode[][] colonyNodes, Ant currentAnt) {
            int x = currentAnt.getXSquare();
            int y = currentAnt.getYSquare();
            ColonyNode currentNode;
            currentNode = colonyNodes[x][y];


            int lastX = x;
            int lastY = y;
            ColonyNode lastNode;


            int pheromones = 0;
            int newX = x;
            int newY = y;

            ColonyNode newNode=currentNode;

        if (currentAnt.getAge()>3650){
            currentAnt.setAlive(false);
            currentNode.setForagerCount(currentNode.getForagerCount()-1);
            currentNode.nodeAntList.remove(currentAnt);
        }
        if (!currentAnt.hasMovedThisTurn() && currentAnt.getAlive()) {

            //movement history
            if (currentAnt.getMovementHistory().size() > 0) {
                lastNode = (ColonyNode) currentAnt.getMovementHistory().peek();
                lastX = lastNode.getX();
                lastY = lastNode.getY();
            }


            //forage mode
            if (currentAnt.getBehaviorMode() == "forager") {

                //we're seeing which block has the most pheromones
                for (int i = 0; i < 8; i++) {
                   ColonyNode testNewNode = randomMoveSwitch(i, colonyNodes, x, y);
                    newX = testNewNode.getX();
                    newY = testNewNode.getY();
                    if ((testNewNode.isNodeDiscovered()) && (testNewNode.getPheromones() > pheromones) && (newX != x && newY != y) && (newX != lastX && newY != lastY) &&(testNewNode.getX()!=13 && testNewNode.getY()!=13)) {
                            newNode = testNewNode;
                            pheromones = newNode.getPheromones();
                            System.out.println("pheromones");

                    }
                    else {
                        testNewNode = randomMove(colonyNodes, currentAnt);
                        while (!testNewNode.isNodeDiscovered()){
                            testNewNode = randomMove(colonyNodes, currentAnt);
                        }
                            newNode=testNewNode;
                            System.out.println("random thing");

                    }
                    newX = newNode.getX();
                    newY = newNode.getY();

                }

                //setting new node forager count to one more
                currentAnt.setMovedThisTurn(true);
                newNode.setForagerCount(newNode.getForagerCount() + 1);
                newNode.nodeAntList.add(currentAnt);
                //setting new position for this ant
                currentAnt.setXSquare(newX);
                currentAnt.setYSquare(newY);

                //setting current node forager count to one less
                currentNode.setForagerCount(currentNode.getForagerCount() - 1);
                currentNode.nodeAntList.remove(currentAnt);



                currentAnt.addMovementHistory(colonyNodes, newX, newY);


                //checking for food in the new node
                if(newNode.getX()!=13 && newNode.getY()!=13){
                    int currentFood = newNode.getFood();
                    if (currentFood > 0) { //if there's food, we're taking one and going back to the nest
                        currentAnt.setBehaviorMode("backtonest");
                        newNode.setFood(currentFood - 1);

                        //adding pheromones in this node
                        newNode.setPheromones(newNode.getPheromones() + 10);
                    }
                }
            }


            else if (currentAnt.getBehaviorMode() == "backtonest") {


                //setting forager count of last square to account for ant leaving
                currentNode.setForagerCount(colonyNodes[x][y].getForagerCount() - 1);
                currentNode.nodeAntList.remove(currentAnt);

                //getting the next node in the stack
                ColonyNode returnNode = (ColonyNode) currentAnt.getMovementHistory().pop();

                //setting forager count of new square to include this ant
                returnNode.setForagerCount(returnNode.getForagerCount() + 1);
                returnNode.nodeAntList.add(currentAnt);

                //setting ant location
                currentAnt.setXSquare(returnNode.getX());
                currentAnt.setYSquare(returnNode.getY());

                //accounting for if we're in the queen node
                if (returnNode.getX() == 13 && returnNode.getY() == 13) {
                    returnNode.setFood(returnNode.getFood() + 1);
                    System.out.println("We brought food to the queen");
                    currentAnt.addMovementHistory(colonyNodes,13,13);
                    currentAnt.setBehaviorMode("forager");
                }
                else {
                    returnNode.setPheromones(returnNode.getPheromones() + 10);
                }
                currentAnt.setMovedThisTurn(true);
            }

        }
    }

}
