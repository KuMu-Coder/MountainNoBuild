package qq3643203568.mountainNoBuild.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qq3643203568.mountainNoBuild.MountainStatic;

import static qq3643203568.mountainNoBuild.MountainStatic.sendInfo;


public class NoBuildCommand implements CommandExecutor {
    //子命令
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String str, String[] args){
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (sender.isOp()){
                if (args.length ==0){
                    sendInfo(p,"HelpMessage");
                    return true;
                }

                switch (args[0].toLowerCase()){
                    case "reload":
                        MountainStatic.ReloadCfg(sender);
                        return true;
                    case "add":
                        String world = p.getWorld().getName();
                        MountainStatic.addWorld(p,world);
                        break;
                    case "remove":
                        world = p.getWorld().getName();
                        MountainStatic.removeWorld(p,world);
                        break;
                    default:
                        sendInfo(p,"NullMessage");
                }
            }else {
                sender.sendMessage(MountainStatic.replace(MountainStatic.getmsg("NoOpMessage")));
                return false;
            }
        }else {
            sender.sendMessage(MountainStatic.replace(MountainStatic.getmsg("NoPlayerMessage")));
        }
        return false;
    }
}