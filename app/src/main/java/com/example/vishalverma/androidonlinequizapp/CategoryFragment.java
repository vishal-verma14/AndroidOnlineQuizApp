package com.example.vishalverma.androidonlinequizapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vishalverma.androidonlinequizapp.Common.Common;
import com.example.vishalverma.androidonlinequizapp.Interface.ItemClickListener;
import com.example.vishalverma.androidonlinequizapp.Model.Category;
import com.example.vishalverma.androidonlinequizapp.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class CategoryFragment extends Fragment {


    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    DatabaseReference categories;

    String[] subject = new String[]{
            "Maths",
            "Science",
            "Chemistry",
            "Physics",
    };

    String currentItem;
    ArrayList<String> filterArray = new ArrayList<String>();

    // Boolean array for initial selected items
    final boolean[] checked_subject = new boolean[]{
            false, // Red
            false, // Green
            false, // Blue
            false, // Purple
            false // Olive

    };
    final List<String> subjectList = Arrays.asList(subject);


    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_category, container, false);
        listCategory = (RecyclerView) myFragment.findViewById(R.id.listCategory);
        floatingActionButton = (FloatingActionButton) myFragment.findViewById(R.id.filter_float);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategory();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "FLOATING", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                builder.setMultiChoiceItems(subject, checked_subject, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, final boolean isChecked) {
                        checked_subject[which] = isChecked;

                        // Get the current focused item
                        currentItem = subjectList.get(which);

                        // Notify the current action
                        Toast.makeText(getActivity(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();


                        filterArray.add(currentItem);

                    }
                });

                builder.setTitle("Preferred Subject");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        for ( int i = 0; i < checked_subject.length; i++) {
                            final boolean checked = checked_subject[i];

                            final int finalI = i;
                            categories.child(String.valueOf(finalI)).child("booleanvalue").setValue(checked);

                        }
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

            }


        });


        return myFragment;


    }

//       if (checked) {
//        if (key.equals(String.valueOf(subjectList.get(finalI))))
//            Log.d("Your preferred subject.....", subjectList.get(finalI));
//        if (key.equals(subjectList.get(finalI))){
//            categories.child(str).child("booleanvalue").setValue(true);
//        }else {
//            categories.child(str).child("booleanvalue").setValue(false);
//        }
//    }

    private void loadCategory() {



        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class, R.layout.category_layout, CategoryViewHolder.class, categories.orderByChild("booleanvalue").equalTo(true)) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {


                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        super.onClick(view, position, isLongClick);
//                        Toast.makeText(getActivity(),String.format("%d|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                        Intent startIntent = new Intent(getActivity(), Start.class);
                        Common.categoryId = adapter.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startIntent);

                    }
                });

            }

        };

          listCategory.setAdapter(adapter);
          adapter.notifyDataSetChanged();



    }


    }

