package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddAppointmentCommand.
 */
public class AddAppointmentCommandTest {

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, new Appointment(new Title("A"), LocalDateTime.now())));
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand("S1234567A", null));
    }

    @Test
    public void execute_personExists_addSuccessful() throws Exception {
        ModelStubWithPerson modelStub = new ModelStubWithPerson(ALICE);
        Appointment appointment = new Appointment(new Title("Dental Checkup"),
                LocalDateTime.of(2020, 02, 20, 20, 20));

        AddAppointmentCommand command = new AddAppointmentCommand(ALICE.getNric().toString(), appointment);
        CommandResult commandResult = command.execute(modelStub);

        Person updatedPerson = modelStub.updatedPerson;
        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(updatedPerson), appointment), commandResult.getFeedbackToUser());
        assertTrue(updatedPerson.getAppointments().contains(appointment));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        ModelStubEmpty modelStub = new ModelStubEmpty();
        Appointment appointment = new Appointment(new Title("Checkup"),
                LocalDateTime.of(2020, 02, 20, 20, 20));

        AddAppointmentCommand command = new AddAppointmentCommand("S1234567Z", appointment);
        assertThrows(CommandException.class,
                AddAppointmentCommand.MESSAGE_PERSON_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Appointment appt1 = new Appointment(new Title("A"), LocalDateTime.of(2025, 10, 1, 10, 0));
        Appointment appt2 = new Appointment(new Title("B"), LocalDateTime.of(2025, 11, 2, 11, 0));

        AddAppointmentCommand cmd1 = new AddAppointmentCommand("S1234567A", appt1);
        AddAppointmentCommand cmd2 = new AddAppointmentCommand("S1234567A", appt1);
        AddAppointmentCommand cmd3 = new AddAppointmentCommand("S1234567B", appt1);
        AddAppointmentCommand cmd4 = new AddAppointmentCommand("S1234567A", appt2);

        // same object -> true
        assertTrue(cmd1.equals(cmd1));

        // same values -> true
        assertTrue(cmd1.equals(cmd2));

        // null -> false
        assertFalse(cmd1.equals(null));

        // different type -> false
        assertFalse(cmd1.equals(1));

        // different NRIC or appointment -> false
        assertFalse(cmd1.equals(cmd3));
        assertFalse(cmd1.equals(cmd4));
    }

    @Test
    public void toStringMethod() {
        Appointment appointment = new Appointment(new Title("Checkup"),
                LocalDateTime.of(2025, 10, 15, 9, 0));
        AddAppointmentCommand command = new AddAppointmentCommand("S1234567A", appointment);
        String expected = AddAppointmentCommand.class.getCanonicalName()
                + "{targetNric=S1234567A, toAdd=" + appointment + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person findPersonByNric(String targetNric) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with a single existing person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;
        private Person updatedPerson;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person findPersonByNric(String targetNric) {
            if (person.getNric().toString().equalsIgnoreCase(targetNric)) {
                return person;
            }
            return null;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);
            if (target.equals(person)) {
                this.updatedPerson = editedPerson;
            } else {
                throw new AssertionError("Unexpected target");
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that has no matching person (for failure case).
     */
    private class ModelStubEmpty extends ModelStub {
        @Override
        public Person findPersonByNric(String targetNric) {
            return null;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}