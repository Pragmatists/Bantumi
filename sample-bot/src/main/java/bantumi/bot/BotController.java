package bantumi.bot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class BotController {

    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return "Still alive!";
    }
}

