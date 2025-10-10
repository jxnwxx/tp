package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DobTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dob(null));
    }

    @Test
    public void constructor_invalidDob_throwsIllegalArgumentException() {
        String invalidDob = "";
        assertThrows(IllegalArgumentException.class, () -> new Dob(invalidDob));
    }

    @Test
    public void isValidDob() {
        // null name
        assertThrows(NullPointerException.class, () -> Dob.isValidDob(null));

        // invalid name
        assertFalse(Dob.isValidDob("")); // empty string
        assertFalse(Dob.isValidDob(" ")); // spaces only
        assertFalse(Dob.isValidDob("31-87-2002")); // Note valid date

        // valid name
        assertTrue(Dob.isValidDob("20-12-2002"));
        assertTrue(Dob.isValidDob("12-12-1999"));
        assertTrue(Dob.isValidDob("02-10-2993"));
    }

    @Test
    public void equals() {
        Dob dob = new Dob("20-12-2002");

        // same value -> return true
        assertTrue(dob.equals(new Dob("20-12-2002")));

        // same object -> return true
        assertTrue(dob.equals(dob));

        // null -> returns false
        assertFalse(dob.equals(null));

        // different types -> returns false
        assertFalse(dob.equals(5.0f));

        // different values -> return false
        assertFalse(dob.equals("02-02-1999"));
    }
}
