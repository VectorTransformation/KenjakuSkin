package kenjakuSkin.command;

import kenjakuSkin.data.CustomData;
import kenjakuSkin.skin.CustomSkin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomCommand implements TabExecutor {
    @Override
    public List<String> onTabComplete(
        CommandSender sender,
        Command command,
        String label,
        String[] args
    ) {
        switch (args.length) {
            case 1 -> {
                return commands();
            }
            case 2 -> {
                switch (args[0]) {
                    case "remove" -> {
                        return CustomData.skinList();
                    }
                    case "reset", "save", "set" -> {
                        return onlinePlayersName();
                    }
                }
            }
            case 3 -> {
                switch (args[0]) {
                    case "save" -> {
                        return List.of("tmp");
                    }
                    case "set" -> {
                        return CustomData.skinList();
                    }
                }
            }
        }
        return List.of();
    }

    @Override
    public boolean onCommand(
        CommandSender sender,
        Command command,
        String label,
        String[] args
    ) {
        switch (args.length) {
            case 2 -> {
                switch (args[0]) {
                    case "remove" -> remove(args[1]);
                    case "reset" -> reset(args[1]);
                    case "save" -> save(args[1], "tmp");
                }
            }
            case 3 -> {
                switch (args[0]) {
                    case "save" -> save(args[1], args[2]);
                    case "set" -> set(args[1], args[2]);
                }
            }
        }
        return true;
    }

    public List<String> commands() {
        return List.of(
            "remove",
            "reset",
            "save",
            "set"
        );
    }

    public List<String> onlinePlayersName() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }

    public void remove(String name) {
        if (CustomData.skinList().contains(name)) {
            CustomData.removeYml(name);
        }
    }

    public void reset(String playerName) {
        if (onlinePlayersName().contains(playerName)) {
            CustomSkin.reset(Bukkit.getPlayer(playerName));
        }
    }

    public void save(String playerName, String name) {
        if (onlinePlayersName().contains(playerName) && !name.contains(".")) {
            CustomData.createYml("skin", name, CustomSkin.profileSkinPair(Bukkit.getPlayer(playerName)));
        }
    }

    public void set(String playerName, String name) {
        if (onlinePlayersName().contains(playerName) && CustomData.skinList().contains(name)) {
            CustomSkin.set(Bukkit.getPlayer(playerName), name);
        }
    }
}
