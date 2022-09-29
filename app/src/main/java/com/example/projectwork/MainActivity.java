package com.example.projectwork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textview;
    private TextToSpeech t2sp;
    private Button button;
    private int x,y,ans;

    String userans="";
    private int count=0;
    private final int RESULT_SPEECH=1;
    private TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        button=findViewById(R.id.button);
        //-------------------------------------------------
        t2sp=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    t2sp.setLanguage(Locale.UK);
                }
            }
        });
       //-----------------------------------------------

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
//                     display the question
                        displayquestion();
                    }
                };
                ans=x*y;
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
//                     inputs answer from user
                        answerinput();
                    }
                };
                Runnable runnable3 = new Runnable() {
                    @Override
                    public void run() {
//                     posses the result and reply correct or incorrect
                        possesresult(ans);
                    }
                };
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(runnable, 1000);
                handler.postDelayed(runnable2, 6000);
                handler.postDelayed(runnable3, 13000);
            }

            private void possesresult(int read) {
//                if(read==1 && (userans.equalsIgnoreCase("one") || userans=="1")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//
//                else if(read==2 && (userans.equalsIgnoreCase("two") || userans=="2")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==3 && (userans.equalsIgnoreCase("three") || userans=="3")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==4 && (userans.equalsIgnoreCase("four") || userans=="4")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==5 && (userans.equalsIgnoreCase("five") || userans=="5")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==6 && (userans.equalsIgnoreCase("six") || userans=="6")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==7 && (userans.equalsIgnoreCase("seven") || userans=="7")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==8 && (userans.equalsIgnoreCase("eight") || userans=="8")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==9 && (userans.equalsIgnoreCase("nine") || userans=="9")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else if(read==10 && (userans.equalsIgnoreCase("ten") || userans=="10")){
//                    t2sp.speak("Correct answer",TextToSpeech.QUEUE_FLUSH,null);
//                }
//                else{
//                    t2sp.speak("Incorrect",TextToSpeech.QUEUE_FLUSH,null);
//                }
                if(read==Integer.valueOf(userans))
                    t2sp.speak("Incorrect",TextToSpeech.QUEUE_FLUSH,null);
                else
                    t2sp.speak("correct",TextToSpeech.QUEUE_FLUSH,null);
            }

            private void answerinput() {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    tv2.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            private void displayquestion() {
                x = 1 + (int) (Math.random() * 10);
                y = 1 + (int) (Math.random() * 10);
                textview.setText(x+" X "+y);
                String temp=x+"Multiply"+y;
                t2sp.speak(temp,TextToSpeech.QUEUE_FLUSH,null);

            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv2.setText(text.get(0));
                    userans=text.get(0);
                }
                break;
        }
    }
}