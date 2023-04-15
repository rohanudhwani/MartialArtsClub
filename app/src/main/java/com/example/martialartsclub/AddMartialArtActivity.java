package com.example.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martialartsclub.Model.DatabaseHandler;
import com.example.martialartsclub.Model.MartialArt;

public class AddMartialArtActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtPrice, edtColor;
    Button btnAddMartialArt, btnBack;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martial_art);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtColor = findViewById(R.id.edtColor);

        btnAddMartialArt = findViewById(R.id.btnMartialArt);
        btnBack = findViewById(R.id.btnGoBack);

        databaseHandler = new DatabaseHandler(AddMartialArtActivity.this);

        btnAddMartialArt.setOnClickListener(AddMartialArtActivity.this);
        btnBack.setOnClickListener(AddMartialArtActivity.this);


    }

    private void addMartialArtObjectToDatabase() {
        String nameValue = edtName.getText().toString();
        String priceValue = edtPrice.getText().toString();
        String colorValue = edtColor.getText().toString();

        try{
            double priceDoubleValue = Double.parseDouble(priceValue);
            MartialArt martialArtObject = new MartialArt(0, nameValue, priceDoubleValue, colorValue);
            databaseHandler.addMartialArt(martialArtObject);
            Toast.makeText(this, martialArtObject.toString() + " Martial Art Object is added to Database", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMartialArt:
                addMartialArtObjectToDatabase();
                break;

            case R.id.btnGoBack:
                this.finish();
                break;
        }
    }
}