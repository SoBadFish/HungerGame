package org.sobadfish.hunger.event;

import cn.nukkit.plugin.Plugin;
import org.sobadfish.hunger.player.PlayerInfo;
import org.sobadfish.hunger.room.GameRoom;

import java.util.List;


/**
 * 玩家失败事件
 * @author SoBadFish
 * 2022/5/24
 */
public class TeamDefeatEvent extends GameRoomEvent {

    private final List<PlayerInfo> teamInfo;

    public TeamDefeatEvent(List<PlayerInfo> teamInfo, GameRoom room, Plugin plugin) {
        super(room, plugin);
        this.teamInfo = teamInfo;
    }

    public List<PlayerInfo> getTeamInfo() {
        return teamInfo;
    }
}
