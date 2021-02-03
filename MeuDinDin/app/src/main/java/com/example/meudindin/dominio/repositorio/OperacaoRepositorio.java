package com.example.meudindin.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.meudindin.activityPesquisar;
import com.example.meudindin.dominio.entidades.Operacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperacaoRepositorio {


    private SQLiteDatabase conexao;

    private activityPesquisar listaPersonalisada;

    public OperacaoRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }


    public void inserir(Operacao operacao) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("DATA_OPERACAO", String.valueOf(operacao.data));
        contentValues.put("TIPO_OPERACAO", operacao.operacao);      //SAUDE, TRANSFERENCIAS, SALARIO, OUTROS
        contentValues.put("VALOR_OPERACAO", operacao.valor);
        contentValues.put("CLASSIFICACAO_OPERACAO", operacao.tipo);  // CREDITO OU DEBITO

        conexao.insertOrThrow("OPERACAO", null, contentValues);

    }


    public void excluir(int codigo) {


    }

    public void alterar(Operacao operacao) {


    }

    public List<Operacao> buscarTodos() throws Exception {

        List<Operacao> operacaos = new ArrayList<Operacao>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODIGO, CLASSIFICACAO_OPERACAO , DATA_OPERACAO , TIPO_OPERACAO,VALOR_OPERACAO ");
        sql.append(" FROM OPERACAO ORDER BY DATA_OPERACAO DESC ");




        Cursor resultdo = conexao.rawQuery(sql.toString(), null);


        if (resultdo.getCount() > 0) {
            resultdo.moveToFirst();


            do {

                Operacao opc = new Operacao();

                String data1 = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data1);

                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

                String convertidodata = fmt.format(date1);



                opc.codigo = resultdo.getInt(resultdo.getColumnIndexOrThrow("CODIGO"));
              //  opc.data = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));

                opc.data = convertidodata;

                opc.operacao = resultdo.getString(resultdo.getColumnIndexOrThrow("TIPO_OPERACAO"));
                opc.valor = resultdo.getDouble(resultdo.getColumnIndexOrThrow("VALOR_OPERACAO"));
                opc.tipo = resultdo.getString(resultdo.getColumnIndexOrThrow("CLASSIFICACAO_OPERACAO"));

                operacaos.add(opc);


            } while (resultdo.moveToNext());


        }

        return operacaos;

    }

    public List<Operacao> buscarListaClassificada() {

        List<Operacao> operacaos = new ArrayList<Operacao>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  CLASSIFICACAO_OPERACAO, TIPO_OPERACAO, SUM(VALOR_OPERACAO)as VALOR_OPERACAO FROM OPERACAO GROUP BY TIPO_OPERACAO ORDER BY  VALOR_OPERACAO DESC ");




        Cursor resultdo = conexao.rawQuery(sql.toString(), null);


        if (resultdo.getCount() > 0) {
            resultdo.moveToFirst();


            do {

                Operacao opc = new Operacao();

             //   opc.codigo = resultdo.getInt(resultdo.getColumnIndexOrThrow("CODIGO"));
              //  opc.data = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));
                opc.operacao = resultdo.getString(resultdo.getColumnIndexOrThrow("TIPO_OPERACAO"));
                opc.valor = resultdo.getDouble(resultdo.getColumnIndexOrThrow("VALOR_OPERACAO"));
                opc.tipo = resultdo.getString(resultdo.getColumnIndexOrThrow("CLASSIFICACAO_OPERACAO"));

                operacaos.add(opc);


            } while (resultdo.moveToNext());


        }

        return operacaos;

    }

    public Operacao buscarOperacao(int codigo) {

        return null;
    }

    public List<Operacao> buscarFiltrado(String sql) throws Exception {
        List<Operacao> operacaos = new ArrayList<Operacao>();


        StringBuilder sqlnew = new StringBuilder(sql);
        //  StringBuilder sql = new StringBuilder();


        Cursor resultdo = conexao.rawQuery(sqlnew.toString(), null);


        if (resultdo.getCount() > 0) {
            resultdo.moveToFirst();


            do {

                Operacao opc = new Operacao();

                String data1 = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data1);

                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

                String convertidodata = fmt.format(date1);









                opc.codigo = resultdo.getInt(resultdo.getColumnIndexOrThrow("CODIGO"));

             //  opc.data = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));

               opc.data = convertidodata;

                opc.operacao = resultdo.getString(resultdo.getColumnIndexOrThrow("TIPO_OPERACAO"));
                opc.valor = resultdo.getDouble(resultdo.getColumnIndexOrThrow("VALOR_OPERACAO"));
                opc.tipo = resultdo.getString(resultdo.getColumnIndexOrThrow("CLASSIFICACAO_OPERACAO"));

                operacaos.add(opc);


            } while (resultdo.moveToNext());


        }

        return operacaos;
    }

    public String totalDebitos() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUM(VALOR_OPERACAO) FROM  OPERACAO WHERE CLASSIFICACAO_OPERACAO ='Debito' ORDER BY CODIGO DESC LIMIT 15; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

}

    public String totalCreditos() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUM(VALOR_OPERACAO) FROM  OPERACAO WHERE CLASSIFICACAO_OPERACAO ='Credito' ORDER BY CODIGO DESC LIMIT 15; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

    }

    public String GastosCategoriaDebito() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUM(VALOR_OPERACAO) FROM  OPERACAO WHERE CLASSIFICACAO_OPERACAO ='Debito' ORDER BY CODIGO DESC; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

    }

    public String GastosCategoriaCredito() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUM(VALOR_OPERACAO) FROM  OPERACAO WHERE CLASSIFICACAO_OPERACAO ='Credito' ORDER BY CODIGO DESC; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

    }


    public String UltimoLancamento() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT VALOR_OPERACAO FROM  OPERACAO ORDER BY CODIGO DESC LIMIT 1; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

    }

    public String UltimoLancamentoCategoria() {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT TIPO_OPERACAO FROM  OPERACAO ORDER BY CODIGO DESC LIMIT 1; ");

        Cursor resultdo = conexao.rawQuery(sql.toString(), null);
        resultdo.moveToFirst();

        String teste;

        teste = resultdo.getString(0);

        return teste;

    }


    public List<Operacao> buscarExtrato() throws Exception {

        List<Operacao> operacaos = new ArrayList<Operacao>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODIGO, CLASSIFICACAO_OPERACAO , DATA_OPERACAO , TIPO_OPERACAO,VALOR_OPERACAO ");
        sql.append(" FROM OPERACAO ORDER BY DATA_OPERACAO DESC LIMIT 15 ");




        Cursor resultdo = conexao.rawQuery(sql.toString(), null);


        if (resultdo.getCount() > 0) {
            resultdo.moveToFirst();


            do {

                Operacao opc = new Operacao();

                String data1 = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(data1);

                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

                String convertidodata = fmt.format(date1);



                opc.codigo = resultdo.getInt(resultdo.getColumnIndexOrThrow("CODIGO"));
                //  opc.data = resultdo.getString(resultdo.getColumnIndexOrThrow("DATA_OPERACAO"));

                opc.data = convertidodata;

                opc.operacao = resultdo.getString(resultdo.getColumnIndexOrThrow("TIPO_OPERACAO"));
                opc.valor = resultdo.getDouble(resultdo.getColumnIndexOrThrow("VALOR_OPERACAO"));
                opc.tipo = resultdo.getString(resultdo.getColumnIndexOrThrow("CLASSIFICACAO_OPERACAO"));

                operacaos.add(opc);


            } while (resultdo.moveToNext());


        }

        return operacaos;

    }




}
