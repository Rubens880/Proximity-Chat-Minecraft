package com.ruben.proximitychat.Listeners;

import com.ruben.proximitychat.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ChatListener implements Listener {

    private Main main;
    private YamlConfiguration config;

    private NamespacedKey key;

    public ChatListener(Main main) {
        this.main = main;
        this.config = main.getConfig();
        this.key = main.getKey();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);


        int CHAT_RANGE = 0;

        Player sender = e.getPlayer();
        Location senderLoc = sender.getLocation();
        String messsage = e.getMessage();
        ItemStack itemInHand = sender.getInventory().getItemInMainHand();
        ItemMeta itemInHandMeta = itemInHand.getItemMeta();


        //Checks if player has microphone
        if (itemInHandMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
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
