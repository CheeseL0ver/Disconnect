package io.flygone.disconnect.socket;

import io.flygone.disconnect.gateway.message.CreateMessage;
import io.flygone.disconnect.gateway.message.ReadyMessage;
import io.flygone.disconnect.gateway.message.ServerMessage;
import io.flygone.disconnect.handler.ConfigHandler;
import io.flygone.disconnect.handler.WebsocketServerHandler;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.flygone.disconnect.Disconnect.logger;

public class Client extends WebSocketClient {

    public static long heartbeat;
    public boolean isScheduled;
    public boolean isIdentified;
    public boolean heartbeatAcknowledged;
    private ScheduledExecutorService scheduleTaskExecutor;
    private final String TOKEN = ConfigHandler.discordToken;
    private static String sessionID;
    private int lastSeqNum;

    public Client(URI serverUri , Draft draft ) {
        super( serverUri, draft );
        this.isScheduled = false;
        this.isIdentified = false;
        this.heartbeatAcknowledged = false;
    }

    public Client( URI serverURI ) {
        super( serverURI );
    }

    public Client( URI serverUri, Map<String, String> httpHeaders ) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        System.out.println(String.format("On open: %s", handshakeData));

    }

    @Override
    public void onMessage(String message) {

        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

        switch(serverMessage.op){

            case 7:
                //Reconnect
                try {
                    reconnectClient();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;

            case 11:
                heartbeatAcknowledged = true;
                break;
            case 10:
                heartbeat = (serverMessage.d.heartbeat_interval);
                //Perform heartbeat
                sendPulse(serverMessage.s);
                if (!isScheduled){
                    //schedule
                    schedule(serverMessage);
                }
                if (!isIdentified){
                    //identify
                    identify();
                }
                break;

            case 0:
                //Update the seq number
                try{
                    lastSeqNum = serverMessage.s;
                }
                catch(NullPointerException exception){
                    lastSeqNum = Integer.parseInt("null");
                }

                if (serverMessage.t.equals("MESSAGE_CREATE")){
                    //Send message to server chat
                    CreateMessage createdMessage = new Gson().fromJson(message, CreateMessage.class);
//                    System.out.println(String.format("Discord User Message Created: User- %s, Message: %s", createdMessage.d.author.username, createdMessage.d.content));
                    WebsocketServerHandler.sendGlobalMessage(createdMessage);
                }
                if (serverMessage.t.equals("MESSAGE_UPDATE")){
                    //Send message to server chat
                    CreateMessage updatedMessage = new Gson().fromJson(message, CreateMessage.class);
//                    System.out.println(String.format("Discord User Message Updated: User- %s, Message: %s", updatedMessage.d.author.username, updatedMessage.d.content));
                }
                if (serverMessage.t.equals("READY")){
                    ReadyMessage readyMessage = new Gson().fromJson(message, ReadyMessage.class);
                    sessionID = readyMessage.d.session_id; //Save the sessionID
//                    System.out.println(String.format("Discord Ready Message: SessionID- %s", sessionID));
                }
                break;

        }

//        System.out.println(String.format("Server message: %s", message));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.log(Level.ERROR, String.format("Gateway connection closed: %d, %s", code, reason));
    }

    @Override
    public void onError(Exception ex) {

        logger.log(Level.ERROR, (String.format("An error occurred : %s", ex)));
    }

    public void schedule(ServerMessage serverMessage){
        scheduleTaskExecutor= Executors.newScheduledThreadPool(5);
        isScheduled = true;

        // This schedule a task to run every {heartbeat} milliseconds:
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                sendPulse(serverMessage.s);
            }
        }, heartbeat, heartbeat, TimeUnit.MILLISECONDS);
    }

    public void identify(){
        if (TOKEN.equals(""))
            return;
        String json = String.format("{\"op\":%d,\"d\":{\"token\":\"%s\",\"properties\":{\"$os\":\"%s\",\"$broswer\":\"%s\",\"$device\":\"%s\"}}}", 2, TOKEN, "linux", "java", "x1");
        this.send(json);
        isIdentified = true;
    }

    public void sendPulse(int s){
            String json = String.format("{\"op\":%d,\"d\": %d}", 1, s);
            logger.log(Level.DEBUG, (String.format("Client message: %s", json)));
            this.send(json);
    }

    public void reconnectClient() throws URISyntaxException {

        //Remove heartbeat
        scheduleTaskExecutor.shutdown();
        this.close();
    }
}
