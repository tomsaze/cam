package cam;


import java.io.IOException;
import java.net.*;


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


    public void sendMessage( int acceleration, double heading, double latitude, double longitude, double speed, int id, long timestamp,  int yawRate, int alert) throws IOException, InterruptedException {

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
                                                    .setAcceleration(acceleration)
                                                    .setHeading(heading)
                                                    .setLatitude(latitude)
                                                    .setLongitude(longitude)
                                                    .setSpeed(speed)
                                                    .setStationId(id)
                                                    .setTimestamp(timestamp)
                                                    .setYawRate(yawRate)
                                                    .setAlert(alert)
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
    }



}
