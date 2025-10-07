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

    private final String name;
    private final LocalDateTime date;

    /**
     * Constructs a {@code Appointment}.
     * @param name An appointment name
     * @param date An appointment datetime
     */
    public Appointment(String name, LocalDateTime date) {
        requireAllNonNull(name, date);
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getDateTime() {
        return this.date;
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

        return name.equals(otherAppointment.name) && date.equals(otherAppointment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.date.format(DATETIME_FORMAT));
    }
}