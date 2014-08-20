package bantumi.engine;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BantumiConfiguration.class, TestConfiguration.class})
@WebAppConfiguration
public class EngineControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    Engine engine;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void registers_player() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(APPLICATION_JSON)
                .content("{\"port\": 123}"))
                .andExpect(status().isOk());

        assertThat(engine.getPlayers()).extracting("port").containsExactly(123L);
    }
}