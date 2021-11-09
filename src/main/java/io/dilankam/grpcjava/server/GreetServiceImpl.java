package io.dilankam.grpcjava.server;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResonse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
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
    //send the response
    responseObserver.onNext(resonse);

    // complete the RPC call
    responseObserver.onCompleted();

  }
}
