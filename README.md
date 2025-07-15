# gRPC Spring Boot Example

A complete example demonstrating how to implement a gRPC service using Spring Boot. This project showcases the integration of gRPC with Spring Boot, providing a simple greeting service that can be used as a template for building more complex microservices.

## 🚀 Features

- **gRPC Service**: Simple greeting service with request/response pattern
- **Spring Boot Integration**: Leverages Spring Boot's auto-configuration
- **Protocol Buffers**: Clean service contract definition
- **Modular Architecture**: Separate modules for common code and service implementation
- **Code Generation**: Automatic generation of Java classes from protobuf definitions
- **Maven Multi-module**: Well-structured project with parent and child modules

## 📋 Prerequisites

- **Java**: 11 or higher
- **Maven**: 3.6 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended)

## 🏗️ Project Structure

```
grpc-springboot/
├── pom.xml                                    # Parent Maven POM
├── greeting-common/                           # Common module with protobuf definitions
│   ├── pom.xml                               # Maven configuration for common module
│   └── src/main/proto/
│       └── greeting.proto                    # Protocol buffer service definition
├── greeting-service/                          # Spring Boot service module
│   ├── pom.xml                               # Maven configuration for service module
│   ├── src/main/java/com/techprimer/grpc/
│   │   ├── GrpcSpringbootApplication.java    # Spring Boot main application class
│   │   └── service/
│   │       └── GreetingServiceImpl.java      # gRPC service implementation
│   ├── src/main/resources/
│   │   └── application.properties            # Spring Boot configuration
│   └── src/test/java/com/techprimer/grpc/
│       └── GrpcSpringbootApplicationTests.java # Basic test class
└── .gitignore                                # Git ignore rules
```

## 🔧 Technology Stack

- **Spring Boot**: 2.5.4
- **gRPC**: 1.35.0
- **Protocol Buffers**: 3.14.0
- **gRPC Spring Boot Starter**: 2.12.0.RELEASE
- **Java**: 11
- **Maven**: Multi-module project

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd grpc-springboot
```

### 2. Build the Project
```bash
# Build the entire project (including protobuf code generation)
mvn clean install
```

### 3. Run the Application
```bash
# Navigate to the service module
cd greeting-service

# Run the Spring Boot application
mvn spring-boot:run
```

The gRPC server will start on port **9090** (default gRPC port).

## 🎯 Service Implementation

### Protocol Buffer Definition

The service contract is defined in `greeting-common/src/main/proto/greeting.proto`:

```protobuf
syntax = "proto3";
package com.techprimer.greeting;

option java_multiple_files = true;
option java_package = "com.techprimer.grpc";

service GreetingService {
    rpc Greeting(GreetingRequest) returns (GreetingResponse);
}

message GreetingRequest {
    string message = 1;
}

message GreetingResponse {
    string message = 1;
}
```

### Service Implementation

The gRPC service is implemented in `GreetingServiceImpl.java`:

```java
@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String greetingMessage = request.getMessage();
        System.out.println("Received message from client: " + greetingMessage);

        GreetingResponse response = GreetingResponse.newBuilder()
                .setMessage("Received you from server: " + greetingMessage + " Hello from server")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```

## 🔍 Interacting with the gRPC Application

This section provides a step-by-step guide on how to interact with your gRPC application using `grpcurl`, a command-line tool for interacting with gRPC servers.

### Step 1: Install grpcurl

First, you need to install `grpcurl` on your system:

#### On macOS:
```bash
brew install grpcurl
```

#### On Linux (Ubuntu/Debian):
```bash
# Download the latest release
curl -sSL https://github.com/fullstorydev/grpcurl/releases/latest/download/grpcurl_$(uname -s)_$(uname -m).tar.gz | tar -xz

# Move to a directory in your PATH
sudo mv grpcurl /usr/local/bin/
```

#### On Linux (using package manager):
```bash
# Ubuntu/Debian
sudo apt-get install grpcurl

# CentOS/RHEL
sudo yum install grpcurl
```

#### On Windows:
```bash
# Using Chocolatey
choco install grpcurl

# Using Scoop
scoop install grpcurl
```

### Step 2: Discover Available Services

Once your gRPC server is running, you can discover what services are available:

```bash
# List all available services on the server
grpcurl --plaintext localhost:9090 list
```

**Expected Output:**
```
com.techprimer.greeting.GreetingService
```

### Step 3: Explore Service Methods

Discover what methods are available in a specific service:

```bash
# List all methods in the GreetingService
grpcurl --plaintext localhost:9090 list com.techprimer.greeting.GreetingService
```

**Expected Output:**
```
Greeting
```

### Step 4: View Service Schema

You can also inspect the service definition and message schemas:

```bash
# Describe the service
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingService

# Describe a specific method
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingService.Greeting

# Describe message types
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingRequest
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingResponse
```

### Step 5: Interact with the API

Now you can make actual calls to your gRPC service:

```bash
# Call the Greeting method with a JSON payload
grpcurl --plaintext -d '{"message": "how are you"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

**Expected Response:**
```json
{
  "message": "Received you from server: how are you Hello from server"
}
```

