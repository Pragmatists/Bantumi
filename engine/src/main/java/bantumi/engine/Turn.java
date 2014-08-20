package bantumi.engine;

public enum Turn {
    BOTTOM_PLAYER, TOP_PLAYER;

    public Turn next() {
        if(this == BOTTOM_PLAYER) {
            return TOP_PLAYER;
        }
        return BOTTOM_PLAYER;
    }
}
