package kenjakuSkin.data;

import kenjakuSkin.pair.CustomPair;
import kenjakuSkin.skin.CustomSkin;
import kenjakuSkin.system.KenjakuSkin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CustomData {
    public static String dataFolderPath = KenjakuSkin.plugin.getDataFolder().getPath();

    public static void loadAll() {
        load(Paths.get(dataFolderPath, "last"));
        load(Paths.get(dataFolderPath, "old"));
        load(Paths.get(dataFolderPath, "skin"));
    }

    public static void load(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void create(Path path) {
        if (!Files.exists(path)) {
            try {
                load(path.getParent());
                Files.createFile(path);
                info("create file : " + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void remove(Path path) {
        try {
            if (Files.deleteIfExists(path)) {
                info("remove file : " + path);
                var parent = path.getParent();
                var skin = Paths.get(dataFolderPath, "skin");
                if (!parent.toString().substring(skin.toString().length()).isEmpty() && skinList(parent).isEmpty()) {
                    remove(parent);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createLastYml(Player player) {
        createYml("last", uuid(player), KenjakuSkin.skins.get(player));
    }

    public static void createYml(String parent, String name, CustomPair<String, String> pair) {
        var path = Paths.get(dataFolderPath, parent, name + ".yml");
        var file = path.toFile();
        if (file.exists() && parent.equals("skin")) {
            createYml("old", name, skinPair("skin", name));
        }
        create(path);
        var yml = YamlConfiguration.loadConfiguration(file);
        yml.set("texture", pair.left());
        yml.set("signature", pair.right());
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeYml(String name) {
        remove(Paths.get(dataFolderPath, "skin", name + ".yml"));
    }

    public static List<String> skinList() {
        return skinList(Paths.get(dataFolderPath, "skin"));
    }

    public static List<String> skinList(Path path) {
        try {
            return Files.walk(path)
                .map(Path::toString)
                .filter(p -> p.endsWith(".yml"))
                .map(p -> p.substring(path.toString().length() + 1, p.length() - 4))
                .map(p -> p.replace("\\", "/"))
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomPair<String, String> skinPair(String parent, String name) {
        var path = Paths.get(dataFolderPath, parent, name + ".yml");
        if (Files.exists(path)) {
            var yml = YamlConfiguration.loadConfiguration(path.toFile());
            var texture = yml.getString("texture");
            var signature = yml.getString("signature");
            if (texture != null && signature != null) return CustomPair.of(texture, signature);
        }
        return CustomSkin.randomProfileSkinPair();
    }

    public static void info(String message) {
        KenjakuSkin.logger.info(message.replace("\\", "/"));
    }

    public static String uuid(Player player) {
        return player.getUniqueId().toString();
    }
}
