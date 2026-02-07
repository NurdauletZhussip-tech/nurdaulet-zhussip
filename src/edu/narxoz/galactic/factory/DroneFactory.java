package edu.narxoz.galactic.factory;

import edu.narxoz.galactic.drones.*;

public class DroneFactory {
    public static Drone createAutomaticDrone(double weight) {
        if (weight <= 50.0) {
            return new LightDrone("L-AUTO-" + (int)(Math.random() * 99), 50.0);
        } else {
            return new HeavyDrone("H-AUTO-" + (int)(Math.random() * 99), 500.0);
        }
    }
}