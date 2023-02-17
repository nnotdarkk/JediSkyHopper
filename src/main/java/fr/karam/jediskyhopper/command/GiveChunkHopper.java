package fr.karam.jediskyhopper.command;

import fr.karam.jediskyhopper.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveChunkHopper extends Command {

    private final Main main;

    public GiveChunkHopper(String name, Main main) {
        super(name);
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission("jediskyhopper.give")){
            sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande");
            return false;
        }

        if(args.length <= 1){
            sender.sendMessage("§cUsage: /givechunkhopper <player> <nombre>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);

        if(target == null){
            sender.sendMessage("§cCe joueur n'est pas connecté");
            return false;
        }

        if(amount <= 0 || amount > 64){
            sender.sendMessage("§cLa quantité doit être entre 1 et 64");
            return false;
        }
        ItemStack itemStack = new ItemStack(Material.HOPPER, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(main.getConfig().getString("item-name").replaceAll("&", "§"));

        List<String> lore = main.getConfig().getStringList("item-lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        target.getInventory().addItem(itemStack);
        return false;
    }
}
