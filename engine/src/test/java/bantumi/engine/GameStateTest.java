package bantumi.engine;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class GameStateTest {

    private final Game game = new Game();

    @Test
    public void default_size() {
        game.start(4);
        assertThat(game.getAllBuckets()).hasSize(10);
    }

    @Test
    public void default_amount() {
        game.start(2);
        assertThat(game.getAllBuckets()).containsExactly(
                3, 3, 0,
                3, 3, 0
        );
    }

    @Test
    public void makes_move() {
        game.start(2);

        game.pickFromBucket(0);

        assertThat(game.getAllBuckets()).containsExactly(
                0, 4, 1,
                4, 3, 0
        );
    }
}