### Additional grpcurl Examples

#### Test with different messages:
```bash
# Simple greeting
grpcurl --plaintext -d '{"message": "Hello World"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting

# Ask a question
grpcurl --plaintext -d '{"message": "What is the weather today?"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting

# Send a longer message
grpcurl --plaintext -d '{"message": "This is a test message from grpcurl client"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

#### Using a JSON file for complex requests:
```bash
# Create a JSON file
echo '{"message": "Hello from file"}' > request.json

# Use the file in grpcurl
grpcurl --plaintext -d @request.json \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

#### Verbose output for debugging:
```bash
# Add -v flag for verbose output
grpcurl -v --plaintext -d '{"message": "debug test"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

### Troubleshooting grpcurl

#### Common Issues:

1. **Connection Refused:**
   ```bash
   # Make sure the server is running
   # Check if the port is correct
   netstat -tulpn | grep 9090
   ```

2. **Service Not Found:**
   ```bash
   # Verify the service name is correct
   grpcurl --plaintext localhost:9090 list
   ```

3. **Invalid JSON Format:**
   ```bash
   # Ensure JSON is properly formatted
   # Use single quotes around the JSON string
   grpcurl --plaintext -d '{"message": "test"}' \
     localhost:9090 com.techprimer.greeting.GreetingService/Greeting
   ```

4. **TLS/SSL Issues:**
   ```bash
   # Use --plaintext for non-TLS connections
   # Remove --plaintext for TLS connections
   ```

## 🧪 Testing the Service

### Using grpcurl (Recommended)

1. **Install grpcurl** (if not already installed):
   ```bash
   # On macOS
   brew install grpcurl
   
   # On Linux
   sudo apt-get install grpcurl
   ```

2. **Test the service**:
   ```bash
   # List available services
   grpcurl -plaintext localhost:9090 list
   
   # Test the greeting service
   grpcurl -plaintext -d '{"message": "Hello from client"}' \
     localhost:9090 com.techprimer.greeting.GreetingService/Greeting
   ```

### Expected Response
```json
{
  "message": "Received you from server: Hello from client Hello from server"
}
```

### Using a Custom gRPC Client

You can also create a custom gRPC client using the generated stubs:

```java
import com.techprimer.grpc.GreetingRequest;
import com.techprimer.grpc.GreetingResponse;
import com.techprimer.grpc.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = 
            GreetingServiceGrpc.newBlockingStub(channel);

        GreetingRequest request = GreetingRequest.newBuilder()
                .setMessage("Hello from Java client")
                .build();

        GreetingResponse response = stub.greeting(request);
        System.out.println("Response: " + response.getMessage());

        channel.shutdown();
    }
}
```

## 🔄 Development Workflow

### 1. Modify Service Contract
Edit `greeting-common/src/main/proto/greeting.proto` to add new RPC methods or modify existing ones.

### 2. Regenerate Code
```bash
cd greeting-common
mvn clean compile
```

### 3. Update Service Implementation
Modify `GreetingServiceImpl.java` to implement the new methods.

### 4. Test Changes
```bash
cd greeting-service
mvn spring-boot:run
```

### 5. Test with Client
Use grpcurl or your custom client to test the new functionality.

## 📁 Key Files Explained

| File | Purpose |
|------|---------|
| `greeting.proto` | Service contract definition using Protocol Buffers |
| `GreetingServiceImpl.java` | Business logic implementation of the gRPC service |
| `GrpcSpringbootApplication.java` | Spring Boot application entry point |
| `application.properties` | Spring Boot configuration |
| `pom.xml` files | Maven configuration for each module |

## 🛠️ Configuration

### Application Properties
The application uses minimal configuration in `application.properties`:
```properties
spring.application.name=grpc-springboot
```

### gRPC Server Configuration
The gRPC server runs on the default port 9090. You can customize this by adding:
```properties
grpc.server.port=9090
```

## 🚀 Deployment

### Build Executable JAR
```bash
cd greeting-service
mvn clean package
```

### Run the JAR
```bash
java -jar target/grpc-spring-boot-example-0.0.1-SNAPSHOT.jar
```

## 🔍 Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Change the gRPC server port in `application.properties`
   - Kill the process using the port: `lsof -ti:9090 | xargs kill -9`

2. **Protobuf Compilation Errors**
   - Ensure you have the correct protobuf compiler version
   - Run `mvn clean compile` in the `greeting-common` module

3. **gRPC Connection Issues**
   - Verify the server is running on the correct port
   - Check firewall settings
   - Ensure you're using the correct service name in grpcurl

## 📚 Additional Resources

- [gRPC Official Documentation](https://grpc.io/docs/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Protocol Buffers Guide](https://developers.google.com/protocol-buffers)
- [gRPC Spring Boot Starter](https://github.com/yidongnan/grpc-spring-boot-starter)
- [grpcurl Documentation](https://github.com/fullstorydev/grpcurl)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- gRPC team for the high-performance RPC framework
- The gRPC Spring Boot Starter community for the integration library

---

**Happy coding! 🎉** 