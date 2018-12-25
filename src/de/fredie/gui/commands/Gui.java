package de.fredie.gui.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Gui implements CommandExecutor, Listener {

	private Inventory inv = null;
	private ItemStack guiItem2 = null;
	private ItemStack guiItem3 = null;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		
		// Das Prinzip von den Ifs hier nennt man Short-Condition-Exit
		// das heißt mit so wenig wie möglich Abfragen feststellen, ob
		// die Vaerarbeitung zugelassen werden kann.
		//
		// Auch wird hier Multiexit (mehrere 'return's) verwendet.
		
		
		// joestr instanceof Player -> true (wahr)
		// Console instanceof Player -> false (nicht wahr)
		// CommandBlock instanceof Player -> false (nicht wahr)
		// 
		
		if(!(sender instanceof Player)) {
			
			// Wenn man hier ist, ist der Befehlssender _definitiv_ kein Spieler,
			// heißt der Spieler Befehlspart muss nicht eingerückt sein.
			
			sender.sendMessage("Diesen Befehl können nur Spieler ausführen!");
			return true;
		}
		
		// Ab hier ist der Befehlssender _definitiv_ ein Spieler,
		// Direkt Permissions abfragen.
		
		Player player = (Player) sender;
		
		// Wenn der Spieler beide Permissions nicht hat -> Fertig, wir müssen nix mehr machen
		if(!player.hasPermission("command.gui") && !player.hasPermission("premium")) {
			
			if(player.hasPermission("command.error.help")) {
		    	   player.sendMessage("§l§cDu brauchst das Recht: §r§ocommand.gui" + "§l§c!");
		    	   return true;
		    }
			
			player.sendMessage("Du hast keine Berechtigung!");
			return true;
		}
		
		if(args.length != 0) {
			player.sendMessage("§l§cBitte benutze /gui");
			return true;
		}
		
		// Command args: 0      1      2     3       4
		// /zone         create
		// /zone         rights zone#1 allow player1 *
		// if(args[0].equalsIgnoreCase("create")) {
		//	
		// }
		
		if(player.hasPermission("command.gui") && player.hasPermission("premium")) {
			Inventory inv = Bukkit.createInventory(null, 9 * 1, "§l§6GUI");
			
			ItemStack guiItem1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
			this.guiItem2 = new ItemStack(Material.WOOL, 1, (short) 5);
			ItemMeta healMeta = guiItem1.getItemMeta();
			this.guiItem2.setItemMeta(healMeta);
			
			healMeta.setDisplayName("§a/heal");
			healMeta.setLore(Arrays.asList("§o§7Damit kannst dich heilen und deine Hunger sättigen"));
			
			inv.setItem(8,guiItem1);
			inv.setItem(7,guiItem2);
			inv.setItem(6,guiItem1);
			inv.setItem(5,guiItem1);
			inv.setItem(4,guiItem1);
			inv.setItem(3,guiItem1);
			inv.setItem(2,guiItem1);
			inv.setItem(1,guiItem1);
			inv.setItem(0,guiItem1);
			
			player.openInventory(inv);
			player.sendMessage("§aDU hast das GUI geöffnet");
			
			return true;
		}
		
		if(player.hasPermission("command.gui")) {
			Inventory inv = Bukkit.createInventory(null, 9 * 1, "§l§6GUI");
			
			ItemStack guiItem1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
			
			this.guiItem3 = new ItemStack(Material.WOOL, 1, (short) 5);
			ItemMeta noHealMeta = guiItem3.getItemMeta();
			noHealMeta.setDisplayName("§7/heal");
			noHealMeta.setLore(Arrays.asList("§o§l§4Nur für" + "§l§6Premium" + "§o§l§4!"));
			this.guiItem3.setItemMeta(noHealMeta);
			
			inv.setItem(8,guiItem1);
			inv.setItem(7,guiItem3);
			inv.setItem(6,guiItem1);
			inv.setItem(5,guiItem1);
			inv.setItem(4,guiItem1);
			inv.setItem(3,guiItem1);
			inv.setItem(2,guiItem1);
			inv.setItem(1,guiItem1);
			inv.setItem(0,guiItem1);
			
			player.openInventory(inv);
			player.sendMessage("§aDU hast das GUI geöffnet");
			
			return true;
		}
		
		return false; 
	}
	
	@EventHandler
	public void onGuiDrag(InventoryDragEvent event) {
		
		// Wenn das Inventar nicht unser inv ist
		if(!event.getInventory().equals(inv)) {
			// können wir gleich Schluß machen.
			return;
		}
		
		if(!event.isCancelled()) event.setCancelled(true);
	}
	
	@EventHandler
	public void onGuiInteract(InventoryInteractEvent event) {
		
		// Wenn das Inventar nicht unser inv ist
		if(!event.getInventory().equals(inv)) {
			// können wir gleich Schluß machen.
			return;
		}
		
		if(!event.isCancelled()) event.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onGuiInteract2(InventoryClickEvent event) {
		
		// Wenn das Inventar nicht unser inv ist
		if(!event.getClickedInventory().equals(inv)) {
			// können wir gleich Schluß machen.
			return;
		}
		
		if(!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		if(event.getClickedInventory().getItem(event.getRawSlot()) == null) {
			return;
		}
		
		if(event.getClickedInventory().getItem(event.getRawSlot()) == this.guiItem3) {
			
			// Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "tell " + event.getWhoClicked().getName() + " Guten Tag!");
			Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "heal");
		}
		
		if(!event.isCancelled()) event.setCancelled(true);
	}
	
	// Reihenfolge
	// LOWEST, LOW, NORMAL, HIGH, HIGHEST, MONITOR
	// LOWEST sagt: event=false
	// (LOW nicht vorhanden)
	// NORMAL sagt: event=true
	// HIGH sagt: event=true
	// HIGHEST sagt: event=false
	// MONITOR sieht zum schluss: event=false
	// zuerst werden alle  LOWEST, dann LOW usw ...
	// MONITOR immer zum schluss und darf _nichts_ verändern!
	@EventHandler(priority=EventPriority.LOWEST)
	public void onGuiInteract(InventoryClickEvent event) {
		
		// Wenn das Inventar nicht unser inv ist
		if(!event.getClickedInventory().equals(inv)) {
			// können wir gleich Schluß machen.
			return;
		}
		
		if(!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		if(event.getClickedInventory().getItem(event.getRawSlot()) == null) {
			return;
		}
		
		if(event.getClickedInventory().getItem(event.getRawSlot()) == this.guiItem2) {
			
			// Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "tell " + event.getWhoClicked().getName() + " Guten Tag!");
			Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "heal");
		}
		
		if(event.getClickedInventory().getItem(event.getRawSlot()) == this.guiItem3) {
				
			// Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "tell " + event.getWhoClicked().getName() + " Guten Tag!");
			
			TextComponent message = new TextComponent(TextComponent.fromLegacyText("§l§8Du musst dir zuerst Premium kaufen! "));
			TextComponent link = new TextComponent(TextComponent.fromLegacyText("§6In unserem Shop"));
			link.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org"));
			
			Player playerToSend = (Player) event.getWhoClicked();
			
			playerToSend.spigot().sendMessage(message, link);
		}
		
		
		if(!event.isCancelled()) event.setCancelled(true);
	}
}

	
