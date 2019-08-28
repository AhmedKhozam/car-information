package com.ahmedabdelmajeedkhozam.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedabdelmajeedkhozam.myapplication.model.Car;
import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {
    public static final int PIC_IMAGE_REQ_CODE = 1;
    public static final int ADD_CAR_RESULT_CODE = 1;
    public static final int EDIT_CAR_RESULT_CODE = 1;
    private Toolbar toolbar;
    private TextInputEditText et_model, et_color, et_dpl, et_description;
    private ImageView iv;
    private DatabaseAccess db;


    private int carId = -1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        db = DatabaseAccess.getInstance(this);

        iv = findViewById(R.id.details_iv);
        et_model = findViewById(R.id.et_details_model);
        et_color = findViewById(R.id.et_details_color);
        et_dpl = findViewById(R.id.et_details_dpl);
        et_description = findViewById(R.id.et_details_description);

        Intent intent = getIntent();
        carId = intent.getIntExtra(MainActivity.CAR_KEY, -1);


        Log.d("ahmed", carId + "");

        if (carId == -1) {
            //add
            enableFields();
            clearFiels();

        } else {
            //show details
            disableFields();
            db.open();
            Car c = db.getCar(carId);
            db.close();
            if (c != null) {
                fillCarToFields(c);

            }
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, PIC_IMAGE_REQ_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);
        if (carId == -1) {
            //add
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        } else {
            //show details
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    private void fillCarToFields(Car c) {
        if (c.getImage() != null && !c.getImage().equals(""))
            iv.setImageURI(Uri.parse(c.getImage()));
        et_model.setText(c.getModel());
        et_description.setText(c.getDescription());
        et_color.setText(c.getColor());
        et_dpl.setText(String.valueOf(c.getDpl()));

    }

    private void disableFields() {
        iv.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_description.setEnabled(false);
        et_dpl.setEnabled(false);
    }

    private void enableFields() {
        iv.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_description.setEnabled(true);
        et_dpl.setEnabled(true);
    }

    private void clearFiels() {
        iv.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_description.setText("");
        et_dpl.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String model, color, desc, image = "";
        double dpl;
        switch (item.getItemId()) {
            case R.id.details_menu_save:
                model = et_model.getText().toString().trim();
                color = et_color.getText().toString().trim();
                desc = et_description.getText().toString().trim();
                dpl = Double.parseDouble(et_dpl.getText().toString().trim());
                if (imageUri != null)
                    image = imageUri.toString();

                boolean res = false;
                Car c = new Car(carId, model, color, dpl, image, desc);

                db.open();
                if (carId == -1) {
                    res = db.insertCar(c);
                    if (res)
                        Toast.makeText(this, "car added successfully", Toast.LENGTH_SHORT).show();
                    setResult(ADD_CAR_RESULT_CODE, null);
                    finish();
                } else {
                    res = db.updateCar(c);
                    if (res)
                        Toast.makeText(this, "car modified successfully", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_RESULT_CODE, null);
                    finish();
                }
                db.close();

                return true;


            case R.id.details_menu_edit:
                enableFields();
                MenuItem save = toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;


            case R.id.details_menu_delete:

                Car c2 = new Car(carId, null, null, 0, null, null);

                db.open();

                res = db.deleteCar(c2);
                if (res) {
                    Toast.makeText(this, "car deleted", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_RESULT_CODE, null);
                    finish();
                }


                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQ_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            iv.setImageURI(imageUri);

        }
    }
}
