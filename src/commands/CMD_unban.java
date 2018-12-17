package commands;

import me.main.Main;
import me.main.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CMD_unban extends Command {

    public CMD_unban() {
        super("unban");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {

        if(cs.hasPermission("system.unban")) {

            if(args.length == 1){

                if(isBanned(args[0])){

                    MySQL.update("DELETE FROM bansystem WHERE Spielername='"+args[0]+"'");
                    cs.sendMessage(Main.prefix+"§aDu hast §7"+args[0]+" §aentbannt!");

                    for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()){
                        if(all.hasPermission("system.ban.notice")){

                            all.sendMessage(Main.prefix+"§7Der Spieler §a"+args[0]+" §7wurde von §c"+cs.getName()+" §7entbannt!");

                        }
                    }

                }else{
                    cs.sendMessage(Main.prefix+"§cDer Spieler ist nicht gebannt!");
                }

            }else{
                cs.sendMessage(Main.prefix+"§cNutze: §a/unban <Spieler>");
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

}
