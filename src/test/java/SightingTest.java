import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

/**
 * SightingTest this class tests the sighting of an animal.
 */
public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void sighting_instantiatesCorrectly_true() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        assertTrue(testSighting instanceof Sighting);
    }

    @Test
    public void getRanger_sightingInstantiatesWithRanger_Jones() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        assertEquals("Jones", testSighting.getRangerName());
    }

    @Test
    public void getLocation_sightingInstantiatesWithLocation_Norht() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        assertEquals("North", testSighting.getLocation());
    }

    @Test
    public void equals_returnsTrueIfSightingDetailsAreTheSame() {
        Sighting firstSighting = new Sighting("North", "Jones", 1);
        Sighting secondSighting = new Sighting("North", "Jones", 1);
        assertTrue(firstSighting.equals(secondSighting));
    }
    
    @Test
    public void save_insertsObjectsIntoDataBase_Sighting() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        testSighting.save();
        assertTrue(Sighting.all().get(0).equals(testSighting));
    }

    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Sighting firstSighting = new Sighting("North", "Jones", 1);
        firstSighting.save();
        Sighting secondSighting = new Sighting("North", "Jones", 1);
        secondSighting.save();
        assertTrue(Sighting.all().get(0).equals(firstSighting));
        assertTrue(Sighting.all().get(1).equals(secondSighting));
    }

    @Test
    public void save_assignsIdToObject() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        testSighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(testSighting.getId(), savedSighting.getId());
    }

    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Sighting firstSighting = new Sighting("North", "Jones", 1);
        firstSighting.save();
        Sighting secondSighting = new Sighting("North", "Jenny", 1);
        secondSighting.save();
        assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
    }

    @Test
    public void save_savesAnimalIdIntoDB() {
        EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Rhino", "okay", "young");
        testEndangeredAnimal.save();
        Sighting testSighting = new Sighting("North", "Jones", testEndangeredAnimal.getId());
        testSighting.save();
        Sighting savedSighting = Sighting.find(testSighting.getId());
        assertEquals(savedSighting.getAnimalId(), testEndangeredAnimal.getId());
    }

    @Test
    public void save_recordsTimeSpottedIntoTheDatabase() {
        Sighting testSighting = new Sighting("North", "Jones", 1);
        testSighting.save();
        Timestamp savedSighting = Sighting.find(testSighting.getId()).getTimeSpotted();
        Timestamp rightNow = new Timestamp(new Date().getTime());
        assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedSighting));
    }
}