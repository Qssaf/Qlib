package dev.parrotstudios.qlib.item;

import dev.parrotstudios.qlib.item.event.QEvent;
import dev.parrotstudios.qlib.item.event.QEventType;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@SuppressWarnings("unused")
public final class QItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private QItemBuilder(){
        this.itemStack = null;
        this.itemMeta = null;

    }

    private QItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    private QItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public static QItemBuilder from(ItemStack itemStack) {
        return new QItemBuilder(itemStack);
    }

    public static QItemBuilder create(Material material) {
        return new QItemBuilder(material);
    }

    public QItemBuilder name(String name) {
        itemMeta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(name).decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public QItemBuilder lore(String... lines) {
        List<TextComponent> lore = Arrays.stream(lines)
                .map(line -> LegacyComponentSerializer.legacyAmpersand().deserialize(line))
                .toList();
        itemMeta.lore(lore);
        return this;
    }

    public QItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public QItemBuilder glow() {
        itemMeta.setEnchantmentGlintOverride(true);
        return this;
    }

    public QItemBuilder glow(boolean state) {
        itemMeta.setEnchantmentGlintOverride(state);
        return this;
    }

    public QItemBuilder hideEnchants() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public QItemBuilder hideEnchants(boolean state) {

        if (state) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public QItemBuilder addFlags(ItemFlag... flags){
        itemMeta.addItemFlags(flags);

        return this;
    }

    public QItemBuilder removeFlags(ItemFlag... flags){
        itemMeta.removeItemFlags(flags);
        return this;
    }

    public QItemBuilder unbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    public QItemBuilder unbreakable(boolean state) {
        itemMeta.setUnbreakable(state);
        return this;
    }

    public QItemBuilder model(String nameSpace, String key) {
        itemMeta.setItemModel(new NamespacedKey(nameSpace, key));
        return this;
    }

    public QItemBuilder model(JavaPlugin plugin, String key) {
        itemMeta.setItemModel(new NamespacedKey(plugin, key));
        itemMeta.clone();
        return this;
    }

    public QItemBuilder toolTipStyle(String nameSpace, String key) {
        itemMeta.setTooltipStyle(new NamespacedKey(nameSpace, key));
        return this;
    }

    public QItemBuilder toolTipStyle(JavaPlugin plugin, String key) {
        itemMeta.setTooltipStyle(new NamespacedKey(plugin, key));
        return this;
    }

    public <T extends Event> QItemBuilder addEvent(QEventType<T> eventType, String key, Consumer<T> task) {

        QEvent<T> qEvent = QEvent.create(eventType)
                .setItemKey(key)
                .setTask(task)
                .register();

        itemMeta.getPersistentDataContainer().set(qEvent.getNamespacedKey(), PersistentDataType.BOOLEAN, true);
        return this;
    }
    public QItemBuilder enchant(Enchantment enchantment, byte level) {
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    public QItemBuilder enchant(Enchantment enchantment, byte level,boolean ignoreRestriction) {
        itemMeta.addEnchant(enchantment, level, ignoreRestriction);
        return this;
    }

    public QItemBuilder addNameSpacedKey(JavaPlugin plugin,String keyName, String value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
        return this;
    }

    public QItemBuilder addNameSpacedKey(String nameSpace,String keyName, String value) {
        NamespacedKey key = new NamespacedKey(nameSpace, keyName);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
