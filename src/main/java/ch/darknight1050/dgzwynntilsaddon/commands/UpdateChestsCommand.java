package ch.darknight1050.dgzwynntilsaddon.commands;

import ch.darknight1050.dgzwynntilsaddon.ModCore;
import ch.darknight1050.dgzwynntilsaddon.utils.WebUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class UpdateChestsCommand extends CommandBase implements IClientCommand {

    @Override
    public String getName() {
        return "updatechests";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Updates Chests";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("force")) {
                WebUtils.UploadWaypoints();
                sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Uploaded your Chests!"));
                return;
            }
        }
        int count = WebUtils.DownloadWaypoints();
        WebUtils.UploadWaypoints();
        sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "Updated your Chests! " + TextFormatting.YELLOW
                + "[" + count + "/" + ModCore.GetWaypointsCount() + "]"));
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

}
