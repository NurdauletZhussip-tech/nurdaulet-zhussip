package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.*;
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

        System.out.println("=== ГАЛАКТИЧЕСКИЙ ДИСПЕТЧЕР 2.0 ===");

        try {
            System.out.println("\n[1] Точка отправления:");
            System.out.print("Название планеты: ");
            String pName = scanner.nextLine();
            System.out.print("Координаты X и Y (через пробел): ");
            double px = scanner.nextDouble();
            double py = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("\n[2] Точка назначения:");
            System.out.print("Название станции: ");
            String sName = scanner.nextLine();
            System.out.print("Координаты X и Y (через пробел): ");
            double sx = scanner.nextDouble();
            double sy = scanner.nextDouble();
            System.out.print("\n[3] Вес груза (кг): ");
            double weight = scanner.nextDouble();
            Planet origin = new Planet(pName, px, py, "Standard");
            SpaceStation destination = new SpaceStation(sName, sx, sy, 1);
            Cargo cargo = new Cargo(weight, "Essential Supplies");
            DeliveryTask task = new DeliveryTask(origin, destination, cargo);
            Drone drone = DroneFactory.createAutomaticDrone(weight);

            System.out.println("\n------------------------------------");
            System.out.println("СИСТЕМА: Для веса " + weight + " кг выделен " + drone.getClass().getSimpleName());
            System.out.println("ID Дрона: " + drone.getId());
            var res = dispatcher.assignTask(task, drone);
            if (res.ok()) {
                double distance = origin.distanceTo(destination);
                System.out.printf("Дистанция полета: %.2f units\n", distance);
                System.out.printf("Расчетное время: %.2f мин\n", task.estimateTime());

                dispatcher.completeTask(task);
                System.out.println("СТАТУС: Груз успешно доставлен.");
            } else {
                System.out.println("ОШИБКА: " + res.reason());
            }

        } catch (Exception e) {
            System.out.println("Ошибка ввода. Проверьте формат чисел.");
        } finally {
            scanner.close();
        }
    }
}
