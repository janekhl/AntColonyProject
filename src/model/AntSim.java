package model;

import dataStructures.ArrayList;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AntSim implements SimulationEventListener, ActionListener {
    private static boolean queenAlive = true;
    javax.swing.Timer Timer;
    int turnCount;
    int dayCount;
    Colony antColony;
    ArrayList antList;
    ColonyView colonyView;
    ColonyNodeView[][] colonyGrid;
    ColonyNode[][] colonyNodes;


    public static void setQueenAlive(boolean queenAlive) {
        AntSim.queenAlive = queenAlive;
    }

    public AntSim (AntSimGUI antSimGui){
        turnCount=0;
        dayCount=0;
        antSimGui.addSimulationEventListener(this);
        antColony = new Colony( new ColonyView(27,27), antSimGui);
        colonyView=antColony.getColonyView();
        colonyGrid = antColony.getColonyNodeView();
        antSimGui.initGUI(colonyView);
        colonyNodes = antColony.getColonyNodes();

    }
//



    public void start() {
        //start stuff
        Timer = new Timer(5,this);
        Timer.start();
    }

    public void stop() {
        //stop stuff

    }
    public void singleTurn(){
        //runs single term only of sim
        queenTurn();
        Ant queen = (Ant) antList.get(0);
        if (!queen.getAlive()){
            colonyGrid[13][13].setQueen(false);
            colonyNodes[13][13].setQueenNode(false);
            queenAlive=false;
        }
        antColony.createBalaAnts(colonyNodes,antList);
        for (int i=0; i<antList.size(); i++){
            Ant currentAnt = (Ant) antList.get(i);
            if (turnCount%10==0) {
                currentAnt.setAge(currentAnt.getAge() + 1);
            }
            String type = currentAnt.getAntType();
            if (type=="Scout" && !currentAnt.hasMovedThisTurn()){
                Scout.ScoutMode(colonyNodes,currentAnt);
            }
            if (type=="Forager" && !currentAnt.hasMovedThisTurn()){
                Forager.foragerMovement(colonyNodes,currentAnt);
            }
            if (type=="Soldier" && !currentAnt.hasMovedThisTurn()){
                Soldier.SoldierMode(colonyNodes,antList,currentAnt, turnCount);
            }
//            if (type=="Bala" && !currentAnt.hasMovedThisTurn()){
//                Bala.BalaMode(colonyNodes,antList,currentAnt);
//            }
            currentAnt.setMovedThisTurn(false);
        }
        turnCount++;
    }

    public void foragerTurn(){
        for(int i=0; i<antList.size();i++){
            Ant currentAnt = (Ant) antList.get(i);
            String type = currentAnt.getAntType();
            if (type=="Forager" && !currentAnt.hasMovedThisTurn()){
                Forager.foragerMovement(colonyNodes,currentAnt);
            }
        }

    }

    public void queenTurn(){
        Queen.QueenMode(colonyNodes,antList,turnCount);
    }

    public void SoldierTurn(){
        for(int i=0; i<antList.size();i++) {
            Ant currentAnt = (Ant) antList.get(i);
            String type = currentAnt.getAntType();
            if (type == "Soldier" && !currentAnt.hasMovedThisTurn()) {
                Soldier.SoldierMode(colonyNodes, antList, currentAnt,turnCount);
            }
        }
    }

    public void scoutTurn(){
        for(int i=0; i<antList.size();i++) {
            Ant currentAnt = (Ant) antList.get(i);
            String type = currentAnt.getAntType();
            if (type == "Scout" && !currentAnt.hasMovedThisTurn()) {
                Scout.ScoutMode(colonyNodes, currentAnt);
            }
        }
    }

    public void newDay(){
        turnCount=0;
        dayCount++;
    }

    public void endSimulation(){
    }

    public static int randomize(int n, int m){
        Random r = new Random();
        int randNum = r.nextInt(n - m) + m;
        return randNum;
    }

    public void getAnts(){
        for(int i=0; i<antList.size(); i++){
            Ant ant = (Ant) antList.get(i);
            System.out.println(ant.getId());
        }
    }

    public void setUpEvent(){
        antColony.setUpColonyView(antColony.getColonyView());
        antColony.createInitialAnts();
        antList = antColony.getAntList();

    }

    @Override
    public void simulationEventOccurred(SimulationEvent simEvent) {

        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT)
            {
                setUpEvent();
            }
        else if (simEvent.getEventType() == SimulationEvent.QUEEN_TEST_EVENT)
            {
                queenTurn();
                // set up simulation for testing the queen ant
            }
        else if (simEvent.getEventType() == SimulationEvent.SCOUT_TEST_EVENT)
            {
                scoutTurn();
                // set up simulation for testing the scout ant
            }
        else if (simEvent.getEventType() == SimulationEvent.FORAGER_TEST_EVENT)
                {
                    foragerTurn();
                    // set up simulation for testing the forager ant
                }
        else if (simEvent.getEventType() == SimulationEvent.SOLDIER_TEST_EVENT)
                {
                    SoldierTurn();
                    // set up simulation for testing the soldier ant
                }
        else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT)
                {
                    start();
                    // run the simulation continuously
                }
        else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT)
                {
                    singleTurn();
                    // run the next turn of the simulation
                }
        else
                {
                    // invalid event occurred - probably will never happen
                }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //if queen dies, stop simulation immediately stop method of timer;
        if (queenAlive== false){
            System.out.println("years passed: " + turnCount/365/10 + "\n queen alive? " + queenAlive + "Food left: " + colonyNodes[13][13].getFood() + " turn count : " + turnCount);
            Timer.stop();
        }
        //otherwise invoke method of single turn
        else {
            singleTurn();
        }
    }
}
