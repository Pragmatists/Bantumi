package bantumi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import bantumi.engine.Engine;
import bantumi.engine.EngineController;

public class Main {
    public static void main(String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(new Class[]{
                EngineController.class,
                Engine.class
        }, args);

        Engine engine = context.getBean(Engine.class);
        engine.start();
    }
}
