package com.example.meudindin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.meudindin.Database.DadosOpenHelper;
import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase conexao;

    private ConstraintLayout layoutCadastro;

    private DadosOpenHelper dadosOpenHelper;

    private OperacaoRepositorio operacaoRepositorio;

    private Operacao operacao;

    private OperacaoAdpterExtrato OperacaoAdpterExtrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView DashUltimo = findViewById(R.id.DashUltimoLan);
        TextView Dashsaldo = findViewById(R.id.DashSaldo);
        TextView DashCategoria = findViewById(R.id.DashCategoria);

        criarConexao();

        double debitovalor;
        double creditovalor;
        double ultimoLancamento;
        String ultimoLancamentoCategoria;

        String debito = operacaoRepositorio.totalDebitos();

        if(debito !=null) {
             debitovalor = Double.parseDouble(debito);
        }else {
              debitovalor = 0;
        }


        String credito = operacaoRepositorio.totalCreditos();

        if(credito !=null){
            creditovalor = Double.parseDouble(credito);
        }else{
            creditovalor =0;
        }

        if(creditovalor != 0 || debitovalor != 0){
            String ultimoLanc = operacaoRepositorio.UltimoLancamento();
            ultimoLancamento = Double.parseDouble(ultimoLanc);
        }else {
            ultimoLancamento = 0;
        }


        if(ultimoLancamento != 0) {
             ultimoLancamentoCategoria = operacaoRepositorio.UltimoLancamentoCategoria();
        }else {
            ultimoLancamentoCategoria = "Cadastre operacoes";
        }


        double saldoFinalExtrato = creditovalor - debitovalor;

        Dashsaldo.setText("R$" + converterDoubleString(saldoFinalExtrato));
        DashUltimo.setText("R$" + converterDoubleString(ultimoLancamento));
        DashCategoria.setText(ultimoLancamentoCategoria);

        if(saldoFinalExtrato <0){
            Dashsaldo.setTextColor(Color.parseColor("#FF0000"));
        }else{
            Dashsaldo.setTextColor(Color.parseColor("#00FF0C"));
        }

    }

    public void cadastro(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }

    public void ListaPersonalizada(View view){
        Intent intent = new Intent(this, activityPesquisar.class);
        startActivity(intent);
        //   finish();
    }

    public void AbrirExtrato(View view){
        Intent intent = new Intent(this, activity_extrato.class);
        startActivity(intent);
        //   finish();
    }

        public void listaClassificada(View view){
        Intent intent = new Intent(this, acitivity_listaClassificada.class);
        startActivity(intent);
        //   finish();
    }


    public void fecharaplicativo(View view){
        finishAffinity();

    }


    public static String converterDoubleString(double precoDouble) {
        /*Transformando um double em 2 casas decimais*/
        DecimalFormat fmt = new DecimalFormat("0.00");   //limita o número de casas decimais
        String string = fmt.format(precoDouble);
        String[] part = string.split("[.]");
        String preco = part[0]+"."+part[1];
        return preco;
    }


    private void criarConexao() {

        try {
            dadosOpenHelper = new DadosOpenHelper(this);

            conexao = dadosOpenHelper.getWritableDatabase();



            operacaoRepositorio = new OperacaoRepositorio(conexao);


        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();


        }

    }

}

