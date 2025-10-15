package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;

public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Appointment> appointmentListView;

    /**
     * Creates a {@code AppointmentListPanel} with an empty list.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointments) {
        super(FXML);
        appointmentListView.setItems(appointments);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    public void setAppointmentListView(ObservableList<Appointment> appointmentList) {
        appointmentListView.setItems(appointmentList);
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Appointment} using a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment, getIndex() + 1).getRoot());
            }
        }
    }
}
