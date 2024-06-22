package samatov.jdbcProject.view;

import samatov.jdbcProject.controller.LabelController;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {

    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView(LabelController labelController) {
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Показать все метки");
            System.out.println("2. Найти метку по ID");
            System.out.println("3. Добавить новую метку");
            System.out.println("4. Обновить метку");
            System.out.println("5. Удалить метку");
            System.out.println("6. Выход");

            int count = scanner.nextInt();
            switch (count) {
                case 1:
                    displayAllLabels();
                    break;
                case 2:
                    displayLabelById();
                    break;
                case 3:
                    createLabel();
                    break;
                case 4:
                    updateLabel();
                    break;
                case 5:
                    deleteLabel();
                    break;
                case 6:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void displayAllLabels() {
        List<LabelDTO> labels = labelController.getAllLabels();
        labels.forEach(System.out::println);
    }

    private void displayLabelById() {
        System.out.print("Введите ID метки: ");
        int id = scanner.nextInt();
        LabelDTO label = labelController.getLabelById(id);
        System.out.println(label);
    }

    private void createLabel() {
        System.out.print("Введите имя метки: ");
        String name = scanner.next();
        LabelDTO label = LabelDTO.builder().name(name).build();
        labelController.saveLabel(label);
        System.out.println("Метка добавлена.");
    }

    private void updateLabel() {
        System.out.print("Введите ID метки для обновления: ");
        int id = scanner.nextInt();
        System.out.print("Введите новое имя метки: ");
        String name = scanner.next();
        LabelDTO label = new LabelDTO(id, name);
        labelController.updateLabel(label);
        System.out.println("Метка обновлена.");
    }

    private void deleteLabel() {
        System.out.print("Введите ID метки для удаления: ");
        int id = scanner.nextInt();
        labelController.deleteLabelById(id);
        System.out.println("Метка удалена.");
    }
}
