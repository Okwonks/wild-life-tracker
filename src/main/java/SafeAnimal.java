import java.util.List;
import org.sql2o.*;
/**
 * This class deals with animals considered safe
 * the DATABASE_TYPE defines the animal type as safe
 */

public class SafeAnimal extends Animal{
    public static final String DATABASE_TYPE = "safe";

    public SafeAnimal(String name, String health) {
        this.name = name;
        type = DATABASE_TYPE;
        this.health = health;
    }

    public static List<SafeAnimal> all() {
        String sql = "SELECT * FROM animals WHERE type='safe';";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
              .throwOnMappingFailure(false)
              .executeAndFetch(SafeAnimal.class);
        }
    }

    public static SafeAnimal find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE id = :id";
            SafeAnimal animal = con.createQuery(sql)
              .addParameter("id", id)
              .throwOnMappingFailure(false)
              .executeAndFetchFirst(SafeAnimal.class);
            return animal;
        }
    }
    
    @Override
    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, type, health) VALUES (:name, :type, :health)";
            this.id = (int) con.createQuery(sql, true)
              .addParameter("name", this.name)
              .addParameter("type", this.type)
              .addParameter("health", this.health)
              .executeUpdate()
              .getKey();
        }
    }
}