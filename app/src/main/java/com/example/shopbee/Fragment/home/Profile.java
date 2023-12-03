package com.example.shopbee.Fragment.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shopbee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;


public class Profile extends Fragment {
    private String TAG = "new";
    private View mView;
    private ImageView ImgAvatar, ImgAvatar_change;
    private TextView textView_name, textView_email, name_change, email_change, pass_change;
    private Button button, button_change;
    private final int REQUEST_CODE_SELECT_IMAGE = 100;
    private CardView cardView;
    private Bitmap bitmap;
    private Uri UriAvt;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_profile, container, false);
        NoBar();
        UnitUi();
        ShowInformation();
        CallBtn();

        return mView;
    }

    private void BackFg() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isCardViewVisible()) {
                    hideCardView();
                } else {
                    // Nếu không có CardView, xử lý back bình thường
                    getParentFragmentManager().popBackStack();
                }
            }
        });
    }

    private boolean isCardViewVisible() {
        return cardView != null && cardView.getVisibility() == View.VISIBLE;
        //trả về true nếu đang hiện thị cardview
    }


    private void CallBtn() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckVisibility();
                BackFg();
            }
        });
        ImgAvatar_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickRequestPermission();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                getGetImg.launch(intent);
                Toast.makeText(getActivity(), "đang tải hình ảnh", Toast.LENGTH_SHORT).show();
            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_name.setText(name_change.getText().toString());
                UpDataFireBase();
                hideCardView();
            }
        });
    }

    private void OpenGallery() {

    }

    private void OnclickRequestPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            OpenGallery();
        }else {
            String [] Permission ={Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(Permission,REQUEST_CODE_SELECT_IMAGE);

        }

    }

    private void UpDataFireBase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name_change.getText().toString().trim())
                .setPhotoUri(UriAvt)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ShowInformation();
                            Toast.makeText(getActivity(), "tải ảnh lên db thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void CheckVisibility() {
        if (cardView.getVisibility() == View.VISIBLE) {
            hideCardView();
        } else {
            showCardView();
        }
    }

    private void showCardView() {
        if (cardView != null) {
            cardView.setVisibility(View.VISIBLE);
        }
    }

    private void hideCardView() {
        if (cardView != null) {
            cardView.setVisibility(View.INVISIBLE);
        }
    }

    public void NoBar() {
        // set cho fragment không hiện thị actions bar
        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();

        // Hiển thị lại thanh toolbar khi Fragment bị hủy
        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }

    private void ShowInformation() {
        // hiện thị thông tin lên fragment
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri PhotoUrl = user.getPhotoUrl();

        if (name == null) {
            textView_name.setVisibility(View.GONE);
        } else {
            textView_name.setVisibility(View.VISIBLE);
            textView_name.setText(name);
        }
        textView_email.setText(email);

        Glide.with(this).load(PhotoUrl).error(R.drawable.avt_default).into(ImgAvatar);
    }

    private void UnitUi() {
        ImgAvatar = mView.findViewById(R.id.your_avt);
        ImgAvatar_change = mView.findViewById(R.id.avt_change);
        cardView = mView.findViewById(R.id.card);
        button = mView.findViewById(R.id.button_change_inf);
        textView_name = mView.findViewById(R.id.your_name);
        textView_email = mView.findViewById(R.id.your_email);
        email_change = mView.findViewById(R.id.email_change);
        name_change = mView.findViewById(R.id.name_change);
        pass_change = mView.findViewById(R.id.pass_change);
        button_change = mView.findViewById(R.id.button_change_if);

    }

    private final ActivityResultLauncher<Intent> getGetImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        UriAvt = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), UriAvt);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (bitmap != null) {
                            ImgAvatar_change.setImageBitmap(bitmap);
                            Toast.makeText(getActivity(), "tải hình ảnh thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "tải hình ảnh thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

}