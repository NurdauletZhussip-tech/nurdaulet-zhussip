package edu.narxoz.galactic.factory;
import edu.narxoz.galactic.drones.*;

public class DroneFactory {
    public static Drone getAutomaticDrone(double weight) {
        if (weight <= 50){return new LightDrone("Auto-Light-" + (int)(Math.random() * 100), 50.0);
        }else{
            return new HeavyDrone("Auto-Heavy-" + (int)(Math.random() * 100), 500.0);
        }
    }}
