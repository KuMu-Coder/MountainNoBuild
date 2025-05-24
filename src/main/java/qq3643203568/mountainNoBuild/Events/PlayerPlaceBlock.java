package qq3643203568.mountainNoBuild.Events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import qq3643203568.mountainNoBuild.MountainStatic;

public class PlayerPlaceBlock implements Listener {
    @EventHandler
    public void PlayerPlace(BlockPlaceEvent event){
        Player p = event.getPlayer();
        if (!MountainStatic.isPlayerOP(p)){
            if (MountainStatic.isBlackWolrd(p)){
                event.setCancelled(true);
                MountainStatic.sendBar(p,"PlaceMessage");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,0.7f,0.8f);
            }
        }
    }
}
