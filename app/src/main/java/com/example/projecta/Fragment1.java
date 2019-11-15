package com.example.projecta;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class Fragment1 extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Data> arrayList= new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    Fragment1 fragment1;
    MyAdapter myAdapter;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment1, container, false);
        recyclerView=v.findViewById(R.id.recyclerview);
        swipeRefreshLayout=v.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter= new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        arrayList.clear();
        dataPut();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                dataPut();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return v;
    }
    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int postion=viewHolder.getAdapterPosition();
            Data data=arrayList.get(postion);
            String name=data.name;
            arrayList.remove(postion);
            myAdapter.notifyItemRemoved(postion);
            delete(name);
        }

        private void delete(String name) {
            sqLiteDatabase.execSQL("DELETE FROM tb_project WHERE name = '" + name + "';");
        }
    };

    void dataPut(){
        dbHelper= new DBHelper(getContext());
        sqLiteDatabase= dbHelper.getReadableDatabase();
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
            //TODO: 나중에 개발하자 ㅎ
        }
        myAdapter.notifyDataSetChanged();
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
                    final LinearLayout dialog_button=v.findViewById(R.id.diaog_button);
                    final  TextView ok=v.findViewById(R.id.dialog_ok);
                    final TextView no=v.findViewById(R.id.dialog_no);

                    date.setText(data.date);
                    time.setText(data.time);
                    desc.setText(data.desc);

                    final Data data2= data;
                    boolean flag=false;

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sqLiteDatabase=dbHelper.getWritableDatabase();
                            ContentValues values= new ContentValues();
                            values.put("date",data2.date);
                            values.put("time",data2.time);
                            values.put("description",data2.desc);
                            sqLiteDatabase.update("tb_project",values,"date=?", new String[]{data.date});
                            dialog_button.setVisibility(View.GONE);

                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_button.setVisibility(View.GONE);
                        }
                    });

                    date.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            dialog_button.setVisibility(View.VISIBLE);
                            data2.date=charSequence.toString();

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            }
                    });

                    time.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            dialog_button.setVisibility(View.VISIBLE);
                            data2.time=charSequence.toString();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    desc.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            dialog_button.setVisibility(View.VISIBLE);
                            data2.desc=charSequence.toString();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
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
