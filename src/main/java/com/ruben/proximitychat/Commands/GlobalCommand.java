package com.ruben.proximitychat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                Player player = (Player) sender;

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ChatColor.RED.toString() + ChatColor.BOLD + "GLOBAL >> ");
                stringBuilder.append(player.getName() + ": ");
                for (int i = 0; i < args.length; i++) {
                    stringBuilder.append(args[i] + " ");
                }
                Bukkit.broadcastMessage(stringBuilder.toString());
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid usage! Use /global <message>.");
            }
        }


        return false;
    }
}
