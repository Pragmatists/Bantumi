package bantumi.engine;

import static bantumi.engine.Turn.*;

public class Logic {
    Board board;

    private Turn turn;

    public Logic(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public Board getBoardState() {
        return board;
    }

    public void pickFrom(int selectedBucket) {
        assertBucketValidity(selectedBucket);
        int lastBucket = board.pickFromBucket(translate(selectedBucket));
        int scoreBucket = BOTTOM_PLAYER.equals(turn) ? board.bottomScoreBucket() : board.topScoreBucket();
        if (wasEmpty(lastBucket) && lastBucket != scoreBucket && isMineBucket(lastBucket)) {
            board.move(lastBucket, scoreBucket);
            steal(lastBucket, scoreBucket);
        }
        if (lastBucket != scoreBucket) {
            turn = turn.next();
        }
    }

    private void steal(int lastBucket, int scoreBucket) {
        board.move(oppositeTo(lastBucket), scoreBucket);
    }

    private int oppositeTo(int bucket) {
        return board.bucketsPerPlayer * 2 - bucket;
    }

    private boolean wasEmpty(int lastBucket) {
        return board.getBeansIn(lastBucket) == 1;
    }

    private boolean isMineBucket(int lastBucket) {
        return (lastBucket <= translate(board.bucketsPerPlayer - 1) && lastBucket >= translate(0));
    }

    private int translate(int selectedBucket) {
        return BOTTOM_PLAYER.equals(turn) ? selectedBucket : selectedBucket + board.bucketsPerPlayer + 1;
    }

    private void assertBucketValidity(int bucketNumber) {
        if (bucketNumber >= board.bucketsPerPlayer ) {
            throw new IllegalMove("Bucket does not exist.");
        }else if(board.isBucketEmpty(translate(bucketNumber))){
            throw new IllegalMove("Bucket is empty.");
        }
    }

    public boolean canMakeMove() {
        return board.bottomPlayerBucketsAreNotEmpty() && board.topPlayerBucketsAreNotEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Logic)) {
            return false;
        }

        Logic logic = (Logic) o;

        if (board != null ? !board.equals(logic.board) : logic.board != null) {
            return false;
        }
        if (turn != logic.turn) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (turn != null ? turn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Logic{" +
                "board=" + board +
                ", turn=" + turn +
                '}';
    }

    public Score getScore() {
        int bottomPlayerScore = board.buckets(0, board.bottomScoreBucket() + 1).sum();
        int topPlayerScore = board.buckets(board.bottomScoreBucket() + 1, board.topScoreBucket() + 1).sum();

        return new Score(bottomPlayerScore, topPlayerScore);
    }

    public Turn turn() {
        return turn;
    }
}
