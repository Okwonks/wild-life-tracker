import org.junit.*;
import static org.junit.Assert.*;

/**
 * SightingTest this class tests the sighting of an animal.
 */
public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void sighting_instantiatesCorrectly_true() {
        Sighting testSighting = new Sighting(1, "North", "Jones");
        assertTrue(testSighting instanceof Sighting);
    }

    @Test
    public void getRanger_sightingInstantiatesWithRanger_Jones() {
        Sighting testSighting = new Sighting(1, "North", "Jones");
        assertEquals("Jones", testSighting.getRangerName());
    }

    @Test
    public void getLocation_sightingInstantiatesWithLocation_Norht() {
        Sighting testSighting = new Sighting(1, "North", "Jones");
        assertEquals("North", testSighting.getLocation());
    }

    @Test
    public void equals_returnsTrueIfSightingDetailsAreTheSame() {
        Sighting firstSighting = new Sighting(1, "North", "Jones");
        Sighting secondSighting = new Sighting(1, "North", "Jones");
        assertTrue(firstSighting.equals(secondSighting));
    }
    
    @Test
    public void save_insertsObjectsIntoDataBase_Sighting() {
        Sighting testSighting = new Sighting(1, "North", "Jones");
        testSighting.save();
        assertTrue(Sighting.all().get(0).equals(testSighting));
    }

    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Sighting firstSighting = new Sighting(1, "North", "Jones");
        firstSighting.save();
        Sighting secondSighting = new Sighting(1, "North", "Jones");
        secondSighting.save();
        assertTrue(Sighting.all().get(0).equals(firstSighting));
        assertTrue(Sighting.all().get(1).equals(secondSighting));
    }

    @Test
    public void save_assignsIdToObject() {
        Sighting testSighting = new Sighting(1, "North", "Jones");
        testSighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(testSighting.getId(), savedSighting.getId());
    }

    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Sighting firstSighting = new Sighting(1, "North", "Jones");
        firstSighting.save();
        Sighting secondSighting = new Sighting(1, "North", "Jenny");
        secondSighting.save();
        assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
    }
}