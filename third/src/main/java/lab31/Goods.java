package lab31;

import java.util.Objects;

public class Goods {
    protected String name;
    protected String description;

    public Goods(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Goods(Goods other) {
        this.name = new String(other.name);
        this.description = new String(other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name) + Objects.hashCode(description);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Goods) {
            return name.equals(((Goods) o).name) && description.equals(((Goods) o).description);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}
