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


if __name__ == "__main__":
    logging.basicConfig()
    run()