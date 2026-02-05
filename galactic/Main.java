package edu.narxoz.galactic;
import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.bodies.SpaceStation;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.factory.DroneFactory;
import edu.narxoz.galactic.task.DeliveryTask;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dispatcher dispatcher = new Dispatcher();
        System.out.println("=== GALACTIC LOGISTICS TERMINAL ===");

        try {
            System.out.println("\n--- 1. Настройка Отправления (Планета) ---");
            System.out.print("Введите название планеты: ");
            String planetName = scanner.nextLine();
            System.out.print("Введите координату X: ");
            double planetX = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите координату Y: ");
            double planetY = Double.parseDouble(scanner.nextLine());
            System.out.print("Тип атмосферы: ");
            String atmosphere = scanner.nextLine();

            Planet origin = new Planet(planetName, planetX, planetY, atmosphere);

            System.out.println("\n--- 2. Настройка Назначения (Станция) ---");
            System.out.print("Введите название станции: ");
            String stationName = scanner.nextLine();
            System.out.print("Введите координату X: ");
            double stationX = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите координату Y: ");
            double stationY = Double.parseDouble(scanner.nextLine());
            System.out.print("Уровень станции (целое число): ");
            int level = Integer.parseInt(scanner.nextLine());

            SpaceStation destination = new SpaceStation(stationName, stationX, stationY, level);

            System.out.println("\n--- 3. Регистрация Груза ---");
            System.out.print("Описание груза: ");
            String cargoDesc = scanner.nextLine();
            System.out.print("Вес груза (кг): ");
            double cargoWeight = Double.parseDouble(scanner.nextLine());

            Cargo cargo = new Cargo(cargoWeight, cargoDesc);

            System.out.println("\n--- 4. Запрос Дрона ---");
            System.out.print("Введите ID дрона: ");
            String droneId = scanner.nextLine();
            System.out.print("Требуемая грузоподъемность дрона (кг): ");
            double dronePayload = Double.parseDouble(scanner.nextLine());

            Drone drone = DroneFactory.createDrone(droneId, dronePayload);

            System.out.println(">> Система подобрала дрон типа: " + drone.getClass().getSimpleName());
            System.out.println(">> Скорость дрона: " + drone.speedKmPerMin() + " км/мин");

            System.out.println("\n--- 5. Обработка Задачи ---");
            DeliveryTask task = new DeliveryTask(origin, destination, cargo);

            System.out.println("Попытка назначить дрон...");
            Result assignResult = dispatcher.assignTask(task, drone);

            if (assignResult.ok()) {
                System.out.println("[OK] Дрон успешно назначен!");

                double distance = origin.distanceTo(destination);
                double time = task.estimateTime();

                System.out.printf("Дистанция: %.2f км\n", distance);
                System.out.printf("Расчетное время полета: %.2f мин\n", time);

                System.out.println("Нажмите Enter, чтобы завершить миссию...");
                scanner.nextLine();

                Result completeResult = dispatcher.completeTask(task);
                if (completeResult.ok()) {
                    System.out.println("[SUCCESS] Миссия завершена. Груз доставлен.");
                } else {
                    System.out.println("[ERROR] Ошибка завершения: " + completeResult.reason());
                }

            } else {
                System.out.println("[FAILED] Не удалось назначить дрон.");
                System.out.println("Причина: " + assignResult.reason());
                System.out.println("Совет: Попробуйте заказать дрон с большей грузоподъемностью.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода! Пожалуйста, вводите числа там, где это требуется.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
