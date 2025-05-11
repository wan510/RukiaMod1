package rukiamod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rukiamod.powers.SpiritualPower;
import rukiamod.powers.FrostbitePower;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY;
import static rukiamod.characters.MyCharacter.PlayerColorEnum.RUKIA_BLEACH;

public class IceBreak extends CustomCard {
    public static final String ID = "RukiaMod:IceBreak";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "RukiaModResources/img/cards/Test.png";

    public IceBreak() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION,
                ATTACK, RUKIA_BLEACH, COMMON, ENEMY);
        this.baseDamage = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean brokeBlock = (m.currentBlock > 0 && m.currentBlock < this.damage);

        // 正常伤害
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL))
        );

        // 击破格挡：添加冻伤
        if (brokeBlock) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, p, new FrostbitePower(m, 1), 1)
            );
        }

        // 灵压4或以上：给予格挡 & 失去HP（非伤害）
        if (p.hasPower(SpiritualPower.POWER_ID) && p.getPower(SpiritualPower.POWER_ID).amount >= 4) {
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(m, p, 3)
            );

            if (m.hasPower(FrostbitePower.POWER_ID)) {
                int frostCount = m.getPower(FrostbitePower.POWER_ID).amount;
                int hpLoss = frostCount * 2;
                if (hpLoss > 0) {
                    AbstractDungeon.actionManager.addToBottom(
                            new LoseHPAction(m, p, hpLoss)
                    );
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.initializeDescription();
        }
    }
}