package org.sobadfish.hunger.event;

import cn.nukkit.plugin.Plugin;
import org.sobadfish.hunger.player.PlayerInfo;
import org.sobadfish.hunger.room.GameRoom;


/**
 * 玩家在游戏中死亡的事件
 * @author SoBadFish
 * 2022/1/15
 */
public class PlayerGameDeathEvent extends PlayerRoomInfoEvent{

    public PlayerGameDeathEvent(PlayerInfo playerInfo, GameRoom room, Plugin plugin) {
        super(playerInfo,room, plugin);
    }
}
