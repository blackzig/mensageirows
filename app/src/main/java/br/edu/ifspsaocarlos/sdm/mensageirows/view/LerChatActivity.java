package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.adapter.ChatAdapter;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LerChatActivity extends AppCompatActivity {

    EditText primeiroId, segundoId, idMensagem;
    TextView cabecalhoMensagem;
    Button procurar;
    ListView listaChat;

    LinearLayout listaLinearLayout;

    private MensageiroApi mensageiroApi;

    List<Mensagem> listaMensagensFinal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_chat);

        primeiroId = findViewById(R.id.et_digite_o_primeiro_id);
        segundoId = findViewById(R.id.et_digite_o_segundo_id);
        idMensagem = findViewById(R.id.et_digite_o_id_da_mensagem);
        procurar = findViewById(R.id.bt_procurar);
        listaChat = findViewById(R.id.lv_lista_do_chat_entre_remetente_e_destinatario);

        listaLinearLayout = findViewById(R.id.list_chat);

        cabecalhoMensagem = findViewById(R.id.tv_chat_entre);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nobile.pro.br/sdm/mensageiro/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mensageiroApi = retrofit.create(MensageiroApi.class);

          listaMensagensFinal.clear();

        procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Mensagem>> mensagensDoRemetente = mensageiroApi.getMensagems(
                        idMensagem.getText().toString(),
                        primeiroId.getText().toString(),
                        segundoId.getText().toString());

                mensagensDoRemetente.enqueue(new Callback<List<Mensagem>>() {

                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                        List<Mensagem> listaMensagensRemetente = response.body();

                        if (!listaMensagensRemetente.isEmpty()) {
                            cabecalhoMensagem.setText("Mensagens entre " + listaMensagensRemetente.get(0).getOrigem().getNomeCompleto()
                                    + " e " + listaMensagensRemetente.get(0).getDestino().getNomeCompleto());

                            for (Mensagem m : listaMensagensRemetente) {
                                Log.i("mens ", m.getCorpo());
                                listaMensagensFinal.add(m);
                            }

                            Call<List<Mensagem>> mensagensDoDestinatario = mensageiroApi.getMensagensDoDestinatario(
                                    idMensagem.getText().toString(),
                                    segundoId.getText().toString(),
                                    primeiroId.getText().toString());

                            mensagensDoDestinatario.enqueue(new Callback<List<Mensagem>>() {

                                @Override
                                public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                                    List<Mensagem> listaMensagensDestinatario = response.body();

                                    if (!listaMensagensDestinatario.isEmpty()) {

                                        for (Mensagem m : listaMensagensDestinatario) {
                                            Log.i("men ", m.getCorpo());
                                            listaMensagensFinal.add(m);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Mensagem>> call, Throwable t) {

                                }
                            });

                            ChatAdapter messageAdapter = new ChatAdapter(listaMensagensFinal, LerChatActivity.this);
                            listaChat.setAdapter(messageAdapter);
                        } else {
                            Toast.makeText(LerChatActivity.this, "Não há nenhuma mensagem.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {

                    }
                });
            }
        });

    }

}
