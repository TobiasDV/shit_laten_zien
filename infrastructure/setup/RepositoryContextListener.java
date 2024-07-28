package infrastructure.setup;

import infrastructure.persistence.MemoryBattleSnakeRepository;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextEvent;

@WebListener
public class RepositoryContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Saves any data to disk before shutting down
        System.out.println("Shutting down repositories");
        MemoryBattleSnakeRepository.getInstance().saveToFile();
    }
}
