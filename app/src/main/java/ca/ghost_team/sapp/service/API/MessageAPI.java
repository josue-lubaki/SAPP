package ca.ghost_team.sapp.service.API;

import java.util.List;

import ca.ghost_team.sapp.model.Message;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MessageAPI {
    @FormUrlEncoded
    @POST("message.php")
    Call<List<Message>> getAllMessagesViaAPI(@Field("idSenderReceiver") int id);

    @FormUrlEncoded
    @POST("sendmessage.php")
    Call<String> sendMessageViaAPI(
            @Field("message") String message,
            @Field("idSender") int idSender,
            @Field("idReceiver") int idReceiver,
            @Field("idAnnonce") int idAnnonce,
            @Field("date") String date
    );
}
