# SimpleMention

SimpleMention is a professional, high-performance Minecraft plugin designed for modern server environments including **Paper, Leaf, and Folia** (1.21.1+). It enhances player interaction by providing smart chat mentions with visual highlighting and sound notifications.

##  Features

- **Smart Highlighting**: Automatically detects player names in chat and applies professional formatting:
  - **Color**: Gold (`§6`)
  - **Style**: Underlined (`§n`)
  - **Symbol**: Prefixed with `@`
- **Case-Insensitive Matching**: Mention players regardless of how you type their name (e.g., typing `yoursenior` will correctly highlight `YourSenior`).
- **Official Auto-Correction**: Always displays the mentioned player's official in-game capitalization in chat, no matter how it was typed by the sender.
- **Word Boundary Protection**: Prevents accidental pings. It only triggers on exact name matches, so typing `YourSeniorn` will not ping `YourSenior`.
- **High-Quality Notifications**: Plays the distinct `ENTITY_EXPERIENCE_ORB_PICKUP` sound to mentioned players.
- **Privacy & Control**:
  - **Global Toggle**: Players can disable all mention sounds for themselves.
  - **Block List**: Players can block specific users from ever triggering a ping sound for them.
- **Folia Optimized**: Built using thread-safe scheduling to work perfectly on regionalized/multi-threaded servers.

##  Commands & Usage

| Command | Usage | Description |
|---------|-------|-------------|
| `/togglemention` | `/togglemention` | Enable or disable all mention sound notifications for yourself. |
| `/mention block` | `/mention block <player>` | Stop hearing the ping sound when a specific player mentions you. |
| `/mention unblock` | `/mention unblock <player>` | Resume hearing mention sounds from a previously blocked player. |

##  Technical Details

- **Supported Versions**: 1.21, 1.21.XX, and higher.
- **Supported Platforms**: Paper, Folia, Leaf, Spigot, Bukkit.
- **Java Requirement**: Java 17+ (Compiled for maximum compatibility).
- **API**: Built using the Paper/Spigot API for high performance.
- **Lightweight**: Zero external dependencies (shaded YAML/GSON), extremely low CPU and RAM usage.

##  Installation

1. Download the `simple-mention-1.0.0.jar`.
2. Drop it into your server's `plugins/` directory.
3. Restart your server or use a plugin loader.
4. No configuration is required out of the box!

---
*Powered by senior - Enhance your server's chat experience.*
