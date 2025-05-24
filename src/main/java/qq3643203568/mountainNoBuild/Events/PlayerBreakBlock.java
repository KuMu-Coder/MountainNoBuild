package qq3643203568.mountainNoBuild.Events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import qq3643203568.mountainNoBuild.MountainStatic;

//监听玩家破坏方块
public class PlayerBreakBlock implements Listener {
    @EventHandler
    public void PlayerBreack(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (!MountainStatic.isPlayerOP(player)){
            if (MountainStatic.isBlackWolrd(player)){
                event.setCancelled(true);
                MountainStatic.sendBar(player,"BreakMessage");
                player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO,0.7f,0.8f);
            }
        }
    }
}
