package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withNric("S3216508G")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withNric("S9599208J")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withAppointments(new ArrayList<>(List.of(
                new Appointment(
                        new Title("Dentist Appointment"),
                        LocalDateTime.of(2025, 10, 7, 14, 30)
                )
            ))).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withNric("T1257515C")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withNric("T4509297B")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withNric("F2827236R")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withNric("G5568363N")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withNric("M1393796J")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withNric("S2427427F")
            .withPhone("8482424").withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withNric("S8306085I")
            .withPhone("8482131").withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY =
            new PersonBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB =
            new PersonBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
