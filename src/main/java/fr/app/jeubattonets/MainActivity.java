package fr.app.jeubattonets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private Button btnVsPlayer, btnVsComputer;
    private Button btnValider;
    private TextView textView;
    private ImageView imageView;
    private ArrayList<ImageView> imgBatonsSelectionnes = new ArrayList<ImageView>();
    private ArrayList<ImageView> imgBatons = new ArrayList<ImageView>();
    private int numJoueur = 1;
    private int nbBatonSelection = 0;
    private int nbBatonsRestants = 20;
    private LinearLayout myLayout1, myLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_batonnet);

        myLayout1 = (LinearLayout) findViewById(R.id.main1);
        myLayout2 = (LinearLayout) findViewById(R.id.main2);
        btnValider = findViewById(R.id.btnValider);
        textView = findViewById(R.id.msgSelection);
        textView.setTextColor(Color.parseColor("#00AA00")); // Vert

        for(int i = 0; i < 20; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.batonnet);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(96, 512);
            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(30, 0, 0, 0);
            if(i < 10)
                myLayout1.addView(imageView);
            else
                myLayout2.addView(imageView);

            imgBatons.add(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(ImageView img : imgBatonsSelectionnes) {
                        if(img == v) {
                            ImageView image = (ImageView) v;
                            image.setImageResource(R.drawable.batonnet);

                            imgBatonsSelectionnes.remove(img);
                            nbBatonSelection--;
                            return;
                        }
                    }

                    if(nbBatonSelection < 3) {
                        ImageView image = (ImageView) v;
                        image.setImageResource(R.drawable.batonnet_sel);
                        nbBatonSelection++;

                        imgBatonsSelectionnes.add(image);
                    } else {
                        displayNotif("3 batonnets sélectionnés maximum", false);
                    }
                }
            });
        }

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImageView img : imgBatonsSelectionnes) {
                    img.setVisibility(View.INVISIBLE);
                }

                TextView textView = findViewById(R.id.msgSelection);
                nbBatonsRestants -= nbBatonSelection;

                imgBatonsSelectionnes.clear();

                if(nbBatonsRestants <= 1) {
                    displayNotif("Victoire pour le joueur " + numJoueur, true);

                    initialize();
                } else {
                    changePlayer(textView);
                }
            }
        });
    }

    public void changePlayer(TextView tv) {
        if(numJoueur == 1) {
            numJoueur = 2;
            tv.setText(R.string.msgSelection2);
            tv.setTextColor(Color.parseColor("#AA0000")); // Rouge
        } else {
            numJoueur = 1;
            tv.setText(R.string.msgSelection1);
            tv.setTextColor(Color.parseColor("#00AA00")); // Vert
        }

        nbBatonSelection = 0;
    }

    public void initialize() {
        for(ImageView img : imgBatons) {
            img.setImageResource(R.drawable.batonnet);
            img.setVisibility(View.VISIBLE);
        }
        numJoueur = 1;
        nbBatonsRestants = 20;
        nbBatonSelection = 0;
        textView.setText(R.string.msgSelection1);
        textView.setTextColor(Color.parseColor("#00AA00")); // Vert
    }

    public void displayNotif(String msg, boolean estCentre) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        if(estCentre)
            toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}