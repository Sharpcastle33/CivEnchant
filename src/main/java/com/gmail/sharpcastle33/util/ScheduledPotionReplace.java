import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


public class ScheduledPotionReplace extends BukkitRunnable {

  Player player;  // The player whose potion effect must be returned
  PotionEffect effect;  // The potion effect
  int duration; // How long the replacing potion will last. Once that duration is over, bring back the original effect
  
  
  public ScheduledPotionReplace(Player player, PotionEffect effect, int duration){
  
      this.player = player;
      this.effect = effect;
      this.duration = duration * 20;  // MC runs 20 ticks / sec, so duration = seconds * 20

  } 

      @Override
      public void run() {

        if(duration > 0){
        
          duration--;
          
        } else {
        
          player.addPotionEffect(effect);
          this.cancel();
        }


      }




}
