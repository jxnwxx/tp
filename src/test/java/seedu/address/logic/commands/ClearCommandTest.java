package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalDoctorBase;

import org.junit.jupiter.api.Test;

import seedu.address.model.DoctorBase;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyDoctorBase_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDoctorBase_success() {
        Model model = new ModelManager(getTypicalDoctorBase(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalDoctorBase(), new UserPrefs());
        expectedModel.setDoctorBase(new DoctorBase());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
