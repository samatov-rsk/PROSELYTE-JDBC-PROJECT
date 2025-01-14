package samatov.jdbcProject.view;

import samatov.jdbcProject.controller.WriterController;
import samatov.jdbcProject.controller.LabelController;
import samatov.jdbcProject.model.Writer;
import samatov.jdbcProject.model.Post;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.enums.PostStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final LabelController labelController;
    private final Scanner scanner;

    public WriterView(WriterController writerController, LabelController labelController) {
        this.writerController = writerController;
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Показать всех писателей");
            System.out.println("2. Найти писателя по ID");
            System.out.println("3. Добавить нового писателя");
            System.out.println("4. Обновить писателя");
            System.out.println("5. Удалить писателя");
            System.out.println("6. Удалить посты писателя");
            System.out.println("7. Добавить пост к писателю");
            System.out.println("8. Выход");

            int count = scanner.nextInt();
            switch (count) {
                case 1:
                    displayAllWriter();
                    break;
                case 2:
                    displayWriterById();
                    break;
                case 3:
                    createWriter();
                    break;
                case 4:
                    updateWriter();
                    break;
                case 5:
                    deleteWriter();
                    break;
                case 6:
                    deleteWriterPosts();
                    break;
                case 7:
                    addPostToWriter();
                    break;
                case 8:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void displayAllWriter() {
        List<Writer> writers = writerController.getAllWriter();
        writers.forEach(writer -> {
            System.out.println("Писатель: " + writer);
            writer.getPosts().forEach(post -> System.out.println("  Пост: " + post));
        });
    }

    private void displayWriterById() {
        System.out.print("Введите ID писателя: ");
        int id = scanner.nextInt();
        Writer writer = writerController.getWriterById(id);
        System.out.println("Писатель: " + writer);
        writer.getPosts().forEach(post -> System.out.println("  Пост: " + post));
    }

    private void createWriter() {
        System.out.print("Введите имя писателя: ");
        String firstName = scanner.next();
        System.out.print("Введите фамилию писателя: ");
        String lastName = scanner.next();
        Writer writer = Writer.builder().firstName(firstName).lastName(lastName).build();
        writerController.saveWriter(writer);
        System.out.println("Писатель добавлен.");
    }

    private void updateWriter() {
        System.out.print("Введите ID писателя для обновления: ");
        int id = scanner.nextInt();
        System.out.print("Введите новое имя писателя: ");
        String firstName = scanner.next();
        System.out.print("Введите новую фамилию писателя: ");
        String lastName = scanner.next();
        Writer writer = Writer.builder().id(id).firstName(firstName).lastName(lastName).build();
        writerController.updateWriter(writer);
        System.out.println("Писатель обновлен.");
    }

    private void deleteWriter() {
        System.out.print("Введите ID писателя для удаления: ");
        int id = scanner.nextInt();
        writerController.deleteWriterById(id);
        System.out.println("Писатель удален.");
    }

    private void deleteWriterPosts() {
        System.out.print("Введите ID писателя для удаления постов: ");
        int writerId = scanner.nextInt();
        writerController.removePostsByWriterId(writerId);
        System.out.println("Посты писателя удалены.");
    }

    private void addPostToWriter() {
        System.out.print("Введите ID писателя: ");
        int writerId = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        System.out.print("Введите текст поста: ");
        String content = scanner.nextLine();

        List<Label> labels = new ArrayList<>();
        System.out.print("Введите количество меток: ");
        int labelCount = scanner.nextInt();
        for (int i = 0; i < labelCount; i++) {
            System.out.print("Введите ID метки: ");
            int labelId = scanner.nextInt();
            Label label = labelController.getLabelById(labelId);
            if (label != null) {
                labels.add(label);
            } else {
                System.out.println("Метка с таким ID не найдена. Пропускаю.");
            }
        }

        Post post = Post.builder()
                .content(content)
                .created(new Timestamp(System.currentTimeMillis()))
                .updated(new Timestamp(System.currentTimeMillis()))
                .status(PostStatus.ACTIVE)
                .labels(labels)
                .build();

        writerController.addPostToWriter(post, labels, writerId);
        System.out.println("Пост добавлен к писателю.");
    }

}
