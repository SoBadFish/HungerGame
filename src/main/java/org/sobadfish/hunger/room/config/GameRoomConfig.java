package org.sobadfish.hunger.room.config;

import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import org.sobadfish.hunger.manager.TotalManager;
import org.sobadfish.hunger.player.team.config.TeamConfig;
import org.sobadfish.hunger.player.team.config.TeamInfoConfig;
import org.sobadfish.hunger.room.floattext.FloatTextInfoConfig;
import org.sobadfish.hunger.tools.Utils;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 房间的基本配置，可以根据自身的需求修改
 * @author Sobadfish
 * @date 2022/9/9
 */
public class GameRoomConfig {

    /**
     * 房间名
     * */
    public String name;

    /**
     * 地图配置
     * */
    public WorldInfoConfig worldInfo;

    /**
     * 游戏时长
     * */
    public int time;
    /**
     * 等待时长
     * */
    public int waitTime;

    /**
     * 满人等待时长
     * */
    private int maxWaitTime;
    /**
     * 是否掉落物品
     * */
    public boolean deathDrop = true;

    /**
     * 最低人数
     * */
    public int minPlayerSize;

    /**
     * 最大人数
     * */
    private int maxPlayerSize;
    /**
     * 概率
     * */
    private int round = 10;


    //自动进入下一局
    public boolean isAutomaticNextRound;

    /**
     * 队伍数据信息
     * */
    public LinkedHashMap<String, TeamConfig> teamCfg = new LinkedHashMap<>();

    /**
     * 队伍
     * */
    public ArrayList<TeamInfoConfig> teamConfigs;


    /**
     * 是否允许旁观
     * */
    public boolean hasWatch = true;


    /**
     * 等待大厅拉回坐标
     * */
    public int callbackY = 17;

    /**
     * 游戏浮空字
     * */
    public List<FloatTextInfoConfig> floatTextInfoConfigs = new CopyOnWriteArrayList<>();
    /**
     * 禁用指令
     * */
    public ArrayList<String> banCommand = new ArrayList<>();

    /**
     * 边界
     * */
    public ArrayList<String> border = new ArrayList<>();

    /**
     * 退出房间执行指令
     * */
    public ArrayList<String> quitRoomCommand = new ArrayList<>();


    /**
     * 玩家胜利执行命令
     * */
    public ArrayList<String> victoryCommand = new ArrayList<>();

    /**
     * 玩家失败执行命令
     * */
    public ArrayList<String> defeatCommand = new ArrayList<>();

    /**
     * 游戏开始的一些介绍
     * */
    public ArrayList<String> gameStartMessage = new ArrayList<>();

    /**
     * 箱子物品
     * */
    public Map<String,ItemConfig> items = new LinkedHashMap<>();

    public int noDamage = 60;


    private GameRoomConfig(String name,
                           WorldInfoConfig worldInfo,
                           int time,
                           int waitTime,
                           int maxWaitTime,
                           int minPlayerSize,
                           int maxPlayerSize,
                           ArrayList<TeamInfoConfig> teamConfigs){
        this.name = name;
        this.worldInfo = worldInfo;
        this.time = time;
        this.waitTime = waitTime;
        this.maxWaitTime = maxWaitTime;
        this.minPlayerSize = minPlayerSize;
        this.maxPlayerSize = maxPlayerSize;
        this.teamConfigs = teamConfigs;

    }


    public LinkedHashMap<String, TeamConfig> getTeamCfg() {
        return teamCfg;
    }

