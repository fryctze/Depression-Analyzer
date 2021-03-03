package dsc.machung.depressionanalyzer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dsc.machung.depressionanalyzer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}