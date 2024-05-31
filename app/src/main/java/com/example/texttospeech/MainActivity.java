package com.example.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.texttospeech.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    int imageList[] = {R.drawable.horse,R.drawable.cow};

    int data[] = {R.string.img1_txt,R.string.img2_txt};

    int count=imageList.length;
    int currentIndex = 0;


    int i ;

    TextToSpeech tts;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        binding.imageSwitcher.setImageResource(imageList[0]);
        binding.text.setText(data[0]);
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.searchBar.getText().toString().toLowerCase().equals("horse")){
                    binding.imageSwitcher.setImageResource(imageList[0]);
                    binding.text.setText(data[0]);
                }
                else if (binding.searchBar.getText().toString().toLowerCase().equals("cow"))
                {
                    binding.imageSwitcher.setImageResource(imageList[1]);
                    binding.text.setText(data[1]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.stop();
                i=0;
                binding.imageSwitcher.setInAnimation(MainActivity.this,R.anim.from_right);
                binding.imageSwitcher.setOutAnimation(MainActivity.this,R.anim.to_left);
                --currentIndex;
                if (currentIndex<0){
                    currentIndex=imageList.length-1;

                }
                binding.imageSwitcher.setImageResource(imageList[currentIndex]);
                binding.text.setText(data[currentIndex]);

            }
        });

        binding.arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.stop();
                i=0;
                binding.imageSwitcher.setInAnimation(MainActivity.this,R.anim.from_left);
                binding.imageSwitcher.setOutAnimation(MainActivity.this,R.anim.to_right);
                currentIndex++;
                if (currentIndex==count){
                    currentIndex=0;

                }
                binding.imageSwitcher.setImageResource(imageList[currentIndex]);
                binding.text.setText(data[currentIndex]);
            }
        });

        i=0;

        binding.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i%2==0) {
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            Toast.makeText(MainActivity.this, "Please wait..!!", Toast.LENGTH_SHORT).show();
                            if (status == TextToSpeech.SUCCESS) {
                                tts.setLanguage(Locale.ENGLISH);
                                tts.setSpeechRate(1.0f);
                                tts.speak(binding.text.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                            }
                        }


                    });
                }else {
                    tts.stop();
                    Toast.makeText(MainActivity.this, "Playback stopped..!!", Toast.LENGTH_SHORT).show();
                }
                i++;
            }
        });







    }
}