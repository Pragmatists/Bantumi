package bantumi.engine;

import static bantumi.engine.Turn.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LogicTest {

    private Logic logic;

    @Test
    public void simple_move() {
        logic = new Logic(new Board(
                1, 1, 0,
                0, 0, 0
        ), BOTTOM_PLAYER);

        logic.move(0);

        assertThat(logic).isEqualTo(new Logic(new Board(
                0, 2, 0,
                0, 0, 0
        ),TOP_PLAYER));
    }

    @Test
    public void makes_move() {
        logic = new Logic(new Board(
                3, 3, 0,
                3, 3, 0
        ), BOTTOM_PLAYER);

        logic.move(0);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                0, 4, 1,
                4, 3, 0
        ));
    }

    @Test
    @Parameters({"2", "5"})
    public void cannot_move_from_score_bucket(int bucket) {
        logic = new Logic(new Board(
                3, 3, 0,
                3, 3, 0
        ), BOTTOM_PLAYER);

        Throwable exception = capture(() -> logic.move(bucket));

        assertThat(exception).isInstanceOf(IllegalMove.class).hasMessage("Bucket does not exist.");
    }

    @Test
    public void another_move_is_for_another_player() {
        logic = new Logic(new Board(
                1, 3, 0,
                1, 3, 0
        ), Turn.TOP_PLAYER);

        logic.move(0);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                1, 3, 0,
                0, 4, 0
        ));
    }

    @Test
    public void you_do_not_drop_beans_in_opponents_score_bucket() {
        logic = new Logic(new Board(
                4, 4, 0,
                4, 4, 0
        ), BOTTOM_PLAYER);

        logic.move(1);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                5, 0, 1,
                5, 5, 0
        ));
    }

    @Test
    public void you_do_not_drop_beans_in_opponents_score_bucket_starting_on_the_other_side() {
        logic = new Logic(new Board(
                4, 4, 0,
                4, 4, 0
        ), BOTTOM_PLAYER);

        Throwable throwable = capture(() -> logic.move(4));

        assertThat(throwable).isInstanceOf(IllegalMove.class).hasMessage("Bucket does not exist.");
    }

    @Test
    public void move_ending_in_empty_bucket_moves_bean_to_score_bucket() {
        logic = new Logic(new Board(
                1, 0, 0,
                0, 0, 0
        ), BOTTOM_PLAYER);

        logic.move(0);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                0, 0, 1,
                0, 0, 0
        ));
    }

    @Test
    public void move_ending_in_empty_bucket_steals_from_opposite_bucket() {
        logic = new Logic(new Board(
                1, 0, 0,
                1, 1, 0
        ), BOTTOM_PLAYER);

        logic.move(0);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                0, 0, 2,
                0, 1, 0
        ));
    }

    @Test
    public void do_not_steal_when_ending_on_opponent_empty_bucket() {
        logic = new Logic(new Board(
                1, 3, 0,
                0, 0, 0
        ), BOTTOM_PLAYER);

        logic.move(1);

        assertThat(logic.getBoardState()).isEqualTo(new Board(
                1, 0, 1,
                1, 1, 0
        ));
    }

    @Test
    public void have_another_turn_after_ending_in_own_score_bucket() {
        logic = new Logic(new Board(
                2, 0, 0,
                1, 1, 0
        ), BOTTOM_PLAYER);

        logic.move(0);

        assertThat(logic).isEqualTo(new Logic(new Board(
                0, 1, 1,
                1, 1, 0
        ), BOTTOM_PLAYER));

    }

    @Test
    public void game_ends_when_current_player_has_no_beans_in_buckets() {
        logic = new Logic(new Board(
                0, 0, 1,
                0, 1, 2
        ), BOTTOM_PLAYER);

        assertThat(logic.canMakeMove()).isFalse();
    }

    @Test
    public void game_ends_when_opponent_has_no_beans_in_buckets() {
        logic = new Logic(new Board(
                0, 0, 1,
                0, 1, 2
        ), TOP_PLAYER);

        assertThat(logic.canMakeMove()).isFalse();
    }

    @Test
    public void game_does_not_end_when_both_players_have_beans_in_buckets() {
        logic = new Logic(new Board(
                0, 1, 1,
                0, 1, 2
        ), TOP_PLAYER);

        assertThat(logic.canMakeMove()).isTrue();
    }

    @Test
    public void calculates_score() {
        logic = new Logic(new Board(
                2, 3, 1,
                0, 0, 2
        ), TOP_PLAYER);

        assertThat(logic.getScore()).isEqualTo(new Score(6,2));
    }

    public static Throwable capture(Runnable runnable) {
        Throwable result = null;
        try {
            runnable.run();
        } catch (Throwable throwable) {
            result = throwable;
        }
        return result;
    }

}
