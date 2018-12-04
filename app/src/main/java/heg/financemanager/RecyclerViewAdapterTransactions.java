package heg.financemanager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapterTransactions extends RecyclerView.Adapter<RecyclerViewAdapterTransactions.ViewHolderTransactions>{

    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mComptes = new ArrayList<>();
    private ArrayList<String> mCategories = new ArrayList<>();
    private ArrayList<Double> mMontants = new ArrayList<>();


    private Context mContext;

    public RecyclerViewAdapterTransactions(Context mContext,ArrayList<String> mDates, ArrayList<String> mComptes,ArrayList<String> mCategories,ArrayList<Double> mMontants) {
        this.mContext = mContext;
        this.mDates = mDates;
        this.mComptes = mComptes;
        this.mCategories = mCategories;
        this.mMontants = mMontants;
    }

    @NonNull
    @Override
    public ViewHolderTransactions onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_transactions_listitem, viewGroup, false);
        return new ViewHolderTransactions(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactions viewHolderTransactions, int i) {
        viewHolderTransactions.date.setText(mDates.get(i));
        viewHolderTransactions.compte.setText(mComptes.get(i));
        viewHolderTransactions.categorie.setText(mCategories.get(i));

        TextView montant = viewHolderTransactions.itemView.findViewById(R.id.montant);
        if(mMontants.get(i) < 0){
            montant.setTextColor(Color.parseColor("#d81b60"));
        }else{
            montant.setTextColor(Color.parseColor("#008577"));
        }
        viewHolderTransactions.montant.setText("CHF " + String.format("%.2f",mMontants.get(i)));
    }


    @Override
    public int getItemCount() {
        return mDates.size();
    }


    public class ViewHolderTransactions extends RecyclerView.ViewHolder{
        TextView date;
        TextView compte;
        TextView categorie;
        TextView montant;

        public ViewHolderTransactions(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            compte = itemView.findViewById(R.id.compte);
            categorie = itemView.findViewById(R.id.categorie);
            montant = itemView.findViewById(R.id.montant);
        }
    }
}