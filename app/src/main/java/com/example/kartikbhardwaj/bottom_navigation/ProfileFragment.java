package com.example.kartikbhardwaj.bottom_navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;
import com.example.kartikbhardwaj.bottom_navigation.howitworks.Fragment0;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    ImageButton cross;
    Button save;
    TextView username;
    CardView faqs;
    CardView toptraders;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile_,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toptraders=view.findViewById(R.id.toptraders);
        faqs=view.findViewById(R.id.faq);



        cross=view.findViewById(R.id.cross_btn);
        username=view.findViewById(R.id.username);
        profilePic=view.findViewById(R.id.profile_pic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               // intent to faq activity
                Intent faqintent =new Intent(getContext(), Faq_Activity.class);
                startActivity(faqintent);


            }
        });




    }


    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());\
            Glide.with(this).load(targetUri).into(profilePic);

            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(getContext(),"onActivityResult",Toast.LENGTH_LONG).show();


        }







    }


}
