package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.effects.BetterLightningEffect;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.BasicStance;
import blademaster.powers.BleedingPower;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class Vortex extends CustomCard {


    public static final String ID = Blademaster.makeID("Vortex");
    public static final String IMG = Blademaster.makePath("cards/Vortex.png");
    public static final String WIMG = Blademaster.makePath("cards/WindVortex.png");
    public static final String LIMG = Blademaster.makePath("cards/LightningVortex.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int AMT = 5;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;


    public Vortex() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = AMT;
        this.tags.add(BlademasterTags.WIND_STANCE);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BetterLightningEffect(monster.drawX, monster.drawY, 0.5F, Blademaster.GetStanceColor())));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new BleedingPower(monster, p, this.magicNumber), this.magicNumber));
            if (p.hasPower(LightningStance.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, 1, false), 1));
            }
            if (p.hasPower(WindStance.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, 1, false), 1));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            if (! WindArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, WIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0]);
                this.initializeDescription();
                WindArt = true;
                LightningArt = false;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            if (! LightningArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1]);
                this.initializeDescription();
                WindArt = false;
                LightningArt = true;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (! BaseArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                this.rawDescription = DESCRIPTION;
                this.initializeDescription();
                WindArt = false;
                LightningArt = false;
                BaseArt = true;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Vortex();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeMagicNumber(2);
            this.upgradeName();
            this.initializeDescription();
        }
    }
}