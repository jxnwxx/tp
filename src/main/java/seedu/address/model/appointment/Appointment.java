package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * Represents an Appointment in the address book.
 */
public class Appointment {
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");

    private final Title title;
    private final LocalDateTime date;

    /**
     * Constructs a {@code Appointment}.
     * @param title An appointment title
     * @param date An appointment datetime
     */
    public Appointment(Title title, LocalDateTime date) {
        requireAllNonNull(title, date);
        this.title = title;
        this.date = date;
    }

    public Title getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return date;
    }

    /**
     * Returns true if both Appointments have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment otherAppointment)) {
            return false;
        }

        return title.equals(otherAppointment.title) && date.equals(otherAppointment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date);
    }

    public String toString() {
        return String.format("%s (%s)", title, date.format(DATETIME_FORMAT));
    }
}
