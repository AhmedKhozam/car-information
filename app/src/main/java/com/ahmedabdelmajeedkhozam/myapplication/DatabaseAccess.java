package com.ahmedabdelmajeedkhozam.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.constraintlayout.solver.ArrayLinkedVariables;

import com.ahmedabdelmajeedkhozam.myapplication.model.Car;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;

    private SQLiteOpenHelper OpenHelper;


    private static DatabaseAccess inststance;


    private DatabaseAccess(Context context) {
        this.OpenHelper = new Mydatabase(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (inststance == null) {
            inststance = new DatabaseAccess(context);
        }
        return inststance;
    }


    public void open() {
        database = this.OpenHelper.getWritableDatabase();

    }

    public void close() {
        if (this.database != null) {
            database.close();
        }

    }


    //add method
    public boolean insertCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(Mydatabase.CAR_CLN_MEDEL, car.getModel());
        values.put(Mydatabase.CAR_CLN_COLOR, car.getColor());
        values.put(Mydatabase.CAR_CLN_DPL, car.getDpl());
        values.put(Mydatabase.CAR_CLN_IMAGE, car.getImage());
        values.put(Mydatabase.CAR_CLN_DESCRIPTION, car.getDescription());

        long result = database.insert(Mydatabase.CAR_TB_NAME, null, values);
        return result != -1;

    }

    //update method
    public boolean updateCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(Mydatabase.CAR_CLN_MEDEL, car.getModel());
        values.put(Mydatabase.CAR_CLN_COLOR, car.getColor());
        values.put(Mydatabase.CAR_CLN_DPL, car.getDpl());
        values.put(Mydatabase.CAR_CLN_IMAGE, car.getImage());
        values.put(Mydatabase.CAR_CLN_DESCRIPTION, car.getDescription());
      String args[]={String.valueOf(car.getId())};
        long result = database.update(Mydatabase.CAR_TB_NAME,values,"id=?",args);
        return result > 0;
    }

    //get number of all cars
    public long getCarsCount() {
        return DatabaseUtils.queryNumEntries(database, Mydatabase.CAR_TB_NAME);

    }

    public boolean deleteCar(Car car) {
        String[] args = {String.valueOf(car.getId())};
        int result = database.delete(Mydatabase.CAR_TB_NAME, "id=?", args);

        return result > 0;
    }

    //retrieve method
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + Mydatabase.CAR_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MEDEL));
                String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
                double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));
                String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESCRIPTION));

                Car c = new Car(id, model, color, dpl, image, description);
                cars.add(c);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }


    //searching
    public ArrayList<Car> getCars(String modelSearch) {


        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + Mydatabase.CAR_TB_NAME + " Where " + Mydatabase.CAR_CLN_MEDEL + " like ?", new String[]{modelSearch + "%"});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MEDEL));
                String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
                double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));
                String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESCRIPTION));

                Car c = new Car(id, model, color, dpl, image, description);
                cars.add(c);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }


    //get one car

    public Car getCar(int carId) {


        Cursor cursor = database.rawQuery("select * from " + Mydatabase.CAR_TB_NAME + " where " + Mydatabase.CAR_CLN_ID + "=?", new String[]{String.valueOf(carId)});
        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
            String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MEDEL));
            String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
            double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));
            String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
            String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESCRIPTION));
            Car c = new Car(id, model, color, dpl, image, description);
            cursor.close();
            return c;

        }
        return null;
    }

}
