package bantumi.engine;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class BoardTest {

    private final Board board = new Board();

    @Test
    public void default_size() {
        board.start(4);
        assertThat(board.getAllBuckets()).hasSize(10);
    }

    @Test
    public void default_amount() {
        board.start(2);
        assertThat(board.getAllBuckets()).containsExactly(
                3, 3, 0,
                3, 3, 0
        );
    }

    @Test
    public void makes_move() {
        board.start(2);

        board.pickFromBucket(0);

        assertThat(board.getAllBuckets()).containsExactly(
                0, 4, 1,
                4, 3, 0
        );
    }

    @Test
    @Parameters({"2", "5"})
    public void cannot_move_from_score_bucket(int bucket) {
        board.start(2);

        Throwable exception = capture(() -> board.pickFromBucket(bucket));

        assertThat(exception).isInstanceOf(IllegalMove.class).hasMessage("Bucket does not exist.");
    }

    @Test
    public void can_move_on_opposite_side() {
        board.start(2);

        board.pickFromBucket(3);

        assertThat(board.getAllBuckets()).containsExactly(
                4, 3, 0,
                0, 4, 1
        );
    }

    @Test
    public void you_do_not_drop_beans_in_opponents_score_bucket(){
        board.start(2,4);

        board.pickFromBucket(1);

        assertThat(board.getAllBuckets()).containsExactly(
                5, 0, 1,
                5, 5, 0
        );
    }

    @Test
    public void you_do_not_drop_beans_in_opponents_score_bucket_starting_on_the_other_side() {
        board.start(2,4);

        board.pickFromBucket(4);

        assertThat(board.getAllBuckets()).containsExactly(
                5, 5, 0,
                5, 0, 1
        );
    }

    @Test
    public void move_ending_in_empty_bucket_moves_bean_to_score_bucket() {
        board.init(new int[]{
                1, 0, 0,
                0, 0, 0
        });

        board.pickFromBucket(0);

        assertThat(board.getAllBuckets()).containsExactly(
                0,0,1,
                0,0,0
        );
    }


    @Test
    public void move_ending_in_empty_bucket_steals_from_opposite_bucket() {
        board.init(new int[]{
                1,0,0,
                1,1,0
        });

        board.pickFromBucket(0);

        assertThat(board.getAllBuckets()).containsExactly(
                0,0,2,
                0,1,0
        );
    }

    @Test
    public void do_not_steal_when_ending_on_opponent_empty_bucket() {
        board.init(new int[]{
                1,3,0,
                0,0,0
        });

        board.pickFromBucket(1);

        assertThat(board.getAllBuckets()).containsExactly(
                1,0,1,
                1,1,0
        );
    }

    public static Throwable capture( Runnable runnable) {
        Throwable result = null;
        try {
            runnable.run();
        } catch( Throwable throwable ) {
            result = throwable;
        }
        return result;
    }

}