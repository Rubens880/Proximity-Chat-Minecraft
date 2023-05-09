package com.ruben.proximitychat.Commands;

import com.ruben.proximitychat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetMegaphoneCommand implements CommandExecutor {

    private Main main;

    public GetMegaphoneCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("proximitychat.megaphone") && sender.isOp()) {
                ItemStack megaPhone = main.getMegaphoneItemStack();
                ((Player) sender).getInventory().addItem(megaPhone);
            }
        }
        return false;
    }
}
