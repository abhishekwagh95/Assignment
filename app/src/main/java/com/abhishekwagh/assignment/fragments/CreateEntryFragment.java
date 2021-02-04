package com.abhishekwagh.assignment.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import com.abhishekwagh.assignment.R;
import com.abhishekwagh.assignment.activities.MainActivity;
import com.abhishekwagh.assignment.db.AppDatabase;
import com.abhishekwagh.assignment.db.Entry;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CreateEntryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText textName, textDOB, textMobile;
    Button buttonSave;
    ViewPager viewPager;
    TabLayout tabLayout;

    Uri fileUri;




    public CreateEntryFragment() {
        // Required empty public constructor
    }

    public static CreateEntryFragment newInstance(String param1, String param2) {
        CreateEntryFragment fragment = new CreateEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_entry, container, false);
        init(view);
        return view;

    }

    private void init(View view) {
        imageView =view.findViewById(R.id.image_view);

        textName = view.findViewById(R.id.tv_name);
        textDOB = view.findViewById(R.id.tv_dob);
        textDOB.setFocusable(false);
        textMobile = view.findViewById(R.id.tv_mobile);
        buttonSave = view.findViewById(R.id.save_button);
        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        viewPager = getActivity().findViewById(R.id.view_pager);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry(textName.getText().toString(),
                        textDOB.getText().toString(),
                        textMobile.getText().toString());
                db.entryDao().insertEntry(entry);
//                getFragmentManager()
//                        .beginTransaction()
//                        .replace(new ViewEntryListFragment())
//                        .commit();

                viewPager.setCurrentItem(0);
            }
        });

        //To add images
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(CreateEntryFragment.this)
                        //.crop()                    //Crop image(Optional), Check Customization for more option
                        //.cropOval()                //Allow dimmed layer to have a circle inside
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)   //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();




            }
        });

                textDOB.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String month_text = new DateFormatSymbols().getMonths()[month-1];
        String date = dayOfMonth+"/"+ month_text +"/"+year;
        Log.d("abc","date: "+date);
        textDOB.setText(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



//        super.onActivityResult(requestCode, resultCode, data);
        Log.d("abc",data.toString());
        //Toast.makeText(getActivity(), data.toString(), Toast.LENGTH_SHORT).show();
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            fileUri = data.getData();
            imageView.setImageURI(fileUri);











          // Toast.makeText(getActivity(), fileUri.toString(), Toast.LENGTH_SHORT).show();


           // Log.d("abc",fileUri.toString());

            //You can get File object from intent
//            File file = ImagePicker.getFile(data);

            //You can also get File Path from intent
//                    val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getActivity(), "Error_here", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }


    }
}
