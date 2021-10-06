package com.example.driver.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.adapter.DroupdownMenuAdapter;
import com.example.driver.model.DroupDownModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.BuildConfig;

public class Util {
    private static Dialog dialog;
    private  static AlertDialog alertDialog;
    public static void show(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void showDialog(String dialogMessage, Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String convertTime(String time) {
        String inputPattern = "HH:mm";
        String outputPattern = "hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        String str = null;

        try {
            Date date = inputFormat.parse(time);
            assert date != null;
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static void UpdateApk(Context context) {
//        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
//        builder2.setCancelable(false);
//        View view2 = LayoutInflater.from(context).inflate(R.layout.updatealertlayout, null);
//        MaterialButton updateBtn = view2.findViewById(R.id.updateBtn);
//        updateBtn.setOnClickListener(v -> {
//            String url = "https://play.google.com/store/apps/details?id=com.dbcorp.noi";
//             Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//             context.startActivity(i);
//        });
//        builder2.setView(view2);
//
//        alertDialog = builder2.create();
//        alertDialog.show();
    }

    public static void showDropDown(ArrayList<DroupDownModel> droupDownModelslist, String tittle, Context context , DroupdownMenuAdapter.OnMeneuClickListnser dropContext) {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setCancelable(false);
        View view2 = LayoutInflater.from(context).inflate(R.layout.droup_down_layout, null);
        ImageView closeImg=view2.findViewById(R.id.closeImg);
        TextView tittleName=view2.findViewById(R.id.tittleName);
        EditText inputSearch=view2.findViewById(R.id.search);
        tittleName.setVisibility(View.VISIBLE);
        tittleName.setText(tittle);
        RecyclerView listCountryData2 = view2.findViewById(R.id.listView);

        listCountryData2.setHasFixedSize(true);
        //syllabus_type_recyclerview.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        listCountryData2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        DroupdownMenuAdapter optionList1 = new DroupdownMenuAdapter(droupDownModelslist, dropContext, context);
        listCountryData2.setAdapter(optionList1);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                Log.e("bhs==>>", text);


                optionList1.getFilter().filter(cs);
                optionList1.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        builder2.setView(view2);
        closeImg.setOnClickListener(v->{
            alertDialog.cancel();
            alertDialog.dismiss();
        });

        alertDialog = builder2.create();
        alertDialog.show();
    }



    public static void hideDropDown() {


        alertDialog.cancel();
        alertDialog.dismiss();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = String.format("0%s", h);
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void hideDialog() {
        dialog.dismiss();
        dialog.cancel();
    }



}
