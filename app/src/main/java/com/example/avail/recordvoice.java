package com.example.avail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class recordvoice extends Fragment implements View.OnClickListener {

    private EditText txvResult;
    static int flag=0;
    ImageView imge;
    Intent myFile;
    SpannableString already;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recordvoice, container, false);

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        txvResult = (EditText) view.findViewById(R.id.txvResult);

        txvResult.setMovementMethod(new ScrollingMovementMethod());

        imge = (ImageView)  view.findViewById(R.id.btnSpeak);
        imge.setOnClickListener(this);



        Button clr = view.findViewById(R.id.clear);
        clr.setOnClickListener(this);

        Button co = view.findViewById(R.id.copy);
        co.setOnClickListener(this);
        return view;
    }

    private void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(getActivity(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    String already = txvResult.getText().toString();
                    already =  new SpannableString(txvResult.getText());
//                    Spannable already = (Spannable)txvResult.getText();
                    String str = result.get(0);
                    String input = str.substring(0, 1).toUpperCase() + str.substring(1);
                    SpannableString ss2;

                    if (input.equals("New line"))
                    {
                        ss2 = new SpannableString(TextUtils.concat(already,"\n"));
                        txvResult.setText(ss2);
                    }
                    else if(input.equals("Heading")){
                        flag=1;
                    }
                    else if(input.equals("Bold")){
                        flag=2;
                    }
                    else if(input.equals("Italic")){
                        flag=3;
                    }
                    else if(input.equals("Bold Italic")){
                        flag=4;
                    }
                    else if(input.equals("Underline")){
                        flag=5;
                    }
                    else if(input.equals("Strikethrough")){
                        flag=6;
                    }
                    else if(input.equals("Title")){
                        flag=7;
                    }
                    else if(input.equals("Include image")){
                        flag=8;
                    }
                    else if (flag==0){
                        ss2 = new SpannableString(TextUtils.concat(already,input,"."));
                        txvResult.setText(ss2);
                    }
                    else{
                        SpannableString ss1=  new SpannableString(input);
                        if(flag==1){


                            StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                            ss1.setSpan(boldspan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            UnderlineSpan underlinespan = new UnderlineSpan();
                            ss1.setSpan(underlinespan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss1 = new SpannableString(TextUtils.concat(already,"\n","\n",ss1,":"));
                            ss1 = new SpannableString(TextUtils.concat(ss1,"\n"));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==2){

                            StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                            ss1.setSpan(boldspan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//                            txvResult.setText(already+ss1+".");
                            ss1 = new SpannableString(TextUtils.concat(already,ss1,"."));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==3){

                            StyleSpan italicspan = new StyleSpan(Typeface.BOLD);
                            ss1.setSpan(italicspan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss1 = new SpannableString(TextUtils.concat(already,ss1,"."));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==4){

                            StyleSpan bolditalicspan = new StyleSpan(Typeface.BOLD_ITALIC);
                            ss1.setSpan(bolditalicspan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss1 = new SpannableString(TextUtils.concat(already,ss1,"."));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==5){

                            UnderlineSpan underlinespan = new UnderlineSpan();
                            ss1.setSpan(underlinespan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss1 = new SpannableString(TextUtils.concat(already,ss1,"."));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==6){

                            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                            ss1.setSpan(strikethroughSpan,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss1 = new SpannableString(TextUtils.concat(already,ss1,"."));

                            txvResult.setText(ss1);
                            flag=0;
                        }
                        else if(flag==7){
                            SpannableString ss4=  new SpannableString(input.toUpperCase());
                            StyleSpan bolditalicspan = new StyleSpan(Typeface.BOLD_ITALIC);
                            ss4.setSpan(bolditalicspan,0,ss4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            if (input.length() <= 28) {
                                int sam = 59 - (input.length());
                                int tes = (int) Math.floor(sam / 2);
                                String app = "";
                                for (int i = 0; i < tes; i++) {
                                    app = app + " ";
                                }
                                ss4 = new SpannableString(TextUtils.concat(app,ss4));
                            }
                            ss4 = new SpannableString(TextUtils.concat(already,"\n",ss4,"\n"));

                            txvResult.setText(ss4);
                            flag=0;
                        }
                        else if(flag==8){

                            Toast.makeText(getActivity(), "Filemanager is opened...!", Toast.LENGTH_LONG).show();

                            myFile = new Intent(Intent.ACTION_GET_CONTENT);
                            myFile.setType("*/*");
                            startActivityForResult(myFile,100);


                        }
                    }

                }
                break;
            case 100:
                if(resultCode == RESULT_OK)
                {
                    SpannableStringBuilder image =  new SpannableStringBuilder();
                    Toast.makeText(getActivity(), "photo you selected was included...!", Toast.LENGTH_LONG).show();
//                    try {

//                        File f= new File(String.valueOf(myFile));
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                        Bitmap bitm = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bitm, 400,780, true);
//
//                        Drawable dr = new BitmapDrawable(getResources(), bitmap);
//                        dr.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//                        Spannable imageSpannable = new SpannableString("\uFFFC");
//                        ImageSpan imgSpan = new ImageSpan(dr, DynamicDrawableSpan.ALIGN_BOTTOM);
//
//                        imageSpannable.setSpan(imgSpan, 0, image.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        image.append(imageSpannable);


//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    image = new SpannableStringBuilder(TextUtils.concat(already,"\n","\n",image,"\n","\n"));

                    txvResult.setText(image);

                    flag=0;
                    break;
                }
        }
    }




    private Spannable getImageSpannable(int drawableId, int targetWidth, int targetHeight) {

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), drawableId);

        Bitmap bitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);
        Drawable dr = new BitmapDrawable(getResources(), bitmap);
        dr.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Spannable imageSpannable = new SpannableString("\uFFFC");
        ImageSpan imgSpan = new ImageSpan(dr, DynamicDrawableSpan.ALIGN_BOTTOM);

        imageSpannable.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imageSpannable;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                txvResult.setText("");
                break;
            case R.id.copy:
                String txt= txvResult.getText().toString();

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", txt);
                clipboard.setPrimaryClip(clip);
                txvResult.setText("");
                break;
            case R.id.btnSpeak:
                getSpeechInput(v);
                break;
        }

    }
}
