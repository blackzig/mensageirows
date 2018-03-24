package br.edu.ifspsaocarlos.sdm.mensageirows.api;


import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.model.Contato;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zigui on 22/03/2018.
 */

public interface MensageiroApi {

    @POST("mensagem")
    Call<ResponseBody> postMensagem(@Body RequestBody novoContato);

    @GET("rawmensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<List<Mensagem>> getMensagems(@Path("ultimaMensagemId") String ultimaMensagemId,
                                      @Path("origemId") String origemId, @Path("destinoId") String destinoId);

    @GET("rawmensagens/{ultimaMensagemId}/{destinoId}/{origemId}")
    Call<List<Mensagem>> getMensagensDoDestinatario(@Path("ultimaMensagemId") String ultimaMensagemId,
                                                    @Path("destinoId") String destinoId, @Path("origemId") String origemId);

    @GET("rawcontatos")
    Call<List<Contato>> getContatos();

}
