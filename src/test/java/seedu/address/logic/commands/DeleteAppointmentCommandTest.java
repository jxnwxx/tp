package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteAppointmentCommand.MESSAGE_APPOINTMENT_NOT_FOUND;
import static seedu.address.logic.commands.DeleteAppointmentCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    @Test
    public void execute_validNricValidIndex_success() throws Exception {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(),
                INDEX_FIRST_PERSON);

        Appointment appointmentToDelete = BENSON.getAppointments().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(BENSON), appointmentToDelete);

        ModelStubWithPerson modelStub = new ModelStubWithPerson(BENSON);
        CommandResult commandResult = deleteAppointmentCommand.execute(modelStub);

        Person updatedPerson = modelStub.person;
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertTrue(updatedPerson.getAppointments().isEmpty());
    }

    @Test
    public void execute_validNricInvalidIndex_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(),
                INDEX_SECOND_PERSON);

        ModelStubWithPerson modelStub = new ModelStubWithPerson(BENSON);
        assertCommandFailure(deleteAppointmentCommand, modelStub,
                String.format(MESSAGE_APPOINTMENT_NOT_FOUND, INDEX_SECOND_PERSON.getOneBased()));
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                ALICE.getNric().toString(),
                INDEX_FIRST_PERSON);

        ModelStubWithPerson modelStub = new ModelStubWithPerson(BENSON);
        assertCommandFailure(deleteAppointmentCommand, modelStub, MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteAppointmentFirstCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_PERSON);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_SECOND_PERSON);
        DeleteAppointmentCommand deleteAppointmentThirdCommand = new DeleteAppointmentCommand(
                ALICE.getNric().toString(), INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_PERSON);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));

        // different nric -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentThirdCommand));
    }

    @Test
    public void toStringMethod() {
        String targetNric = BENSON.getNric().toString();
        Index targetIndex = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(targetNric, targetIndex);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetNric=" + targetNric + ", "
                + "targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }

    /**
     * A default model stub that have all methods failing.
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
        private final AddressBook addressBook;
        private final Person person;

        private final FilteredList<Person> filteredPersons;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
            this.addressBook = new AddressBook();
            addressBook.addPerson(person);
            this.filteredPersons = new FilteredList<>(addressBook.getPersonList());
        }

        @Override
        public Person findPersonByNric(String targetNric) {
            if (person.getNric().toString().equalsIgnoreCase(targetNric)) {
                return person;
            }
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
