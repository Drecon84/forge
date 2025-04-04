package forge.itemmanager.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JPanel;

import forge.card.CardRules;
import forge.item.PaperCard;
import forge.item.PaperCardPredicates;
import forge.itemmanager.ItemManager;
import forge.itemmanager.SItemManagerUtil.StatTypes;
import forge.itemmanager.SpellShopManager;
import forge.util.IterableUtil;


public class CardTypeFilter extends StatTypeFilter<PaperCard> {
    public CardTypeFilter(ItemManager<? super PaperCard> itemManager0) {
        super(itemManager0);
    }

    @Override
    public ItemFilter<PaperCard> createCopy() {
        return new CardTypeFilter(itemManager);
    }

    @Override
    protected void buildWidget(JPanel widget) {
        if (itemManager instanceof SpellShopManager) {
            addToggleButton(widget, StatTypes.PACK_OR_DECK);
        }
        addToggleButton(widget, StatTypes.LAND);
        addToggleButton(widget, StatTypes.ARTIFACT);
        addToggleButton(widget, StatTypes.CREATURE);
        addToggleButton(widget, StatTypes.ENCHANTMENT);
        addToggleButton(widget, StatTypes.PLANESWALKER);
        addToggleButton(widget, StatTypes.INSTANT);
        addToggleButton(widget, StatTypes.SORCERY);
        addToggleButton(widget, StatTypes.BATTLE);
    }

    @Override
    protected final Predicate<PaperCard> buildPredicate() {
        final List<Predicate<CardRules>> types = new ArrayList<>();

        for (StatTypes s : buttonMap.keySet()) {
            if (s.predicate != null && buttonMap.get(s).isSelected()) {
                types.add(s.predicate);
            }
        }

        if (types.size() == buttonMap.size()) {
            //use custom return true delegate to validate the item is a card
            return card -> true;
        }
        return PaperCardPredicates.fromRules(IterableUtil.or(types));
    }
}
