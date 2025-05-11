package rukiamod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import rukiamod.powers.SpiritualPower;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY;
import static rukiamod.characters.MyCharacter.PlayerColorEnum.RUKIA_BLEACH;

public class KickIchigo extends CustomCard {
    public static final String ID = "RukiaMod:KickIchigo";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "RukiaModResources/img/cards/Test.png"; // 测试图像
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int VULNERABLE = 2;
    private static final int UPGRADE_VULNERABLE = 1;

    public KickIchigo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, ATTACK, RUKIA_BLEACH, BASIC, ENEMY);
        this.baseDamage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 造成伤害
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL))
        );

        // 判断灵压是否 ≥ 2，若是，则附加易伤
        if (p.hasPower(SpiritualPower.POWER_ID)) {
            int spirit = p.getPower(SpiritualPower.POWER_ID).amount;
            if (spirit >= 2) {
                int vuln = upgraded ? VULNERABLE + UPGRADE_VULNERABLE : VULNERABLE;
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m, p, new VulnerablePower(m, vuln, false), vuln)
                );
            }
        }
    }
}
