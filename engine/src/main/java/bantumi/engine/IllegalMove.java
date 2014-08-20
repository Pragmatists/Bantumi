package bantumi.engine;

public class IllegalMove extends RuntimeException {

    public IllegalMove() {
        super("Bucket does not exist.");

    }

}
