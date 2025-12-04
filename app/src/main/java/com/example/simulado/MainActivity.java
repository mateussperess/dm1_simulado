package com.example.simulado;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText etSaldoInicial;
    private EditText etValorOp;
    private EditText etRotuloOp;
    private RadioGroup radioGroup;
    private RadioButton rdBtnDebito;
    private RadioButton rdBtnCredito;
    private RadioButton rdBtnSelecionado;
    private TextView tvSaldoAtual;
    private TextView tvUltimaOp;
    private Button btnInserir;

    private float saldoAtual = 0;
    private float totalCreditos = 0;
    private float totalDebitos = 0;
    private boolean primeiraOperacao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        limparCamposAoClicar();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                rdBtnSelecionado = findViewById(radioGroup.getCheckedRadioButtonId());
            }
        });

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etSaldoInicial.getText().toString().isEmpty()) {
                        throw new Exception("Informe o saldo inicial!");
                    }
                    if (etValorOp.getText().toString().isEmpty()) {
                        throw new Exception("Informe o valor da operação!");
                    }
                    if (etRotuloOp.getText().toString().isEmpty()) {
                        throw new Exception("Informe o rótulo!");
                    }
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        throw new Exception("Selecione Crédito ou Débito!");
                    }

                    float saldoInicial = Float.parseFloat(etSaldoInicial.getText().toString());
                    float valorOperacao = Float.parseFloat(etValorOp.getText().toString());
                    String rotulo = etRotuloOp.getText().toString();
                    String tipoOperacao = rdBtnSelecionado.getText().toString();
//
//                    Log.d("DADOS", "Saldo Inicial: " + saldoInicial);
//                    Log.d("DADOS", "Valor Operação: " + valorOperacao);
//                    Log.d("DADOS", "Rótulo: " + rotulo);
//                    Log.d("DADOS", "Tipo: " + tipoOperacao);

                    if (primeiraOperacao) {
                        saldoAtual = saldoInicial;
                        primeiraOperacao = false;
                    }

                    if(tipoOperacao.equals("Débito")) {
                        saldoAtual -= valorOperacao;
                        totalDebitos += valorOperacao;
//                        Log.d("CALCULO", "Débito aplicado. Novo saldo: " + saldoAtual);
                    } else if (tipoOperacao.equals("Crédito")) {
                        saldoAtual += valorOperacao;
                        totalCreditos += valorOperacao;
//                        Log.d("CALCULO", "Cŕedito aplicado. Novo saldo: " + saldoAtual);
                    }

                    tvSaldoAtual.setText("Saldo atual: R$ " + String.format("%.2f", saldoAtual));
                    tvUltimaOp.setText("Última operação: " + tipoOperacao + " - " + rotulo + " - R$ " + String.format("%.2f", valorOperacao));

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("saldoInicial", saldoInicial);
                    intent.putExtra("saldoAtual", saldoAtual);
                    intent.putExtra("tipoOperacao", tipoOperacao);
                    intent.putExtra("valorOperacao", valorOperacao);
                    intent.putExtra("rotulo", rotulo);
                    intent.putExtra("totalCreditos", totalCreditos);
                    intent.putExtra("totalDebitos", totalDebitos);
                    startActivity(intent);

                    etValorOp.setText("");
                    etRotuloOp.setText("");
                    radioGroup.clearCheck();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Erro: Valor inválido!", Toast.LENGTH_SHORT).show();
//                    Log.e("ERRO", "NumberFormatException: " + e.getMessage());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("ERRO", "Exception: " + e.getMessage());
                }
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarComponentes() {
        etSaldoInicial = findViewById(R.id.etSaldoInicial);
        etValorOp = findViewById(R.id.etValorOp);
        etRotuloOp = findViewById(R.id.etRotuloOp);
        radioGroup = findViewById(R.id.radioGroup);
        rdBtnDebito = findViewById(R.id.rdBtnDebito);
        rdBtnCredito = findViewById(R.id.rdBtnCredito);
        tvSaldoAtual = findViewById(R.id.tvSaldoAtual);
        tvUltimaOp = findViewById(R.id.tvUltimaOp);
        btnInserir = findViewById(R.id.btnInserir);
    }

    private void limparCamposAoClicar() {
        etSaldoInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSaldoInicial.setText("");
            }
        });

        etValorOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etValorOp.setText("");
            }
        });

        etRotuloOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRotuloOp.setText("");
            }
        });
    }
}