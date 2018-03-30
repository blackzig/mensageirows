package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    EditText idOrigem, idDestinatario, idMensagem;
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

        idOrigem = findViewById(R.id.et_digite_o_primeiro_id);
        idDestinatario = findViewById(R.id.et_digite_o_segundo_id);
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


        procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaMensagensFinal.clear();

                Call<List<Mensagem>> mensagensDoRemetente = mensageiroApi.getMensagems(
                        idMensagem.getText().toString(),
                        idOrigem.getText().toString(),
                        idDestinatario.getText().toString());

                mensagensDoRemetente.enqueue(new Callback<List<Mensagem>>() {

                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                        List<Mensagem> listaMensagensRemetente = response.body();

                        if (!listaMensagensRemetente.isEmpty()) {
                            cabecalhoMensagem.setText("Mensagens entre " + listaMensagensRemetente.get(0).getOrigem().getNomeCompleto()
                                    + " e " + listaMensagensRemetente.get(0).getDestino().getNomeCompleto());

                            for (Mensagem m : listaMensagensRemetente) {
                                listaMensagensFinal.add(m);
                            }

                            mensagensDoDestinatario();

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

    public void mensagensDoDestinatario() {
        Call<List<Mensagem>> mensagensDoDestinatario = mensageiroApi.getMensagensDoDestinatario(
                idMensagem.getText().toString(),
                idDestinatario.getText().toString(),
                idOrigem.getText().toString());

        mensagensDoDestinatario.enqueue(new Callback<List<Mensagem>>() {

            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                List<Mensagem> listaMensagensDestinatario = response.body();

                if (!listaMensagensDestinatario.isEmpty()) {

                    for (Mensagem m : listaMensagensDestinatario) {
                        listaMensagensFinal.add(m);
                    }

                    Collections.sort(listaMensagensFinal, new Comparator<Mensagem>() {
                        @Override
                        public int compare(Mensagem o1, Mensagem o2) {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });

                    ChatAdapter messageAdapter = new ChatAdapter(listaMensagensFinal, LerChatActivity.this);
                    listaChat.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });

    }

}
