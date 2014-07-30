package bantumi.engine;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class EngineController {

    @Autowired
    private Engine engine;

    @RequestMapping(value = "/register", method = POST)
    @ResponseBody
    void registerBot(@RequestBody Player player) {
        engine.register(player);
    }

}

