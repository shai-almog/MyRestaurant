package com.myrestaurant.app.ui;

import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 */
public class MainMenuForm extends BaseForm {
    public MainMenuForm() {
        super("Menu");
    }

    @Override
    protected List<String> createCategoryList() {
        List<String> l = new List<String>("Breakfast", "Lunch", "Specials", "Drinks", "Deserts") {
            @Override
            protected boolean shouldRenderSelection() {
                return true;
            }
        };
        ((DefaultListCellRenderer<String>)l.getRenderer()).setAlwaysRenderSelection(true);
        l.setIgnoreFocusComponentWhenUnfocused(false);
        l.setOrientation(List.HORIZONTAL);
        l.setFixedSelection(List.FIXED_CENTER);
        return l;
    }

    
    @Override
    protected Container createContent() {
        Image chocolate = null;
        try {
            chocolate = Image.createImage("/chocolate-cake.jpg");
        } catch(IOException err) {
            Log.e(err);
        }
        Container c = BoxLayout.encloseY(
                createDishContainer("My great dish", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Maecenas pharetra leo quis sapien rutrum scelerisque. Aenean vulputate lobortis orci eu "
                        + "mollis. ", 8, chocolate),
                createDishContainer("My great dish", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Maecenas pharetra leo quis sapien rutrum scelerisque. Aenean vulputate lobortis orci eu "
                        + "mollis. ", 8, chocolate),
                createDishContainer("My great dish", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Maecenas pharetra leo quis sapien rutrum scelerisque. Aenean vulputate lobortis orci eu "
                        + "mollis. ", 8, chocolate),
                createDishContainer("My great dish", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Maecenas pharetra leo quis sapien rutrum scelerisque. Aenean vulputate lobortis orci eu "
                        + "mollis. ", 8, chocolate)
        );
        c.setScrollableY(true);
        c.setScrollVisible(false);
        return c;
    }

    private Container createDishContainer(String title, String body, float price, Image dish) {
        TextArea ta = new TextArea(body, 3, 80);
        ta.setEditable(false);
        ta.setFocusable(false);
        ta.setGrowByContent(false);
        ta.setUIID("DishListBody");
        
        Button order = new Button("Order " + L10NManager.getInstance().formatCurrency(price), "AddToOrderButton");
        Button moreInfo = new Button("More Info", "MoreInfoButton");
        
        Image dishMasked = MaskManager.maskWithRoundRect(dish);
        
        Container dishContainer = BorderLayout.center(
                BoxLayout.encloseY(
                        new Label(title, "DishListTitle"),
                        ta
                        )
        );
        dishContainer.add(BorderLayout.EAST, new Label(dishMasked));
        dishContainer.add(BorderLayout.SOUTH, GridLayout.encloseIn(2, order, moreInfo));
        
        dishContainer.setUIID("DishListEntry");
        
        return dishContainer;
    }
    
    @Override
    protected void onSearch(String searchString) {
    }
    
}
