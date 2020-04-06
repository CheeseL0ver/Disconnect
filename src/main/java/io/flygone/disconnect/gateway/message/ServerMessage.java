package io.flygone.disconnect.gateway.message;

public class ServerMessage {
    public String t;
    public int s;
    public int op;
    public D d;

    public ServerMessage(String t, int s, int op, D d){
        this.t = t;
        this.s = s;
        this.op = op;
        this.d = d;
    }

    public class D{
        public long heartbeat_interval;

        public D(long heartbeat_interval){
            this.heartbeat_interval = heartbeat_interval;
        }
    }
}
