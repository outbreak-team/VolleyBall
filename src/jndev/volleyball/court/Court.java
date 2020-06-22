package jndev.volleyball.court;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import jndev.volleyball.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * volleyball court with customizable ball stats and court size
 */
public class Court {
    
    /**
     * all loaded courts
     */
    private static final HashMap<String, Court> courts = new HashMap<>();
    
    /**
     * name of the court
     */
    private String name;
    
    /**
     * whether the court is enabled
     */
    private boolean enabled;
    
    /**
     * whether the court has animation enabled
     */
    private boolean animations;
    
    /**
     * player head texture for the ball
     */
    private String texture;
    
    /**
     * world the court is in
     */
    private World world;
    
    /**
     * speed modifier of ball
     */
    private double speed;
    
    /**
     * court bounds
     */
    private double[][] bounds;
    
    /**
     * net bounds
     */
    private double[][] net;
    
    /**
     * create a court with default values with the specified name
     *
     * @param name court name
     */
    public Court(String name) {
        this.name = name;
        enabled = false;
        animations = true;
        speed = 1;
        texture = "http://textures.minecraft.net/texture/9b2513c8d08c60ad3785d3a9a651b7329c5f26937aca2fc8dfaf3441c9bd9da2";
        world = null;
        bounds = new double[2][3];
        net = new double[2][3];
        courts.put(name, this);
    }
    
    /**
     * get a court by name
     *
     * @param name name of court
     * @return court if it exists
     */
    public static Court getCourt(String name) {
        if (courts.containsKey(name))
            return courts.get(name);
        return null;
    }
    
    /**
     * get the hashmap of all the courts that are loaded
     *
     * @return hashmap of courts
     */
    public static HashMap<String, Court> getCourts() {
        return courts;
    }
    
    /**
     * get the name of this court
     *
     * @return name of court
     */
    public String getName() {
        return name;
    }
    
    /**
     * set the name of this court
     *
     * @param name new name
     */
    public void setName(String name) {
        remove();
        this.name = name;
        courts.put(name, this);
    }
    
    /**
     * check if a court is enabled
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * enable or disable a court
     *
     * @param enabled true to enable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * check if a court has animations enabled
     *
     * @return true if animations are enabled
     */
    public boolean hasAnimations() {
        return animations;
    }
    
    /**
     * enable or disable animations for a court
     *
     * @param animations true to enable
     */
    public void setAnimations(boolean animations) {
        this.animations = animations;
    }
    
    /**
     * get the url for the ball texture
     *
     * @return string url of ball texture
     */
    public String getTexture() {
        return texture;
    }
    
    /**
     * set the ball texture for this court
     *
     * @param texture string url of ball texture
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }
    
    /**
     * get the world the court is in
     *
     * @return world the court is in
     */
    public World getWorld() {
        return world;
    }
    
    /**
     * get the speed modifier for the court's ball
     *
     * @return speed of ball
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * set the speed modifier for the court's ball
     *
     * @param speed speed modifier
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * get the court bounds
     *
     * @return 2D array bounds
     */
    public double[][] getBounds() {
        return bounds;
    }
    
    /**
     * set the court bounds
     *
     * @param bounds 2D array bounds
     */
    public void setBounds(double[][] bounds) {
        this.bounds = bounds;
    }
    
    /**
     * set court bounds using worldedit
     *
     * @param player worldedit player
     */
    public void setBounds(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }
        
        if (selection != null) {
            bounds[0][0] = selection.getMinimumPoint().getX();
            bounds[0][1] = selection.getMinimumPoint().getY();
            bounds[0][2] = selection.getMinimumPoint().getZ();
            bounds[1][0] = selection.getMaximumPoint().getX();
            bounds[1][1] = selection.getMaximumPoint().getY();
            bounds[1][2] = selection.getMaximumPoint().getZ();
            world = BukkitAdapter.adapt(selection.getWorld());
            
            
            player.sendMessage(Message.SUCCESS_SET_COURT_BOUNDS.replace("$COURT$", name));
            
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }
    
    /**
     * get net bounds for court
     *
     * @return 2D array net bounds
     */
    public double[][] getNet() {
        return net;
    }
    
    /**
     * set the court net bounds
     *
     * @param net 2D array net bounds
     */
    public void setNet(double[][] net) {
        this.net = net;
    }
    
    /**
     * set the net bounds with worldedit
     *
     * @param player worldedit player
     */
    public void setNet(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        } catch (Exception e) {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
            return;
        }
        
        if (selection != null) {
            net[0][0] = selection.getMinimumPoint().getX();
            net[0][1] = selection.getMinimumPoint().getY();
            net[0][2] = selection.getMinimumPoint().getZ();
            net[1][0] = selection.getMaximumPoint().getX();
            net[1][1] = selection.getMaximumPoint().getY();
            net[1][2] = selection.getMaximumPoint().getZ();
            
            
            player.sendMessage(Message.SUCCESS_SET_NET_BOUNDS.replace("$COURT$", name));
            
        } else {
            player.sendMessage(Message.ERROR_NULL_BOUNDS);
        }
    }
    
    /**
     * remove a court
     */
    public void remove() {
        courts.remove(name);
    }
    
    /**
     * get the court in a decorated string format for chat
     *
     * @return court as a colored string
     */
    @Override
    public String toString() {
        return Message.COURT_INFO
                .replace("$NAME$", name)
                .replace("$ENABLED$", Boolean.toString(isEnabled()))
                .replace("$ANIMATIONS$", Boolean.toString(hasAnimations()))
                .replace("$SPEED$", Double.toString(getSpeed()))
                .replace("$TEXTURE$", getTexture())
                .replace("$BOUNDS$",
                        "(" + bounds[0][0] + "," + bounds[0][1] + "," + bounds[0][2] + ") to (" +
                                bounds[1][0] + "," + bounds[1][1] + "," + bounds[1][2] + ")")
                .replace("$NET$",
                        "(" + net[0][0] + "," + net[0][1] + "," + net[0][2] + ") to (" +
                                net[1][0] + "," + net[1][1] + "," + net[1][2] + ")")
                .replace("$WORLD$", world.getName());
        
    }
}