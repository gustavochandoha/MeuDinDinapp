package com.example.meudindin.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.meudindin.dominio.entidades.Operacao;
import com.example.meudindin.dominio.repositorio.OperacaoRepositorio;

public class DadosOpenHelper extends SQLiteOpenHelper {



    public DadosOpenHelper(@Nullable Context context) {
        super(context, "DADOS", null, 2);


        //PARA DELETAR O BANCO DE DADOS
       //context.deleteDatabase("DADOS");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(ScriptDLL.getCreateTableOperacao());


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
