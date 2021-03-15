package ru.iu3.labs.fclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PinPadActivity extends android.app.Activity {
    TextView textView;
    String pin = "";

    private static final int[] numeredButtonsIDs = {
            R.id.btnKey0,
            R.id.btnKey1,
            R.id.btnKey2,
            R.id.btnKey3,
            R.id.btnKey4,
            R.id.btnKey5,
            R.id.btnKey6,
            R.id.btnKey7,
            R.id.btnKey8,
            R.id.btnKey9,
    };

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_pinpad);

        textView = findViewById(R.id.txtPin);

        shuffleKeys();

        findViewById(R.id.btnKeyOK).setOnClickListener((View) -> {
            Intent intent = new Intent();
            intent.putExtra("pin", pin);
            setResult(RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.btnKeyReset).setOnClickListener((View) -> {
            pin = "";
            textView.setText(pin);
        });

        for (int id : numeredButtonsIDs) {
            findViewById(id).setOnClickListener((View v) -> {
                String key = ((TextView)v).getText().toString();
                int size = pin.length();
                if (size < 4) {
                    pin += key;
                    textView.setText("****".substring(3-size));
                }
            });
        }
    }

    protected void shuffleKeys(){
        byte[] random = MainActivity.randomBytes(numeredButtonsIDs.length);
        for (int i = 0; i < random.length; ++i) {
            int idx = (random[i] & 0xFF) % 10;
            Button rndButton = ((Button)findViewById(numeredButtonsIDs[idx]));
            Button orig = ((Button)findViewById(numeredButtonsIDs[i]));
            CharSequence txt = rndButton.getText();
            rndButton.setText(orig.getText());
            orig.setText(txt);
        }
    }
}
