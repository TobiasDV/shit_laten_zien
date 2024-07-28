package infrastructure.security;

import infrastructure.persistence.MemoryUserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final MemoryUserRepository repository = MemoryUserRepository.getInstance();

    public boolean authenticate(String username, String password) {
        var user = repository.getUser(username);
        if (user.isPresent())
            return BCrypt.checkpw(password, user.get().getPassword());
        return false;
    }

    public String issueToken(String username) {
        return JwtUtil.generateToken(username);
    }
}
