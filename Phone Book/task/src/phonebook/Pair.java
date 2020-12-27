package phonebook;

public class Pair<T, F> {
    public T first;
    public F second;

    private Pair(T first, F second) {
        this.first = first;
        this.second = second;
    }

    public static <T, F> Pair<T, F> of(T first, F second) {
        return new Pair<>(first, second);
    }
}
