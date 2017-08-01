package com.example.ishaandhamija.transformer.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ishaandhamija.transformer.Models.PDFFile;
import com.example.ishaandhamija.transformer.R;

import java.util.ArrayList;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsHolder> {

    Context ctx;
    ArrayList<PDFFile> allContactsArrayList;

    public AllContactsAdapter(Context context, ArrayList<PDFFile> list) {
        this.ctx = context;
        this.allContactsArrayList = list;
    }

    @Override
    public AllContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.sample_pdf, parent, false);

        return new AllContactsHolder(itemView, ctx);
    }

    @Override
    public void onBindViewHolder(AllContactsHolder holder, int position) {
        final PDFFile contactDetails = allContactsArrayList.get(position);
        holder.allContactsName.setText(contactDetails.getName());
    }

    @Override
    public int getItemCount() {
        return allContactsArrayList.size();
    }
}
