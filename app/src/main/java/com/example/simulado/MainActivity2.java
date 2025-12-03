package com.example.simulado;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    private ImageView imageView;
    private TextView txtResponse;
    private TextView txtSaldoAtual;
    private TextView txtTotalCreditos;
    private TextView txtTotalDebitos;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        inicializarComponentes();
        recuperarDados();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // https://developer.android.com/guide/components/activities/activity-lifecycle?hl=pt-br
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarComponentes() {
        imageView = findViewById(R.id.imageView);
        txtResponse = findViewById(R.id.txtResponse);
        txtSaldoAtual = findViewById(R.id.txtSaldoAtual);
        txtTotalCreditos = findViewById(R.id.txtTotalCreditos);
        txtTotalDebitos = findViewById(R.id.txtTotalDebitos);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void recuperarDados() {
        Intent intent = getIntent();

        try {
            float saldoAtual = intent.getFloatExtra("saldoAtual", 0);
            String tipoOperacao = intent.getStringExtra("tipoOperacao");
            float valorOperacao = intent.getFloatExtra("valorOperacao", 0);
            String rotulo = intent.getStringExtra("rotulo");
            float totalCreditos = intent.getFloatExtra("totalCreditos", 0);
            float totalDebitos = intent.getFloatExtra("totalDebitos", 0);

            mostrarDados(saldoAtual, tipoOperacao, valorOperacao, rotulo, totalCreditos, totalDebitos);
        } catch (Exception e) {
            Toast.makeText(MainActivity2.this, "Ocorreu um erro ao recuperar os dados", Toast.LENGTH_SHORT).show();
            Log.e("ERRO", "Exception: " + e.getMessage());
        }
    }

    private void mostrarDados(float saldoAtual, String tipoOperacao, float valorOperacao, String rotulo, float totalCreditos, float totalDebitos) {
//        Log.d("DADOS", "saldoAtual: " + saldoAtual);
//        Log.d("DADOS", "tipoOperacao: " + tipoOperacao);
//        Log.d("DADOS", "valorOperacao: " + valorOperacao);
//        Log.d("DADOS", "rotulo: " + rotulo);
//        Log.d("DADOS", "totalCreditos: " + totalCreditos);
//        Log.d("DADOS", "totalDebitos: " + totalDebitos);

        txtSaldoAtual.setText("Saldo atual: R$" + String.format("%.2f", saldoAtual));
        txtResponse.setText("Operação: " + tipoOperacao + " - " + rotulo + " R$" + String.format("%.2f", valorOperacao));
        txtTotalDebitos.setText("Total de Débitos: " + String.format("%.2f", totalDebitos));
        txtTotalCreditos.setText("Total de Créditos: " + String.format("%.2f", totalCreditos));

        if(tipoOperacao != null) {
            switch (tipoOperacao) {
                case "Débito":
                    imageView.setImageResource(R.drawable.debito);
                    break;
                case "Crédito":
                    imageView.setImageResource(R.drawable.credito);
                    break;
            }
        } else {
            imageView.setImageResource(R.drawable.btc);
        }

        imageView.setMaxHeight(300);
        imageView.setMaxWidth(300);
    }
}