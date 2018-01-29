import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class EnchantmentCooldown extends BukkitRunnable {

  Player player;
  int duration;
  ArrayList listOfPlayers;
  
  public EnchantmentCooldown(Player player, int duration, ArrayList listOfPlayers){
      
    this.player = player;
    this.duration = duration * 20; // convert ticks to seconds
    this.listOfPlayers = listOfPlayers;
    

  } 

  @Override
  public void run(){
  
    
    duration--;
    
    if(duration <= 0){
      
      listOfPlayers.remove(player);
      this.cancel();
    }
  
  
  }
  
  




}
