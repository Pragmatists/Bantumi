package bantumi.engine;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Sets.*;
import static java.lang.String.*;
import static java.lang.System.out;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Engine {

    private Collection<Game> games = newArrayList();

    private Set<Player> players = newHashSet();

    public void start() {

    }

    public void register(Player player) {
        players.add(player);
        out.println(new RestTemplate().getForObject(format("http://localhost:%s/test", player.getPort()), String.class));
    }

    public Collection<Player> getPlayers() {
        return players;
    }
}
