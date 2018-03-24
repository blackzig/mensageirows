package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.service.BuscaNovoContatoService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Intent serviceIntent;

    private String[] opcoes = new String[]{"Enviar mensagem", "Ler mensagens do remetente",
            "Ler mensagens entre duas pessoas", "Sair"};

    private ListView principalLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        principalLv = findViewById(R.id.lv_principal);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, opcoes);
        principalLv.setAdapter(arrayAdapter);
        principalLv.setOnItemClickListener(this);

        serviceIntent = new Intent(getApplicationContext(), BuscaNovoContatoService.class);
        startService(serviceIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (principalLv.getAdapter().getItem(position).toString()) {
            case "Enviar mensagem":
                startActivity(new Intent(this, EnviarActivity.class));
                break;
            case "Ler mensagens do remetente":
                startActivity(new Intent(this, LerActivity.class));
                break;
            case "Ler mensagens entre duas pessoas":
                startActivity(new Intent(this, LerChatActivity.class));
                break;
            case "Sair":
            default:
                finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
}
