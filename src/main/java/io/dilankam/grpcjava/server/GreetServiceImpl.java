package io.dilankam.grpcjava.server;

import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

/**
 * @author DilankaM
 * @created 10/11/2021 - 00:16
 */
public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
  @Override
  public void greet(GreetRequest request, StreamObserver<GreetResonse> responseObserver) {
    Greeting greeting = request.getGreeting();
    String firstName = greeting.getFirstName();
    String lastName = greeting.getLastName();

    String result = "Hello " + firstName;

    GreetResonse resonse = GreetResonse.newBuilder().setResult(result).build();
    // send the response
    responseObserver.onNext(resonse);

    // complete the RPC call
    responseObserver.onCompleted();
  }

  @Override
  public void greetManyTimes(
      GreetMenyTimeRequest request, StreamObserver<GreetMenyTimeResponse> responseObserver) {
    String firstName = request.getGreeting().getFirstName();

    try {
      for (int i = 0; i < 10; i++) {
        String result = "Hello " + firstName + ", response number: " + i;
        GreetMenyTimeResponse response =
            GreetMenyTimeResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        Thread.sleep(1000L);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      responseObserver.onCompleted();
    }
  }
}
