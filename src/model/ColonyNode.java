 package model;

import dataStructures.ArrayList;
import view.ColonyNodeView;

public class ColonyNode{

int food;
int pheromones;
boolean queenNode;
boolean nodeDiscovered;
int x;
int y;
int balaCount;
int foragerCount;
int scoutCount;
int soldierCount;
ArrayList nodeAntList;

    ColonyNodeView colonyNodeView;
    ColonyNodeView[][] colonyNodeViewGrid;

    public ColonyNode (int food,int pheromones,boolean queenNode,boolean nodeDiscovered,int x,int y,int balaCount,int foragerCount,int scoutCount,int soldierCount, ColonyNodeView colonyNodeView){
        this.food = food;
        this.pheromones=pheromones;
        this.queenNode = queenNode;
        this.nodeDiscovered= nodeDiscovered;
        this.x= x;
        this.y= y;
        this.balaCount= balaCount;
        this.foragerCount= foragerCount;
        this.scoutCount= scoutCount;
        this.soldierCount= soldierCount;
        this.colonyNodeView = colonyNodeView;
        nodeAntList = new ArrayList();
    }

    public ArrayList getNodeAntList(){
        return nodeAntList;
    }



    public boolean isNodeDiscovered() {
        return nodeDiscovered;
    }

    public boolean isQueenNode() {
        return queenNode;
    }

    public int getBalaCount() {
        return balaCount;
    }

    public int getFood() {
        return food;
    }

    public int getForagerCount() {
        return foragerCount;
    }

    public int getPheromones() {
        return pheromones;
    }

    public int getScoutCount() {
        return scoutCount;
    }

    public int getSoldierCount() {
        return soldierCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setQueenNode(boolean queenNode) {
        this.colonyNodeView.setQueen(queenNode);
        this.colonyNodeView.showQueenIcon();
        this.queenNode = queenNode;
    }

    public void setBalaCount(int balaCount) {
        this.balaCount = balaCount;
        this.colonyNodeView.setBalaCount(balaCount);
    }

    public void setColonyNodeView(ColonyNodeView colonyNodeView) {
        this.colonyNodeView = colonyNodeView;
    }

    public void setFood(int food) {
        this.food = food;
        this.colonyNodeView.setFoodAmount(food);

    }

    public void setForagerCount(int foragerCount) {
        this.foragerCount = foragerCount;
        this.colonyNodeView.setForagerCount(foragerCount);

    }

    public void setNodeDiscovered(boolean nodeDiscovered) {
        this.nodeDiscovered = nodeDiscovered;
        if (nodeDiscovered == true){
            this.colonyNodeView.showNode();
        }
        else{
            this.colonyNodeView.hideNode();

        }
    }

    public void setPheromones(int pheromones) {
        this.pheromones = pheromones;
        this.colonyNodeView.setPheromoneLevel(pheromones);

    }

    public void setScoutCount(int scoutCount) {
        this.scoutCount = scoutCount;
        this.colonyNodeView.setScoutCount(scoutCount);
    }

    public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
        this.colonyNodeView.setSoldierCount(soldierCount);

    }
}

