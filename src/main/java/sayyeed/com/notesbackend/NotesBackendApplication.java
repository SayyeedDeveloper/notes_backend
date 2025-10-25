package sayyeed.com.notesbackend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesBackendApplication {

    static {
        // Only load .env file if not in production
        String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (!"production".equals(activeProfile)) {
            try {
                Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
                dotenv.entries().forEach(entry ->
                        System.setProperty(entry.getKey(), entry.getValue())
                );
            } catch (Exception e) {
                // Ignore if .env file is not found
                System.out.println("No .env file found, using system environment variables");
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(NotesBackendApplication.class, args);
    }
}