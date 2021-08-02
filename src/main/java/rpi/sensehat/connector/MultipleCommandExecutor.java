package rpi.sensehat.connector;

import rpi.sensehat.api.dto.CommandResult;
import rpi.sensehat.exception.InvalidSystemArchitectureException;

/**
 * Created by jcincera on 27/06/2017.
 * Updated by knyc on 02/08/2021 to include LED rotation and low light settings in the command executor
 */
public class MultipleCommandExecutor implements CommandExecutor {

    MultipleCommandExecutor() {
        if (!System.getProperty("os.arch").toLowerCase().contains("arm")) {
            throw new InvalidSystemArchitectureException("System architecture is not supported for this command executor");
        }
    }

    @Override
    public CommandResult execute(Command command, String... args) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public CommandResult executeLED(String lowLight, String rotation, Command command, String... args) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
