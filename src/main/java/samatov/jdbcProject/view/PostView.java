package samatov.jdbcProject.view;

import lombok.RequiredArgsConstructor;
import samatov.jdbcProject.controller.PostController;
import samatov.jdbcProject.dto.LabelDTO;
import samatov.jdbcProject.dto.PostDTO;
import samatov.jdbcProject.enums.PostStatus;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PostView {

    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController) {
        this.postController = postController;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Показать все посты");
            System.out.println("2. Найти пост по ID");
            System.out.println("3. Добавить новый пост");
            System.out.println("4. Обновить пост");
            System.out.println("5. Удалить пост");
            System.out.println("6. Добавить метку к посту");
            System.out.println("7. Удалить метку с поста");
            System.out.println("8. Выход");
            System.out.print("Выберите опцию: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    showAllPosts();
                    break;
                case 2:
                    findPostById();
                    break;
                case 3:
                    addNewPost();
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
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте еще раз.");
            }
        }
    }

    private void showAllPosts() {
        List<PostDTO> posts = postController.getAllPosts();
        for (PostDTO post : posts) {
            System.out.println(post);
        }
    }

    private void findPostById() {
        System.out.print("Введите ID поста: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        PostDTO post = postController.getPostById(id);
        System.out.println(post);
    }

    private void addNewPost() {
        System.out.print("Введите текст поста: ");
        String content = scanner.nextLine();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostDTO postDTO = PostDTO.builder()
                .content(content)
                .created(timestamp)
                .updated(timestamp)
                .status(PostStatus.ACTIVE) // начальный статус
                .labels(Collections.emptyList()) // Инициализация пустым списком
                .build();

        postDTO = postController.createPost(postDTO);

        System.out.print("Введите количество меток для добавления: ");
        int labelCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < labelCount; i++) {
            System.out.print("Введите название метки: ");
            String labelName = scanner.nextLine();
            LabelDTO labelDTO = LabelDTO.builder()
                    .name(labelName)
                    .build();
            postController.addLabelToPost(postDTO.getId(), labelDTO.getId());
        }
        System.out.println("Пост добавлен: " + postDTO);
    }

    private void updatePost() {
        System.out.print("Введите ID поста: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        PostDTO postDTO = postController.getPostById(id);
        if (postDTO == null) {
            System.out.println("Пост не найден.");
            return;
        }

        System.out.print("Введите новый текст поста: ");
        String content = scanner.nextLine();
        postDTO.setContent(content);
        postDTO.setUpdated(new Timestamp(System.currentTimeMillis()));

        postDTO = postController.updatePost(postDTO);
        System.out.println("Пост обновлен: " + postDTO);
    }

    private void deletePost() {
        System.out.print("Введите ID поста: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        postController.deletePost(id);
        System.out.println("Пост удален.");
    }

    private void addLabelToPost() {
        System.out.print("Введите ID поста: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Введите ID метки: ");
        int labelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        postController.addLabelToPost(postId, labelId);
        System.out.println("Метка добавлена к посту.");
    }


    private void removeLabelFromPost() {
        System.out.print("Введите ID поста: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Введите ID метки: ");
        int labelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        postController.removeLabelFromPost(postId, labelId);
        System.out.println("Метка удалена с поста.");
    }
}
