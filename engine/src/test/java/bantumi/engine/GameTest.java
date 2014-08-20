package bantumi.engine;

import static bantumi.engine.GameResult.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Test
    public void single_move_game() {
        Game game = new Game(new Board(
                2, 0,
                1, 0
        ), () -> 0, () -> 0);

        GameResult gameResult = game.play();

        assertThat(gameResult).isEqualTo(TOP_PLAYER);
    }

    @Test
    public void multi_moves_game() {
        Player bottomPlayer = mock(Player.class);
        Player topPlayer = mock(Player.class);
        Game game = new Game(new Board(
                3, 1, 0,
                1, 0, 0
        ), bottomPlayer, topPlayer);

        when(bottomPlayer.selectBucket()).thenReturn(1).thenReturn(0);
        when(topPlayer.selectBucket()).thenReturn(0).thenReturn(1);

        GameResult gameResult = game.play();

        assertThat(gameResult).isEqualTo(BOTTOM_PLAYER);
    }

    @Test
    public void illegal_move_means_lost_game() {
        Game game = new Game(new Board(
                0, 1, 0,
                0, 1, 0
        ), () -> 0, () -> 0);

        GameResult gameResult = game.play();

        assertThat(gameResult).isEqualTo(TOP_PLAYER);
    }
}
