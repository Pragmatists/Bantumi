package bantumi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import bantumi.engine.BantumiConfiguration;
import bantumi.engine.Engine;

public class Main {
    public static void main(String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class[]{
                BantumiConfiguration.class,
                Engine.class
        }, args);

        Engine engine = context.getBean(Engine.class);
        engine.start();
    }
}
