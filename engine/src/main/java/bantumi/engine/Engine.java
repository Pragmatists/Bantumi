package bantumi.engine;

import static com.google.common.collect.Sets.*;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Engine {

    private Set<Player> players = newHashSet();

    public void start() {

    }

    public void register(Player player) {
        players.add(player);
    }

    public Collection<Player> getPlayers() {
        return players;
    }
}
