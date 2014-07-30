package bantumi;

import org.springframework.boot.SpringApplication;

import bantumi.bot.BotController;

public class Main {
    public static void main(String... args) {
        SpringApplication.run(BotController.class, args);
    }
}
