package ch.darknight1050.dgzwynntilsaddon.commands;

import java.util.List;

import com.wynntils.modules.map.MapModule;
import com.wynntils.modules.map.configs.MapConfig;
import com.wynntils.modules.map.instances.WaypointProfile;

import ch.darknight1050.dgzwynntilsaddon.ModCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class FixDuplicatesCommand extends CommandBase implements IClientCommand {

    @Override
    public String getName() {
        return "fixduplicates";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Fixes your duplicated Chests";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            try {
                int count = 0;
                int radius = Integer.valueOf(args[0]);
                List<WaypointProfile> waypoints = MapConfig.Waypoints.INSTANCE.waypoints;
                for (int i = 0; i < waypoints.size(); i++) {
                    WaypointProfile waypoint1 = waypoints.get(i);
                    if (ModCore.IsLootChest(waypoint1)) {
                        for (int j = i + 1; j < waypoints.size(); j++) {
                            WaypointProfile waypoint2 = waypoints.get(j);
                            if (ModCore.IsLootChest(waypoint2)) {
                                double diffX = waypoint2.getX() - waypoint1.getX();
                                double diffY = waypoint2.getY() - waypoint1.getY();
                                double diffZ = waypoint2.getZ() - waypoint1.getZ();
                                if (diffX * diffX + diffY * diffY + diffZ * diffZ < radius * radius) {
                                    MapConfig.Waypoints.INSTANCE.waypoints.remove(i);
                                    count++;
                                }
                            }
                        }
                    }
                }
                MapConfig.Waypoints.INSTANCE.saveSettings(MapModule.getModule());
                sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Fixed " + TextFormatting.YELLOW + "["
                        + count + "/" + ModCore.GetWaypointsCount() + "]" + TextFormatting.GOLD
                        + " duplicated Chests!"));
                return;
            } catch (Exception e) {
            }
        }
        sender.sendMessage(new TextComponentString(
                TextFormatting.RED + "Set a radius: " + TextFormatting.GOLD + "/fixduplicates [radius]"));
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

}
