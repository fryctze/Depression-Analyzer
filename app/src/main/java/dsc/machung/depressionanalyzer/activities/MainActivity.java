package dsc.machung.depressionanalyzer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import dsc.machung.depressionanalyzer.R;
import dsc.machung.depressionanalyzer.databinding.ActivityMainBinding;
import dsc.machung.depressionanalyzer.utils.GlideApp;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.textView.setText(firebaseAuth.getCurrentUser().getDisplayName());

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        String title = "Hello "+ firebaseAuth.getCurrentUser().getDisplayName() +",";

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.mainToolbarTitle.setText(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_profile);
        View view = menuItem.getActionView();

        CircleImageView circleProfileImage = view.findViewById(R.id.circle_image_menu_profile);

        GlideApp.with(this)
                .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                .into(circleProfileImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
                Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_logout:
                Toast.makeText(this, "logout clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void showPopup(View view){
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this::onOptionsItemSelected);
        popup.inflate(R.menu.main_menu_detail);
        popup.show();
    }

    private void signOut() {
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                firebaseAuth.signOut();

                Toast.makeText(MainActivity.this, "Logged out and have a nice day!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "Failed logout "+e.getMessage(), e);
                Toast.makeText(MainActivity.this, "Failed to logout, try again ", Toast.LENGTH_LONG).show();
            }
        });
    }
}