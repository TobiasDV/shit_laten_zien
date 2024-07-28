package infrastructure.persistence;

import application.service.response.HttpCode;
import application.service.response.ServiceResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import infrastructure.security.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class MemoryUserRepository {
    private final List<User> data;
    private final String repositoryName = "UserInfoRepository";
    
    private static MemoryUserRepository instance;
    public static MemoryUserRepository getInstance() {
        if (instance == null) instance = new MemoryUserRepository();
        return instance;
    }
    
    private MemoryUserRepository() {
        var maybeData = JsonFileManager.readFromFile(repositoryName, new TypeReference<List<User>>() {});
        data = maybeData.orElseGet(ArrayList::new);
        
        if(data.isEmpty()) // Create default user
            createNewUser("admin", "admin");
    }
    
    public void saveToFile() { JsonFileManager.writeToFile(data, repositoryName); }

    public Optional<User> getUser(String username) {
        return data.stream()
            .filter(user -> user.getUsername().equals(username))
            .findFirst();
    }

    private void createNewUser(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        var newUser = new User(username, hashedPassword);
        data.add(newUser);
    }
}
