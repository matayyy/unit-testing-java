package pl.projects.testing.exercise;

public class Cargo {

    private String name;
    private int weight;

    Cargo(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cargo cargo = (Cargo) o;

        if (weight != cargo.weight) return false;
        return name != null ? name.equals(cargo.name) : cargo.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + weight;
        return result;
    }

    public int getWeight() {
        return weight;
    }
}
