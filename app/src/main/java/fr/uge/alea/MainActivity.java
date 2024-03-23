package fr.uge.alea;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends AppCompatActivity {
    private static final String DICE_SYMBOLS = "⚀⚁⚂⚃⚄⚅";
    private static final int[] DICE_VIEWS = new int[]{R.id.diceValue1, R.id.diceValue2, R.id.diceValue3, R.id.diceValue4, R.id.diceValue5, R.id.diceValue6};
    private static int nbDice=1;
    private List<List<Integer>> history = new ArrayList<List<Integer>>();
    private int historyPosition=-1;
    private int compteur10=0;
    private long lastClick=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        easterEgg();
        setContentView(R.layout.activity_main);
        //Bouton pour lancer les dés -----------------------------
        Button b = (Button) findViewById(R.id.throwDiceButton);
        String buttonText= getString(R.string.throw_dice,nbDice);
        b.setText(buttonText);
        //Si on clique sur le bouton
        b.setOnClickListener(button->{

           List<Integer> list = new ArrayList<Integer>();

            Random random= new Random();
            int nb6=0;
            for(int i=0;i<nbDice;i++){
                int rand = random.nextInt(6) + 1;
                if(rand==6){
                    nb6++;
                }
                TextView dice = (TextView) findViewById(DICE_VIEWS[i]);
                dice.setText(DICE_SYMBOLS.charAt(rand-1) + "");
                list.add(rand);

            }
            //ajouut dans l'historique
            history.add(list);
            Log.i("List", "onCreate: " + history.toString());
            //si tout les des sont des 6
            if(nb6==nbDice){
                Toast t= Toast.makeText(this,getString(R.string.congrats6), Toast.LENGTH_SHORT);
                t.show();
            }
            historyPosition++;
        });
        //SeekBar pour choisir le nombre de dés --------------------------------
        SeekBar bar = (SeekBar) findViewById(R.id.seekbar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nbDice=progress +1;
                String buttonText= getString(R.string.throw_dice,nbDice);
                b.setText(buttonText);
                for(int i=0;i<6;i++){
                    TextView dice = (TextView) findViewById(DICE_VIEWS[i]);
                    if(i<=progress){
                        dice.setVisibility(View.VISIBLE);
                    }
                    else {
                        dice.setText("?");
                        dice.setVisibility(View.GONE);
                    }
                }
                easterEgg();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Retour arrière -----------------------------------------------------
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(historyPosition<0){
                    finish();
                }
                List<Integer> last = history.get(historyPosition);
                Log.i("last", "handleOnBackPressed: " + last.toString());
                for(int i=0;i<6;i++){
                    TextView dice = (TextView) findViewById(DICE_VIEWS[i]);
                    if(i<last.size()){
                        dice.setText(DICE_SYMBOLS.charAt(last.get(i)-1) + "");
                        dice.setVisibility(View.VISIBLE);
                    }
                    else {
                        dice.setText("?");
                        dice.setVisibility(View.GONE);
                    }
                }
                historyPosition--;
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        Button switchButton = (Button) findViewById(R.id.switchActivity);
        switchButton.setOnClickListener(button->{
            Intent intent = new Intent(this, FortuneActivity.class);
            startActivity(intent);
        });



    }

    public void easterEgg(){
        for(int i=1;i<nbDice;i++){
            TextView dice = (TextView) findViewById(DICE_VIEWS[i-1]);
            dice.setOnClickListener(diceView->{
                Log.i("easteregg", "onCreate: appuie sur un de");
                if(compteur10==10){
                    Toast t= Toast.makeText(this,getString(R.string.easterEgg), Toast.LENGTH_SHORT);
                    t.show();
                    compteur10=0;
                }
                if(SystemClock.elapsedRealtime()-lastClick<100000) {
                    compteur10++;
                }
                else{
                    compteur10=0;
                }
                lastClick = SystemClock.elapsedRealtime();
            });
        }}


}