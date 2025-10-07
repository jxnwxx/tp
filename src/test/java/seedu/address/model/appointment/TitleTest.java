package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Title(" Checkup"));   // leading space
        assertThrows(IllegalArgumentException.class, () -> new Title(""));           // empty string
        assertThrows(IllegalArgumentException.class, () -> new Title("~"));          // single invalid symbol
        assertThrows(IllegalArgumentException.class, () -> new Title("   "));        // only spaces
    }

    @Test
    public void isValidTitle() {
        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid title
        assertFalse(Title.isValidTitle(""));         // empty string
        assertFalse(Title.isValidTitle(" "));        // spaces only
        assertFalse(Title.isValidTitle("~"));        // invalid character
        assertFalse(Title.isValidTitle(" Checkup")); // leading space

        // valid title
        assertTrue(Title.isValidTitle("Dental Checkup"));        // alphabets only
        assertTrue(Title.isValidTitle("Room 2B"));               // alphanumeric
        assertTrue(Title.isValidTitle("Covid-19 (PCR)"));        // parentheses and hyphen
        assertTrue(Title.isValidTitle("Dr Tan's Appointment"));  // apostrophe allowed
        assertTrue(Title.isValidTitle("Follow-up, Eczema"));     // comma allowed
    }


    @Test
    public void equals() {
        Title title = new Title("Valid Title");

        // same values -> returns true
        assertTrue(title.equals(new Title("Valid Title")));

        // same object -> returns true
        assertTrue(title.equals(title));

        // null -> returns false
        assertFalse(title.equals(null));

        // different types -> returns false
        assertFalse(title.equals(5.0f));

        // different values -> returns false
        assertFalse(title.equals(new Title("Other Valid Title")));
    }
}
