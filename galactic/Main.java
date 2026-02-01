package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;

public class Main {
    public static void main(String[] args) {
        Planet earth = new Planet("Earth", 0, 0, "Nitrogen-Oxygen");
        SpaceStation moonStation = new SpaceStation("MoonBase", 300, 400, 5);

        Cargo heavyCargo = new Cargo(60.0, "Mining Equipment");

        LightDrone lightDrone = new LightDrone("L-001", 50.0);
        HeavyDrone heavyDrone = new HeavyDrone("H-900", 200.0);

        Dispatcher dispatcher = new Dispatcher();

        System.out.println("--- SCENARIO 1: Fail to assign overweight cargo ---");
        DeliveryTask task1 = new DeliveryTask(earth, moonStation, heavyCargo);

        Result res1 = dispatcher.assignTask(task1, lightDrone);
        System.out.println("Assign LightDrone Result: " + res1.ok() + " (" + res1.reason() + ")");

        System.out.println("\n--- SCENARIO 2: Success with HeavyDrone ---");
        Result res2 = dispatcher.assignTask(task1, heavyDrone);
        System.out.println("Assign HeavyDrone Result: " + res2.ok() + " (" + res2.reason() + ")");

        System.out.println("\n--- SCENARIO 3: Estimate Time ---");
        System.out.println("Estimated Time: " + task1.estimateTime() + " minutes");

        System.out.println("\n--- SCENARIO 4: Completion ---");
        System.out.println("Current Task State: " + task1.getState());
        System.out.println("Current Drone Status: " + heavyDrone.getStatus());

        Result res3 = dispatcher.completeTask(task1);
        System.out.println("Completion Result: " + res3.ok());

        System.out.println("Final Task State: " + task1.getState());
        System.out.println("Final Drone Status: " + heavyDrone.getStatus());
    }
}