    public ArrayList<TeamInfoConfig> getTeamConfigs() {
        return teamConfigs;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public boolean isDeathDrop() {
        return deathDrop;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayerSize() {
        return maxPlayerSize;
    }

    public void setWorldInfo(WorldInfoConfig worldInfo) {
        this.worldInfo = worldInfo;
    }

    public void setTeamConfigs(ArrayList<TeamInfoConfig> teamConfigs) {
        this.teamConfigs = teamConfigs;
    }

    public WorldInfoConfig getWorldInfo() {
        return worldInfo;
    }

    public int getRound() {
        return round;
    }

    public void setTeamCfg(LinkedHashMap<String, TeamConfig> teamCfg) {
        this.teamCfg = teamCfg;
    }

    public static GameRoomConfig getGameRoomConfigByFile(String name, File file) {
        //TODO 构建房间配置逻辑
        if(file.isDirectory()) {
            try {

                Config team = new Config(file + "/team.yml", Config.YAML);
                LinkedHashMap<String, TeamConfig> teamConfigs = new LinkedHashMap<>();
                for (Map<?, ?> map : team.getMapList("team")) {
                    TeamConfig teamConfig = TeamConfig.getInstance(map);
                    teamConfigs.put(teamConfig.getName(), teamConfig);
                }
                if(!new File(file+"/room.yml").exists()){
                    TotalManager.sendMessageToConsole("&e检测到未完成房间模板");
                    Utils.toDelete(file);
                    TotalManager.sendMessageToConsole("&a成功清除未完成的房间模板");
                    return null;
                }
                if(!new File(file+"/items.yml").exists()){
                    TotalManager.saveResource("items.yml","/rooms/"+name+"/items.yml",false);
                }
                Config item = new Config(file + "/items.yml", Config.YAML);
                List<Map> strings = item.getMapList("chests");
                Map<String,ItemConfig> buildItem = buildItem(strings);
                Config room = new Config(file+"/room.yml",Config.YAML);
                WorldInfoConfig worldInfoConfig = WorldInfoConfig.getInstance(name,room);
                if(worldInfoConfig == null){
                    TotalManager.sendMessageToConsole("&c未成功加载 &a"+name+"&c 的游戏地图");
                    return null;
                }

                int time = room.getInt("gameTime");
                int waitTime = room.getInt("waitTime");
                int maxWaitTime = room.getInt("max-player-waitTime");
                int minPlayerSize = room.getInt("minPlayerSize");
                int maxPlayerSize =  room.getInt("maxPlayerSize");
                ArrayList<TeamInfoConfig> teamInfoConfigs = new ArrayList<>();
                for(Map<?,?> map: room.getMapList("teamSpawn")){
                    teamInfoConfigs.add(TeamInfoConfig.getInfoByMap(
                            teamConfigs.get(map.get("name").toString()),map));
                }
                GameRoomConfig roomConfig = new GameRoomConfig(name,worldInfoConfig,time,waitTime,maxWaitTime,minPlayerSize,maxPlayerSize,teamInfoConfigs);
                roomConfig.hasWatch = room.getBoolean("hasWatch",true);

                roomConfig.banCommand = new ArrayList<>(room.getStringList("ban-command"));
                roomConfig.isAutomaticNextRound = room.getBoolean("AutomaticNextRound",true);
                roomConfig.quitRoomCommand = new ArrayList<>(room.getStringList("QuitRoom"));
                roomConfig.victoryCommand = new ArrayList<>(room.getStringList("victoryCmd"));
                roomConfig.defeatCommand = new ArrayList<>(room.getStringList("defeatCmd"));
                roomConfig.deathDrop = room.getBoolean("deathDrop",true);
                roomConfig.border = new ArrayList<>(room.getStringList("border"));
                roomConfig.items = buildItem;

                roomConfig.round = room.getInt("round",10);
                roomConfig.noDamage = room.getInt("noDamage",60);
                List<FloatTextInfoConfig> configs = new ArrayList<>();
                if(room.exists("floatSpawnPos")){
                    for(Map<?,?> map: room.getMapList("floatSpawnPos")){
                        FloatTextInfoConfig config = FloatTextInfoConfig.build(map);
                        if(config != null){
                            configs.add(config);
                        }
                    }
                    roomConfig.floatTextInfoConfigs = configs;
                }
                if(room.exists("roomStartMessage")){
                    roomConfig.gameStartMessage = new ArrayList<>(room.getStringList("roomStartMessage"));
                }else{
                    roomConfig.gameStartMessage = defaultGameStartMessage();
                }
                return roomConfig;

            }catch (Exception e){
                TotalManager.sendMessageToConsole("加载房间出错: "+e.getMessage());

                return null;

            }
        }

       return null;

    }

    private static Map<String,ItemConfig> buildItem(List<Map> itemList){
        LinkedHashMap<String,ItemConfig> configLinkedHashMap = new LinkedHashMap<>();
        for(Map map: itemList){
            if(map.containsKey("block")) {
                String block = map.get("block").toString().split(":")[0];

                List<Item> items = new ArrayList<>();
                String name = "未命名";
                if(map.containsKey("items")) {
                    List<?> list = (List<?>) map.get("items");
                    for (Object s : list) {
                        items.addAll(stringToItemList(s.toString()));
                    }
                    Collections.shuffle(items);
                }
                if(map.containsKey("name")){
                    name = map.get("name").toString();
                }
                TotalManager.sendMessageToConsole("&e物品读取完成 &7("+block+")&r》"+items.size()+"《");
                configLinkedHashMap.put(block,new ItemConfig(block,name,items));
            }
        }
        TotalManager.sendMessageToConsole("&a物品加载完成: &r》"+configLinkedHashMap.size()+"《");
        return configLinkedHashMap;

    }

    private static List<Item> stringToItemList(String str){
        ArrayList<Item> items = new ArrayList<>();
        String[] sl = str.split("-");
        if(sl.length > 1){
            for(int i = 0;i < Integer.parseInt(sl[1]);i++){
                items.add(stringToItem(sl[0]));
            }
        }else{
            items.add(stringToItem(sl[0]));
        }
        return items;
    }

    private static Item stringToItem(String s){
        String[] sList = s.split(":");
        Item item;
        try {
            item = Item.get(Integer.parseInt(sList[0]));
        }catch (Exception e){
            item = Item.fromString(sList[0].replace(".",":"));
        }
        if(sList.length > 1){
            item.setDamage(Integer.parseInt(sList[1]));
            if(sList.length > 2){
                item.setCount(Integer.parseInt(sList[2]));
            }else{
                item.setCount(1);
            }
        }
        return item;

    }



    public boolean notHasFloatText(String name){
        for(FloatTextInfoConfig config: floatTextInfoConfigs){
            if(config.name.equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }

    public void removeFloatText(String name){
        floatTextInfoConfigs.removeIf(config -> config.name.equalsIgnoreCase(name));
    }

    public static ArrayList<String> defaultGameStartMessage(){
        ArrayList<String> strings = new ArrayList<>();

        strings.add("&a■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        strings.add("&f小游戏");
        strings.add("&e");
        strings.add("&e小游戏介绍");
        strings.add("&e");
        strings.add("&a■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        return strings;
    }

    public void save(){
        //TODO 保存配置逻辑
        Config config = new Config(TotalManager.getDataFolder()+"/rooms/"+getName()+"/room.yml",Config.YAML);
        config.set("world",worldInfo.getLevel());
        config.set("gameTime",time);

        config.set("callbackY",callbackY);
        config.set("waitTime",waitTime);
        config.set("max-player-waitTime",maxWaitTime);
        config.set("minPlayerSize",minPlayerSize);
        config.set("maxPlayerSize",maxPlayerSize);
        ArrayList<LinkedHashMap<String, Object>> teamSpawn = new ArrayList<>();
        for(TeamInfoConfig infoConfig: teamConfigs) {
            teamSpawn.add(infoConfig.save());
        }
        config.set("teamSpawn",teamSpawn);

        config.set("waitPosition",WorldInfoConfig.positionToString(worldInfo.getWaitPosition()));
        config.set("ban-command",banCommand);
        config.set("QuitRoom",quitRoomCommand);
        config.set("hasWatch", hasWatch);
        config.set("AutomaticNextRound",isAutomaticNextRound);
        config.set("defeatCmd",defeatCommand);
        config.set("deathDrop", deathDrop);
        config.set("victoryCmd",victoryCommand);
        config.set("round",round);
        config.set("noDamage",noDamage);
        config.set("roomStartMessage",gameStartMessage);
        List<Map<String,Object>> pos = new ArrayList<>();
        for(FloatTextInfoConfig floatTextInfoConfig: floatTextInfoConfigs){
            pos.add(floatTextInfoConfig.toConfig());
        }
        config.set("floatSpawnPos",pos);
        config.save();

    }

    @Override
    public GameRoomConfig clone() {
        try {
            return (GameRoomConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GameRoomConfig){
            return name.equalsIgnoreCase(((GameRoomConfig) obj).name);
        }
        return false;
    }

    public static GameRoomConfig createGameRoom(String name,int size,int maxSize){
        GameRoomConfig roomConfig = new GameRoomConfig(name,null,300,120,20,size,maxSize,new ArrayList<>());
        TotalManager.saveResource("team.yml","/rooms/"+name+"/team.yml",false);
        TotalManager.saveResource("items.yml","/rooms/"+name+"/items.yml",false);
        loadTeamConfig(roomConfig);
        return roomConfig;

    }

    public static void loadTeamConfig(GameRoomConfig roomConfig) {
        Config team = new Config(TotalManager.getDataFolder() + "/rooms/" + roomConfig.name + "/team.yml", Config.YAML);
        LinkedHashMap<String, TeamConfig> teamConfigs = new LinkedHashMap<>();
        for (Map<?, ?> map : team.getMapList("team")) {
            TeamConfig teamConfig = TeamConfig.getInstance(map);
            teamConfigs.put(teamConfig.getName(), teamConfig);
        }
        roomConfig.setTeamCfg(teamConfigs);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
