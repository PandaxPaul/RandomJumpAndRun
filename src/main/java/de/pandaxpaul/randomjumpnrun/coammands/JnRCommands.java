package de.pandaxpaul.randomjumpnrun.coammands;

import de.pandaxpaul.randomjumpnrun.RandomJumpNRun;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JnRCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "springen":
            case "skip": {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Location playerLocation = player.getLocation();

                    Location teleportLocation = new Location(playerLocation.getWorld(), RandomJumpNRun.getnewx(), RandomJumpNRun.getnewy() + 1, RandomJumpNRun.getnewz());
                    player.teleport(teleportLocation);
                    //player.sendMessage("U got tped to: " + RandomJumpNRun.getnewx() + " " +  RandomJumpNRun.getnewy() + " " + RandomJumpNRun.getnewz());


                    break;
                }

                break;
            }
            case "starten":
            case "start": {
                Player player = (Player) sender;
                if (sender instanceof Player) {
                    Player playertostart = (Player) sender;
                    RandomJumpNRun.startJnR(true, playertostart);

                    break;
                }
                break;
            }
            default:
                sendUsage(sender);
                break;
        }
        return false;
    }

    private void sendUsage(CommandSender sender){
        sender.sendMessage(ChatColor.GRAY + "Verwendung:");
        sender.sendMessage(ChatColor.BLUE + "/jnr start (/jnr starten)" + ChatColor.GRAY + " Hiermit startet du das Jump and Run");
        sender.sendMessage(ChatColor.BLUE + "/jnr skip (/jnr springen)" + ChatColor.GRAY + " Hiermit überspringst du einen Sprung");
    }
}
