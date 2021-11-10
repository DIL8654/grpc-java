package io.dilankam.grpcjava.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResonse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.sum.CalculatorServiceGrpc;
import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author DilankaM
 * @created 10/11/2021 - 00:38
 */
public class CalculatorClient {

    public static void main(String[] args){
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();


        // create greet service client ( blockig- synchronous)
        CalculatorServiceGrpc.CalculatorServiceBlockingStub sumClient =
                CalculatorServiceGrpc.newBlockingStub(channel);
        // create proto buff greeting message
        Sum sum = Sum.newBuilder().setFirstNumber(3).setSecondNumber(10).build();
        // create Greet request - proto buff
        SumRequest sumRequest = SumRequest.newBuilder().setSum(sum).build();

        // call RPC
        SumResponse sumResponse = sumClient.sum(sumRequest);
        System.out.println(sumResponse.getResult());

        // do something
        System.out.println("shutting down the channel");
        channel.shutdown(); // shutdown the connection
    }

}
