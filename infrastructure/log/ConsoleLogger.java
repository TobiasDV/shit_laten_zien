package infrastructure.log;

import application.repository.ILogger;

public class ConsoleLogger implements ILogger {
    // This class is a singleton instance
    private static ConsoleLogger instance;
    public static ConsoleLogger getInstance() {
        if (instance == null) instance = new ConsoleLogger();
        return instance;
    }

    private ConsoleLogger() {}

    @Override
    public void logInfo(String message) { System.out.println("[INFO]: " + message); }
    @Override
    public void logWarning(String message) { System.out.println("[WARNING]: " + message); }
    @Override
    public void logError(String message) { System.out.println("[ERROR]: " + message); }
}
