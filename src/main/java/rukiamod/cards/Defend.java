package rukiamod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static rukiamod.characters.MyCharacter.PlayerColorEnum.RUKIA_BLEACH;

public class Defend extends CustomCard {
    public static final String ID = "RukiaMod:Defend";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);// 唯一标识符
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "RukiaModResources/img/cards/Test.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = RUKIA_BLEACH;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Defend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock =5; // 基础格挡值
        this.tags.add(CardTags.STARTER_DEFEND); // 标记为初始卡牌
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 使用时添加格挡
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名称显示"+"
            this.upgradeBlock(3); // 升级后格挡值+3
        }
    }
}
