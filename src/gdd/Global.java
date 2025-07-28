package gdd;

public class Global {
    private Global() {
        // Prevent instantiation
    }

    public static final int SCALE_FACTOR = 1; // Scaling factor for sprites

    public static final int BOARD_WIDTH = 716; // Doubled from 358
    public static final int BOARD_HEIGHT = 700; // Doubled from 350
    public static final int BORDER_RIGHT = 60; // Doubled from 30
    public static final int BORDER_LEFT = 10; // Doubled from 5

    public static final int GROUND = 580; // Doubled from 290
    public static final int BOMB_HEIGHT = 10; // Doubled from 5

    public static final int ALIEN_HEIGHT = 24; // Doubled from 12
    public static final int ALIEN_WIDTH = 24; // Doubled from 12
    public static final int ALIEN_INIT_X = 300; // Doubled from 150
    public static final int ALIEN_INIT_Y = 10; // Doubled from 5
    public static final int ALIEN_GAP = 30; // Gap between aliens

    public static final int GO_DOWN = 30; // Doubled from 15
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 30; // Doubled from 15
    public static final int PLAYER_HEIGHT = 20; // Doubled from 10

/*     // Images
    public static final String IMG_ENEMY = "src/images/alien.png";
    public static final String IMG_PLAYER = "src/images/player.png";
    public static final String IMG_SHOT = "src/images/shot.png";
    public static final String IMG_EXPLOSION = "src/images/explosion.png";
    public static final String IMG_POWERUP_SPEEDUP = "src/images/powerup-s.png"; */

    // UFO Enemy
    public static final String IMG_UFO01 = "src/images/Enemy/UFO/Ship_01.png";
    public static final String IMG_UFO02 = "src/images/Enemy/UFO/Ship_02.png";
    public static final String IMG_UFO03 = "src/images/Enemy/UFO/Ship_03.png"; 
    public static final String IMG_UFO04 = "src/images/Enemy/UFO/Ship_04.png";
    public static final String IMG_UFO05 = "src/images/Enemy/UFO/Ship_05.png";
    public static final String IMG_UFO06 = "src/images/Enemy/UFO/Ship_06.png";

    //Pirate Enemy
    public static final String IMG_PIRATE01 = "src/images/Enemy/PirateShip/Ship_01.png";
    public static final String IMG_PIRATE02 = "src/images/Enemy/PirateShip/Ship_02.png";
    public static final String IMG_PIRATE03 = "src/images/Enemy/PirateShip/Ship_03.png";
    public static final String IMG_PIRATE04 = "src/images/Enemy/PirateShip/Ship_04.png";
    public static final String IMG_PIRATE05 = "src/images/Enemy/PirateShip/Ship_05.png";
    public static final String IMG_EnemySHOT = "src/images/Enemy/PirateShip/EnemyShot.png";
 

    //Player
    public static final String IMG_PLAYER01 = "src/images/Player/Ship_LVL_1.png";
    public static final String IMG_PLAYER02 = "src/images/Player/Ship_LVL_2.png";
    public static final String IMG_PLAYER_SHOT = "src/images/Player/FireShot.png";

    //Player Animation 
    public static final String ANIM_PLAYER01_SHOT = "src/animation/Player/shot.png";
    public static final String ANIM_PLAYER01_FLYING = "src/animation/Player/01/flying.png";
    public static final String ANIM_PLAYER01_EXPLOSION = "src/animation/Player/01/death.png"; // Reuse bomb explosion for player death

    //Bonus Item
    public static final String IMG_POWERUP_SPEEDUP = "src/images/Bonus_ITEM/SpeedUp.png";
    public static final String IMG_POWERUP_HEALTHUP = "src/images/Bonus_ITEM/HealthUp.png";

    // Missile 
    public static final String IMG_MISSILE01 = "src/images/Props/Missile01.png";
    public static final String IMG_MISSILE02 = "src/images/Props/Missile02.png";
    public static final String IMG_MISSILE03 = "src/images/Props/Missile03.png";

    public static final String ANIM_MISSILE01_FLYING = "src/animation/Missile/01/idle.png";
    public static final String ANIM_MISSILE01_EXPLOSION = "src/animation/Missile/01/explosion.png"; 

    //Bomb
    public static final String IMG_BOMB01 = "src/images/Props/Bomb_01.png";
    public static final String IMG_BOMB02 = "src/images/Props/Bomb_02.png";
    public static final String IMG_BOMB03 = "src/images/Props/Bomb_03.png";

    //Bomb Animation 
    public static final String ANIM_BOMB01_IDLE = "src/animation/Bomb/01/idle.png";
    public static final String ANIM_BOMB01_EXPLOSION = "src/animation/Bomb/01/explosion.png";

    public static final String ANIM_BOMB02_IDLE = "src/animation/Bomb/02/Idle.png";
    public static final String ANIM_BOMB02_EXPLOSION = "src/animation/Bomb/02/Explosion.png";   

    public static final String ANIM_BOMB03_IDLE = "src/animation/Bomb/03/Idle.png";
    public static final String ANIM_BOMB03_EXPLOSION = "src/animation/Bomb/03/Explosion.png";

    //Bonus Items
    public static final String IMG_SPEEDUP = "src/images/Bonus_Item/SpeedUp.png";
    public static final String IMG_ARMOR = "src/images/Bonus_Item/Armor_Bonus.png";
    public static final String IMG_MULTISHOT = "src/images/Bounus_Items/Rockets_Bonus.png"; 

    //Levels
    
    public static final String IMG_TITLE = "src/images/Map/TitlePage/titlesScene_716*700pixels.png"; 
    public static final String IMG_LEVEL01 = "src/images/Map/Level_01.png";
    public static final String IMG_LEVEL02 = "src/images/Map/Level_02.png";

    
}
