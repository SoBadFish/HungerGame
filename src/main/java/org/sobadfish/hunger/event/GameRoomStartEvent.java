package org.sobadfish.hunger.event;


import cn.nukkit.plugin.Plugin;
import org.sobadfish.hunger.room.GameRoom;

/**
 * 房间游戏开始事件
 * @author SoBadFish
 * 2022/1/15
 */
public class GameRoomStartEvent extends GameRoomEvent {

    public GameRoomStartEvent(GameRoom room, Plugin plugin) {
        super(room, plugin);
    }
}
