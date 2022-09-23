package org.sobadfish.hunger;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.hunger.command.HungerGameAdminCommand;
import org.sobadfish.hunger.command.HungerGameCommand;
import org.sobadfish.hunger.command.HungerGameSpeakCommand;
import org.sobadfish.hunger.manager.TotalManager;

/**
 * 饥饿游戏
 * @author Sobadfish
 * 13:07
 */
public class HungerGameMain extends PluginBase {


    @Override
    public void onEnable() {

        this.getLogger().info(TextFormat.colorize('&',"&e _  _                         ___                "));
        this.getLogger().info(TextFormat.colorize('&',"&e| || |_  _ _ _  __ _ ___ _ _ / __|__ _ _ __  ___ "));
        this.getLogger().info(TextFormat.colorize('&',"&e| __ | || | ' \\/ _` / -_) '_| (_ / _` | '  \\/ -_)"));
        this.getLogger().info(TextFormat.colorize('&',"&e|_||_|\\_,_|_||_\\__, \\___|_|  \\___\\__,_|_|_|_\\___|"));
        this.getLogger().info(TextFormat.colorize('&',"&e               |___/                             "));
        this.getLogger().info(TextFormat.colorize('&',"&e正在加载 HungerGame 插件 本版本为&av"+this.getDescription().getVersion()));
        this.getLogger().info(TextFormat.colorize('&',"&a插件加载完成，祝您使用愉快"));

        TotalManager.init(this);
        this.getServer().getCommandMap().register("hunger",new HungerGameAdminCommand("hga"));
        this.getServer().getCommandMap().register("hunger",new HungerGameCommand("hg"));
        this.getServer().getCommandMap().register("hunger",new HungerGameSpeakCommand("hgs"));

        this.getLogger().info(TextFormat.colorize('&',"&a插件加载完成，祝您使用愉快"));

    }

    @Override
    public void onDisable() {
       TotalManager.onDisable();
    }

}
