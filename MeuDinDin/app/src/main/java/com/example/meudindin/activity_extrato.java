package com.example.meudindin;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.meudindin.Database.DadosOpenHelper;
import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;


public class activity_extrato extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SQLiteDatabase conexao;

    private ConstraintLayout layoutCadastro;

    private DadosOpenHelper dadosOpenHelper;

    private OperacaoRepositorio operacaoRepositorio;

    private Operacao operacao;

    private OperacaoAdpterExtrato OperacaoAdpterExtrato;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        RecyclerView lstDadosExtrato = (RecyclerView) findViewById(R.id.lstDadosExtrato);

        criarConexao();


        lstDadosExtrato.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lstDadosExtrato.setLayoutManager(linearLayoutManager);

        operacaoRepositorio = new OperacaoRepositorio(conexao);

        List<Operacao> dados = null;
        try {
            dados = operacaoRepositorio.buscarExtrato();
        } catch (Exception e) {
            e.printStackTrace();
        }

        OperacaoAdpterExtrato = new OperacaoAdpterExtrato(dados);

        lstDadosExtrato.setAdapter(OperacaoAdpterExtrato);

        TextView debitosExtrato = findViewById(R.id.debitosExtrato);
        TextView creditosExtrato = findViewById(R.id.creditoExtrato);
        TextView saldoFinal = findViewById(R.id.saldoExtrato);

        debitosExtrato.setText(operacaoRepositorio.totalDebitos());
        creditosExtrato.setText(operacaoRepositorio.totalCreditos());

        double debitovalor;
        String debito = operacaoRepositorio.totalDebitos();
        if(debito !=null){
             debitovalor = Double.parseDouble(debito);
        }else{
            debitovalor = 0;
        }


        double creditovalor;
        String credito = operacaoRepositorio.totalCreditos();

        if(credito !=null){
             creditovalor = Double.parseDouble(credito);
        }else {
            creditovalor = 0;
        }








        double saldoFinalExtrato = creditovalor - debitovalor;

        saldoFinal.setText(converterDoubleString(saldoFinalExtrato));

       // saldoFinal.setText(String.valueOf(saldoFinalExtrato));







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


    public static String converterDoubleString(double precoDouble) {
        /*Transformando um double em 2 casas decimais*/
        DecimalFormat fmt = new DecimalFormat("0.00");   //limita o número de casas decimais
        String string = fmt.format(precoDouble);
        String[] part = string.split("[.]");
        String preco = part[0]+"."+part[1];
        return preco;
    }

    public static double converterDoubleDoisDecimais(double precoDouble) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(precoDouble);
        String[] part = string.split("[,]");
        String string2 = part[0]+"."+part[1];
        double preco = Double.parseDouble(string2);
        return preco;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void voltarExtrato(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}