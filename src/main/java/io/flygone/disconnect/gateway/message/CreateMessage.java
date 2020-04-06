package io.flygone.disconnect.gateway.message;

public class CreateMessage {

    public String t;
    public int s;
    public int op;
    public D d;

    public class D{
        public String timestamp;
        public String content;
        public Author author;

        public D(String timestamp, String content, Author author){
            this.timestamp = timestamp;
            this.content = content;
            this.author = author;
        }

        public class Author{
            public String username;

            public Author(String username){
                this.username = username;
            }
        }
    }

}
