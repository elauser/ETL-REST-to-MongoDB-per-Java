enum REQUEST_LIMIT {
    PER_MINUTE(50),
    PER_SECOND(10);

    private final int value;

    REQUEST_LIMIT(final int newValue) {
        value = newValue;
    }
    public int getValue() { return value; }
}
