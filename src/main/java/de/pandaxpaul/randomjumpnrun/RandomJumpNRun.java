package de.pandaxpaul.randomjumpnrun;

import de.pandaxpaul.randomjumpnrun.coammands.JnRCommands;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public final class RandomJumpNRun extends JavaPlugin implements Listener {

    private static RandomJumpNRun plugin;
    private boolean endeverytickRunnable;

    private boolean runfirsttime = true;


    private static int newx;
    private static int newy;
    private static int newz;
    private static boolean start;

    private int oldBlockx;
    private double oldBlocky;
    private int oldBlockz;

    private static Player playertostart;

    public static void startJnR(boolean bool, Player player){
        start = bool;
        playertostart = player;
    }
    public static  int getnewx(){
        return(newx);
    }
    public static  int getnewy(){
        return(newy);
    }
    public static int getnewz(){
        return(newz);
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.BLUE + "Random Jump n Run active!");
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(this, this);
        getCommand("jnr").setExecutor(new JnRCommands());
        start = false;
        checkeverytick();
        this.plugin = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void everytick( int newx, int newy, int newz, Player player){

        final BukkitTask everytickRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Location playerLocation = player.getLocation();
                double xdouble = playerLocation.getX();
                double ydouble = playerLocation.getY();
                double zdouble = playerLocation.getZ();
                int x = (int) xdouble;
                int y = (int) ydouble;
                int z = (int) zdouble;

                if(y <= newy - 1.5 ){
                    Location teleportLocation = new Location(playerLocation.getWorld(), oldBlockx, oldBlocky + 1, oldBlockz);
                    player.teleport(teleportLocation);
                }

                if (x == newx && y >= newy &&  y <= newy + 1  && z == newz) {
                    //getLogger().info("x and z are the same!");
                    nextblock(player);
                    endeverytickRunnable = true;
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 0, 1); //every tick



    }

    public void checkeverytick(){

        final BukkitTask checkeverytick = new BukkitRunnable() {
            @Override
            public void run() {

                if(start == true){
                   /* Location playerLocation = playertostart.getLocation();
                    Location teleportLocation = new Location(playerLocation.getWorld(), (int) playerLocation.getX(), (int) playerLocation.getY() + 6, (int) playerLocation.getZ());
                    playertostart.teleport(teleportLocation);

                    int greenX = (int) playerLocation.getX();
                    int greenY = (int) playerLocation.getY()  + 5;
                    int greenZ = (int) playerLocation.getZ();


                    playertostart.getLocation().getWorld().getBlockAt( greenX, greenY, greenZ).setType(Material.GREEN_CONCRETE);
                    getLogger().info("spawned block at " + greenX +  " " + greenY + " " + greenZ); */
                    nextblock(playertostart);
                    start = false;

                }
            }

        }.runTaskTimer(this, 0, 1); //every tick



    }

    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        nextblock(player);
    }


    public void nextblock(Player player){
        this.plugin = this;


        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Location playerLocation = player.getLocation();
                double x = playerLocation.getX();
                double y = playerLocation.getY();
                double z = playerLocation.getZ();

                if(runfirsttime == true) {
                    oldBlockx = (int) x;
                    oldBlocky = y - 1;
                    oldBlockz = (int) z;
                    //getLogger().info("old Block cords: " + oldBlockx + " " + oldBlocky + " " + oldBlockz + " " +  runfirsttime);
                }
                Random random = new Random();
                int randomNumber = (int) (random.nextInt(9) - 4.5);
                newx = (int) (x + randomNumber);
                randomNumber = (int) (random.nextInt(2) -1);
                newy = (int) (y + randomNumber);

                if(runfirsttime == true) {
                    newy = (int) y;
                }

                randomNumber = (int) (random.nextInt(9) - 4.5);
                newz = (int) (z + randomNumber);

                if (x == newx && z == newz) {
                    nextblock(player);
                    player.getLocation().getWorld().getBlockAt(newx, newy, newz).setType(Material.AIR);

                }else {
                    player.getLocation().getWorld().getBlockAt(newx, newy, newz).setType(Material.BLUE_CONCRETE);
                    player.getLocation().getWorld().getBlockAt(oldBlockx, (int) oldBlocky, oldBlockz).setType(Material.AIR);
                    everytick(newx, newy, newz, player);
                   /* getLogger().info("player cords: " + x + " " + y + " " + z + " ");
                    getLogger().info("new Block cords: " + newx + " " + newy + " " + newz + " ");
                    getLogger().info("old Block cords: " + oldBlockx + " " + oldBlocky + " " + oldBlockz + " " +  runfirsttime);*/
                    if(runfirsttime == false) {
                        oldBlockx = (int) x;
                        oldBlocky = y - 1;
                        oldBlockz = (int) z;
                        //getLogger().info("old Block cords: " + oldBlockx + " " + oldBlocky + " " + oldBlockz + " " +  runfirsttime);
                    }

                    runfirsttime = false;


                }
            }
        }, 0L);
    }

}

