package model;

import view.AntSimGUI;

public class SimDriver {

    public static void main(String[] args) {
        AntSimGUI antSimGui = new AntSimGUI();
	    new AntSim(antSimGui);
	    System.out.println("Ant Simulation Starting...");
    }
}
