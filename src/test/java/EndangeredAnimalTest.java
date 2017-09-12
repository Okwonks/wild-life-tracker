import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import org.sql2o.*;

public class EndangeredAnimalTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void endangeredAnimal_InstantiatesCorrectly_true() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        assertTrue(testEndangeredAnimal instanceof EndangeredAnimal);
    }

    @Test
    public void EndangeredAnimal_instantiatesWithName_String() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        assertEquals("Rhino", testEndangeredAnimal.getName());
    }

    @Test
    public void equals_returnsTrueIfEndangeredAnimalDetailsAreEqual() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        EndangeredAnimal otherEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        assertTrue(testEndangeredAnimal.equals(otherEndangeredAnimal));
    }

    @Test
    public void save_addsEndangeredAnimalToDatabase_successfully() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        testEndangeredAnimal.save();
        assertTrue(EndangeredAnimal.all().get(0).equals(testEndangeredAnimal));
    }

    @Test
    public void save_assignsIdToEndangeredAnimal() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        testEndangeredAnimal.save();
        EndangeredAnimal savedEndangeredAmAnimal = EndangeredAnimal.all().get(0);
        assertEquals(savedEndangeredAmAnimal.getId(), testEndangeredAnimal.getId());
    }

    @Test
    public void all_returnsAllInstancesOfEndangeredAnimals() {
        EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        firstEndangeredAnimal.save();
        EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        secondEndangeredAnimal.save();
        assertTrue(EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
        assertTrue(EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
    }

    @Test
    public void find_returnsEndangeredAnimalWithSameId_secondAnimal() {
        EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        firstEndangeredAnimal.save();
        EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Elephant", "okay", "old");
        secondEndangeredAnimal.save();
        assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
    }
}