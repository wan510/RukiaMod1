package rukiamod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import rukiamod.powers.FrostbitePower;
import rukiamod.powers.SpiritualPower;

import static rukiamod.characters.MyCharacter.PlayerColorEnum.RUKIA_BLEACH;

public class ReiryokuGather extends CustomCard {
    public static final String ID = "RukiaMod:ReiryokuGather";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "RukiaModResources/img/cards/Test.png"; // 保持测试用图像
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = RUKIA_BLEACH;
    public static final CardRarity RARITY = CardRarity.BASIC;
    public static final CardTarget TARGET = CardTarget.ALL;

    private static final int BLOCK = 3;

    public ReiryokuGather() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.exhaust = false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 仅当灵力 < 3 时，给予一点灵力
        if (!p.hasPower(SpiritualPower.POWER_ID) || p.getPower(SpiritualPower.POWER_ID).amount < 3) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new SpiritualPower(p, 1), 1)
            );
        }

        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(mo, mo, this.block)
                );
            }
        }


        // 玩家获得格挡
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );

        // 所有敌人失去生命（与冻伤层数相关）
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped() && mo.hasPower(FrostbitePower.POWER_ID)) {
                int frostbite = mo.getPower(FrostbitePower.POWER_ID).amount;
                int hpLoss = 2 * frostbite;
                AbstractDungeon.actionManager.addToBottom(
                        new LoseHPAction(mo, p, hpLoss, AbstractGameAction.AttackEffect.FIRE)
                );
            }
        }

        // 升级效果：灵力大于2则抽牌
        if (this.upgraded && p.hasPower(SpiritualPower.POWER_ID)) {
            int spirit = p.getPower(SpiritualPower.POWER_ID).amount;
            if (spirit > 2) {
                AbstractDungeon.actionManager.addToBottom(
                        new DrawCardAction(p, 1)
                );
            }
        }
    }
}
