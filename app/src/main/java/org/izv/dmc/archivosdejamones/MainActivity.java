package org.izv.dmc.archivosdejamones;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getName()+"xyzyx";
    EditText etId;
    TextView tvContent;
    public String fileName="data.csv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvContent.setText("");
            readInternalFile();

    }

    private void initialize() {
        try {

            File f=new File(getFilesDir(), fileName);
            FileWriter fw;
            fw=new FileWriter(f,true);
            fw.write("");
            fw.flush();
            fw.close();
            }catch (NullPointerException | IOException nu){

        }




        Button btAñadir=findViewById(R.id.btEditar);
        etId=findViewById(R.id.etId);
        tvContent=findViewById(R.id.tvContent);
        readInternalFile();
        btAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().isEmpty()){
                    Intent i=new Intent(getApplicationContext(), NewVino.class);
                    startActivity(i);

                }else{
                  //  if(fileName) {
                        Intent i = new Intent(getApplicationContext(), EditVino.class);
                        i.putExtra("id", etId.getText().toString());
                        startActivity(i);
                   // }
                }
            }
        });

    }
    private void readInternalFile() {
        String content="";
        try {
            String[] vinos=readFile(getFilesDir(),fileName).toString().split("\n");
            for (String vino:vinos) {
                content+= "Id: "+vino.split(";")[0]+"\n"+
                        "Nombre: "+vino.split(";")[1]+"\n"+
                        "Bodega: "+vino.split(";")[2]+"\n"+
                        "Color: "+vino.split(";")[3]+"\n"+
                        "Origen: "+vino.split(";")[4]+"\n"+
                        "Graduación: "+vino.split(";")[5]+"\n"+
                        "Fecha: "+vino.split(";")[6]+"\n\n";
            }
            tvContent.setText(content);
        }catch (NullPointerException np){
            Log.v(TAG,"no hay vino ");
        }catch(ArrayIndexOutOfBoundsException ae){
            Log.v(TAG,"archivo vacio");
        }

    }
    private String readFile(File file, String fileName){
        File f=new File(file,fileName);
        String texto="";
        try{
            BufferedReader br=new BufferedReader(new FileReader(f));
            String linea;
            while ((linea=br.readLine())!=null){
                texto+=linea+"\n";
            }
            br.close();
        }catch (IOException io){
            texto=null;
            Log.v(TAG,io.toString());
        }
        return texto;
    }
}