package cam;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.net.*;

import static java.lang.Thread.*;

public class SendMessage {


    byte[] serializedMessage;
    byte[] data = "teste".getBytes();
    int i = 1;
    Message.MessageP2A message;
    DatagramPacket datagramPacket;
    DatagramSocket sendRequestSocket = new DatagramSocket(8080);
    InetAddress sendRequestIP = InetAddress.getByName("192.168.1.11");

    public SendMessage() throws SocketException, UnknownHostException {

    }


    public void sendMessage() throws IOException, InterruptedException {

        while (true) {
            data = ("CarID: " + 1+ "Timestamp:"+ "Latitude\", \"Longitude\", \"Heading\", \"Speed\", \"Acceleration\", \"YawRate\", }" + i).getBytes();


            message = Message.MessageP2A.newBuilder()
                    .setMessageHeader(
                            Message.MessageHeader.newBuilder()
                                    .setVersion(1)
                                    .setMessageId(i)
                    ).setSendRequest(
                            Message.SendRequestMessage.newBuilder()
                                    .setCam(
                                            Message.CAM_p2a_Message.newBuilder()
                                                    .setAcceleration(234)
                                                    .setHeading(50)
                                                    .setLatitude(12.3456)
                                                    .setLongitude(8.4556)
                                                    .setSpeed(200)
                                                    .setStationId(2)
                                                    .setTimestamp(300)
                                                    .setYawRate(0)
                                    )
                    )
                    .build();




        serializedMessage = message.toByteArray();

        datagramPacket = new DatagramPacket(serializedMessage, 0, serializedMessage.length, sendRequestIP, 8080);


        try {
            sendRequestSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent opaque, payload: " + new String(data));
        i++;
        sleep(1000);
    }
    }



}
