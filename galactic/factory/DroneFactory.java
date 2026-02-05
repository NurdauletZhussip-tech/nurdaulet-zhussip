package edu.narxoz.galactic.factory;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
public class DroneFactory {
    public static Drone createDrone(String id, double maxPayload) {
        if (maxPayload <= 50.0) {
            return new LightDrone(id, maxPayload);
        } else {
            return new HeavyDrone(id, maxPayload);
        }
    }
}