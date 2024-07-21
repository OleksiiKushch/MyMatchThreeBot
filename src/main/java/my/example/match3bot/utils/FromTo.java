package my.example.match3bot.utils;

public record FromTo(int fromY, int fromX, int toY, int toX) {

    @Override
    public String toString() {
        return "FromTo{" +
                "fromY=" + fromY +
                ", fromX=" + fromX +
                ", toY=" + toY +
                ", toX=" + toX +
                '}';
    }
}
