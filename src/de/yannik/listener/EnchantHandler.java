package de.yannik.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.yannik.builder.ItemBuilder;



public class EnchantHandler implements CommandExecutor, Listener {

	
	@Override
	public boolean onCommand(CommandSender s, Command c, String arg2, String[] args) {
		if(c.getName().equalsIgnoreCase("openEnchants")){
			Player p = (Player)s;
			
			if (p.isOp()) {
			openEnchantments(p);
			}
		}
		return false;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null) {
		if (e.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
			openEnchantments(e.getPlayer());
			e.setCancelled(true);
			
		}
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() != null) {
		if (e.getView().getTitle().startsWith("§8Enchantment")) {

		e.setCancelled(true);
		}
		
		
		
		if (e.getView().getTitle().startsWith("§8Enchantment")) {

		for (Enchantment enchantment : Enchantment.values()) {
        	
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§f" + enchantment.getKey().getKey())) {

	            String enchantmentName = enchantment.getKey().getKey();

	            if (enchantmentName.equals(e.getCurrentItem().getItemMeta().getDisplayName().replace("§f", ""))) {
				if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
					
	        		e.setCancelled(true);
	        		p.sendMessage("§cBitte nehme einen Gegenstand in die Hand!");
					p.closeInventory();
	                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
					return;
				}
				if (!checkItem(p)) {
	        		e.setCancelled(true);
	        		p.sendMessage("§cDiesen Gegenstand kann man nicht verzaubern!");
					p.closeInventory();
	                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
					return;
				}
				if (e.getCurrentItem().getAmount() < p.getLevel() || e.getCurrentItem().getAmount() == p.getLevel()) {
					p.setLevel(p.getLevel() - e.getCurrentItem().getAmount());
					p.sendTitle("§6§l" + enchantmentName, "§c§l" + e.getCurrentItem().getAmount());
					p.getItemInHand().addUnsafeEnchantment(enchantment, e.getCurrentItem().getAmount());
					p.closeInventory();
	                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
	        		e.setCancelled(true);
				} else {
					p.closeInventory();
	                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
	        		e.setCancelled(true);
				}
	            }

			}
        }
		}
		if (e.getView().getTitle().startsWith("§8Enchantments")) {
	        for (Enchantment enchantment : Enchantment.values()) {
	        	
	        	
	            String enchantmentName = enchantment.getKey().getKey();
	    
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(enchantmentName)) {
	                openEnchantmentunder(p, enchantment);

				}
	        }
			
		}
		}
	}
	
	  
    private boolean checkItem(final Player p) {

                

    	ItemStack iHand = p.getItemInHand();
    	
    	if(iHand.getType().name().contains("SWORD")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("PICKAXE")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("AXE")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("CHESTPLATE")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("TRIDENT")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("BOOTS")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("LEGGINGS")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("HELMET")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("SHELL")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("ARROW")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("BOW")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("SHOVEL")) {
    		return true;
    	}
    	if(iHand.getType().name().contains("HOE")) {
    		return true;
    	}
        return false;

    }
	  public static void openEnchantments(Player p) {

		        Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9 *5, "§8Enchantments");

		        for (Enchantment enchantment : Enchantment.values()) {
		            String enchantmentName = enchantment.getKey().getKey();
		            inv.addItem(new ItemBuilder(Material.ENCHANTED_BOOK, 1, 0).setDisplayName(enchantmentName).setLore(new String[]{  "§8> §7§lStart-Level§8: §e" + enchantment.getStartLevel(), "§8> §7§lMax-Level§8: §e" + enchantment.getMaxLevel(), "", "§8§l> §e§lKlicke zum auswählen."}).build());
		        }
		        
		        p.openInventory(inv);
		        
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 25.0f, 25.0f);

		    }
	  
	  public static void openEnchantmentunder(Player p, Enchantment enchantment) {

	        Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9, "§8Enchantment");

	        
	        int maxlevel = enchantment.getMaxLevel();
	        
	    
	        for (int i = 0; i < maxlevel + 1; i += 1) {
	        	if (i != 0) {
	            inv.addItem(new ItemBuilder(Material.EXPERIENCE_BOTTLE, i, 0).setDisplayName("§f" + enchantment.getKey().getKey()).setLore(new String[]{  "§8> §7§lBenötigtes Level§8: §e" + i, "", "§8§l> §5§lKlicke zum verzaubern."}).build());
	        	}
	        	}
	        
	        p.openInventory(inv);
	        
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 25.0f, 22.0f);

	    }
}
