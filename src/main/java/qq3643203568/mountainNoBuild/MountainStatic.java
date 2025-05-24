package qq3643203568.mountainNoBuild;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MountainStatic {
    static Plugin plugin = MountainNoBuild.getProvidingPlugin(MountainStatic.class);
    static File dataFolder;
    private static FileConfiguration langcfg;
    private static File langFile;

    //初始化数据文件夹
    static {
        if (plugin != null) {
            dataFolder = plugin.getDataFolder();
            DefaultCfg();
            loadlangcfg();
        }
    }

    //创建默认配置文件与语言文件
    public static void DefaultCfg() {
        if (plugin == null || dataFolder == null) {
            plugin.getLogger().info("插件未成功加载,无法创建默认配置文件");
            return;
        }
        //保存默认配置文件(config.yml)
        plugin.saveDefaultConfig();
        //创建语言文件
        File langyml = new File(dataFolder, "lang.yml");
        if (!langyml.exists()) {
            try {
                plugin.saveResource("lang.yml", false);
                plugin.getLogger().info("默认语言文件创建成功");
            } catch (Exception e) {
                plugin.getLogger().info("创建默认语言文件出错 原因：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //加载语言配置文件
    private static void loadlangcfg() {
        if (plugin == null) return;

        try {
            langFile = new File(dataFolder, "lang.yml");
            if (!langFile.exists()){
                DefaultCfg();
            }
            langcfg = YamlConfiguration.loadConfiguration(langFile);
            InputStream defaultLang = plugin.getResource("lang.yml");
            if (defaultLang != null){
                FileConfiguration defaultLangcfg = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultLang));
                langcfg.setDefaults(defaultLangcfg);
            }
            plugin.getLogger().info("已加载语言配置");
        } catch (Exception e) {
            plugin.getLogger().info("加载语言配置文件时出错 原因：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //替换颜色字符
    public static String replace(String msg) {
        return msg.replaceAll("&", "§");
    }

    //读取语言配置中对应的文本
    public static String getmsg(String key) {
        if (langcfg == null || key == null) {
            return "[" + key + "]当前关键值不存在";
        }
        if (langcfg.isSet(key)){
            String msg = langcfg.getString(key);
            return msg !=null ? replace(msg): msg;
        }
        String defaultstr = getdefaultmsg(key);
        if (defaultstr != null){
            langcfg.set(key,defaultstr);
            try {
                langcfg.save(langFile);
                plugin.getLogger().info("检测到缺失的语言键["+key+"]已自动补全");
            }catch (IOException e){
                plugin.getLogger().info("保存语言配置文件时出错 原因："+e.getMessage());
                e.printStackTrace();
            }
            return replace(defaultstr);
        }
        return "["+key+"]当前关键值不存在";
    }
    //从插件本体读取配置
    private static String getdefaultmsg(String key){
        InputStream defaultlang = plugin.getResource("lang.yml");
        if (defaultlang !=null){
            try (InputStreamReader reader = new InputStreamReader(defaultlang)){
                FileConfiguration defaultcfg = YamlConfiguration.loadConfiguration(reader);
                return defaultcfg.getString(key);
            }catch (Exception e){
                plugin.getLogger().info("读取默认语言配置文件失败 原因："+ e.getMessage());
            }
        }
        return null;
    }
    //重载配置文件
    public static void ReloadCfg(CommandSender sender) {
        if (plugin == null) {
            if (sender != null) sender.sendMessage("插件未初始化无法重载配置");
            return;
        }
        try {
            plugin.reloadConfig();
            loadlangcfg();
            if (sender != null) {
                sender.sendMessage(replace(getmsg("ReloadMessage")));
            }
        } catch (Exception e) {
            if (sender != null) {
                sender.sendMessage(replace("&b重载失败" + e.getMessage()));
            }
            plugin.getLogger().info("重载配置文件失败 原因：" + e.getMessage());
            e.printStackTrace();
        }
    }
    //添加世界至黑名单列表
    public static void addWorld(Player p,String world){
        List<String> worlds = plugin.getConfig().getStringList("ListenerWorld");
        if (worlds == null) worlds = new ArrayList<>();
        if (worlds.contains(world)){
            sendInfo(p,"AddInListMessage");
            return;
        }
        worlds.add(world);
        plugin.getConfig().set("ListenerWorld",worlds);
        plugin.saveConfig();
    }
    //从黑名单列表中移除世界
    public static void removeWorld(Player p,String world){
        List<String> worlds = plugin.getConfig().getStringList("ListenerWorld");
        if (!worlds.contains(world)){
            sendInfo(p,"RemoveInListMessage");
            return;
        }
        worlds.remove(world);
        plugin.getConfig().set("ListenerWorld",worlds);
        plugin.saveConfig();
        sendInfo(p,"RemoveWorldMessage");
    }
    //发送信息
    public static void sendInfo(Player p, String key) {
        if (p != null && key != null) {
            if (getmsg(key) !=null){
                String msg = replace(getmsg(key));
                p.sendMessage(msg);
            }else {
                String msg = replace("当前关键值["+key+"]不存在");
                p.sendMessage(msg);
            }
        }
    }
    //发送bar信息
    public static void sendBar(Player p,String key){
        String msg = replace(getmsg(key));
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
    }
    //判断方法
    public static boolean isPlayerOP(Player p) {
        if (p == null) return false;
        if (p.isOp()) return true;
        return false;
    }
    //判断世界
    public static boolean isBlackWolrd(Player p){
        String world = p.getWorld().getName();
        List<String> worlds = plugin.getConfig().getStringList("ListenerWorld");
        if (worlds.contains(world))return true;
        return false;
    }
}
