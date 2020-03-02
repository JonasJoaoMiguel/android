package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class ResultadoActivity extends AppCompatActivity {

    private ImageView imageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        this.imageResult = findViewById(R.id.imageView2);

        int rand = new Random().nextInt(2);

        if ( rand == 0 ) {
            imageResult.setImageResource(R.drawable.moeda_coroa);
        }
    }

    public void voltar(View view) {
        finish();
    }
}
