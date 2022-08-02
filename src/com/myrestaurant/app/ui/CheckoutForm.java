package com.myrestaurant.app.ui;

import com.codename1.io.Log;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.Transition;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;

public class CheckoutForm extends Form {
    private Transition oldTransition;
    private CheckoutForm(Form previous) {
        super(new BorderLayout());
        Button x = new Button("", "Title");
        FontImage.setMaterialIcon(x, FontImage.MATERIAL_CLOSE);
        x.addActionListener(e -> {
            previous.showBack();
            previous.setTransitionOutAnimator(oldTransition);
        });
        
        add(BorderLayout.NORTH, 
                BorderLayout.centerAbsoluteEastWest(new Label("Checkout", "Title"), null, x)
                );
        add(BorderLayout.SOUTH, new Button("Order & Pay", "CheckoutButton"));
        

        Container itemsContainer = new Container(BoxLayout.y());
        Container totalsContainer = new Container(BoxLayout.y());
        Image chocolate = null;
        try {
            chocolate = Image.createImage("/chocolate-cake.jpg");
        } catch(IOException err) {
            Log.e(err);
        }
        itemsContainer.add(createShoppingCartContainer("My great dish", 8, chocolate));
        itemsContainer.add(createShoppingCartContainer("My great dish", 8, chocolate));
        itemsContainer.setUIID("PaymentDialogTop");
        totalsContainer.setUIID("PaymentDialogBottom");
        totalsContainer.add(new Label("Total: " + L10NManager.getInstance().formatCurrency(33.45), "PriceTotal"));
        Container checkout = BoxLayout.encloseY(itemsContainer, totalsContainer);
        checkout.setScrollableY(true);
        add(BorderLayout.CENTER, checkout);
    }
    
    
    private Container createShoppingCartContainer(String title, float price, Image dish) {
        Image dishMasked = MaskManager.maskWithRoundRect(dish);
        
        Container dishContainer = FlowLayout.encloseMiddle(
                new Label(dishMasked),
                BoxLayout.encloseY(
                        new Label(title, "DishCheckoutTitle"),
                        new Label(L10NManager.getInstance().formatCurrency(price), "CheckoutPrice")
                        ));
        
        return dishContainer;
    }
    
    public static void showCheckOut() {
        Form existingForm = Display.getInstance().getCurrent();
        CheckoutForm f = new CheckoutForm(existingForm);
        f.oldTransition = existingForm.getTransitionOutAnimator();;
        Image background = Image.createImage(existingForm.getWidth(), existingForm.getHeight());
        Graphics g = background.getGraphics();
        existingForm.paintComponent(g, true);
        g.setAlpha(150);
        g.setColor(0);
        g.fillRect(0, 0, background.getWidth(), background.getHeight());
        background = Display.getInstance().gaussianBlurImage(background, 10);
        f.getUnselectedStyle().setBgImage(background);
        f.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        f.setTransitionOutAnimator(CommonTransitions.createUncover(CommonTransitions.SLIDE_VERTICAL, true, 200));

        existingForm.setTransitionOutAnimator(CommonTransitions.createEmpty());
        existingForm.setTransitionOutAnimator(CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, false, 200));
        f.show();
    }
    
}
