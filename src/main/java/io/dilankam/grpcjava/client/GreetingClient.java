package io.dilankam.grpcjava.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author DilankaM
 * @created 09/11/2021 - 23:58
 */
public class GreetingClient {

  public static void main(String[] args) {
    System.out.println("hello, this is a gRPC client..!");
    GreetingClient greetingClient = new GreetingClient();
    greetingClient.run();
  }

  public void run() {
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    //    runUnaryExample(channel);
    //    runServerStreamingExample(channel);
    runClientStreaming(channel);

    System.out.println("shutting down the channel");
    channel.shutdown(); // shutdown the connection
  }

  private void runClientStreaming(ManagedChannel channel) {
    GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);
    CountDownLatch latch = new CountDownLatch(1);

    StreamObserver<LongGreetRequest> requestObserver =
        asyncClient.longGreet(
            new StreamObserver<LongGreetResponse>() {
              @Override
              public void onNext(LongGreetResponse longGreetResponse) {
                // get response from the server
                System.out.println("received response from the server");
                System.out.println(longGreetResponse.getResult());
              }

              @Override
              public void onError(Throwable throwable) {
                // get error from the server
              }

              @Override
              public void onCompleted() {
                // server completed the sending data
                System.out.println("server has completed sending data");
                latch.countDown();
              }
            });
    System.out.println("sending message 1");
    requestObserver.onNext(
        LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("Client streaming 1").build())
            .build());
      System.out.println("sending message 2");
    requestObserver.onNext(
        LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("Client streaming 2").build())
            .build());
      System.out.println("sending message 3");
    requestObserver.onNext(
        LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("Client streaming 3").build())
            .build());
    // tell to the server that client is completed sending data
    requestObserver.onCompleted();
      try {
          latch.await(3L, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }

  private void runServerStreamingExample(ManagedChannel channel) {

    // server streaming
    GreetServiceGrpc.GreetServiceBlockingStub greetClient =
        GreetServiceGrpc.newBlockingStub(channel);

    GreetMenyTimeRequest greetMenyTimeRequest =
        GreetMenyTimeRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("Streaming Client"))
            .build();
    // stream the responses by blocking manner
    greetClient
        .greetManyTimes(greetMenyTimeRequest)
        .forEachRemaining(
            response -> {
              System.out.println(response.getResult());
            });
    // do something

  }

  private void runUnaryExample(ManagedChannel channel) {
    // Unary

    //         create greet service client ( blockig- synchronous)
    GreetServiceGrpc.GreetServiceBlockingStub greetClient =
        GreetServiceGrpc.newBlockingStub(channel);
    // create proto buff greeting message
    Greeting greeting =
        Greeting.newBuilder().setFirstName("AngryBird").setLastName("Github").build();
    // create Greet request - proto buff
    GreetRequest greetRequest = GreetRequest.newBuilder().setGreeting(greeting).build();

    // call RPC
    GreetResonse greetResponse = greetClient.greet(greetRequest);
    System.out.println(greetResponse.getResult());
  }
}
