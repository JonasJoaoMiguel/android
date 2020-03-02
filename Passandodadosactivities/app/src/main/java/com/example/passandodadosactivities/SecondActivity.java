package com.example.passandodadosactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textNome, textIdade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.textNome = findViewById(R.id.textNome);
        this.textIdade = findViewById((R.id.textIdade));

        //Recuperar dados enviados
        Bundle dados = getIntent().getExtras();
        String nome = dados.getString("nome");
        int idade = dados.getInt("idade");
        Usuario usuario = (Usuario) dados.getSerializable("objeto");

        //Configurar valores recuperados
        this.textNome.setText( usuario.getNome() );
        this.textIdade.setText( String.valueOf(idade));


    }
}
