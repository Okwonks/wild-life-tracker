import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Animal {
    public String name;
    public String type;
    public int id;
    public String health;
    public String age;
    // public int animalAge;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        // if (animalAge <= 1) {
        //     age = "newborn";
        // } else if (animalAge <= 4) {
        //     age = "young";
        // } else {
        //     age = "old";
        // }
        return age;
    }

    @Override
    public boolean equals(Object otherAnimal) {
        if (!(otherAnimal instanceof Animal)) {
            return false;
        } else {
            Animal newAnimal = (Animal) otherAnimal;
            return this.getName().equals(newAnimal.getName()) &&
                   this.getId() == newAnimal.getId() &&
                   this.getHealth().equals(newAnimal.getHealth()) &&                                    
                   this.getAge().equals(newAnimal.getAge());
        }
    }

    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, type, health, age) VALUES (:name, :type, :health, :age)";
            this.id = (int) con.createQuery(sql, true)
              .addParameter("name", this.name)
              .addParameter("type", this.type)
              .addParameter("health", this.health)
              .addParameter("age", this.age)
              .executeUpdate()
              .getKey();
        }
    }

}