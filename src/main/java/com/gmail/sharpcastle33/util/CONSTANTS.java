
package com.gmail.sharpcastle33.util;


public class CONSTANTS {
    
    // ## CONVENTION RULES ##
    // Begin identifier with datatype letter. 
    // Make sure to leave a meaningful comment above every entry.
    // All constants should be final.
    // Mention the unit if you can (Seconds, Ticks, Damage, etc.) 
    // All uppercase seperated by _
    // For all "chance" bounds, the higher the value : the lower the chance of success
    // Keep Related items together (durations with durations, bounds with bounds, etc)
    
    
    //Damage taken off of an attack for every level of endurance defender's armor has
    public static final double D_ENDURANCE_DAMAGE_REDUCTION = 0.15;
    
    //Added damage for glowing enemies
    public static final double D_HUNTERS_MARK_DAMAGE = 0.25;
    
    
    
    
    
    //Base duration for Hunter's Mark
    public static final int I_BASE_HUNTERS_MARK_SECONDS = 2;
    
    //Starting time window for rage
    public static final int I_RAGE_INITIAL_DURATION_SECONDS = 5;
    
    //Time window for rage inbetween hits
    public static final int I_RAGE_HIT_DURATION_SECONDS = 3;
    
    // Duration of Second Wind effect in seconds
    public static final int I_SECOND_WIND_DURATION_SECONDS = 10;
    
    // Duration of Last Stand effect in seconds
    public static final int I_LAST_STAND_DURATION_SECONDS = 10;
    
    // Duration of Adrenaline effect in seconds
    public static final int I_ADRENALINE_DURATION_SECONDS = 10;
    
    // Duration of Second Wind cooldown in seconds
    public static final int I_SECOND_WIND_COOLDOWN_DURATION_SECONDS = 300;
    
    // Duration of Last Stand cooldown in seconds
    public static final int I_LAST_STAND_COOLDOWN_DURATION_SECONDS = 300;
    
    // Duration of Adrenaline cooldown in seconds
    public static final int I_ADRENALINE_COOLDOWN_DURATION_SECONDS = 150;
    
    //Duration of speed boost from crystal attunement
    public static final int I_CRYSTAL_ATTUNEMENT_DURATION_SECONDS = 2;
    
    //Duration of divine intervention
    public static final int I_DIVINE_INTERVENTION_DURATION_SECONDS = 3;
    
    public static final int I_DIVINE_INTERVENTION_COOLDOWN_DURATION_SECONDS = 600;
    
    //Amount of time between heals for the regen effect
    public static final int I_REGEN_INTERVAL_SECONDS = 15;
    
    
    
    public static final int I_RAGE_CHAIN_MAX_COUNTER = 5;
    
    
    
    //Amount of experience awarded after successful proficient trigger
    public static final int I_PROFICIENT_EXP_AMOUNT = 1;
    
    
    
    
    //Upper bound of proficient proc chance
    public static final int I_PROFICIENT_CHANCE_BOUND = 80;
    
    //Upper bound of emerald resonance proc chance
    public static final int I_EMERALD_RESONANCE_BOUND = 100;
    
    //lifesteal trigger chance
    public static final int I_LIFESTEAL_CHANCE_BOUND = 33;
    
    //Upper bound of stonemason
    public static final int I_STONEMASON_CHANCE_BOUND = 100;
    
    //chance for umbral to trigger
    public static final double D_UMBRAL_CHANCE = 0.04;
    
    //Upper bound for carpentry
    public static final int I_CARPENTRY_CHANCE_BOUND = 100;
    
    //Upper bound for appleseed
    public static final int I_APPLESEED_CHANCE_BOUND = 18;
    
    //Upper bound for evasion, with maxed out enchants a set could at most be 12/x, where x = this value
    public static final int I_EVASION_CHANCE_BOUND = 99;
    
    //Upper bound for Hunter's Blessing, Bound is divided by enchant level, so odds are x/y where x=this value and y=enchant level
    public static final int I_HUNTERS_BLESSING_CHANCE_BOUND = 60;
    
    //Upper bound for head hunter, chance is (enchant_lvl * 3) / this value
    public static final int I_HEADHUNTER_CHANCE_BOUND = 99;
    
    //Upper bound for Bricklayer, chance is (enchant_lvl * 5) / this value
    public static final int I_BRICKLAYER_CHANCE_BOUND = 50;
    
    //Upper bound for demolishing, chance is (25 * ench lvl) / this value
    public static final int I_DEMOLISHING_CHANCE_BOUND = 100;
    
    
    
    // Max distance for Far shot damage
    public static final int I_FAR_SHOT_MAX_DISTANCE_BLOCKS = 90;
    
    // Medium distance for Far shot damage
    public static final int I_FAR_SHOT_MED_DISTANCE_BLOCKS = 60;
    
    // Minimum distance for Far shot damage
    public static final int I_FAR_SHOT_MIN_DISTANCE_BLOCKS = 50;
    
    // Max distance for Far shot damage
    public static final int I_POINT_BLANK_MAX_DISTANCE_BLOCKS = 10;
    
    // Medium distance for Far shot damage
    public static final int I_POINT_BLANK_MED_DISTANCE_BLOCKS = 7;
    
    // Minimum distance for Far shot damage
    public static final int I_POINT_BLANK_MIN_DISTANCE_BLOCKS = 3;
    
}
