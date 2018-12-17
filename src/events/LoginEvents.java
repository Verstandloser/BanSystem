package events;

import me.main.Main;
import me.main.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginEvents implements Listener {

    public LoginEvents(Main main) {
    }

    @EventHandler
    public void onLogin(LoginEvent e){

        if(isBanned(e.getConnection().getName())){

            ResultSet rs = MySQL.getResult("SELECT * FROM bansystem WHERE Spielername='"+e.getConnection().getName()+"'");
            try {
                while (rs.next()){

                    if(rs.getString("Dauer").equalsIgnoreCase("Permanent")){
                        e.setCancelReason("§aSERVER \n \n §cDu wurdest gebannt! \n §7Grund: §c"+rs.getString("Grund")+
                        " \n §7Dauer: §4Permanent \n \n §bBitte erstelle auf §eexample.net/unban §beinen Entbannungsantrag!");
                        e.setCancelled(true);
                    }else{
                        if(rs.getLong("Unban") >= System.currentTimeMillis()){
                            e.setCancelReason("§aSERVER \n \n §cDu wurdest gebannt! \n §7Grund: §c"+rs.getString("Grund")+
                                    " \n §7Dauer: §c"+rs.getString("Dauer")+" " +
                                    "\n \n §bBitte erstelle auf §eexample.net/unban §beinen Entbannungsantrag!");
                            e.setCancelled(true);
                        }else{
                            BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(),
                                    "unban "+e.getConnection().getName());
                        }
                    }


                }
            } catch (SQLException e1) {
                e1.printStackTrace();
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
