package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_TITLE = "Dental";
    public static final String DEFAULT_DATETIME = "02-02-2020, 2020";

    private Title title;
    private LocalDateTime dateTime;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        title = new Title(DEFAULT_TITLE);
        dateTime = LocalDateTime.parse(DEFAULT_DATETIME,
                Appointment.DATETIME_FORMAT);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointment}.
     */
    public AppointmentBuilder(Appointment appointment) {
        title = appointment.getTitle();
        dateTime = appointment.getDateTime();
    }

    /**
     * Sets the {@code Title} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, Appointment.DATETIME_FORMAT);
        return this;
    }

    public Appointment build() {
        return new Appointment(title, dateTime);
    }
}
