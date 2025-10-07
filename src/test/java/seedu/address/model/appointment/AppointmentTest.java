package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_validInput_success() {
        Title validTitle = new Title("Dentist Appointment");
        LocalDateTime validDate = LocalDateTime.of(2025, 10, 7, 14, 30);

        Appointment appointment = new Appointment(validTitle, validDate);

        // check field assignments
        assertEquals(validTitle, appointment.getTitle());
        assertEquals(validDate, appointment.getDateTime());

        // check string format (dd-MM-yyyy, HHmm)
        String expectedToString = "Dentist Appointment (07-10-2025, 1430)";
        assertEquals(expectedToString, appointment.toString());
    }
}
