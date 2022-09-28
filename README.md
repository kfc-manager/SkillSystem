# SkillSystem

## Features

This plugin adds a skill system to the server. Every skill can be purchased with coins, which you will receive after killing a mob. With the command **/skills** you can open a gui in which you can overview all skills, purchase or enable/disable them. Each skill applys a permanent potion effect to the player. You can also make changes to the plugin with the configuration file **settings.yml**, which is used to make balance changes to the plugin (how much coins are dropped by a mob and how much a skill costs).

## Installation

In the project folder "SkillSystem/target/" is a compiled .jar file, which you can simply drag in your plugins folder of your server. You can also directly download the .jar with this link: https://github.com/kfc-manager/SkillSystem/raw/main/target/SkillSystem-1.0.jar . Reload the server and the console should say: "SkillSystem has been enabled!". Also in your plugins folder should be a "SkillSystem" folder be generated, which holds two .yml files. It is recommended that you do not make changes to the playerdata.yml file. However sttings.yml serve a configuration purpose for the plugin (but it does not need to be configured, it has default settings). Follow the **Configuration** instructions below, if you want to make changes to the plugin.

## Configuration

NOTE: Make sure to reload the server after making changes to the configuration files.

**settings.yml**

In this file you can decide how much coins a mob drops when it gets killed by a player and how much a skill costs to purchase. Make sure that you put only integers and no negative numbers. If an entry could not be properly read the drop/costs will be 0.
