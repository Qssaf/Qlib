package dev.parrotstudios.qlib.item.event;

import dev.parrotstudios.qlib.QLib;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class QEvent<T extends Event> {

    private final QEventType<T> eventType;
    private final Class<T> eventClass;
    private String itemKey;
    private NamespacedKey namespacedKey;
    private Consumer<T> consumer;

    private QEvent(QEventType<T> eventType) {
        this.eventType = eventType;
        this.eventClass = eventType.getEventClass();
    }


    public static <T extends Event> QEvent<T> create(QEventType<T> eventType){
        return new QEvent<>(eventType);
    }

    public QEvent<T> setTask(@NonNull Consumer<T> consumer){
        this.consumer = consumer;
        return this;
    }

    public QEvent<T> setItemKey(@NotNull String itemKey){
        this.itemKey = itemKey;
        this.namespacedKey = new NamespacedKey(QLib.getPlugin(), itemKey);
        return this;
    }

    public QEvent<T> register(){
        if(itemKey == null) throw new NullPointerException("Item key is null");
        QEventRegistry.register(this);
        return this;
    }

    public boolean executesWith(@NotNull ItemStack itemstack){
        if (namespacedKey == null) return false;
        return itemstack.getPersistentDataContainer().has(namespacedKey);
    }

    public void execute(Event event) {
        if(eventClass.isInstance(event)){
            consumer.accept(eventClass.cast(event));
        }

    }



    public QEventType<T> getEventType() { return eventType; }
    public String getItemKey() { return itemKey; }
    public NamespacedKey getNamespacedKey() { return namespacedKey; }
}