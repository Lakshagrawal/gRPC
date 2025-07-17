from concurrent import futures
import logging
import random
import time

import grpc
import users_pb2
import users_pb2_grpc


class Users(users_pb2_grpc.UsersServicer):
    def GetUser(self, request, context):
        print("client requested for user: ", request.name)
        return users_pb2.UserReply(message="Hello, %s!" % request.name)
    
    def GetUserStreamReply(self, request, context):
        # print("client requested for user: ", request.name)
        # return users_pb2.UserReply(message="Hello, %s!" % request.name)
        while True:
            temperature = random.uniform(20.0, 30.0)
            print("client requested for user: ", request.name)
            yield users_pb2.UserReply(message="Hello, %s!, temperature: %s" % (request.name, temperature))
            time.sleep(5)
    
    def GetUserBidiStream(self, request_iterator, context):
        for request in request_iterator:
            print("client requested for user: ", request.name)
            yield users_pb2.UserReply(message="Hello, %s!" % request.name)
            time.sleep(5)

    
def serve():
    port = "50051"
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    users_pb2_grpc.add_UsersServicer_to_server(Users(), server)
    server.add_insecure_port("[::]:" + port)
    server.start()
    print("Server started, listening on " + port)
    server.wait_for_termination()


if __name__ == "__main__":
    logging.basicConfig()
    serve()