package com.itdevstar.excelparser;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDataAdapter implements ListAdapter {
    ArrayList<TableDataModel> arrayList;
    Context context;
    public TableDataAdapter(Context context, ArrayList<TableDataModel> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableDataModel data=arrayList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.row_item, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView tvSymbol=convertView.findViewById(R.id.tv_symbol);
            TextView tvEntranceType=convertView.findViewById(R.id.tv_entranceType);
            TextView tvOptionType=convertView.findViewById(R.id.tv_optionType);
            TextView tvStrike = convertView.findViewById(R.id.tv_strike);
            TextView tvOrderDate = convertView.findViewById(R.id.tv_orderDate);
            TextView tvOrderTime = convertView.findViewById(R.id.tv_orderTime);
            TextView tvExpiration = convertView.findViewById(R.id.tv_expiration);
            TextView tvContacts = convertView.findViewById(R.id.tv_contacts);
            TextView tvPremium = convertView.findViewById(R.id.tv_premium);
            TextView tvTotalValue = convertView.findViewById(R.id.tv_totalValue);
            TextView tvStrategy = convertView.findViewById(R.id.tv_strategy);
            TextView tvBearishBullish = convertView.findViewById(R.id.tv_bearish_bullish);

            tvSymbol.setText(data.symbol);
            tvEntranceType.setText(data.entranceType);
            tvOptionType.setText(data.optionType);
            tvStrike.setText(data.strike);
            tvOrderDate.setText(data.orderDate);
            tvOrderTime.setText(data.orderTime);
            tvExpiration.setText(data.expiration);
            tvContacts.setText(data.contacts);
            tvPremium.setText(data.premium);
            tvTotalValue.setText(data.totalValue);
            tvStrategy.setText(data.strategy);
            tvBearishBullish.setText(data.bearish_bullish);
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}
