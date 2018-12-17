package commands;

import me.main.Main;
import me.main.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMD_ban extends Command {
    public CMD_ban() {
        super("ban");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs.hasPermission("system.ban")){
            // /ban SPIELER GRUND
            if(args.length == 2){

                if(MySQL.isConnected()){

                    if(!isBanned(args[0])){

                        if(args[1].equalsIgnoreCase("Hacking")){

                            long i = 2092000000+System.currentTimeMillis();
                            long w =  500000000;
                            long time = i+w;
                            cs.sendMessage(Main.prefix+"§7Du hast §c"+args[0]+" §7gebannt!");
                            setBanned(args[0], cs.getName(), "Hacking", "30 Tage", String.valueOf(time));

                        }else if(args[1].equalsIgnoreCase("Test")){
                            long i = 70000+System.currentTimeMillis();

                            cs.sendMessage(Main.prefix+"§7Du hast §c"+args[0]+" §7gebannt!");
                            setBanned(args[0], cs.getName(), "Test", "70 Sekunden", String.valueOf(i));

                        }else if(args[1].equalsIgnoreCase("Banumgehung")){

                            cs.sendMessage(Main.prefix+"§7Du hast §c"+args[0]+" §7gebannt!");
                            setBanned(args[0], cs.getName(), "Hacking", "Permanent", "-1");

                        }else{
                            if(cs.hasPermission("system.ban.custom")){
                                cs.sendMessage(Main.prefix+"§7Du hast §c"+args[0]+" §7gebannt!");
                                setBanned(args[0], cs.getName(), args[1], "Permanent", "-1");
                            }else{
                                cs.sendMessage(Main.prefix+"§cBitte nutze die Gründe Hacking oder Banumgehung!");
                            }
                        }



                    }else{
                        cs.sendMessage(Main.prefix+"§cDer Spieler ist bereits gebannt!");
                    }

                }else{
                    cs.sendMessage(Main.prefix+"§cEs gab einen Fehler am §3MySQL §cServer! Bitte melde dies einem Admin!");
                }

            }else{
                cs.sendMessage(Main.prefix+"§cNutze: §a/ban <Spieler> <Grund>");
            }

        }else{
            cs.sendMessage(Main.prefix+"§cDu hast nicht ausreichend Rechte, um diesen Befehl zu verwenden!");
        }
    }

    private boolean isBanned(String player){
        ResultSet rs = MySQL.getResult("SELECT * FROM bansystem WHERE Spielername='"+player+"'");

        try {

            while (rs.next()){

                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;

    }

    private void setBanned(String player, String team, String reason, String duration, String unban){

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = sdf.format(new Date());
        MySQL.update("INSERT INTO bansystem (Spielername, Team, Grund, Dauer, Banned, Unban) VALUES ('"+player+
        "','"+team+"','"+reason+"','"+duration+"','"+date+"','"+unban+"')");

        for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()){
            if(all.hasPermission("system.ban.notice")){
                all.sendMessage(Main.prefix+"§7Der Spieler §a"+player+" §7wurde von §c"+team+" §7wegen §3"+reason+" §7gebannt!");
            }
        }

        if(BungeeCord.getInstance().getPlayer(player)!=null){
            BungeeCord.getInstance().getPlayer(player).disconnect("§cDu wurdest gebannt!");
        }

    }
}
