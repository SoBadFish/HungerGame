package org.sobadfish.hunger.room.config;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

import java.util.List;

/**
 * @author Sobadfish
 * @date 2022/9/23
 */
public class ItemConfig {

    public Block block;

    public String name;

    public List<Item> items;

    public ItemConfig(Block block, String name, List<Item> items){
        this.block = block;
        this.name = name;
        this.items = items;
    }


}
