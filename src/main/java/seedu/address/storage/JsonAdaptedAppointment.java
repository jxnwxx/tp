package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {
    private final String title;
    private final String dateTimeString;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code title} and {@code dateTime}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(
            @JsonProperty("title") String title,
            @JsonProperty("dateTimeString") String dateTimeString) {
        this.title = title;
        this.dateTimeString = dateTimeString;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        this.title = source.getTitle().toString();
        this.dateTimeString = source.getDateTimeAsString();
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("dateTimeString")
    public String getDateTimeAsString() {
        return dateTimeString;
    }

    /**
     * Converts this Jackson-friendly adapted Appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        if (!Appointment.isValidDateTimeString(dateTimeString)) {
            throw new IllegalValueException(Appointment.DATETIME_MESSAGE_CONSTRAINTS);
        }

        Title convertedTitle = new Title(title);
        LocalDateTime convertedDateTime = LocalDateTime.from(Appointment.DATETIME_FORMAT.parse(dateTimeString));
        return new Appointment(convertedTitle, convertedDateTime);
    }

}
