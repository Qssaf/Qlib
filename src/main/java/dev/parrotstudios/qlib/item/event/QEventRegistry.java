package dev.parrotstudios.qlib.item.event;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QEventRegistry {


    private static final Map<QEventType<?>, List<QEvent<?>>> registry = new HashMap<>();

    public static void register(QEvent<?> e) {
        List<QEvent<?>> events = registry.get(e.getEventType());
        if (events != null && events.contains(e)) return;
        registry.computeIfAbsent(e.getEventType(), k -> new ArrayList<>()).add(e);
    }


    public static void unregister(QEvent<?> e) {
        List<QEvent<?>> events = registry.get(e.getEventType());
        if (events != null) {
            events.remove(e);

            if (events.isEmpty()) {
                registry.remove(e.getEventType());
            }
        }
    }

    public static List<QEvent<?>> getEventsByType(QEventType<?> type) {
        return registry.getOrDefault(type, new ArrayList<>());
    }

    public static List<QEvent<?>> getForItem(QEventType<?> eventType, ItemStack itemStack) {
        return getEventsByType(eventType).stream().filter(qEvent -> qEvent.executesWith(itemStack)).toList();
    }

    public static <T extends Event> void handle(QEventType<?> eventType, ItemStack itemStack, T event) {
        List<QEvent<?>> events = getForItem(eventType, itemStack);
        if (events.isEmpty()) return;
        for (QEvent<?> qEvent : events) {
            qEvent.execute(event);

        }
    }

    public static HashMap<QEventType<?>, List<QEvent<?>>> getRegistry() {
        return new HashMap<>(registry);
    }


    public static boolean isRegistered(QEvent<?> e) {
        List<QEvent<?>> events = registry.get(e.getEventType());
        return events != null && events.contains(e);
    }

    public static void clearRegistry() {
        registry.clear();
    }
}