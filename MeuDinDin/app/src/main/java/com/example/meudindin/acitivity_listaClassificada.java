package com.example.meudindin;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meudindin.Database.DadosOpenHelper;
import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;

import java.util.List;

public class acitivity_listaClassificada extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    private SQLiteDatabase conexao;

    private ConstraintLayout layoutCadastro;

    private DadosOpenHelper dadosOpenHelper;

    private OperacaoRepositorio operacaoRepositorio;

    private Operacao operacao;

    private OperacaoAdpterExtrato OperacaoAdpterExtrato;

    String query = "SELECT * FROM OPERACAO WHERE TIPO_OPERACAO = 'Salário' ORDER BY VALOR_OPERACAO DESC" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_lista_classificada);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        RecyclerView lstDadosListaClassificada = (RecyclerView) findViewById(R.id.lstDadosListaClassificada);

        criarConexao();



        lstDadosListaClassificada.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lstDadosListaClassificada.setLayoutManager(linearLayoutManager);

        operacaoRepositorio = new OperacaoRepositorio(conexao);

        List<Operacao> dados = operacaoRepositorio.buscarListaClassificada();

        OperacaoAdpterExtrato = new OperacaoAdpterExtrato(dados);

        lstDadosListaClassificada.setAdapter(OperacaoAdpterExtrato);


        TextView GastosCategoria = findViewById(R.id.GastosCategoria);
        TextView RecebidosCategoria = findViewById(R.id.RecebidosCategoria);
        TextView SaldoCategoria = findViewById(R.id.SaldoCategoria);

        GastosCategoria.setText("R$ " + operacaoRepositorio.GastosCategoriaDebito());
        RecebidosCategoria.setText("R$ " + operacaoRepositorio.GastosCategoriaCredito());

        double debitovalor;
        String debito = operacaoRepositorio.GastosCategoriaDebito();
        if(debito !=null){
             debitovalor = Double.parseDouble(debito);
        }else{
             debitovalor = 0;
        }


        double creditovalor;
        String credito = operacaoRepositorio.GastosCategoriaCredito();

        if(credito !=null){
            creditovalor = Double.parseDouble(credito);
        }else {
            creditovalor = 0;
        }


        double saldoFinalListaClassificada = creditovalor - debitovalor;

        SaldoCategoria.setText("R$ " + String.valueOf(saldoFinalListaClassificada));



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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void voltarListaClassifacada(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}