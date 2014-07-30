package bantumi.engine;

public class Game {

    public static final int DEFAULT_BEANS_AMOUNT = 3;

    private int[] buckets;

    public int[] getAllBuckets() {
        return buckets;
    }

    public void start(int bucketsPerPlayer) {
        int allBuckets = bucketsPerPlayer * 2 + 2;
        buckets = new int[allBuckets];
        initializeContent();
    }

    private void initializeContent() {
        for (int i = 0; i < buckets.length; i++) {
            boolean finalBucket = (i != buckets.length - 1) && (i != buckets.length / 2 - 1);
            if (finalBucket) {
                buckets[i] = DEFAULT_BEANS_AMOUNT;
            } else {
                buckets[i] = 0;
            }
        }
    }

    public void pickFromBucket(int bucketNumber) {
        int beans = buckets[bucketNumber];
        buckets[bucketNumber] = 0;
        int nextBucketIndex = bucketNumber + 1 % buckets.length;

        for (int i = nextBucketIndex; beans > 0; i = (i + 1) % buckets.length) {
            buckets[i]++;
            beans--;
        }
    }
}
