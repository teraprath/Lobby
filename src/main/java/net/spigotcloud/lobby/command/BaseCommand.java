package net.spigotcloud.lobby.command;

import net.spigotcloud.lobby.Lobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.*;

public class BaseCommand implements CommandExecutor, TabCompleter {

    private final HashMap<String, SubCommand> subCommands;

    public BaseCommand() {
        this.subCommands = new HashMap<>();
    }

    public Map<String, SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public void registerSubCommand(@Nonnull String command, @Nonnull SubCommand subCommand) {
        this.subCommands.put(command, subCommand);
    }

    public void unregister(@Nonnull String command) {
        this.subCommands.remove(command);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {

            if (subCommands.containsKey(args[0])) {
                SubCommand subCommand = subCommands.get(args[0]);
                if (sender.hasPermission(subCommand.getPermission())) {
                    subCommand.onCommand(sender, command, label, args);
                } else {
                    sender.sendMessage(Lobby.getInstance().getLanguageManager().getNoPermission(subCommand.getPermission()));
                }
            } else {
                sender.sendMessage(Lobby.getInstance().getLanguageManager().getWrongUsage("help"));
            }

        } else {
            sender.sendMessage(Lobby.getInstance().getLanguageManager().getWrongUsage("help"));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();


        if (args.length == 1) {
            subCommands.forEach(((s, subCommand) -> {
                list.add(s.toLowerCase());
            }));
        } else {
            SubCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                return subCommand.onTabComplete(sender, command, label, args);
            }
        }

        list.removeIf(s -> !s.toLowerCase().startsWith(current));
        return list;
    }
}
