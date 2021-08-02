package rpi.sensehat.connector.mock;

import rpi.sensehat.api.dto.CommandResult;
import rpi.sensehat.connector.Command;
import rpi.sensehat.connector.CommandExecutor;

/**
 * Created by jcincera on 04/07/2017.
 * Updated by knyc on 02/08/2021 to include LED rotation and low light settings in the command executor
 */
public class MockCommandExecutor implements CommandExecutor {

    @Override
    public CommandResult execute(Command command, String... args) {
        System.out.println("Mocking command: " + command.getCommand());

        MockCommandResult result = new MockCommandResult("");
        result.setCommand(command);

        return result;
    }

    @Override
    public CommandResult executeLED(String lowLight, String rotation, Command command, String... args) {
        System.out.println("Mocking command: " + command.getCommand());

        MockCommandResult result = new MockCommandResult("");
        result.setCommand(command);

        return result;
    }


}
