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
        String userName = request.getUserName();
        String pass = request.getPass();
        GreetingResponse response;
        if(userName.equals("Lakshya") && pass.equals("admin")){
            response = GreetingResponse.newBuilder()
                    .setMessage("Conguration for login")
                    .build();
        } else {
            response = GreetingResponse.newBuilder()
                    .setMessage("Invalid login")
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
