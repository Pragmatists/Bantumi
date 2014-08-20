package bantumi.engine;

public class Score {
    private final int bottomPlayerScore;

    private final int topPlayerScore;

    public Score(int bottomPlayerScore, int topPlayerScore) {
        this.bottomPlayerScore = bottomPlayerScore;
        this.topPlayerScore = topPlayerScore;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }

        Score score = (Score) o;

        if (bottomPlayerScore != score.bottomPlayerScore) {
            return false;
        }
        if (topPlayerScore != score.topPlayerScore) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bottomPlayerScore;
        result = 31 * result + topPlayerScore;
        return result;
    }

    @Override
    public String toString() {
        return "Score{" +
                "bottomPlayerScore=" + bottomPlayerScore +
                ", topPlayerScore=" + topPlayerScore +
                '}';
    }

    GameResult gameResult() {
        if(topPlayerScore > bottomPlayerScore) {
            return GameResult.TOP_PLAYER;
        } else if (topPlayerScore < bottomPlayerScore) {
            return GameResult.BOTTOM_PLAYER;
        } else {
            return GameResult.DRAW;
        }
    }
}
