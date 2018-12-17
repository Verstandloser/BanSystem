package me.main;

import commands.CMD_ban;
import commands.CMD_check;
import commands.CMD_unban;
import events.LoginEvents;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static String prefix = "§f» §4§lBAN §f« ";
    public static Main main;

    @Override
    public void onEnable() {
        MySQL.connect();
        registerCommands();
        registerEvents();

        main = this;

        BungeeCord.getInstance().getConsole().sendMessage(prefix+"§aPlugin aktiviert!");
    }

    @Override
    public void onDisable() {
        MySQL.close();
        BungeeCord.getInstance().getConsole().sendMessage(prefix+"§cPlugin deaktiviert!");
    }

    private void registerCommands() {

        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_ban());
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_unban());
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_check());

    }

    private void registerEvents() {

        BungeeCord.getInstance().getPluginManager().registerListener(this, new LoginEvents(this));

    }
}
