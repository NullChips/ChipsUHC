package me.nullchips.chipsuhc.commands;

import me.nullchips.chipsuhc.utils.ChatUtils;
import me.nullchips.chipsuhc.utils.GameState;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

/**
 * Created by Tommy on 20/02/2017.
 */
public class CreateUHCWorld implements CommandExecutor {

    ChatUtils cu = ChatUtils.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String [] args) {
        if(cmd.getName().equalsIgnoreCase("createuhcworld")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(cu.getConsoleMessage());
                return  true;
            }

            Player p = (Player) sender;

            if(!p.hasPermission("uhc.createworld")) {
                cu.noPermission(p);
                return true;
            }

            if(!GameState.isGameState(GameState.LOBBY)) {
                cu.message(p, ChatColor.RED + "Unable to generate world when game is in progress.");
                return true;
            }

            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /createuhcworld <seed>");
                p.sendMessage(ChatColor.BLUE + "Info: seed must be in numerical format.");
                return true;
            }

            String seedString = args[0];

            if(seedString.matches("[0-9]+") && seedString.length() > 2) {

                cu.broadcast(ChatColor.YELLOW + "Starting creation of game world, expect some lag.");

                long seed = Long.parseLong(seedString);

                createOverworld(seed);
                createNether();
                createEnd();

                cu.broadcast(ChatColor.YELLOW + "World creation finished.");

            }

        }
        return true;
    }

    private void createEnd() {

        WorldCreator worldCreator = new WorldCreator("uhc_the_end");
        worldCreator.generateStructures(true);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.environment(World.Environment.THE_END);

        Bukkit.createWorld(worldCreator);

    }

    private void createNether() {

        WorldCreator worldCreator = new WorldCreator("uhc_nether");
        worldCreator.generateStructures(true);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.environment(World.Environment.NETHER);

        Bukkit.createWorld(worldCreator);
    }

    private void createOverworld(long seed) {
        WorldCreator worldCreator = new WorldCreator("uhc");
        worldCreator.generateStructures(true);
        worldCreator.seed(seed);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.environment(World.Environment.NORMAL);

        Bukkit.createWorld(worldCreator);
    }

}
