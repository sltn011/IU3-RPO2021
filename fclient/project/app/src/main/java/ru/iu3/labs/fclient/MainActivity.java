package ru.iu3.labs.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("mbedcrypto");
        initRng();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonClickMe = findViewById(R.id.buttonClickMe);
        buttonClickMe.setOnClickListener(
                (View) -> {
                        Toast.makeText(getBaseContext(), "Thanks for clicking me", Toast.LENGTH_SHORT).show();
                }
        );

        Button buttonHexTest = findViewById(R.id.hexTest);
        buttonHexTest.setOnClickListener(
                (View) -> {
                        byte[] key = stringToHex("0123AB##CD1112XD1234ABCD9876KLMN");
                        byte[] data = stringToHex("abcdef00abcdef11");
                        byte[] encrypted = encrypt(key, data);
                        byte[] decrypted = decrypt(key, encrypted);
                        String str = new String(Hex.encodeHex(decrypted)).toUpperCase();
                        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
                }
        );

        Button pinButton = findViewById(R.id.pinActivityKey);
        pinButton.setOnClickListener(
                (View) -> {
                    Intent intent = new Intent(getBaseContext(), PinPadActivity.class);
                    startActivityForResult(intent, 0);
                }
        );

        Button httpClientTestButton = findViewById(R.id.htmlTest);
        httpClientTestButton.setOnClickListener(
                (View) -> {
                    testHttpClient();
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK || data != null) {
                String pin = data.getStringExtra("pin");
                Toast.makeText(this, pin, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static byte[] stringToHex(String s) {
        byte hex[];
        try {
            hex = Hex.decodeHex(s.toCharArray());
        } catch (DecoderException e) {
            hex = null;
        }
        return hex;
    }

    protected void testHttpClient(){
        new Thread(() -> {
           try {
               URL url = new URL("http://10.0.2.2:11237/api/v1/title");
               HttpURLConnection urlConnection = (HttpURLConnection) (url.openConnection());
               InputStream inputStream = urlConnection.getInputStream();
               String html = IOUtils.toString(inputStream);
               String title = getPageTitle(html);
               runOnUiThread(() ->{
                   Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
               });
           } catch (MalformedURLException e) {
               Log.e("fapptag", "Malformed URL in http client test", e);
           } catch (IOException e) {
               Log.e("fapptag", "IO exception in http client test", e);
           }
        }).start();
    }

    protected String getPageTitle(String htmlCode) {
        Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(htmlCode);
        String result;
        if(matcher.find()) {
            result = matcher.group(1);
        }
        else {
            result = "not found";
        }
        return result;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public static native int initRng();
    public static native byte[] randomBytes(int n);

    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);
}