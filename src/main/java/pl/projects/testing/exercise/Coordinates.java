package pl.projects.testing.exercise;

public class Coordinates {

    private int x;
    private int y;

    Coordinates (int x, int y) {

        if(x<0 || y<0) {
            throw new IllegalArgumentException("Position can not be less than 0");
        }

        if(x>100 || y>100) {
            throw new IllegalArgumentException("Position can not be more than 100");
        }

        this.x = x;
        this.y = y;
    }

    static Coordinates copy(Coordinates coordinates, int x, int y) {
        return new Coordinates(coordinates.x + x, coordinates.y + y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
