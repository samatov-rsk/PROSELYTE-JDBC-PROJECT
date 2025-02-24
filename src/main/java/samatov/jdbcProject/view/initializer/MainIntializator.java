package samatov.jdbcProject.view.initializer;

import samatov.jdbcProject.controller.LabelController;
import samatov.jdbcProject.controller.PostController;
import samatov.jdbcProject.controller.WriterController;
import samatov.jdbcProject.repository.impl.LabelRepositoryImpl;
import samatov.jdbcProject.repository.impl.PostRepositoryImpl;
import samatov.jdbcProject.repository.impl.WriterRepositoryImpl;
import samatov.jdbcProject.service.LabelService;
import samatov.jdbcProject.service.PostService;
import samatov.jdbcProject.service.WriterService;
import samatov.jdbcProject.view.MainView;

public class MainIntializator {
    public static MainView initializeApp() {
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        WriterRepositoryImpl writerRepository = new WriterRepositoryImpl();
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        LabelService labelService = new LabelService(labelRepository);
        WriterService writerService = new WriterService(writerRepository,postRepository);
        WriterController writerController = new WriterController(writerService);
        LabelController labelController = new LabelController(labelService);
        PostService postService = new PostService(postRepository);
        PostController postController = new PostController(postService);


        return new MainView(writerController, labelController, postController);
    }
}
