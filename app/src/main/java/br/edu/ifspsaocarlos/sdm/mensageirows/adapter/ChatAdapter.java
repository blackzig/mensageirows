package br.edu.ifspsaocarlos.sdm.mensageirows.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        Mensagem messageChat = (Mensagem) getItem(position);

        subject.setText("Assunto: " + messageChat.getAssunto());
        message.setText("Mensagem: " + messageChat.getCorpo());

        Integer itsEven = position % 2;

        if (itsEven == 0) {
            lineMessage.setBackgroundColor(Color.BLACK);
        } else {
            lineMessage.setBackgroundColor(Color.BLUE);
        }

        return lineMessage;
    }
}
