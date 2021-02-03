package com.example.meudindin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meudindin.Database.DadosOpenHelper;
import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.INVISIBLE;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


   private SQLiteDatabase conexao;

   private ConstraintLayout layoutCadastro;

   private DadosOpenHelper dadosOpenHelper;



   private OperacaoRepositorio operacaoRepositorio;

   private Operacao operacao;

   private OperacaoAdpater operacaoAdpater;


    EditText edittext;
    Button button;
    Calendar calendar;
    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide();



        //CALENDARIO
        //isntanciar o calendario, dia, mes, ano
        calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);


        edittext=(EditText)findViewById(R.id.edittext);
        button=(Button)findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {

                        TextView mascaraData = findViewById(R.id.mascaraData);

                        mascaraData.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                        edittext.setText(year+"-"+(month+1)+"-"+dayOfMonth);

                        edittext.setVisibility(INVISIBLE);

                    }
                }, year, month, day);
                //shows DatePickerDialog
                datePickerDialog.show();

            }
        });


        layoutCadastro = (ConstraintLayout)findViewById(R.id.layoutCadastro);

        //Spinner contendo as variaveis se for selecionado CREDITO

        Spinner spinnerCredito  = findViewById(R.id.spinnerCredito);
        ArrayAdapter<CharSequence> adapterCredito = ArrayAdapter.createFromResource(this,R.array.opcoesCredito, android.R.layout.simple_spinner_dropdown_item);
        adapterCredito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCredito.setAdapter(adapterCredito);
        spinnerCredito.setOnItemSelectedListener(this);
        spinnerCredito.setVisibility(INVISIBLE);


        //Spinner contendo as variaveis se for selecionado DEBITO

        Spinner spinnerDebito  = findViewById(R.id.spinnerDebito);
        ArrayAdapter<CharSequence> adapterDebito = ArrayAdapter.createFromResource(this,R.array.opcoesDebito, android.R.layout.simple_spinner_dropdown_item);
        adapterDebito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDebito.setAdapter(adapterDebito);
        spinnerDebito.setOnItemSelectedListener(this);
        spinnerDebito.setVisibility(INVISIBLE);

        RecyclerView lstDados = (RecyclerView) findViewById(R.id.lstDados);

        RadioGroup RadioGroupCadastro = findViewById(R.id.radioGroupCadastro);
        RadioButton RadioButtonCredito = findViewById(R.id.radioButtonCredito);

        criarConexao();

    }


    public void CreditoSelecionado(View view){

        //AO CLICAR NO RADIOBUTTON ELE DEIXA VISIVEL AS POSSIBILIDADES DE CREDITO NO SPINNER E SETA COMO INVISIVEL O SPINNER DE DEBITO

        Spinner spinnerCredito  = findViewById(R.id.spinnerCredito);
        Spinner spinnerDebito  = findViewById(R.id.spinnerDebito);

        spinnerDebito.setVisibility(INVISIBLE);
        spinnerCredito.setVisibility(View.VISIBLE);



    }



    public void debitoSelecionado(View view){

        //AO CLICAR NO RADIOBUTTON ELE DEIXA VISIVEL AS POSSIBILIDADES DE DEBITO NO SPINNER E SETA COMO INVISIVEL O SPINNER DE CREDTIO

        Spinner spinnerCredito  = findViewById(R.id.spinnerCredito);
        Spinner spinnerDebito  = findViewById(R.id.spinnerDebito);

        spinnerCredito.setVisibility(INVISIBLE);
        spinnerDebito.setVisibility(View.VISIBLE);

    }


      public void cadastrarOperacao(View view) throws ParseException {

          RadioButton radioButtonCredito = findViewById(R.id.radioButtonCredito);
          RadioButton radioButtonDebito = findViewById(R.id.radioButtonDebito);
          edittext = (EditText) findViewById(R.id.edittext);
          EditText valorEditText = findViewById(R.id.editTextNumberValor);

          if (valorEditText.getText().toString().trim().equals("") || valorEditText.getText().toString().trim().equals("")) {


              Toast.makeText(this, "Valores incorretos", Toast.LENGTH_LONG).show();
          } else if (edittext.getText().toString().trim().equals("") || edittext.getText().toString().trim().equals("")) {
              Toast.makeText(this, "Data nao informada", Toast.LENGTH_LONG).show();


          } else if (radioButtonCredito.isChecked() || radioButtonDebito.isChecked()) {
              operacao = new Operacao();


              String data = (edittext.getText().toString().trim());


              Spinner SpinnerCredito = findViewById(R.id.spinnerCredito);
              Spinner SpinnerDebito = findViewById(R.id.spinnerDebito);




              operacao.data = data;


              String operacaocheck;

              if (radioButtonCredito.isChecked()) {

                  operacaocheck = "Credito";
                  operacao.tipo = operacaocheck;


              } else {
                  operacaocheck = "Debito";
                  operacao.tipo = operacaocheck;
              }


              Double valor = Double.parseDouble(valorEditText.getText().toString());

              //  Double valor  = Double.parseDouble(valorEditText.getText().toString());


              if (SpinnerCredito.getVisibility() == View.VISIBLE) {

                  // Its visible
                  String tipoOperacao = SpinnerCredito.getSelectedItem().toString();
                  operacao.operacao = tipoOperacao;

              } else {
                  String tipoOperacao = SpinnerDebito.getSelectedItem().toString();
                  // Either gone or invisible
                  operacao.operacao = tipoOperacao;
              }

              operacao.valor = valor;


              try {

                  operacaoRepositorio.inserir(operacao);

                  Toast.makeText(this, "Cadastro feito!", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(this, MainActivity.class);
                  startActivity(intent);
                  finish();

              } catch (SQLException ex) {

                  AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                  dlg.setTitle("deu errado");
                  dlg.setMessage(ex.getMessage());
                  dlg.setNeutralButton("ok", null);
                  dlg.show();
              }


          } else {

              Toast.makeText(this, "Selecione Debito ou Credito", Toast.LENGTH_LONG).show();

          }
      }


    public void voltarCadastro(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    }

