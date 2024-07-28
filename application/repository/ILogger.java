package application.repository;

public interface ILogger {
    void logInfo(String message);
    void logWarning(String message);
    void logError(String message);
}
