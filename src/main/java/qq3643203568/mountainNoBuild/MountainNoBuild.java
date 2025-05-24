package qq3643203568.mountainNoBuild;

import org.bukkit.plugin.java.JavaPlugin;
import qq3643203568.mountainNoBuild.Commands.NoBuildCommand;
import qq3643203568.mountainNoBuild.Events.PlayerBreakBlock;
import qq3643203568.mountainNoBuild.Events.PlayerPlaceBlock;

public final class MountainNoBuild extends JavaPlugin {

    @Override
    public void onEnable() {
        //初始化所有配置文件
        MountainStatic.DefaultCfg();
        //注册监听类
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(),this);
        getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(),this);
        //注册命令
        getCommand("nobuild").setExecutor(new NoBuildCommand());
        //发送消息
        getLogger().info("插件启用成功");
        getLogger().info("作者QQ：3643203568");

    }

    @Override
    public void onDisable() {
        getLogger().info("插件卸载成功");
        getLogger().info("作者QQ：3643203568");
    }
}
