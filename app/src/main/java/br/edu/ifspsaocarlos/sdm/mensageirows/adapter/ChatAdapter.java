package br.edu.ifspsaocarlos.sdm.mensageirows.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.mensageirows.view.LerChatActivity;

/**
 * Created by zigui on 24/03/2018.
 */

public class ChatAdapter extends BaseAdapter {

    private List<Mensagem> messagesChat;
    private LerChatActivity lerChatActivity;

    public ChatAdapter(List<Mensagem> messagesChat, LerChatActivity lerChatActivity) {
        this.messagesChat = messagesChat;
        this.lerChatActivity = lerChatActivity;
    }

    @Override
    public int getCount() {
        return messagesChat.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesChat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineMessage = lerChatActivity.getLayoutInflater().inflate(R.layout.message, parent, false);

        TextView subject = lineMessage.findViewById(R.id.tv_subject);
        TextView message = lineMessage.findViewById(R.id.tv_message);
        TextView messageOwner = lineMessage.findViewById(R.id.tv_name_owner_mesagem);

        Mensagem messageChat = (Mensagem) getItem(position);

        String idOrigemLista = messageChat.getOrigemId();
        String idDestinoLista = messageChat.getDestinoId();

        if ((Integer.parseInt(idOrigemLista) > Integer.parseInt(idDestinoLista))) {
            messageOwner.setText("Enviado por: " + messageChat.getOrigem().getApelido());
            lineMessage.setBackgroundColor(Color.BLACK);
        } else {
            messageOwner.setText("Enviado por: " + messageChat.getOrigem().getApelido());
            lineMessage.setBackgroundColor(Color.BLUE);
        }


        subject.setText("Assunto: " + messageChat.getAssunto() + " " + messageChat.getId());
        message.setText("Mensagem: " + messageChat.getCorpo());


        return lineMessage;
    }
}
