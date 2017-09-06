import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class SafeAnimalTest {
    
        @Rule
        public DatabaseRule database = new DatabaseRule();
    
        @Test
        public void endangeredAnimal_InstantiatesCorrectly_true() {
            SafeAnimal testSafeAnimal = new SafeAnimal("Rhino", "okay");
            assertTrue(testSafeAnimal instanceof SafeAnimal);
        }
    
        @Test
        public void SafeAnimal_instantiatesWithName_String() {
            SafeAnimal testSafeAnimal = new SafeAnimal("Rhino", "okay");
            assertEquals("Rhino", testSafeAnimal.getName());
        }
    
        @Test
        public void equals_returnsTrueIfSafeAnimalDetailsAreEqual() {
            SafeAnimal testSafeAnimal = new SafeAnimal("Rhino", "okay");
            SafeAnimal otherSafeAnimal = new SafeAnimal("Rhino", "okay");
            assertTrue(testSafeAnimal.equals(otherSafeAnimal));
        }
    
        @Test
        public void save_addsSafeAnimalToDatabase_successfully() {
            SafeAnimal testSafeAnimal = new SafeAnimal("Rhino", "okay");
            testSafeAnimal.save();
            assertTrue(SafeAnimal.all().get(0).equals(testSafeAnimal));
        }
    
        @Test
        public void save_assignsIdToSafeAnimal() {
            SafeAnimal testSafeAnimal = new SafeAnimal("Rhino", "okay");
            testSafeAnimal.save();
            SafeAnimal savedEndangeredAmAnimal = SafeAnimal.all().get(0);
            assertEquals(savedEndangeredAmAnimal.getId(), testSafeAnimal.getId());
        }
    
        @Test
        public void all_returnsAllInstancesOfSafeAnimals() {
            SafeAnimal firstSafeAnimal = new SafeAnimal("Rhino", "okay");
            firstSafeAnimal.save();
            SafeAnimal secondSafeAnimal = new SafeAnimal("Rhino", "okay");
            secondSafeAnimal.save();
            assertTrue(SafeAnimal.all().get(0).equals(firstSafeAnimal));
            assertTrue(SafeAnimal.all().get(1).equals(secondSafeAnimal));
        }
    
        @Test
        public void find_returnsSafeAnimalWithSameId_secondAnimal() {
            SafeAnimal firstSafeAnimal = new SafeAnimal("Rhino", "okay");
            firstSafeAnimal.save();
            SafeAnimal secondSafeAnimal = new SafeAnimal("Elephant", "okay");
            secondSafeAnimal.save();
            assertEquals(SafeAnimal.find(secondSafeAnimal.getId()), secondSafeAnimal);
        }
    }