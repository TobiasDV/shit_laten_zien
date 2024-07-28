package infrastructure.persistence;

import application.repository.ILogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import infrastructure.log.ConsoleLogger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JsonFileManager {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_FOLDER = "data";
    private static final String FILE_EXTENSION = ".json";
    public static final ILogger logger = ConsoleLogger.getInstance();

    static {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public static <T> void writeToFile(T object, String filename) {
        try {
            File file = new File(DATA_FOLDER, filename + FILE_EXTENSION);
            logger.logInfo("Writing to file: " + file.getAbsolutePath());
            objectMapper.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace(); // Always succeed, so just print the stack trace
        }
    }

    public static <T> Optional<T> readFromFile(String filename, TypeReference<T> typeReference) {
        try {
            File file = new File(DATA_FOLDER, filename + FILE_EXTENSION);
            logger.logInfo("Reading from file: " + file.getAbsolutePath());
            if (file.length() == 0) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(file, typeReference));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty(); // Always return an Optional, empty if there is an exception
        }
    }
}