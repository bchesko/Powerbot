package com.hunterz103.rsbot.scripts.aioHerblore;

/**
 * Created by Brian on 2/12/14.
 */
public enum Product {

    //Cleaning Herbs    (Grimy herb, clean herb)
    GUAM_CLEAN          (Type.HERB, 199, 13572),
    MARRENTILL_CLEAN    (Type.HERB, 201, 251),
    TARROMIN_CLEAN      (Type.HERB, 203, 253),
    HARRALANDER_CLEAN   (Type.HERB, 205, 255),
    RANARR_CLEAN        (Type.HERB, 207, 257),
    IRIT_CLEAN          (Type.HERB, 209, 259),
    AVANTOE_CLEAN       (Type.HERB, 211, 261),
    KWUARM_CLEAN        (Type.HERB, 213, 263),
    CADANTINE_CLEAN     (Type.HERB, 215, 265),
    DWARF_WEED_CLEAN    (Type.HERB, 217, 267),
    TORSTOL_CLEAN       (Type.HERB, 219, 269),
    LANTADYME_CLEAN     (Type.HERB, 2485, 2481),
    SPIRIT_WEED_CLEAN   (Type.HERB, 12174, 12172),
    //Unfinished Potions(Vial of water, herb/other, product)
    POTION_MARRENTILL   (Type.POTION_UNFINISHED, 251, 93),
    POTION_TARROMIN     (Type.POTION_UNFINISHED, 253, 95),
    POTION_HARRALANDER  (Type.POTION_UNFINISHED, 255, 97),
    POTION_RANARR       (Type.POTION_UNFINISHED, 257, 99),
    POTION_IRIT         (Type.POTION_UNFINISHED, 259, 101),
    POTION_AVANTOE      (Type.POTION_UNFINISHED, 261, 103),
    POTION_KWUARM       (Type.POTION_UNFINISHED, 263, 105),
    POTION_CADANTINE    (Type.POTION_UNFINISHED, 265, 107),
    POTION_DWARF_WEED   (Type.POTION_UNFINISHED, 267, 109),
    POTION_TORSTOL      (Type.POTION_UNFINISHED, 269, 111),
    POTION_LANTADYME    (Type.POTION_UNFINISHED, 2481, 2483),
    //Finished Potions  (unf Potion, ingredient, product)
    SERUM_207           (Type.POTION_FINISHED, 95, 592, 3410),    //Ashes
    WEAPON_POISON       (Type.POTION_FINISHED, 105, 241, 25487),
    ANTIFIRE            (Type.POTION_FINISHED, 2483, 241, 2454);

    public final int ITEM_1_ID, ITEM_2_ID, PRODUCT_ID;
    public final Type type;
    
    Product(Type type, int... ids) {
        this.type = type;
        int temp1 = -1, temp2 = -1, tempProd = -1;

        switch (type) {
            case HERB:
                temp1 = ids[0];
                tempProd = ids[1];
                break;
            case POTION_UNFINISHED:
                temp1 = 227;
                temp2 = ids[0];
                tempProd = ids[1];
                break;
            case POTION_FINISHED:
                temp1 = ids[0];
                temp2 = ids[1];
                tempProd = ids[2];
                break;
        }
        
        ITEM_1_ID = temp1;
        ITEM_2_ID = temp2;
        PRODUCT_ID = tempProd;
        
    }
    
    enum Type { HERB(), POTION_UNFINISHED, POTION_FINISHED(); }

}
