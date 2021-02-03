package com.example.meudindin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.meudindin.Database.DadosOpenHelper;
import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import static android.view.View.INVISIBLE;

public class activityPesquisar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SQLiteDatabase conexao;

    private ConstraintLayout layoutCadastro;

    private DadosOpenHelper dadosOpenHelper;

    private OperacaoRepositorio operacaoRepositorio;

    private Operacao operacao;

    private OperacaoAdpater operacaoAdpater;

    EditText edittextDataFim;
    EditText edittextDataInicio;
    Calendar calendar;
    int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);



        //CALENDARIO
        //getting calendar instance
        calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        //accessing EditText and Button
        edittextDataInicio=(EditText)findViewById(R.id.edittextDataInicio);
        edittextDataFim=(EditText)findViewById(R.id.edittextDataFim);


        RecyclerView lstDados = (RecyclerView) findViewById(R.id.lstDados);

        criarConexao();

        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lstDados.setLayoutManager(linearLayoutManager);

        operacaoRepositorio = new OperacaoRepositorio(conexao);


        List<Operacao> dados = null;
        try {
            dados = operacaoRepositorio.buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        operacaoAdpater = new OperacaoAdpater(dados);

        lstDados.setAdapter(operacaoAdpater);

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


    public void abrirCalendarioInicio(View view)
    {

        DatePickerDialog datePickerDialog = new DatePickerDialog(activityPesquisar.this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                //sets date in EditText

               edittextDataInicio.setText(year+"-"+(month+1)+"-"+dayOfMonth);




            }
        }, year, month, day);
        //shows DatePickerDialog
        datePickerDialog.show();

    };

    public void abrirCalendarioFim(View view)
    {

        DatePickerDialog datePickerDialog = new DatePickerDialog(activityPesquisar.this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                //sets date in EditText

                edittextDataFim.setText(year+"-"+(month+1)+"-"+dayOfMonth);




            }
        }, year, month, day);
        //shows DatePickerDialog
        datePickerDialog.show();

    };


    public void ClicarFiltrar(View view) throws Exception {


        RadioButton radioButtonCreditoPesquisa = findViewById(R.id.radioButtonCreditoPesquisa);
        RadioButton radioButtonD = findViewById(R.id.radioButtonD);
        RadioButton radioButtonAmbos = findViewById(R.id.radioButtonAmbos);



        String dataInicio=(edittextDataInicio.getText().toString().trim());
        String dataFim=(edittextDataFim.getText().toString().trim());

        String operacaocheck;





        if(radioButtonCreditoPesquisa.isChecked()){

            operacaocheck = "Credito";



        }else if (radioButtonD.isChecked()){
            operacaocheck = "Debito";

        }else if(radioButtonD.isChecked()){

            operacaocheck = "";

        }




        String query = "";
        if(radioButtonAmbos.isChecked()) {


            query = "SELECT CODIGO, CLASSIFICACAO_OPERACAO ,DATA_OPERACAO, TIPO_OPERACAO,VALOR_OPERACAO FROM OPERACAO WHERE DATA_OPERACAO BETWEEN " + "'" + dataInicio + "'" + " AND " + "'" + dataFim + "'" + "ORDER BY DATA_OPERACAO DESC";

        }

        if(radioButtonCreditoPesquisa.isChecked()){
             query = "SELECT * FROM OPERACAO WHERE DATA_OPERACAO BETWEEN " + "'" + dataInicio + "'" + " AND " + "'" + dataFim + "'" + " AND CLASSIFICACAO_OPERACAO ='Credito' ORDER BY DATA_OPERACAO DESC" ;
        }

        if(radioButtonD.isChecked()){

            query = "SELECT * FROM OPERACAO WHERE DATA_OPERACAO BETWEEN " + "'" + dataInicio + "'" + " AND " + "'" + dataFim + "'" + " AND CLASSIFICACAO_OPERACAO ='Debito' ORDER BY DATA_OPERACAO DESC" ;

        }



        RecyclerView lstDados = (RecyclerView) findViewById(R.id.lstDados);

        criarConexao();

        lstDados.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lstDados.setLayoutManager(linearLayoutManager);

        operacaoRepositorio = new OperacaoRepositorio(conexao);

        List<Operacao> dados = operacaoRepositorio.buscarFiltrado(query);

        operacaoAdpater = new OperacaoAdpater(dados);

        lstDados.setAdapter(operacaoAdpater);



    }

    public void voltarPesquisa(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}