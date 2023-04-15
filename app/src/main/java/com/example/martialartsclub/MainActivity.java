package com.example.martialartsclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import com.example.martialartsclub.Model.DatabaseHandler;
import com.example.martialartsclub.Model.MartialArt;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.martialartsclub.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHandler databaseHandler;
    private double totalMartialArtsPrice;
    private ScrollView scrollView;
    private int martialArtButtonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(MainActivity.this);
        totalMartialArtsPrice = 0.0;
        scrollView = findViewById(R.id.scrollView);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        martialArtButtonWidth = screenSize.x / 2;
        modifyUserInterface();


    }

    private void modifyUserInterface() {
        ArrayList<MartialArt> allMartialArtObjects = databaseHandler.returnAllMartialArtObjects();

        scrollView.removeAllViewsInLayout();

        if (allMartialArtObjects.size() > 0) {

            GridLayout gridLayout = new GridLayout(MainActivity.this);
            gridLayout.setRowCount((allMartialArtObjects.size() + 1) / 2);
            gridLayout.setColumnCount(2);

            MartialArtButton[] martialArtButtons = new MartialArtButton[allMartialArtObjects.size()];

            int index = 0;

            for (MartialArt martialArtObject : allMartialArtObjects) {
                martialArtButtons[index] = new MartialArtButton(MainActivity.this, martialArtObject);
                martialArtButtons[index].setText(martialArtObject.getMartialArtID() + "\n" + martialArtObject.getMartialArtName() + "\n" + martialArtObject.getMartialArtPrice());

                switch (martialArtObject.getMartialArtColor()){
                    case "Red":
                        martialArtButtons[index].setBackgroundColor(Color.RED);
                        break;
                    case "Blue":
                        martialArtButtons[index].setBackgroundColor(Color.BLUE);
                        break;
                    case "Black":
                        martialArtButtons[index].setBackgroundColor(Color.BLACK);
                        break;
                    case "Yellow":
                        martialArtButtons[index].setBackgroundColor(Color.YELLOW);
                        break;
                    case "Purple":
                        martialArtButtons[index].setBackgroundColor(Color.CYAN);
                        break;
                    case "Green":
                        martialArtButtons[index].setBackgroundColor(Color.GREEN);
                        break;
                    case "White":
                        martialArtButtons[index].setBackgroundColor(Color.WHITE);
                        break;
                    default:
                        martialArtButtons[index].setBackgroundColor(Color.GRAY);
                        break;
                }

                martialArtButtons[index].setOnClickListener(MainActivity.this);
                gridLayout.addView(martialArtButtons[index], martialArtButtonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

            }

            scrollView.addView(gridLayout);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.add_martial_art:
                Intent addMartialArtIntent = new Intent(MainActivity.this, AddMartialArtActivity.class);
                startActivity(addMartialArtIntent);
                return true;
            case R.id.delete_martial_art:
                Intent deleteMartialArtIntent = new Intent(MainActivity.this, DeleteMartialArtActivity.class);
                startActivity(deleteMartialArtIntent);
                return true;
            case R.id.update_martial_art:
                Intent updateMartialArtIntent = new Intent(MainActivity.this, UpdateMartialArtActivity.class);
                startActivity(updateMartialArtIntent);
                return true;
            case R.id.martial_arts_prices_reset:
                totalMartialArtsPrice = 0.0;
                return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        MartialArtButton martialArtButton = (MartialArtButton) view;
        totalMartialArtsPrice = totalMartialArtsPrice + martialArtButton.getMartialArtPrice();
        String martialArtsPriceFormatted = NumberFormat.getCurrencyInstance().format(totalMartialArtsPrice);
        Toast.makeText(MainActivity.this, martialArtsPriceFormatted, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        modifyUserInterface();
    }
}