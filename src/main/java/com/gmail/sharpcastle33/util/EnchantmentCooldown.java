import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class EnchantmentCooldown extends BukkitRunnable {

  int taskID;
  int duration;
  
  public EnchantmentCooldown(int taskID, int duration){
      
    this.taskID = taskID;
    this.duration = duration * 20; // convert ticks to seconds
    
    

  } 

  @Override
  public void run(){
  
    
    duration--;
    
    if(duration <= 0){
      this.cancel();
    }
  
  
  }
  
  
  @Override
  public int getTaskId(){
      return taskID;
  }



}
