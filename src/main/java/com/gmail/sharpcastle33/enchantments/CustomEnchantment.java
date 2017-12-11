package com.gmail.sharpcastle33.enchantments;

public enum CustomEnchantment {
	
	
	NO_ENCHANTMENT("null", 0),
	ENHANCED("Enhancement", 5),
	
	//Vanilla Enchantments
	
	AQUA_AFFINITY("Aqua Affinity", 1),
	BANE_OF_ARTHROPODS("Bane of Arthropods", 3),
	BLAST_PROTECTION("Blast Protection", 4),
	DEPTH_STRIDER("Depth Strider", 3),
	FEATHER_FALLING("Feather Falling", 3),
	FIRE_ASPECT("Fire Aspect", 2),
	FIRE_PROTECTION("Fire Protection", 4),
	FLAME("Flame", 2),
	FORTUNE("Fortune", 3),
	FROST_WALKER("Frost Walker", 1),
	INFINITY("Infinity", 1),
	KNOCKBACK("Knockback", 3),
	LOOTING("Looting", 3),
	LUCK_OF_THE_SEA("Luck of the Sea", 3),
	LURE("Lure", 3),
	PROJECTILE_PROTECTION("Projectile Protection", 3),
	PUNCH("Punch", 1),
	RESPIRATION("Respiration", 3),
	SILK_TOUCH("Silk Touch", 1),
	SMITE("Smite", 4),
	SWEEPING_EDGE("Sweeping Edge", 3),
	THORNS("Thorns", 3),
	UNBREAKING("Unbreaking", 3),
	
	//Custom Enchantments
	IRON_AFFINITY("Iron Affinity", 2),
	GOLD_AFFINITY("Gold Affinity", 2),
	AUTO_SMELT("Auto Smelt", 3),
	
	LIFESTEAL("Lifesteal", 2),
	RAGE("Rage", 3),
	
	FAR_SHOT("Far Shot", 2),
	POINT_BLANK("Point Blank", 2),
	
	EVASIVE("Evasive", 3),
	VITALITY("Vitality", 2),
	VIGOR("Vigor", 4),
	ENDURANCE("Endurance", 3);
	
	
	private String name;
	private int maxlvl;
	
	CustomEnchantment(String name, int maxLevel){
		this.name = name;
		this.maxlvl = maxLevel;
	}
	
	public int getMaxLevel(){
		return maxlvl;
	}
	
	public String getName(){
		return name;
	}
}
