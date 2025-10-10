package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateOfBirthTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDob_throwsIllegalArgumentException() {
        String invalidDob = "";
        assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDob));
    }

    @Test
    public void isValidDateOfBirth() {
        // null name
        assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid name
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDateOfBirth("31-87-2002")); // Note valid date

        // valid name
        assertTrue(DateOfBirth.isValidDateOfBirth("20-12-2002"));
        assertTrue(DateOfBirth.isValidDateOfBirth("12-12-1999"));
        assertTrue(DateOfBirth.isValidDateOfBirth("02-10-2993"));
    }

    @Test
    public void equals() {
        DateOfBirth dateOfBirth = new DateOfBirth("20-12-2002");

        // same value -> return true
        assertTrue(dateOfBirth.equals(new DateOfBirth("20-12-2002")));

        // same object -> return true
        assertTrue(dateOfBirth.equals(dateOfBirth));

        // null -> returns false
        assertFalse(dateOfBirth.equals(null));

        // different types -> returns false
        assertFalse(dateOfBirth.equals(5.0f));

        // different values -> return false
        assertFalse(dateOfBirth.equals("02-02-1999"));
    }
}
