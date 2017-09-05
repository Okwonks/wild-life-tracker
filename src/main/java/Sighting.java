import org.sql2o.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Sighting {
    private String rangerName;
    private String location;
    private int id;
    private int animalId;

    public Sighting(String location, String rangerName, int animalId) {
        this.rangerName = rangerName;
        this.location = location;
        this.animalId = animalId;
    }

    public String getRangerName() {
        return rangerName;
    }

    public String getLocation() {
        return location;
    }

    public int getAnimalId() {
        return animalId;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object otherSighting) {
        if (!(otherSighting instanceof Sighting)) {
            return false;
        } else {
            Sighting newSighting = (Sighting) otherSighting;
            return this.getRangerName().equals(newSighting.getRangerName()) &&
                   this.getLocation().equals(newSighting.getLocation());
        }
    }

    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (location, rangerName, animalId) VALUES (:location, :rangerName, :animalId)";
            this.id = (int) con.createQuery(sql, true)
              .addParameter("location", this.location)
              .addParameter("rangerName", this.rangerName)
              .addParameter("animalId", this.animalId)
              .executeUpdate()
              .getKey();
        }
    }
    
    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }

    public static Sighting find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE id = :id";
            Sighting sighting  = con.createQuery(sql)
              .addParameter("id", id)
              .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }
}