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
	
	//Tool Enchants
	MUTANDIS("Mutandis", 3), //TODO unimplemented
	NATURES_BOUNTY("Nature's Bounty", 5), //TODO unimplemented
	
	//Hoe Enchants
	GREEN_THUMB("Green Thumb", 3), //TODO unimplemented wait on hoe enchants for first update?
	
	//Axe Enchants
	TIMBER("Timber", 5), //TODO unimplemented
	WOODSMAN("Woodsman", 5), //TODO unimplemented
	CARPENTRY("Carpentry", 5), 
	APPLESEED("Appleseed", 3), //TODO unimplemented
	
	//Shovel Enchants
	
	//Pickaxe Enchants
	IRON_AFFINITY("Iron Affinity", 5), //TODO unimplemented
	GOLD_AFFINITY("Gold Affinity", 5), //TODO unimplemented
	AUTO_SMELT("Auto Smelt", 3),
	DEMOLISHING("Demolishing", 4),
	CRYSTAL_ATTUNEMENT("Crystal Attunement", 5),
	STONEMASON("Stonemason", 4),
	MAGNET("Magnet", 1), //TODO unimplemented
	
	
	
	//Melee Enchants
	LIFESTEAL("Lifesteal", 2), //TODO unimplemented
	RAGE("Rage", 3), //TODO unimplemented
	SOUL_TAKER("Soul Taker", 3), //TODO unimplemented
	CRIPPLING("Crippling", 5),//TODO unimplemented
	FEARING("Fearing", 3),//TODO unimplemented
	BEHEADING("Beheading", 3),//TODO unimplemented
	HELLFIRE("Hellfire", 3), //TODO unimplemented
	
	
	//Ranged Enchants
	FAR_SHOT("Far Shot", 2), 
	POINT_BLANK("Point Blank", 2), 
	TRUE_SHOT("True Shot", 3), //TODO unimplemented
	HUNTERS_BLESSING("Hunter's Blessing", 3), //TODO unimplemented
	HUNTERS_MARK("Hunter's Mark", 3), //TODO unimplemented
	
	//Shield Enchants
	VANGUARD("Vanguard", 5), //TODO unimplemented Wait on shield enchants for 1st update?
	
	//Armor Enchants
	EVASIVE("Evasive", 3), //TODO unimplemented
	VITALITY("Vitality", 3), //TODO unimplemented
	VIGOR("Vigor", 5), //TODO unimplemented
	SECOND_WIND("Second Wind", 3), //TODO unimplemented
	LAST_STAND("Last Stand", 3),//TODO unimplemented
	DIVINE_INTERVENTION("Divine Intervention", 3),//TODO unimplemented
	ADRENALINE("Adrenaline", 3),//TODO unimplemented
	POISON_RESISTANCE("Poison Resistance", 5), //TODO unimplemented
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
