package edu.narxoz.galactic.dispatcher;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;

public class Dispatcher {

    public Result assignTask(DeliveryTask task, Drone drone) {
        if (task == null || drone == null) {
            return new Result(false, "Task or Drone is null");
        }
        if (drone.getStatus() != DroneStatus.IDLE) {
            return new Result(false, "Drone is not IDLE");
        }
        if (task.getCargo().getWeightKg() > drone.getMaxPayloadKg()) {
            return new Result(false, "Cargo too heavy for this drone");
        }
        if (task.getState() != TaskState.CREATED) {
            return new Result(false, "Task is not in CREATED state");
        }
        task.setState(TaskState.ASSIGNED);
        task.setAssignedDrone(drone);
        drone.setStatus(DroneStatus.IN_FLIGHT);

        return new Result(true, "Success");
    }

    public Result completeTask(DeliveryTask task) {
        if (task == null) {
            return new Result(false, "Task is null");
        }
        if (task.getState() != TaskState.ASSIGNED) {
            return new Result(false, "Task is not ASSIGNED");
        }
        if (task.getAssignedDrone() == null) {
            return new Result(false, "No drone assigned");
        }
        if (task.getAssignedDrone().getStatus() != DroneStatus.IN_FLIGHT) {
            return new Result(false, "Drone is not IN_FLIGHT");
        }
        task.setState(TaskState.DONE);
        task.getAssignedDrone().setStatus(DroneStatus.IDLE);

        return new Result(true, "Task completed successfully");
    }
}