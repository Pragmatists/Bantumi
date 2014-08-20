package bantumi.engine;

import static bantumi.engine.Turn.*;

import java.util.concurrent.Future;

public class Game {
    private final Board board;

    private final Player bottomPlayer;

    private final Player topPlayer;

    public Game(Board board, Player bottomPlayer, Player topPlayer) {

        this.board = board;
        this.bottomPlayer = bottomPlayer;
        this.topPlayer = topPlayer;
    }

    public GameResult play() {
        Logic logic = new Logic(board, BOTTOM_PLAYER);
        while (logic.canMakeMove()) {
            try {
                int selectedBucket = currentPlayer(logic.turn()).selectBucket();
                logic.pickFrom(selectedBucket);
            } catch (IllegalMove illegalMove) {
                return lostGame(logic.turn());
            }
        }

        Score score = logic.getScore();
        return score.gameResult();
    }

    private GameResult lostGame(Turn turn) {
        return turn.equals(BOTTOM_PLAYER) ? GameResult.TOP_PLAYER : GameResult.BOTTOM_PLAYER;
    }

    private Player currentPlayer(Turn turn) {
        return turn.equals(BOTTOM_PLAYER) ? bottomPlayer : topPlayer;
    }

}
