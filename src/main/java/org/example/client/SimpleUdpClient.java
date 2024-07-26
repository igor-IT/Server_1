package org.example.client;

import org.example.gen.UserMovementOuterClass;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SimpleUdpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket ds = null;
        InetAddress serverAddress = null;

        try {
            ds = new DatagramSocket();
            serverAddress = InetAddress.getByName("localhost"); // замените "localhost" на адрес сервера
            byte[] sendData;

            for (int i = 1; ; i++) {

                UserMovementOuterClass.Vector3 position = UserMovementOuterClass.Vector3.newBuilder()
                        .setX(100.0f)
                        .setY(50.0f)
                        .setZ(200.0f)
                        .build();

                UserMovementOuterClass.Vector3 velocity = UserMovementOuterClass.Vector3.newBuilder()
                        .setX(1.0f)
                        .setY(0.5f)
                        .setZ(-0.2f)
                        .build();

                UserMovementOuterClass.UserMovement userMovement = UserMovementOuterClass.UserMovement.newBuilder()
                        .setUserId(12345)
                        .setPosition(position)
                        .setVelocity(velocity)
                        .setTimestamp(System.currentTimeMillis())
                        .build();

                byte[] data = userMovement.toByteArray();

                // Подготовьте пакет данных для отправки на сервер
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 9099);

                // Отправьте пакет
                ds.send(packet);

                System.out.println("UDP packet sent: " + "Message " + i);

                // задержка в 1 секунду
                Thread.sleep(100);
            }

        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
