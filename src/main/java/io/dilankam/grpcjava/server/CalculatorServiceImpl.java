package io.dilankam.grpcjava.server;

import com.proto.sum.CalculatorServiceGrpc;
import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import io.grpc.stub.StreamObserver;

/**
 * @author DilankaM
 * @created 10/11/2021 - 00:36
 */
public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
      Sum sum =  request.getSum();
      int result = sum.getFirstNumber() + sum.getSecondNumber();
      SumResponse response = SumResponse.newBuilder().setResult(result).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
}
