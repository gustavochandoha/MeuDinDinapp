package com.example.meudindin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meudindin.dominio.entidades.Operacao;

import java.util.List;
 class OperacaoAdpterExtrato  extends RecyclerView.Adapter<OperacaoAdpterExtrato.ViewHolderOperacao>   {




    private List<Operacao> dados;

    public  OperacaoAdpterExtrato(List<Operacao>dados){
        this.dados = dados;
    }

    @NonNull
    @Override
    public OperacaoAdpterExtrato.ViewHolderOperacao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.linha_extrato,parent,false);

        OperacaoAdpterExtrato.ViewHolderOperacao holderOperacao = new OperacaoAdpterExtrato.ViewHolderOperacao(view);

        return holderOperacao;
    }

     @Override
     public void onBindViewHolder(@NonNull ViewHolderOperacao holder, int position) {
         if((dados != null) && (dados.size() > 0)) {

             Operacao operacao = dados.get(position);

             holder.TxtCDExtrato.setText(operacao.tipo);
             holder.TxtClassificacaoExtrato.setText(operacao.operacao);
             holder.TxtDataExtrato.setText((CharSequence) operacao.data);
             holder.TxtValorExtrato.setText(String.format("%.2f", operacao.valor));
         }
     }



    @Override
    public int getItemCount() {
        return dados.size();
    }


    //Recuperar os dados e colocar no recycler view da activity pesquisar(lista cadastrada)

    public   class ViewHolderOperacao extends RecyclerView.ViewHolder{

        public TextView TxtCDExtrato;
        public TextView TxtDataExtrato;
        public TextView TxtClassificacaoExtrato;
        public TextView TxtValorExtrato;



        public ViewHolderOperacao(@NonNull View itemView) {
            super(itemView);

            TxtCDExtrato =(TextView) itemView.findViewById(R.id.ExtratoCD);
            TxtDataExtrato =(TextView) itemView.findViewById(R.id.ExtratoData);
            TxtClassificacaoExtrato =(TextView) itemView.findViewById(R.id.ExtratoClassificacao);
            TxtValorExtrato = (TextView) itemView.findViewById(R.id.ExtratoValor);



        }
    }

}

