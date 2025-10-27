package seedu.address.ui;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentListCard.fxml";

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label date;
    @FXML
    private Label id;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        title.setText(appointment.getTitle().title);
        date.setText(appointment.getDateTimeAsString());
        if (appointment.getDateTime().isBefore(LocalDateTime.now())) {
            // Past appointment: Grey text
            id.setStyle("-fx-text-fill: #a0a0a0;");
            title.setStyle("-fx-text-fill: #a0a0a0;"); // light gray
            date.setStyle("-fx-text-fill: #a0a0a0;");
        }
    }
}
