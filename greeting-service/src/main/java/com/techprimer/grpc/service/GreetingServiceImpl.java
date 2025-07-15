package com.techprimer.grpc.service;

import com.techprimer.grpc.GreetingRequest;
import com.techprimer.grpc.GreetingResponse;
import com.techprimer.grpc.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {

        String greetingMessage = request.getMessage();
        System.out.println("Received message from client:  " + greetingMessage);

        GreetingResponse response = GreetingResponse.newBuilder()
                .setMessage("Recived you from from server: " + greetingMessage + " Hello from server")
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
