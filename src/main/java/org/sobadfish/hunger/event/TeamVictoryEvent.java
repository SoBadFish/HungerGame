package org.sobadfish.hunger.event;

import cn.nukkit.plugin.Plugin;
import org.sobadfish.hunger.player.PlayerInfo;
import org.sobadfish.hunger.room.GameRoom;


/**
 * 胜利事件
 * @author SoBadFish
 * 2022/1/15
 */
public class TeamVictoryEvent extends GameRoomEvent{

    private final PlayerInfo teamInfo;

    public TeamVictoryEvent(PlayerInfo teamInfo, GameRoom room, Plugin plugin) {
        super(room, plugin);
        this.teamInfo = teamInfo;
    }

    public PlayerInfo getTeamInfo() {
        return teamInfo;
    }
}
