package com.personalproject.grpc.service;

import com.codewithlakshya.grpc.LoginRequest;
import com.codewithlakshya.grpc.LoginResponse;
import com.codewithlakshya.grpc.LoginServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {
    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        String userName = request.getUserName();
        String password = request.getPass();
        LoginResponse loginResponse;

        // fetch the login credentials from the backend and check it
        if(userName.equals("admin") && password.equals("admin")){
            loginResponse = LoginResponse.newBuilder()
                    .setMessage("Congratulations for login")
                    .build();
        } else {
            loginResponse = LoginResponse.newBuilder()
                    .setMessage("Failed to login")
                    .build();
        }

        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();
    }

}
