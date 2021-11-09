package io.dilankam.grpcjava.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResonse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author DilankaM
 * @created 09/11/2021 - 23:58
 */
public class GreetingClient {

  public static void main(String[] args) {

    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    //    DummyServiceGrpc.DummyServiceBlockingStub syncClient =
    //        DummyServiceGrpc.newBlockingStub(channel);

    //      DummyServiceGrpc.DummyServiceFutureStub asyncClient =
    // DummyServiceGrpc.newFutureStub(channel);

    // create greet service client ( blockig- synchronous)
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

    // do something
    System.out.println("shutting down the channel");
    channel.shutdown(); // shutdown the connection
  }
}
