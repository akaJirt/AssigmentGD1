package com.example.assigmentgd1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assigmentgd1.R;
import com.example.assigmentgd1.model.MonHoc;
import com.example.assigmentgd1.model.ThongTin;
import com.example.assigmentgd1.service.DKMonHocService;

import java.util.ArrayList;
import java.util.HashMap;

public class DangKyMonHocAdapter extends RecyclerView.Adapter<DangKyMonHocAdapter.ViewHolder> {
    private Context context;

    private ArrayList<MonHoc> list;

    private int id;

    private boolean isAll;

    public DangKyMonHocAdapter(Context context, ArrayList<MonHoc> list, int id, boolean isAll) {
        this.context = context;
        this.list = list;
        this.id = id;
        this.isAll = isAll;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danhsach_monhoc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtCode.setText(String.valueOf(list.get(position).getCode()));
        holder.txtName.setText(list.get(position).getName());
        holder.txtTeacher.setText(list.get(position).getTeacher());

        if (list.get(position).getIsRegister() == id) {
            holder.btnDangKy.setText("Hủy đăng ký");
            holder.btnDangKy.setBackgroundColor(Color.parseColor("#00BFFF"));
        } else {
            holder.btnDangKy.setText("Đăng ký");
            holder.btnDangKy.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        holder.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DKMonHocService.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("code", list.get(holder.getAdapterPosition()).getCode());
                bundle.putInt("isRegister", list.get(holder.getAdapterPosition()).getIsRegister());
                bundle.putBoolean("isAll", isAll);
                intent.putExtras(bundle);
                context.startService(intent);

            }
        });

        ArrayList<HashMap<String, String>> listThongTin = new ArrayList<>();
        for (ThongTin thongTin : list.get(position).getListThongTin()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("date", thongTin.getDate());
            hashMap.put("address", thongTin.getAddress());
            listThongTin.add(hashMap);
        }

        String[] from = {"date", "address"};
        int[] to = {R.id.txtDate, R.id.txtAddress};
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listThongTin, R.layout.list_item_thongtin, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView txtAddress = view.findViewById(R.id.txtAddress);
                HashMap<String, String> item = listThongTin.get(position);
                String address = item.get("address");
                String formattedAddress = "Phòng: " + address;
                txtAddress.setText(formattedAddress);

                return view;
            }
        };
        holder.spinner.setAdapter(simpleAdapter);

        holder.linearLayout.setVisibility(View.GONE);
        holder.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearLayout.getVisibility() == View.GONE) {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.imgArrow.setImageResource(R.drawable.arrow_right);
                } else {
                    holder.linearLayout.setVisibility(View.GONE);
                    holder.imgArrow.setImageResource(R.drawable.arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCode, txtName, txtTeacher;
        Button btnDangKy;

        ImageView imgArrow;

        LinearLayout linearLayout;

        Spinner spinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            btnDangKy = itemView.findViewById(R.id.btnDangKy);
            imgArrow = itemView.findViewById(R.id.img_arrow);
            spinner = itemView.findViewById(R.id.spnMonHoc);
            linearLayout = itemView.findViewById(R.id.ll_ThongTinMonHoc);
        }
    }


}
