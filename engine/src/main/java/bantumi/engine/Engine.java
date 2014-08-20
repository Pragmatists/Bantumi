package bantumi.engine;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Sets.*;
import static java.lang.System.*;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Engine {

    private Collection<Board> boards = newArrayList();

    private Set<Player> players = newHashSet();

    public void start() {

    }

    public void register(Player player) {
        players.add(player);
        out.println(player.test());
    }

    public Collection<Player> getPlayers() {
        return players;
    }
}
