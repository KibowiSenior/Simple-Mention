package com.simplemention;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleMention extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    private final Set<UUID> soundDisabled = new HashSet<>();
    private final Map<UUID, Set<UUID>> blockedUsers = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("togglemention").setExecutor(this);
        getCommand("mention").setExecutor(this);
        getCommand("mention").setTabCompleter(this);
        getLogger().info("SimpleMention enabled with Paper/Leaf/Folia support!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player sender = event.getPlayer();
        
        boolean modified = false;
        Set<Player> targetsToPing = new HashSet<>();

        // Get all players and sort by name length descending to avoid partial matches
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        onlinePlayers.sort((p1, p2) -> Integer.compare(p2.getName().length(), p1.getName().length()));

        for (Player onlinePlayer : onlinePlayers) {
            String name = onlinePlayer.getName();
            // Regex to match the name case-insensitively with word boundaries
            String regex = "(?i)\\b" + Pattern.quote(name) + "\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                // Replace with the correctly cased name and highlighting
                // The replacement uses the onlinePlayer.getName() which is the official casing
                message = matcher.replaceAll(ChatColor.GOLD + "@" + ChatColor.UNDERLINE + name + ChatColor.RESET);
                modified = true;

                if (soundDisabled.contains(onlinePlayer.getUniqueId())) {
                    continue;
                }

                Set<UUID> blocked = blockedUsers.get(onlinePlayer.getUniqueId());
                if (blocked != null && blocked.contains(sender.getUniqueId())) {
                    continue;
                }

                targetsToPing.add(onlinePlayer);
            }
        }

        if (modified) {
            event.setMessage(message);
            for (Player target : targetsToPing) {
                Bukkit.getScheduler().runTask(this, () -> {
                    target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                });
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("togglemention")) {
            UUID uuid = player.getUniqueId();
            if (soundDisabled.contains(uuid)) {
                soundDisabled.remove(uuid);
                player.sendMessage(ChatColor.GREEN + "Mention sound enabled.");
            } else {
                soundDisabled.add(uuid);
                player.sendMessage(ChatColor.RED + "Mention sound disabled.");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("mention")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Usage: /mention <block|unblock> <player>");
                return true;
            }

            String action = args[0].toLowerCase();
            String targetName = args[1];
            
            Player target = Bukkit.getPlayer(targetName);
            UUID playerUuid = player.getUniqueId();

            if (action.equals("block")) {
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found or not online.");
                    return true;
                }
                UUID targetUuid = target.getUniqueId();
                blockedUsers.computeIfAbsent(playerUuid, k -> new HashSet<>()).add(targetUuid);
                player.sendMessage(ChatColor.RED + "You have blocked mention sounds from " + targetName + ".");
            } else if (action.equals("unblock")) {
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player must be online to unblock via name.");
                    return true;
                }
                UUID targetUuid = target.getUniqueId();
                Set<UUID> blocked = blockedUsers.get(playerUuid);
                if (blocked != null && blocked.remove(targetUuid)) {
                    player.sendMessage(ChatColor.GREEN + "You have unblocked mention sounds from " + targetName + ".");
                } else {
                    player.sendMessage(ChatColor.YELLOW + targetName + " was not blocked.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /mention <block|unblock> <player>");
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("mention")) {
            if (args.length == 1) {
                return Arrays.asList("block", "unblock");
            } else if (args.length == 2) {
                List<String> players = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(p.getName());
                }
                return players;
            }
        }
        return Collections.emptyList();
    }
}
