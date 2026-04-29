package dev.parrotstudios.qlib.item.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
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

public class QEventListener implements Listener {

    private static final JavaPlugin plugin;


    static {
        plugin = QLib.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new QEventListener(), plugin);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null) return;
        QEventRegistry.handle(QEventType.RIGHT_CLICK, event.getItem(), event);

    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) return;
        if (event.getItem() == null) return;
        QEventRegistry.handle(QEventType.LEFT_CLICK, event.getItem(), event);
    }

    @EventHandler
    public void onAnyClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        QEventRegistry.handle(QEventType.ANY_CLICK, event.getItem(), event);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.MOVE, player.getInventory().getItemInMainHand(), event);

    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.JUMP, player.getInventory().getItemInMainHand(), event);
    }


    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().isAir()) return;
        QEventRegistry.handle(QEventType.BREAK, player.getInventory().getItemInMainHand(), event);
    }

    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        if (event.getNewItem().getType().isAir()) return;
        QEventRegistry.handle(QEventType.EQUIP_ARMOR, event.getNewItem(), event);
    }

    @EventHandler
    public void onArmorUnequip(PlayerArmorChangeEvent event) {
        if (event.getOldItem().getType().isAir()) return;
        QEventRegistry.handle(QEventType.UNEQUIP_ARMOR, event.getOldItem(), event);
    }
}
