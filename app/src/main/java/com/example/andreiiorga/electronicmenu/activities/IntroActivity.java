package com.example.andreiiorga.electronicmenu.activities;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import com.example.andreiiorga.electronicmenu.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ace)
                .title("Facultatea de Automatică Calculatoare și Electronică")
                .description("Licenta\n " +
                        "Andrei Iorga\n2017")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ent)
                .title("Restaurant ENTOURAGE")
                .description("Tel: 0723242425")
                .build());


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.tablet_img)
                .title("Rapid, interactiv si fara greseli")
                .description("Va puteti comanda mancarea favorita chiar de aici" +
                        "! \n ")
                .build()
        );


    }
}
