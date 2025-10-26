package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ListAppointmentUpcomingCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ListAppointmentUpcomingCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_listAppointmentUpcoming_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, true, false);
        assertCommandSuccess(new ListAppointmentUpcomingCommand(), model, expectedCommandResult, expectedModel);
    }
}
