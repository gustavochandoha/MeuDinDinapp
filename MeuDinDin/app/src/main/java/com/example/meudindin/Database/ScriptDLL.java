package com.example.meudindin.Database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;



public class ScriptDLL {



    public static String getCreateTableOperacao(){

        StringBuilder sql = new StringBuilder();

            sql.append(" CREATE TABLE IF NOT EXISTS OPERACAO ( ");
            sql.append(" CODIGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
            sql.append(" DATA_OPERACAO DATE NOT NULL," );
            sql.append(" TIPO_OPERACAO VARCHAR(20) NOT NULL, ");
            sql.append(" CLASSIFICACAO_OPERACAO VARCHAR(20) NOT NULL, "); // CREDITO OU DEBITO
            sql.append(" VALOR_OPERACAO DOUBLE NOT NULL" );
            sql.append(");");



            return sql.toString();

    }



}
