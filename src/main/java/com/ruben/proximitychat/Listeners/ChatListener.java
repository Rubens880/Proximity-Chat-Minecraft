package com.ruben.proximitychat.Listeners;

import com.ruben.proximitychat.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class ChatListener implements Listener {

    private Main main;
    private YamlConfiguration config;

    private ItemStack megaPhone;

    public ChatListener(Main main) {
        this.main = main;
        this.config = main.getConfig();
        this.megaPhone = main.getMegaphoneItemStack();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);


        int CHAT_RANGE = 0;

        Player sender = e.getPlayer();
        Location senderLoc = sender.getLocation();
        String messsage = e.getMessage();
        ItemStack itemInHand = sender.getInventory().getItemInMainHand();

        //Checks if player has microphone
        if (itemInHand.equals(megaPhone)) {
            CHAT_RANGE = config.getInt("megaphone-range");
        } else {
            CHAT_RANGE = config.getInt("chat-range");
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            Location targetLoc = target.getLocation();
            if (targetLoc.distance(senderLoc) <= CHAT_RANGE) {
                target.sendMessage(sender.getName() + ": " + messsage);
            }
        }
    }

}
