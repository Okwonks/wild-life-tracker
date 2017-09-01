import java.util.List;

import org.sql2o.Connection;

public class EndangeredAnimal extends Animal {
    public static final String DATABASE_TYPE = "endangered";
    public EndangeredAnimal(String name, String health, String age) {
        this.name = name;
        type = DATABASE_TYPE;
        this.health = health;
        this.age = age;
    }

    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals WHERE type='endangered';";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
              .throwOnMappingFailure(false)
              .executeAndFetch(EndangeredAnimal.class);
        }
    }

    public static EndangeredAnimal find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE id = :id";
            EndangeredAnimal animal = con.createQuery(sql)
              .addParameter("id", id)
              .throwOnMappingFailure(false)
              .executeAndFetchFirst(EndangeredAnimal.class);
            return animal;
        }
    }
}