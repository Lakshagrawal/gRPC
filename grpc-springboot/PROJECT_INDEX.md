# gRPC Spring Boot Project Index

## Project Overview
This is a Maven-based gRPC Spring Boot project that demonstrates how to implement a gRPC service using Spring Boot. The project consists of two main modules: `greeting-common` (containing protocol buffer definitions) and `greeting-service` (containing the Spring Boot application).

## Project Structure

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

## Key Components

### 1. Protocol Buffer Definition (`greeting-common/src/main/proto/greeting.proto`)
- **Service**: `GreetingService` with a single RPC method `Greeting`
- **Request Message**: `GreetingRequest` with a string message field
- **Response Message**: `GreetingResponse` with a string message field
- **Package**: `com.techprimer.greeting`
- **Java Package**: `com.techprimer.grpc`

### 2. Generated Code (`greeting-common/target/generated-sources/protobuf/`)
- **Java Classes**: `GreetingRequest.java`, `GreetingResponse.java`, `Greeting.java`
- **gRPC Classes**: `GreetingServiceGrpc.java` (service interface and client stub)
- **Builder Interfaces**: `GreetingRequestOrBuilder.java`, `GreetingResponseOrBuilder.java`

### 3. Spring Boot Application (`greeting-service/`)
- **Main Class**: `GrpcSpringbootApplication.java` - Spring Boot entry point
- **Service Implementation**: `GreetingServiceImpl.java` - Implements the gRPC service
- **Configuration**: `application.properties` - Basic Spring Boot configuration

## Technology Stack

### Dependencies
- **Spring Boot**: 2.5.4
- **gRPC**: 1.35.0
- **Protocol Buffers**: 3.14.0
- **gRPC Spring Boot Starter**: 2.12.0.RELEASE
- **Java Version**: 11

### Build Tools
- **Maven**: Multi-module project with parent POM
- **Protobuf Maven Plugin**: 0.6.1 for code generation
- **Spring Boot Maven Plugin**: For executable JAR creation

## Service Implementation Details

### GreetingServiceImpl
- **Annotation**: `@GrpcService` - Marks the class as a gRPC service
- **Inheritance**: Extends `GreetingServiceGrpc.GreetingServiceImplBase`
- **Method**: `greeting()` - Processes incoming requests and sends responses
- **Functionality**: Echoes back the received message with additional server greeting

## Build and Run Instructions

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build Commands
```bash
# Build the entire project
mvn clean install

# Build only the common module (generates protobuf code)
cd greeting-common && mvn clean compile

# Build and run the service
cd greeting-service && mvn spring-boot:run
```

### Port Configuration
The gRPC server runs on the default gRPC port (9090) as configured by the gRPC Spring Boot starter.

## Testing
- Basic Spring Boot context test is included
- Manual testing can be done using gRPC clients like `grpcurl` or custom client implementations

## Key Features
1. **Protocol Buffer Integration**: Clean separation of service definitions
2. **Spring Boot Integration**: Leverages Spring Boot's auto-configuration
3. **Modular Architecture**: Separate modules for common code and service implementation
4. **Code Generation**: Automatic generation of Java classes from protobuf definitions
5. **gRPC Server**: Built-in gRPC server with Spring Boot integration

## File Descriptions

### Core Files
- `greeting.proto`: Service contract definition
- `GrpcSpringbootApplication.java`: Application entry point
- `GreetingServiceImpl.java`: Business logic implementation
- `pom.xml` files: Maven configuration for each module

### Generated Files
- `GreetingServiceGrpc.java`: Service interface and client stub
- `GreetingRequest.java`/`GreetingResponse.java`: Message classes
- Various builder and interface classes for protobuf messages

### Configuration Files
- `application.properties`: Spring Boot configuration
- `.gitignore`: Git ignore patterns
- Maven POM files: Build and dependency management

## Development Workflow
1. Modify `greeting.proto` to update service contract
2. Run `mvn compile` in `greeting-common` to regenerate code
3. Update service implementation in `GreetingServiceImpl.java`
4. Test using gRPC client tools
5. Build and deploy the application

This project serves as a good starting point for building gRPC services with Spring Boot, demonstrating best practices for modular architecture and protocol buffer integration. 