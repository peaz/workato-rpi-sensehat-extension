package rpi.sensehat.connector;

import rpi.sensehat.api.dto.CommandResult;
import rpi.sensehat.exception.CommandException;
import rpi.sensehat.exception.CommunicationException;
import rpi.sensehat.exception.InvalidSystemArchitectureException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jcincera on 20/06/2017.
 * Updated by knyc on 02/08/2021 to include LED rotation and low light settings in the command executor
 */
public class SimpleCommandExecutor implements CommandExecutor {

    SimpleCommandExecutor() {
        if (!System.getProperty("os.arch").toLowerCase().contains("arm")) {
            throw new InvalidSystemArchitectureException("System architecture is not supported for this command executor");
        }
    }

    @Override
    public CommandResult executeLED(String lowLight, String rotation, Command command, String... args) {
        try {

            // Create command
            final String completeCommand = createCompleteCommand(lowLight, rotation, command, args);
            System.out.println("Complete Command: " + completeCommand);

            // Call
            System.out.println("Command: " + command.name());
            ProcessBuilder pb = new ProcessBuilder("python", "-c", completeCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            // Read output
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = output.readLine()) != null) {
                result.append(line);
                result.append(lineSeparator);
            }
            System.out.println("Command result: " + result.toString());

            // Handle result
            waitForCommand(p);
            checkCommandException(result);
            return new CommandResult(result.toString());
        }
        catch (Exception e) {
            System.err.println(e);

            if (e instanceof CommandException) {
                throw (CommandException) e;
            }

            throw new CommunicationException("Communication with Sense Hat failed!", e);
        }
    }

    @Override
    public CommandResult execute(Command command, String... args) {
        try {

            // Create command
            final String completeCommand = createCompleteCommand("False", "0", command, args);

            // Call
            System.out.println("Command: " + command.name());
            ProcessBuilder pb = new ProcessBuilder("python", "-c", completeCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            // Read output
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = output.readLine()) != null) {
                result.append(line);
                result.append(lineSeparator);
            }
            System.out.println("Command result: " + result.toString());

            // Handle result
            waitForCommand(p);
            checkCommandException(result);
            return new CommandResult(result.toString());
        }
        catch (Exception e) {
            System.err.println(e);

            if (e instanceof CommandException) {
                throw (CommandException) e;
            }

            throw new CommunicationException("Communication with Sense Hat failed!", e);
        }
    }

    private void checkCommandException(StringBuilder result) {
        if (result.toString().contains("Traceback") || result.toString().contains("Error")) {
            throw new CommandException("Command execution failed!\n" + result.toString());
        }
    }

    private String createCompleteCommand(String lowLight, String rotation, Command command, String[] args) {
        String rawCommand = (args != null && args.length > 0) ?
                String.format(command.getCommand(), (Object[]) args) :
                command.getCommand();        

        return Command.IMPORT_SENSE_HAT.getCommand() + ";" +
                Command.SENSE_OBJECT.getCommand() + ";" +
                String.format(Command.LOW_LIGHT.getCommand(), lowLight) + ";" +                
                String.format(Command.SET_ROTATION.getCommand(), rotation) + ";" +
                rawCommand;
    }

    private void waitForCommand(Process p) {
        try {
            p.waitFor();
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
