package data.entities;

/**
 * Entita pro jazyky.
 *
 * @author Helena Pavlikova
 */
public class Language {

    private int id;
    private final String name;

    public Language(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    public Language(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
