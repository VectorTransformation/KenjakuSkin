package kenjakuSkin.pair;

public record CustomPair<L, R>(L left, R right) {
    public static <L, R> CustomPair<L, R> of(L left, R right) {
        return new CustomPair<>(left, right);
    }
}
