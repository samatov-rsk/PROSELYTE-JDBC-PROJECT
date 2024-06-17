package samatov.jdbcProject.view;

import samatov.jdbcProject.controller.PostController;
import samatov.jdbcProject.controller.WriterController;
import samatov.jdbcProject.controller.LabelController;

import java.util.Scanner;

public class MainView {

    private final PostView postView;
    private final WriterView writerView;
    private final LabelView labelView;
    private final Scanner scanner;

    public MainView(WriterController writerController, LabelController labelController, PostController postController) {
        this.writerView = new WriterView(writerController,labelController);
        this.labelView = new LabelView(labelController);
        this.postView = new PostView(postController, labelController);
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1. Работа с писателями");
            System.out.println("2. Работа с постами");
            System.out.println("3. Работа с метками");
            System.out.println("4. Выход");

            System.out.print("Введите ваш выбор: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    writerView.displayMenu();
                    break;
                case 2:
                    postView.displayMenu();
                    break;
                case 3:
                    labelView.displayMenu();
                    break;
                case 4:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    break;
            }
        }
    }
}
