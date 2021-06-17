package co.uk.isxander.xanderlib.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SimpleCommand extends CommandBase {

    private final String[] names;
    private final String[] potentialSubCmds;
    private final Consumer<String[]> processor;

    public SimpleCommand(String[] names, String[] potentialSubCmds, Consumer<String[]> processor) {
        this.names = names;
        this.potentialSubCmds = potentialSubCmds;
        this.processor = processor;
    }

    @Override
    public String getCommandName() {
        return names[0];
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList(names);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(getCommandName()).append(" [");
        for (int i = 0; i < potentialSubCmds.length; i++) {
            sb.append(potentialSubCmds[i]);
            if (i == potentialSubCmds.length - 1)
                sb.append("]");
            else
                sb.append("|");
        }
        return sb.toString();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        processor.accept(args);
    }

}
