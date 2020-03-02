package com.example.passandodadosactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.botao = findViewById(R.id.buttonEnviar);

        this.botao.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                Usuario usuario = new Usuario("João", "joao@hotmail.com");
                //passando dados
                intent.putExtra("nome","Jonas");
                intent.putExtra("idade",31);
                intent.putExtra("objeto", usuario);

                startActivity(intent);
            }
        });
    }
}
