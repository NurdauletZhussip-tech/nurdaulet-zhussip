package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.factory.DroneFactory;
import edu.narxoz.galactic.task.DeliveryTask;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dispatcher dispatcher = new Dispatcher();

        System.out.println("=== АВТОМАТИЧЕСКАЯ ЛОГИСТИКА ===");
        System.out.print("Введите название планеты отправления: ");
        String pName = scanner.nextLine();
        
        System.out.print("Введите название станции назначения: ");
        String sName = scanner.nextLine();

        System.out.print("Вес груза (кг): ");
        double weight = Double.parseDouble(scanner.nextLine());
        Planet origin = new Planet(pName, 0, 0, "Oxygen");
        SpaceStation destination = new SpaceStation(sName, 100, 200, 1);
        Cargo cargo = new Cargo(weight, "Standard Package");
        DeliveryTask task = new DeliveryTask(origin, destination, cargo);
        Drone autoDrone = DroneFactory.getAutomaticDrone(weight);
        System.out.println("\n[СИСТЕМА]: Выделен дрон " + autoDrone.getId() + " (" + autoDrone.getClass().getSimpleName() + ")");
        var result = dispatcher.assignTask(task, autoDrone);
        if (result.ok()) {
            System.out.println("[УСПЕХ]: Дрон в пути!");
            System.out.printf("Время доставки: %.2f мин\n", task.estimateTime());
            
            dispatcher.completeTask(task);
            System.out.println("[СТАТУС]: Доставлено.");
        } else {
            System.out.println("[ОШИБКА]: " + result.reason());
        }scanner.close();}}
