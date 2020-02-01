package ch.darknight1050.dgzwynntilsaddon.commands;

import java.lang.reflect.Field;

import com.wynntils.modules.map.MapModule;
import com.wynntils.modules.map.configs.MapConfig;
import com.wynntils.modules.map.instances.WaypointProfile.WaypointType;

import ch.darknight1050.dgzwynntilsaddon.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class FixChestsCommand extends CommandBase implements IClientCommand {

    @Override
    public String getName() {
        return "fixchests";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Fixes your Chests";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        MapConfig.Waypoints.INSTANCE.waypoints.stream().forEach(w -> {
            if (w.getType().equals(WaypointType.LOOTCHEST_T1) || w.getType().equals(WaypointType.LOOTCHEST_T2)
                    || w.getType().equals(WaypointType.LOOTCHEST_T3) || w.getType().equals(WaypointType.LOOTCHEST_T4)) {
                w.setGroup(w.getType());
                try {
                    Field zoomNeeded = w.getClass().getDeclaredField("zoomNeeded");
                    zoomNeeded.setAccessible(true);
                    zoomNeeded.setInt(w, (w.getType().equals(WaypointType.LOOTCHEST_T3) || w.getType().equals(WaypointType.LOOTCHEST_T4)) ? -1000 : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Reference.LOGGER.info("zoom: " + w.getZoomNeeded());
            }
        });
        MapConfig.Waypoints.INSTANCE.saveSettings(MapModule.getModule());
        sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Fixed your Chests!"));
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

}
