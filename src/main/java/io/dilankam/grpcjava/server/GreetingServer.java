package io.dilankam.grpcjava.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author DilankaM
 * @created 09/11/2021 - 23:52
 */
public class GreetingServer {

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Hello gRPC server");
      Server server = ServerBuilder.forPort(50051).addService(new GreetServiceImpl()).addService(new CalculatorServiceImpl()).build();

      server.start();

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  System.out.println("Received shutdown request..");
                  server.shutdown();
                  System.out.println("Successfully stopped the server");
                }));

      server.awaitTermination();
  }
}
