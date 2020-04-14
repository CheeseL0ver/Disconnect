# Disconnect

Disconnect is a 1.12.2 [Minecraft](https://minecraft.net/) mod that allows in-game players to text with members in a [Discord](https://discordapp.com/) channel and vice versa.

## Prerequisites

You will need to create a Discord application in the Discord developer portal, create a bot user and give it permission to use your Discord channel. For more information on this check [this](https://canary.discordapp.com/developers/docs/intro) article.

## Configuration

This mod will need to be installed in both the client and server mod folders. Once the mod loads it will generate a `disconnect.cfg` config file. This file contains three fields:

- `bot_secret` - The "_secret_" of the Discord bot you created.
- `channel_id` - The channel ID of the Discord channel the mod will interface with.
- `token` - The "_token_" of the Discord bot you created.

Set the fields using the syntax `field=value`. DO NOT surround the value with quotes as it will not work.

## Licensing

- Source code Copyright &copy; 2020 Shandon Anderson.

  ![GPL3](https://www.gnu.org/graphics/lgplv3-147x51.png)

  This program is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses>.

## Credits

Special thanks to [Mcjty](https://wiki.mcjty.eu/modding/index.php?title=Main_Page)'s tutorials for how to get started developing with forge and John Engelman for the [Gradle Shadow Plugin](https://imperceptiblethoughts.com/shadow/).
