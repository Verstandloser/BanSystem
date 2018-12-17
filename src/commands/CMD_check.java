package commands;

import me.main.Main;
import me.main.MySQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CMD_check extends Command {

    public CMD_check() {
        super("check");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) cs;
            if(p.hasPermission("system.check")){

                if(args.length == 1){

                    if(isBanned(args[0])){

                        ResultSet rs = MySQL.getResult("SELECT * FROM bansystem WHERE Spielername='"+args[0]+"'");
                        try {
                            while (rs.next()){

                                cs.sendMessage("§c§m----------------§r \n §7Name: §a"+args[0]+" \n §7Von: §a"+rs.getString("Team")
                                        +" \n §7Grund: §a"+rs.getString("Grund")+" \n §7Dauer: §3"+rs.getString("Dauer")
                                        + " \n §7Datum: §f"+rs.getString("Banned")+" §c§m----------------");

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }else{
                        cs.sendMessage(Main.prefix+"§cDer Spieler ist nicht gebannt!");
                    }

                }else{
                    cs.sendMessage(Main.prefix+"§cNutze: §a/check <Spieler>");
                }

            }else{
                cs.sendMessage(Main.prefix+"§cDu hast nicht ausreichend Rechte, um diesen Befehl zu verwenden!");
            }

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
