package kenjakuSkin.system;

import kenjakuSkin.command.CustomCommand;
import kenjakuSkin.data.CustomData;
import kenjakuSkin.listener.CustomListener;
import kenjakuSkin.pair.CustomPair;
import kenjakuSkin.skin.CustomSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class KenjakuSkin extends JavaPlugin {
    public static KenjakuSkin plugin;
    public static Logger logger;
    public static ConcurrentHashMap<Player, CustomPair<String, String>> skins = new ConcurrentHashMap<>();

    @Override
    public void onLoad() {
        plugin = this;
        logger = getLogger();
    }

    @Override
    public void onEnable() {
        onData();
        onReload();
        onListener();
        onCmd();
    }

    @Override
    public void onDisable() {
        onUnload();
    }

    public void onData() {
        CustomData.loadAll();
    }

    public void onReload() {
        Bukkit.getOnlinePlayers().forEach(CustomSkin::loadLastSkin);
    }

    public void onListener() {
        Bukkit.getPluginManager().registerEvents(new CustomListener(), this);
    }

    public void onCmd() {
        getCommand("kenjakuskin").setExecutor(new CustomCommand());
    }

    public void onUnload() {
        Bukkit.getOnlinePlayers().forEach(CustomSkin::saveYml);
    }
}
