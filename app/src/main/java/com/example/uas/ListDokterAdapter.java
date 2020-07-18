package com.example.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.uas.data.model.Dokter;

import java.util.ArrayList;
import java.util.List;

public class ListDokterAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<Dokter> listDokter, filterd;

    public ListDokterAdapter(Context mContext, List<Dokter> listDokter) {
        this.mContext = mContext;
        this.listDokter = listDokter;
        this.filterd = this.listDokter;
    }

    @Override
    public int getCount() {
        return filterd.size();
    }

    @Override
    public Object getItem(int position) {
        return filterd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.item_row_doctor, null);
        }

        TextView txt_nama = convertView.findViewById(R.id.tv_item_name);
        TextView txt_ahli = convertView.findViewById(R.id.tv_item_detail);

        Dokter dokter = filterd.get(position);
        txt_nama.setText(dokter.getNama());
        txt_ahli.setText(dokter.getAhli());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        DokterFilter filter = new DokterFilter();
        return null;
    }

    private class DokterFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Dokter> filteredData = new ArrayList<Dokter>();
            FilterResults result = new FilterResults();
            String filterString = charSequence.toString().toLowerCase();
            for (Dokter dokter: listDokter) {
                if (dokter.getNama().toLowerCase().contains(filterString)) {
                    filteredData.add(dokter);
                }
            }
            result.count = filteredData.size();
            result.values = filteredData;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filterd = (List<Dokter>) results.values;
            notifyDataSetChanged();
        }
    }
}
