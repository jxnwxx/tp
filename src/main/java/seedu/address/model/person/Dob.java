package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's DOB in the address book
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class Dob {

    public static final String MESSAGE_CONSTRAINTS =
            "DOB must be in DD-MM-YYYY format.";

    /*
     * Regex for DOB
     */
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";

    public final String value;

    /**
     * Constructs a {@code Dob}.
     *
     * @param dob A valid dob
     */
    public Dob(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        value = dob;
    }

    /**
     * Test to check validity of given dob
     *
     * @param test given dob to test
     * @return true if a given string is a valid dob
     */
    public static boolean isValidDob(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dob)) {
            return false;
        }

        Dob otherDob = (Dob) other;
        return value.equals(otherDob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
