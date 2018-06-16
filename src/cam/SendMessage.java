package cam;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.net.*;

public class SendMessage {


    byte[] serializedMessage;
    byte[] data = "1".getBytes();
    int i = 1;
    Message.MessageP2A message;
    DatagramPacket datagramPacket;
    DatagramSocket sendRequestSocket = new DatagramSocket(0);
    InetAddress sendRequestIP = InetAddress.getByName("192.168.1.23");

    public SendMessage() throws SocketException, UnknownHostException {

    }


    public void sendMessage() throws IOException {

        message = Message.MessageP2A.newBuilder()
                .setMessageHeader(
                        Message.MessageHeader.newBuilder()
                                .setVersion(1)
                                .setMessageId(i)
                ).setSendRequest(
                        Message.SendRequestMessage.newBuilder()
                                .setOpaque(
                                        Message.Opaque.newBuilder()
                                                .setDataTypeValue(1)
                                                .setData(ByteString.copyFrom(data))
                                )
                ).build();
        serializedMessage = message.toByteArray();

        datagramPacket = new DatagramPacket(serializedMessage, 0, serializedMessage.length, sendRequestIP, 9011);


        try {
            sendRequestSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent opaque, payload: " + new String(data));
    }

}
