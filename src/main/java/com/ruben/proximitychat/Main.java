package com.ruben.proximitychat;

import com.ruben.proximitychat.Commands.GetMegaphoneCommand;
import com.ruben.proximitychat.Commands.GlobalCommand;
import com.ruben.proximitychat.Listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Main extends JavaPlugin {

    /*
        ProximityChat:
            - Only people within 10 blocks can read your chat.
            - Using a megaphone makes the read distance 20.
            - Config file with all distances (talk-range, megaphone-range)
            - Command /global <message> sends a global message to everyone.
            - Command /getmegaphone gives megaphone.
     */

    private YamlConfiguration config;
    private NamespacedKey key;

    @Override
    public void onEnable() {
        //configFile
        createConfig();

        //NamespacedKey
        key = new NamespacedKey(this, "chatRangeItem");


        //Commands
        getCommand("global").setExecutor(new GlobalCommand());
        getCommand("getmegaphone").setExecutor(new GetMegaphoneCommand(this));

        //Listeners
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);


    }

    public void createConfig() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                config.set("chat-range", 10);
                config.set("megaphone-range", 20);
                config.save(file);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public ItemStack getMegaphoneItemStack() {
        ItemStack megaphone = new ItemStack(Material.END_ROD);
        ItemMeta megaphoneMeta = megaphone.getItemMeta();
        megaphoneMeta.setDisplayName("Megaphone");
        megaphoneMeta.setLore(List.of(ChatColor.DARK_RED + "Megaphone to say things louder!"));
        PersistentDataContainer pdc = megaphoneMeta.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING, "megaphone");
        megaphone.setItemMeta(megaphoneMeta);
        return megaphone;
    }

    public NamespacedKey getKey() {return key;}

}
