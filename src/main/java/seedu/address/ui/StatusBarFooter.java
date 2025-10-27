package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Patient;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;
    @FXML
    private Label listStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        listStatus.setText("Displaying patients!");
    }

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path} and {@code Patient}.
     */
    public StatusBarFooter(Path saveLocation, Patient patient) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        listStatus.setText("Displaying " + patient.getNric() + " appointments!");
    }

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path} and custom text.
     */
    private StatusBarFooter(Path saveLocation, String text) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        listStatus.setText(text);
    }

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path} and text indicating
     * that upcoming appointments are being displayed.
     */
    public static StatusBarFooter getUpcomingAppointmentsFooter(Path saveLocation) {
        return new StatusBarFooter(saveLocation, "Displaying upcoming appointments!");
    }
}
