
package com.example.computer.proyecto_ordencomida.DataBase;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteQueryBuilder;
        import android.widget.Toast;

        import com.example.computer.proyecto_ordencomida.Modelo.Orden;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Computer on 30/01/2018.
 */

public class SqlHelper extends SQLiteOpenHelper {

    private static final String nombreBD = "EatItBdSQLite";
    private static final String tablaDetalleOrden ="DetalleOrden";
    private static final String campoCodigo = "productId";
    private static final String campoNombre = "productNombre";
    private static final String campoCantidad ="cantidad";
    private static final String campoPrecio = "precio";
    private static final String campoDescuento ="descuento";


    public SqlHelper(Context context) {
        super(context, nombreBD, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreate = "CREATE TABLE "+ tablaDetalleOrden +
                "("+campoCodigo + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                campoNombre + " TEXT,"+
                campoCantidad + " TEXT," +
                campoPrecio + " TEXT," +
                campoDescuento + " TEXT)";

        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Orden> obtenerOrden() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productId", "productNombre", "cantidad", "precio", "descuento"};
        String sqlTable = tablaDetalleOrden;

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<Orden> listaOrden = new ArrayList<>();

        if (cursor.moveToFirst()) {

            do {
                listaOrden.add(new Orden(
                        cursor.getString(cursor.getColumnIndex("productId")),
                        cursor.getString(cursor.getColumnIndex("productNombre")),
                        cursor.getString(cursor.getColumnIndex("cantidad")),
                        cursor.getString(cursor.getColumnIndex("precio")),
                        cursor.getString(cursor.getColumnIndex("descuento"))
                ));
            } while (cursor.moveToNext());


        }

        db.close();

        return listaOrden;

    }

    public void agregarAlcarro(Orden orden, Context context) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(campoCodigo, orden.getProductId());
        contentValues.put(campoNombre, orden.getProductNombre());
        contentValues.put(campoCantidad, orden.getCantidad());
        contentValues.put(campoPrecio, orden.getPrecio());
        contentValues.put(campoDescuento, orden.getDescuento());


        SQLiteDatabase db = getWritableDatabase();
        db.insert(tablaDetalleOrden,null,contentValues);



        Toast.makeText(context, "codigo de comida ingresado: "+campoCodigo, Toast.LENGTH_SHORT).show();
        db.close();

    }
}