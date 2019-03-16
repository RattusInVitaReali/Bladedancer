package blademaster.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import blademaster.Blademaster;
import blademaster.cards.Defend;
import blademaster.cards.RagingBlow;
import blademaster.cards.Stonework;
import blademaster.cards.Strike;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.*;
import blademaster.relics.DancersAmulet;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0

public class TheCharacter extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(Blademaster.class.getName());

    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    private Color hbTextColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    // =============== /BASE STATS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "defaultModResources/images/char/defaultCharacter/orb/layer1.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer2.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer3.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer4.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer5.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer6.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer1d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer2d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer3d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer4d.png",
            "defaultModResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============


    // =============== CHARACTER CLASS START =================

    public TheCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "defaultModResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "defaultModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(Blademaster.makePath("char/defaultCharacter/main.png"), // required call to load textures and setup energy/loadout
                Blademaster.makePath(Blademaster.THE_DEFAULT_SHOULDER_1), // campfire pose
                Blademaster.makePath(Blademaster.THE_DEFAULT_SHOULDER_2), // another campfire pose
                Blademaster.makePath(Blademaster.THE_DEFAULT_CORPSE), // dead corpse
                getLoadout(), 20.0F, - 10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================


        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================


    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Bladedancer",
                "The Bladedancer is the master of blades. NL " + "He uses swift and precise Blades to cut through his foes. ",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList <String> getStartingDeck() {
        ArrayList <String> retVal = new ArrayList <>();

        logger.info("Begind loading started Deck strings");

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);

        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);

        retVal.add(RagingBlow.ID);
        retVal.add(Stonework.ID);


        return retVal;
    }

    // Starting Relics
    public ArrayList <String> getStartingRelics() {
        ArrayList <String> retVal = new ArrayList <>();

        retVal.add(DancersAmulet.ID);

        UnlockTracker.markRelicAsSeen(DancersAmulet.ID);

        return retVal;
    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.DEFAULT_GRAY;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return Blademaster.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return "The Bladedancer";
    }

    //Which starting card should specific events give you?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // The class name as it appears next to your player name in game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Bladedancer";
    }

    // Should return a new instance of your character, sending this.name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheCharacter(this.name, this.chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return Blademaster.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return Blademaster.DEFAULT_GRAY;
    }

    @SpireOverride
    public void renderPowerIcons(SpriteBatch sb, float x, float y) {
        float yoffset = 0.0F;
        float offset = 10.0F;
        float doffset = 10.0F;
        for (AbstractPower p : this.powers) {
            if ((p.ID.equals(LightningStance.POWER_ID)) || (p.ID.equals(WindStance.POWER_ID)) || (p.ID.equals(FuryPower.POWER_ID)) || (p.ID.equals(ComboPower.POWER_ID)) || (p.ID.equals(CalmnessPower.POWER_ID))
                    || (p.ID.equals(TiredPower.POWER_ID)) || (p.ID.equals(WindCharge.POWER_ID)) || (p.ID.equals(LightningCharge.POWER_ID)) || (p.ID.equals(IceStance.POWER_ID)) || (p.ID.equals(StoneStance.POWER_ID))
                    || (p.ID.equals(IceCharge.POWER_ID)) || (p.ID.equals(StoneCharge.POWER_ID)) || (p.ID.equals(BasicStance.POWER_ID))) {
                yoffset = - 40.0F;
                p.renderIcons(sb, x + (doffset * Settings.scale), y + ((- 48.0F + yoffset) * Settings.scale), Color.WHITE);
                doffset += 48.0F;
            } else {
                yoffset = 0F;
                p.renderIcons(sb, x + (offset * Settings.scale), y + ((- 48.0F + yoffset) * Settings.scale), Color.WHITE);
                offset += 48.0F;
            }
        }
        offset = 0.0F;
        doffset = 0.0F;
        for (AbstractPower p : this.powers) {
            if ((p.ID.equals(LightningStance.POWER_ID)) || (p.ID.equals(WindStance.POWER_ID)) || (p.ID.equals(FuryPower.POWER_ID)) || (p.ID.equals(ComboPower.POWER_ID)) || (p.ID.equals(CalmnessPower.POWER_ID))
                    || (p.ID.equals(TiredPower.POWER_ID)) || (p.ID.equals(WindCharge.POWER_ID)) || (p.ID.equals(LightningCharge.POWER_ID)) || (p.ID.equals(IceStance.POWER_ID)) || (p.ID.equals(StoneStance.POWER_ID))
                    || (p.ID.equals(IceCharge.POWER_ID)) || (p.ID.equals(StoneCharge.POWER_ID))) {
                yoffset = - 40.0F;
                p.renderAmount(sb, x + ((doffset + 32.0F) * Settings.scale), y + ((- 66.0F + yoffset) * Settings.scale), Color.WHITE);
                doffset += 48.0F;
            } else {
                yoffset = 0.0F;
                p.renderAmount(sb, x + ((offset + 32.0F) * Settings.scale), y + ((- 66.0F + yoffset) * Settings.scale), Color.WHITE);
                offset += 48.0F;
            }
        }
    }


    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in damage action and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return "You sharpen your blades...";
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~masterful~ ~one,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

}
