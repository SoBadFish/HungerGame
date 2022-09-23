package org.sobadfish.hunger.room.world;


import cn.nukkit.block.Block;
import cn.nukkit.inventory.PlayerEnderChestInventory;
import cn.nukkit.level.Position;
import org.sobadfish.hunger.room.GameRoom;
import org.sobadfish.hunger.room.config.WorldInfoConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 地图的实例化方法，当房间启动后，这个方法也随之启动
 * @author Sobadfish
 * @date 2022/9/9
 */
public class WorldInfo {

    private GameRoom room;


    private boolean isClose;

    public boolean isStart;

    private WorldInfoConfig config;

    public List<Block> placeBlock = new ArrayList<>();

    public List<Position> clickChest = new ArrayList<>();

    public Map<Position, PlayerEnderChestInventory> clickEnder = new LinkedHashMap<>();

    public WorldInfo(GameRoom room,WorldInfoConfig config){
        this.config = config;
        this.room = room;

    }

    public WorldInfoConfig getConfig() {
        return config;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public boolean onChangeBlock(Block block,boolean isPlace){

        if(isPlace){
            placeBlock.add(block);
        }else{
            placeBlock.remove(block);
        }
        return true;
    }

    public void onUpdate() {
        //TODO 地图更新 每秒更新一次 可实现一些定制化功能


        ///////////////////DO Something////////////
    }

    public List<Block> getPlaceBlock() {
        return placeBlock;
    }
}
