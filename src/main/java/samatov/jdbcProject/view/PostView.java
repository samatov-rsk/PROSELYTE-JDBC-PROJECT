package samatov.jdbcProject.view;

import samatov.jdbcProject.controller.PostController;
import samatov.jdbcProject.controller.LabelController;
import samatov.jdbcProject.enums.PostStatus;
import samatov.jdbcProject.model.Label;
import samatov.jdbcProject.model.Post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final LabelController labelController;
    private final Scanner scanner;

    public PostView(PostController postController, LabelController labelController) {
        this.postController = postController;
        this.labelController = labelController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Показать все посты");
            System.out.println("2. Найти пост по ID");
            System.out.println("3. Добавить новый пост");
            System.out.println("4. Обновить пост");
            System.out.println("5. Удалить пост");
            System.out.println("6. Добавить метку к посту");
            System.out.println("7. Удалить метку с поста");
            System.out.println("8. Выход");

            System.out.print("Введите номер действия: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayAllPosts();
                    break;
                case 2:
                    displayPostById();
                    break;
                case 3:
                    createPost();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    addLabelToPost();
                    break;
                case 7:
                    removeLabelFromPost();
                    break;
                case 8:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void displayAllPosts() {
        List<Post> posts = postController.getAllPost();
        if (posts.isEmpty()) {
            System.out.println("Посты не найдены.");
        } else {
            posts.forEach(System.out::println);
        }
    }

    private void displayPostById() {
        System.out.print("Введите ID поста: ");
        int id = scanner.nextInt();
        Post post = postController.getPostById(id);
        if (post != null) {
            System.out.println(post);
        } else {
            System.out.println("Пост с таким ID не найден.");
        }
    }

    private void createPost() {
        System.out.print("Введите текст поста: ");
        scanner.nextLine();
        String content = scanner.nextLine();

        System.out.print("Введите ID писателя: ");
        int writerId = scanner.nextInt();

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

        postController.savePost(post, writerId);
        System.out.println("Пост добавлен.");
    }

    private void updatePost() {
        System.out.print("Введите ID поста для обновления: ");
        int id = scanner.nextInt();
        Post existingPost = postController.getPostById(id);
        if (existingPost == null) {
            System.out.println("Пост с таким ID не найден.");
            return;
        }

        System.out.print("Введите новый текст поста: ");
        scanner.nextLine();
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

        existingPost.setContent(content);
        existingPost.setUpdated(new Timestamp(System.currentTimeMillis()));
        existingPost.setStatus(PostStatus.UNDER);
        existingPost.setLabels(labels);

        postController.updatePost(existingPost);
        System.out.println("Пост обновлен.");
    }

    private void deletePost() {
        System.out.print("Введите ID поста для удаления: ");
        int id = scanner.nextInt();
        if (postController.getPostById(id) == null) {
            System.out.println("Пост с таким ID не найден или не может быть удален.");
        } else {
            postController.deletePostById(id);
            System.out.println("Пост удален.");
        }
    }

    private void addLabelToPost() {
        System.out.print("Введите ID поста: ");
        int postId = scanner.nextInt();
        Post post = postController.getPostById(postId);
        if (post == null) {
            System.out.println("Пост с таким ID не найден.");
            return;
        }

        System.out.print("Введите ID метки: ");
        int labelId = scanner.nextInt();
        Label label = labelController.getLabelById(labelId);
        if (label == null) {
            System.out.println("Метка с таким ID не найдена.");
            return;
        }

        postController.addLabelToPost(postId, label);
        System.out.println("Метка добавлена к посту.");
    }

    private void removeLabelFromPost() {
        System.out.print("Введите ID поста: ");
        int postId = scanner.nextInt();
        Post post = postController.getPostById(postId);
        if (post == null) {
            System.out.println("Пост с таким ID не найден.");
            return;
        }

        System.out.print("Введите ID метки: ");
        int labelId = scanner.nextInt();
        Label label = labelController.getLabelById(labelId);
        if (label == null) {
            System.out.println("Метка с таким ID не найдена.");
            return;
        }

        postController.removeLabelFromPost(postId, labelId);
        System.out.println("Метка удалена с поста.");
    }
}
