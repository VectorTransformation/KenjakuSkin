package kenjakuSkin.listener;

import kenjakuSkin.skin.CustomSkin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CustomListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CustomSkin.save(event.getPlayer());
    }
}
