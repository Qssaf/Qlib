package dev.parrotstudios.qlib.item.event;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.parrotstudios.qlib.QLib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class QEventListener implements Listener{

    private static final JavaPlugin plugin;


    static {
        plugin = QLib.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new QEventListener(), plugin);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        QEventRegistry.handle(QEventType.RIGHTCLICK, e.getItem(),e);

    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent e) {

        if(e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_AIR) return;
        if(e.getItem() == null) return;
        QEventRegistry.handle(QEventType.LEFTCLICK, e.getItem(),e);
    }

    @EventHandler
    public void onAnyClick(PlayerInteractEvent e) {

        if(e.getItem() == null) return;
        QEventRegistry.handle(QEventType.ANYCLICK, e.getItem(),e);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.MOVE, player.getInventory().getItemInMainHand(),e);

    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e){
        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.JUMP, player.getInventory().getItemInMainHand(),e);
    }


    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){

        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.JUMP, player.getInventory().getItemInMainHand(),e);
    }


}
