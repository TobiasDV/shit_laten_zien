package infrastructure.security;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/*
* There is only one user in this application, so we can keep this as simple as possible.
*/
public class SnakeSecurityContext implements SecurityContext {
    private final String username;
    private final boolean isSecure;

    public SnakeSecurityContext(String username, boolean isSecure) {
        this.username = username;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() { return () -> this.username; }

    @Override
    public boolean isUserInRole(String role) { return true; }

    @Override
    public boolean isSecure() { return this.isSecure; }

    @Override
    public String getAuthenticationScheme() { return "Bearer"; }
}
