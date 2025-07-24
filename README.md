[download]: https://img.shields.io/github/downloads/HydrolienF/OPItemsRemover/total
[downloadLink]: https://hangar.papermc.io/Hydrolien/OPItemsRemover
[discord-shield]: https://img.shields.io/discord/728592434577014825?label=discord
[discord-invite]: https://discord.gg/RPNbtRSFqG

[ ![download][] ][downloadLink]
[ ![discord-shield][] ][discord-invite]

[**Discord**](discord-invite) | [**Hangar**](https://hangar.papermc.io/Hydrolien/OPItemsRemover) | [**Modrinth**](https://modrinth.com/plugin/opitemsremover) | [**GitHub**](https://github.com/HydrolienF/OPItemsRemover)

# OPItemRemover
Minecraft plugin to disable some minecraft items.

Items can be disabled in the config, by there [Material](https://jd.papermc.io/paper/1.21.7/org/bukkit/Material.html). The config can be reload with `/opirc reload` to change the disabled item without restarting the server.

For example to disable wither head and witch egg:
`disabledItems: ["WITHER_SKELETON_SKULL", "WITCH_SPAWN_EGG"]`

Disabled item will be removed when player try to collect it by picking it up from the ground, or from any containers or when a player log on.

Support **Paper** forks including **Folia** for version 1.20 to latest. (See version compatibility in releases)
Older version than 1.20 won't be supported.

## Install
Download last version.
Place it in `plugins/` in your server files.
After 1st launch you can edit config in `plugins/OPItemRemover/config.yml`
You need to define a list of the item to disable in config.


## Test
To test plugin you need Java 21+ to compile & package: `./gradlew assemble`. Plugin file will be in `build/libs/`.
Then you need an 1.20 Minecraft server with PaperMc or a fork to run it.


## Statistics
[![bStats Graph Data](https://bstats.org/signatures/bukkit/OPItemRemover.svg)](https://bstats.org/plugin/bukkit/OPItemRemover/21798)
