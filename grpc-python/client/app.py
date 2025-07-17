import logging

import grpc
import users_pb2
import users_pb2_grpc


def run():
    print("Will try to greet world ...")
    with grpc.insecure_channel("localhost:50051") as channel:
        stub = users_pb2_grpc.UsersStub(channel)
        response = stub.GetUser(users_pb2.UserRequest(name="Lakshya Agrawal"))
        print("Greeter client received: " + response.message)

        # print("Getting user stream reply**********************")
        # response = stub.GetUserStreamReply(users_pb2.UserRequest(name="Lakshya Agrawal"))
        # for response in response:
        #     print("Greeter client received: " + response.message)

        print("Getting user bidi stream**********************")
        # Create an iterator of requests for bidirectional streaming
        def request_iterator():
            for i in range(3):  # Send 3 requests
                yield users_pb2.UserRequest(name=f"Lakshya Agrawal {i+1}")
        
        responses = stub.GetUserBidiStream(request_iterator())
        for response in responses:
            print("Greeter client received: " + response.message)
            
            


if __name__ == "__main__":
    logging.basicConfig()
    run()