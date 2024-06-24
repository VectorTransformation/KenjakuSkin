package kenjakuSkin.skin;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import kenjakuSkin.data.CustomData;
import kenjakuSkin.pair.CustomPair;
import kenjakuSkin.system.KenjakuSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CustomSkin {
    public static CustomPair<String, String> randomProfileSkinPair() {
        return CustomSkin.profileSkinPair(randomProfile());
    }

    public static CustomPair<String, String> profileSkinPair(Player player) {
        return profileSkinPair(player.getPlayerProfile());
    }

    public static CustomPair<String, String> profileSkinPair(PlayerProfile playerProfile) {
        return playerProfile.getProperties().stream().map(
            profileProperty -> CustomPair.of(profileProperty.getValue(), profileProperty.getSignature())
        ).findFirst().orElseGet(CustomSkin::randomProfileSkinPair);
    }

    public static void reset(Player player) {
        if (CustomSkin.profileSkinPair(player).hashCode() != KenjakuSkin.skins.get(player).hashCode()) {
            set(player, KenjakuSkin.skins.get(player));
        }
    }

    public static void save(Player player) {
        saveSkin(player);
        saveYml(player);
    }

    public static void saveSkin(Player player) {
        KenjakuSkin.skins.put(player, profileSkinPair(player));
    }

    public static void saveYml(Player player) {
        CustomData.createLastYml(player);
    }

    public static void loadLastSkin(Player player) {
        KenjakuSkin.skins.put(player, CustomData.skinPair("last", CustomData.uuid(player)));
    }

    public static PlayerProfile randomProfile() {
        var playerProfile = Bukkit.createProfile(UUID.randomUUID());
        playerProfile.getProperties().add(new ProfileProperty("textures", "", ""));
        return playerProfile;
    }

    public static void set(Player player, String name) {
        var pair = CustomData.skinPair("skin", name);
        if (CustomSkin.profileSkinPair(player).hashCode() != pair.hashCode()) {
            set(player, pair);
        }
    }

    public static void set(Player player, CustomPair<String, String> pair) {
        var playerProfile = player.getPlayerProfile();
        playerProfile.getProperties().add(new ProfileProperty("textures", pair.left(), pair.right()));
        player.setPlayerProfile(playerProfile);
    }
}
