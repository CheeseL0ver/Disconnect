package io.flygone.disconnect.gateway.message;

public class ReadyMessage {

    public String t;
    public int s;
    public int op;
    public D d;

    public class D{
        public String session_id;

        public D(String session_id){
            this.session_id = session_id;
        }
    }

}
