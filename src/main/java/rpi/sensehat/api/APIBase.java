package rpi.sensehat.api;

import rpi.sensehat.api.dto.CommandResult;
import rpi.sensehat.connector.Command;
import rpi.sensehat.connector.CommandExecutor;
import rpi.sensehat.connector.CommandExecutorFactory;

/**
 * Created by jcincera on 22/06/2017.
 * Updated by knyc on 02/08/2021 to include LED rotation and low light settings in the command executor
 */
public abstract class APIBase {

    private CommandExecutor commandExecutor;

    protected APIBase() {
        this.commandExecutor = CommandExecutorFactory.get();
    }

    protected CommandResult execute(Command command, String... args) {
        return commandExecutor.execute(command, args);
    }
    protected CommandResult executeLED(String lowLight, String rotation, Command command, String... args) {
        return commandExecutor.executeLED(lowLight, rotation, command, args);
    }

}
