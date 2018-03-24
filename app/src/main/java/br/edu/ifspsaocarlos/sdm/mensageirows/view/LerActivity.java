package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LerActivity extends AppCompatActivity {

    private EditText origemEt;
    private EditText destinoEt;
    private EditText ultimaMensagemEt;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler);

        origemEt = findViewById(R.id.et_origem_ler);
        destinoEt = findViewById(R.id.et_destino_ler);
        ultimaMensagemEt = findViewById(R.id.et_ultima_mensagem_ler);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bt_ler) {

            Call<List<Mensagem>> listaMensagensCall = mensageiroApi.getMensagems(
                    ultimaMensagemEt.getText().toString(),
                    origemEt.getText().toString(),
                    destinoEt.getText().toString());

            listaMensagensCall.enqueue(new Callback<List<Mensagem>>() {
                @Override
                public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>>
                        response) {

                    List<Mensagem> listaMensagens = response.body();
                    ArrayList<String> corpoMensagens = new ArrayList<>();

                    for (Mensagem mensagem : listaMensagens) {
                        corpoMensagens.add(mensagem.getCorpo());
                    }

                    Intent mostraMensagensIntent = new Intent(LerActivity.this,
                            MostrarMensagensActivity.class);
                    mostraMensagensIntent.putStringArrayListExtra(
                            getString(R.string.mensagens_string_array_extra),
                            corpoMensagens);
                    startActivity(mostraMensagensIntent);
                }

                @Override
                public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                    Toast.makeText(LerActivity.this, "Erro na recuperação das mensagens!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
