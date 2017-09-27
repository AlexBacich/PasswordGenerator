package com.a1informer.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText passwordField;
    private ProgressBar passwordProgress;
    private TextView info;
    private TextView passwordStrength;
//    private TextView passwordOnlineFast;
//    private TextView passwordOnlineSlow;
//    private TextView passwordOfflineFast;
//    private TextView passwordOfflineSlow;

    // initializing characters set
    private static int length = 6;
    private static boolean lowerCase = true;
    private static boolean upperCase = true;
    private static boolean numbers = false;
    private static boolean special = false;


//    Used for password crack length time. Not used at the moment. To use uncoment and replace content_main.xml with content_main_with_protection.xml

//    public static final String DURATION_SECONDS = "Секунды";
//    public static final String DURATION_MINUTES = "Несколько минут";
//    public static final String DURATION_HOURS = "Несколько часов";
//    public static final String DURATION_DAYS = "Несколько дней";
//    public static final String DURATION_MONTHS = "Месяцы и годы...";

//    private String getReadableTime(double i) {
//        if (i < (60 + 6)) return DURATION_SECONDS;
//        else if (i < 3600 + 6) return DURATION_MINUTES;
//        else if (i < 3600 * 24 + 6) return DURATION_HOURS;
//        else if (i < 3600 * 24 * 30 + 6) return DURATION_DAYS;
//        else return DURATION_MONTHS;
//    }


    private String getReadableScore(int i) {
        switch (i) {
            case 0:
                return getString(R.string.passstrength_weak);
            case 1:
                return getString(R.string.passstrength_fair);
            case 2:
                return getString(R.string.good);
            case 3:
                return getString(R.string.passstrength_strong);
            case 4:
                return getString(R.string.passstrength_verystrong);
            default:
                throw new IllegalArgumentException("Values should be in between 0-4");
        }
    }


    // gets values for password strength measurements and pushes them to UI
    private void makePasswordMeasurementsUpdateFields(String string) {

        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(string);

        passwordStrength.setText(getReadableScore(strength.getScore()));
        Log.d(TAG, "onCreate: password score: " + strength.getScore());

        passwordProgress.setProgress((strength.getScore() + 1));

//        Password crack length time. Not used at the moment. To use uncoment and replace content_main.xml with content_main_with_protection.xml
//        passwordOnlineFast.setText(getReadableTime(strength.getCrackTimeSeconds().getOnlineNoThrottling10perSecond()));
//        passwordOnlineSlow.setText(getReadableTime(strength.getCrackTimeSeconds().getOnlineThrottling100perHour()));
//        passwordOfflineFast.setText(getReadableTime(strength.getCrackTimeSeconds().getOfflineSlowHashing1e4perSecond()));
//        passwordOfflineSlow.setText(getReadableTime(strength.getCrackTimeSeconds().getOfflineFastHashing1e10PerSecond()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passwordField = findViewById(R.id.edit_generated_p);
        info = findViewById(R.id.text_info);
        passwordStrength = findViewById(R.id.text_password_strength);
//        passwordOnlineFast = (TextView) findViewById(R.id.text_online_fast);
//        passwordOnlineSlow = (TextView) findViewById(R.id.text_online_slow);
//        passwordOfflineFast = (TextView) findViewById(R.id.text_offline_fast);
//        passwordOfflineSlow = (TextView) findViewById(R.id.text_offline_slow);
        passwordProgress = findViewById(R.id.progress_password_strength);


        passwordField.setText(PasswordGenerator.generatePassword(length, lowerCase, upperCase, numbers, special));
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                makePasswordMeasurementsUpdateFields(passwordField.getText().toString());
            }
        });

        makePasswordMeasurementsUpdateFields(passwordField.getText().toString());


        info.setText(getString(R.string.info_text, length));

        ImageButton generatePassword = findViewById(R.id.button_generate);
        generatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordField.setText(PasswordGenerator.generatePassword(length, lowerCase, upperCase, numbers, special));
                makePasswordMeasurementsUpdateFields(passwordField.getText().toString());
            }
        });

        ImageButton copyToClipboard = findViewById(R.id.button_copy);
        copyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password", passwordField.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, R.string.copy_to_bufer, Toast.LENGTH_LONG).show();
            }
        });


        TextView webVersion = findViewById(R.id.text_web_version);
        webVersion.setMovementMethod(LinkMovementMethod.getInstance());

        // constants for password min \ max length
        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 33;
        final int STEP = 1;

        SeekBar inputLength = findViewById(R.id.seekBar);
        inputLength.setMax((MAX_LENGTH - MIN_LENGTH) / STEP);
        inputLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                length = MIN_LENGTH + (progress * STEP);
                info.setText(getString(R.string.info_text, length));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        Intent intent;
        switch (id) {
            case R.id.action_tips:
                intent = new Intent(MainActivity.this, TipsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_write:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.sendemail_email)});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.sendemail_subject));
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sendemail_body));
                startActivity(Intent.createChooser(emailIntent, getString(R.string.sendemail_body)));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.check_numbers:
                if (checked) {
                    numbers = true;
                    break;
                } else {
                    numbers = false;
                    break;
                }
            case R.id.check_special:
                if (checked) {
                    special = true;
                    break;
                } else {
                    special = false;
                    break;
                }
        }
    }
}
