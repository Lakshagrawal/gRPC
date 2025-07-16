package com.personalproject.grpc.service;

import com.codewithlakshya.grpc.GreetingRequest;
import com.codewithlakshya.grpc.GreetingResponse;
import com.codewithlakshya.grpc.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String message = request.getMessage();
        System.out.println("Received Message: " + message);

        GreetingResponse response = GreetingResponse.newBuilder()
                        .setMessage("Received your: " + message + ". Hello from server")
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
