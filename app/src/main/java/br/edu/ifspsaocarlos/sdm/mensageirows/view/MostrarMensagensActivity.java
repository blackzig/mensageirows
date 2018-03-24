package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;

public class MostrarMensagensActivity extends AppCompatActivity {

    private ListView mostrarMensagensLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensagens);

        mostrarMensagensLv = findViewById(R.id.lv_mostrar_mensagens);

        ArrayList<String> corpoMensagens = getIntent().getStringArrayListExtra(
                getString(R.string.mensagens_string_array_extra));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                corpoMensagens);
        mostrarMensagensLv.setAdapter(arrayAdapter);
    }
}
