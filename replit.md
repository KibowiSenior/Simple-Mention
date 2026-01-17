# Project Documentation

## Overview

This project is a high-performance Minecraft plugin called "SimpleMention" built for 1.21.1+ servers. It enhances chat by highlighting player names and playing notification sounds. It is fully compatible with Folia, Paper, and Leaf.

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Plugin Architecture
- Built using Java 17 with Spigot/Paper API 1.21.1
- Maven-based project structure
- Event-driven architecture using AsyncPlayerChatEvent
- Folia-safe thread scheduling for sound notifications

### Core Components
- Main class: `com.simplemention.SimpleMention`
- Command system: `/togglemention` and `/mention`
- Data structures: In-memory `Set` and `Map` for sound preferences and block lists

### Features
- Case-insensitive mention matching
- Word-boundary matching (prevents partial pings)
- Automatic casing correction to match official in-game names
- Global mention sound toggle
- Per-player block list for mention sounds
- High-performance, low-resource usage

## Development Tools
- Maven for builds
- Java 17/19/21 compatibility
- DALL-E for logo generation
