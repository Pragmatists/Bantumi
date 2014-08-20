package bantumi.engine;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Board {

    public static final int DEFAULT_BEANS_AMOUNT = 3;

    private int[] buckets;

    int bucketsPerPlayer;

    public Board(int... buckets) {
        init(buckets);
    }

    public int[] getAllBuckets() {
        return buckets;
    }

    public void start(int bucketsPerPlayer, int beansPerBucket) {
        this.bucketsPerPlayer = bucketsPerPlayer;
        int allBuckets = bucketsPerPlayer * 2 + 2;
        buckets = new int[allBuckets];
        initializeContent(beansPerBucket);
    }

    public void start(int bucketsPerPlayer) {
        start(bucketsPerPlayer, DEFAULT_BEANS_AMOUNT);
    }

    private void initializeContent(int beansPerBucket) {
        for (int i = 0; i < buckets.length; i++) {
            boolean finalBucket = (i != buckets.length - 1) && (i != buckets.length / 2 - 1);
            if (finalBucket) {
                buckets[i] = beansPerBucket;
            } else {
                buckets[i] = 0;
            }
        }
    }

    public int pickFromBucket(int bucketNumber) {
        assertBucketValidity(bucketNumber);
        int opponentScoreBucket = opponentScoreBucket(bucketNumber);

        int beans = pickBeansFromBucket(bucketNumber);

        int lastBucket = (bucketNumber + beans) % bucketsAvailableForDroppingBeans();
        for (int i = nextBucketIndex(bucketNumber); beans > 0; i = nextBucketIndex(i)) {
            if (i == opponentScoreBucket) {
                continue;
            }
            buckets[i]++;
            beans--;
        }

        return lastBucket;
    }

    private int pickBeansFromBucket(int bucketNumber) {
        int beans = buckets[bucketNumber];
        buckets[bucketNumber] = 0;
        return beans;
    }

    private int nextBucketIndex(int i) {
        return (i + 1) % buckets.length;
    }

    int topScoreBucket() {
        return buckets.length - 1;
    }

    private int bucketsAvailableForDroppingBeans() {
        return topScoreBucket();
    }

    private int opponentScoreBucket(int bucketNumber) {
        if (bucketNumber < bucketsPerPlayer) {
            return topScoreBucket();
        } else {
            return bucketsPerPlayer;
        }
    }

    private void assertBucketValidity(int bucketNumber) {
        if (bucketNumber == bucketsPerPlayer || bucketNumber > 2 * bucketsPerPlayer) {
            throw new IllegalMove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Board)) {
            return false;
        }

        Board board = (Board) o;

        if (bucketsPerPlayer != board.bucketsPerPlayer) {
            return false;
        }
        if (!Arrays.equals(buckets, board.buckets)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = buckets != null ? Arrays.hashCode(buckets) : 0;
        result = 31 * result + bucketsPerPlayer;
        return result;
    }

    public void init(int[] buckets) {
        this.buckets = buckets;
        bucketsPerPlayer = (buckets.length - 2) / 2;
    }

    @Override
    public String toString() {
        return "Board{" +
                "buckets=" + Arrays.toString(buckets) +
                '}';
    }

    public int getBeansIn(int bucket) {
        return buckets[bucket];
    }

    int bottomScoreBucket() {
        return bucketsPerPlayer;
    }

    public void move(int from, int to) {
        buckets[to] += buckets[from];
        buckets[from] = 0;
    }

    boolean bottomPlayerBucketsAreNotEmpty() {
        return bucketsAreNotEmpty(0, bottomScoreBucket());
    }

    boolean topPlayerBucketsAreNotEmpty() {
        return bucketsAreNotEmpty(bottomScoreBucket() + 1, topScoreBucket());
    }

    private boolean bucketsAreNotEmpty(int startInclusive, int endExclusive) {
        return IntStream.range(startInclusive, endExclusive).anyMatch(i -> buckets[i] != 0);
    }

    public IntStream buckets(int startInclusive, int endExclusive) {
        return IntStream.range(startInclusive, endExclusive).map(i -> buckets[i]);
    }
}
