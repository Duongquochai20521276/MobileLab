package com.example.bai02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//implements View.OnClickListener,View.OnLongClickListener
class RecyclerViewHolder  extends RecyclerView.ViewHolder  {
//    private ItemClickListener itemClickListener;
    public TextView Name;
    public TextView Khoa;
    public TextView MSSV;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.tv_sv);
//        Khoa = itemView.findViewById(R.id.tv_khoa);
//        MSSV = itemView.findViewById(R.id.tv_mssv);
//        itemView.setOnClickListener(this);
//        itemView.setOnLongClickListener(this);
    }

//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

//    @Override
//    public void onClick(View view) {
//        itemClickListener.onClick(view, getAdapterPosition(), false);
//    }
//
//    @Override
//    public boolean onLongClick(View view) {
//        itemClickListener.onClick(view, getAdapterPosition(), true);
//        return true;
//    }
}

public class SinhVienAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private List<SinhVien> mSinhVien = new ArrayList<>();
//    public SinhVienRecycle(Context mContext, ArrayList<SinhVien> mSinhVien) {
//        this.mContext = mContext;
//        this.mSinhVien = mSinhVien;
//    }
    public SinhVienAdapter(Context mContext, List<SinhVien> mSinhVien) {
        this.mContext = mContext;
        this.mSinhVien = mSinhVien;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.sinhvien_item,parent,false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.Name.setText(mSinhVien.get(position).toString());
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick)
//                    Toast.makeText(mContext, "Long Click: "+mSinhVien.get(position), Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(mContext, " "+mSinhVien.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mSinhVien.size();
    }

}
