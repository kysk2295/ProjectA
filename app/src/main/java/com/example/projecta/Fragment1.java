package com.example.projecta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class Fragment1 extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Data> arrayList= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment1, container, false);
        recyclerView=v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter());

        arrayList.clear();
        DBHelper dbHelper= new DBHelper(getContext());
        SQLiteDatabase sqLiteDatabase= dbHelper.getReadableDatabase();
        String sql="select * from tb_project";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(1);
            String date=cursor.getString(2);
            String time=cursor.getString(3);
            String desc=cursor.getString(4);
            Data data= new Data();
            data.name=name;
            data.date=date;
            data.time=time;
            data.desc=desc;

            arrayList.add(data);
        }


        return v;
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Data data=arrayList.get(position);
            holder.cardView.setTitle(data.name);
            holder.cardView.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
                @Override
                public void onExpandChanged(View v, boolean isExpanded) {
                    TextInputEditText date=v.findViewById(R.id._date);
                    TextInputEditText time=v.findViewById(R.id._timer);
                    TextInputEditText desc=v.findViewById(R.id._desc);
                    date.setText(data.date);
                    time.setText(data.time);
                    desc.setText(data.desc);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            ExpandableCardView cardView;
            public ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                cardView=itemView.findViewById(R.id.cardview);
            }
        }
    }
}
