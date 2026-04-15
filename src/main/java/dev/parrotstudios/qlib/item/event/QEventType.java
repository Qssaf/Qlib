package dev.parrotstudios.qlib.item.event;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class QEventType<T extends Event> {

    // These act exactly like Enum constants, but they carry the TYPE with them!
    public static final QEventType<PlayerInteractEvent> RIGHTCLICK = new QEventType<>(PlayerInteractEvent.class);
    public static final QEventType<PlayerInteractEvent> LEFTCLICK = new QEventType<>(PlayerInteractEvent.class);
    public static final QEventType<PlayerInteractEvent> ANYCLICK = new QEventType<>(PlayerInteractEvent.class);
    public static final QEventType<PlayerMoveEvent> MOVE = new QEventType<>(PlayerMoveEvent.class);
    public static final QEventType<BlockBreakEvent> BREAK = new QEventType<>(BlockBreakEvent.class);
    public static final QEventType<PlayerJumpEvent> JUMP = new QEventType<>(PlayerJumpEvent.class);

    private final Class<T> eventClass;

    private QEventType(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    public Class<T> getEventClass() {
        return eventClass;
    }
}