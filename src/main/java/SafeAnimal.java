import java.util.List;
import org.sql2o.*;

public class SafeAnimal extends Animal{
    public static final String DATABASE_TYPE = "safe";

    public SafeAnimal(String name, String health, String age) {
        this.name = name;
        type = DATABASE_TYPE;
        this.health = health;
        this.age = age;
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
    
}