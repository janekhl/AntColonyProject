package model;

import dataStructures.ArrayList;
import view.AntSimGUI;
import view.ColonyNodeView;
import view.ColonyView;

public class Colony {
    ColonyView colonyView;
    ColonyNodeView[][] colonyNodeViewGrid = new ColonyNodeView[27][27];
    ArrayList AntList;
    AntSimGUI antSimGui;
    ColonyNode[][] colonyNodes;
    ColonyNodeView[][] colonyGrid;



    public Colony (ColonyView colonyView, AntSimGUI antSimGUI){

        this.colonyView=colonyView;
        this.antSimGui = antSimGUI;
        AntList = new ArrayList();
        colonyNodes = new ColonyNode[27][27];

    }


    public ColonyNodeView[][] getColonyGrid() {
        return colonyGrid;
    }

    public ColonyView getColonyView(){
        return colonyView;
    }

    public ArrayList getAntList(){
        return AntList;
    }


    public ColonyNodeView[][] getColonyNodeView(){
        return colonyNodeViewGrid;
    }

    // 1. we want to set up the 27 by 27 grid
    public void setUpColonyView(ColonyView colonyView){
        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 27; j++) {
                // 2. then we want to add in the view within each box
                colonyNodeViewGrid[i][j] = new ColonyNodeView();
                String squareId = Integer.toString(i) + ',' + Integer.toString(j);
                colonyNodeViewGrid[i][j].setID(squareId);
                colonyView.addColonyNodeView(colonyNodeViewGrid[i][j],i,j);
                System.out.println("hello "+ colonyNodeViewGrid[i][j]);
                initialColonyNodes(colonyNodeViewGrid,i,j);
            }
        }
    }

    public void initialColonyNodes(ColonyNodeView[][] colonyNodeViewGrid, int x, int y){
        if (x==13&&y==13){
            System.out.println("13 box");
            colonyNodeViewGrid[x][y].showNode();
            colonyNodeViewGrid[x][y].setQueen(true);
            colonyNodeViewGrid[x][y].showQueenIcon();
            colonyNodeViewGrid[x][y].setForagerCount(50);
            colonyNodeViewGrid[x][y].setScoutCount(4);
            colonyNodeViewGrid[x][y].setSoldierCount(10);
            colonyNodeViewGrid[x][y].setBalaCount(0);
            colonyNodeViewGrid[x][y].setFoodAmount(1000);
            colonyNodeViewGrid[x][y].setPheromoneLevel(0);
            colonyNodes[x][y]= new ColonyNode(1000,0,true,true,x,y,0,50,4,10, colonyNodeViewGrid[x][y]);

        }
        else if(x>=12 && x<=14 && y>=12 && y<=14 && !(x==13 && y==13)){
            System.out.println("surrounding box");
            colonyNodeViewGrid[x][y].showNode();
            colonyNodeViewGrid[x][y].setQueen(false);
            colonyNodeViewGrid[x][y].setForagerCount(0);
            colonyNodeViewGrid[x][y].setScoutCount(0);
            colonyNodeViewGrid[x][y].setSoldierCount(0);
            colonyNodeViewGrid[x][y].setBalaCount(0);
            colonyNodeViewGrid[x][y].setFoodAmount(0);
            colonyNodeViewGrid[x][y].setPheromoneLevel(0);
            colonyNodes[x][y]= new ColonyNode(0,0,false,true,x,y,0,0,0,0, colonyNodeViewGrid[x][y]);

        }
//        else if(x==26 || y==26){
//            System.out.println("edge hidden boxes");
//            colonyNodeViewGrid[x][y].hideNode();
//            colonyNodeViewGrid[x][y].setQueen(false);
//            colonyNodeViewGrid[x][y].setFoodAmount(setFood());
//            colonyNodeViewGrid[x][y].setForagerCount(0);
//            colonyNodeViewGrid[x][y].setScoutCount(0);
//            colonyNodeViewGrid[x][y].setSoldierCount(0);
//            colonyNodeViewGrid[x][y].setBalaCount(0);
//            colonyNodeViewGrid[x][y].setFoodAmount(setFood());
//            colonyNodeViewGrid[x][y].setPheromoneLevel(0);
//            colonyNodes[x][y]= new ColonyNode(setFood(),0,false,false,x,y,0,0,0,0, colonyNodeViewGrid[x][y]);
//
//        }
        else{
            System.out.println("other hidden boxes");
            colonyNodeViewGrid[x][y].hideNode();
            colonyNodeViewGrid[x][y].setQueen(false);
            colonyNodeViewGrid[x][y].setFoodAmount(setFood());
            colonyNodeViewGrid[x][y].setForagerCount(0);
            colonyNodeViewGrid[x][y].setScoutCount(0);
            colonyNodeViewGrid[x][y].setSoldierCount(0);
            colonyNodeViewGrid[x][y].setBalaCount(0);
            colonyNodeViewGrid[x][y].setFoodAmount(setFood());
            colonyNodeViewGrid[x][y].setPheromoneLevel(0);
            colonyNodes[x][y]= new ColonyNode(setFood(),0,false,false,x,y,0,0,0,0, colonyNodeViewGrid[x][y]);

        }
    }


    public ColonyNode[][] getColonyNodes() {
        return colonyNodes;
    }


    public static int setFood(){
        //there's a 25% chance of some amount of food
        int percent = AntSim.randomize(100,1);
        int foodAmount;
        if (percent<26){
            foodAmount = AntSim.randomize(1000,500);
        }
        else{
            foodAmount=0;
        }
        System.out.println(foodAmount);
        return foodAmount;
    }

    public void addToAntList(int i, Ant ant){
        AntList.add(i,ant);
    }

    public void createBalaAnts(ColonyNode[][] colonyNodes, ArrayList antList){
        int chances=AntSim.randomize(100,1);
            if(chances<4){
                int place = getAntList().size();
                Bala bala = new Bala(place,"Bala",0,true,"random",26,26,false);
                addToAntList(place, bala);
                ColonyNode enterNode= colonyNodes[26][26];
                enterNode.setBalaCount(enterNode.getBalaCount()+1);
                System.out.println("bala has been added");
            }
    }





    public void createInitialAnts(){
        for (var i =0;i<1;i++) {
            Queen queen = new Queen(0, "Queen", 0,true,"queen",13,13,false);
            addToAntList(i,queen);
            colonyNodes[13][13].nodeAntList.add(queen);
        }
        for (var i=1;i<5;i++){
            Scout scout = new Scout(i,"Scout",0,true,"scout",13,13,false);
            addToAntList(i,scout);
            colonyNodes[13][13].nodeAntList.add(scout);
        }

        for (var i=5;i<16;i++){
            Soldier soldier = new Soldier(i,"Soldier",0,true,"soldier",13,13,false);
            addToAntList(i,soldier);
            colonyNodes[13][13].nodeAntList.add(soldier);
        }
        for (var i=16;i<65;i++){
            Forager forager = new Forager(i,"Forager",0,true,"forager",13,13,false);
            forager.addMovementHistory(colonyNodes,13,13);
            addToAntList(i,forager);
            colonyNodes[13][13].nodeAntList.add(forager);
        }
    }


    // 3. then we want to set up what's inside each box but I think I will do that inside ColonyNode.java
}
