# gRPC Spring Boot Login Application

A complete example demonstrating how to implement a gRPC login service using Spring Boot. This project showcases the integration of gRPC with Spring Boot, providing a simple authentication service that validates username and password credentials.

## 🚀 Features

- **gRPC Login Service**: Authentication service with username/password validation
- **Spring Boot Integration**: Leverages Spring Boot's auto-configuration
- **Protocol Buffers**: Clean service contract definition
- **Modular Architecture**: Separate modules for common code and service implementation
- **Code Generation**: Automatic generation of Java classes from protobuf definitions
- **Maven Multi-module**: Well-structured project with parent and child modules
- **Linux-Optimized**: Designed with Linux development environment in mind

## 📋 Prerequisites

- **Java**: 11 or higher
- **Maven**: 3.6 or higher
- **Linux Environment**: Ubuntu/Debian, CentOS/RHEL, or similar
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended)

## 🏗️ Project Structure

```
grpc-springboot/
├── pom.xml                                    # Parent Maven POM
├── greeting-common/                           # Common module with protobuf definitions
│   ├── pom.xml                               # Maven configuration for common module
│   └── src/main/proto/
│       ├── greeting.proto                    # Protocol buffer service definition
│       └── login.proto                       # Login service definition
├── greeting-service/                          # Spring Boot service module
│   ├── pom.xml                               # Maven configuration for service module
│   ├── src/main/java/com/codewithlakshya/grpc/
│   │   ├── GrpcSpringbootApplication.java    # Spring Boot main application class
│   │   └── service/
│   │       ├── GreetingServiceImpl.java      # gRPC greeting service implementation
│   │       └── LoginServiceImpl.java         # gRPC login service implementation
│   ├── src/main/resources/
│   │   └── application.properties            # Spring Boot configuration
│   └── src/test/java/com/codewithlakshya/grpc/
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

### 2. Build the greeting-common Module
```bash
# Navigate to the greeting-common module
cd greeting-common

# Clean and compile the common module (this generates protobuf classes)
mvn clean compile

# Install the common module to local Maven repository
mvn clean install
```

### 3. Build the greeting-service Module
```bash
# Navigate back to the parent directory
cd ..

# Navigate to the greeting-service module
cd greeting-service

# Clean and compile the service module
mvn clean compile

# Install the service module
mvn clean install
```

### 4. Run the Application
```bash
# Make sure you're in the greeting-service directory
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
option java_package = "com.codewithlakshya.grpc";

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

## 🔐 Login Credentials

**Valid Credentials:**
- **Username**: `admin`
- **Password**: `admin`

**Note**: These credentials are hardcoded in the service implementation for demonstration purposes. In a production environment, you should use a proper authentication system with encrypted passwords and database storage.

## 🔍 Interacting with the gRPC Login Application

This section provides a step-by-step guide on how to interact with your gRPC login application using `grpcurl`, a command-line tool for interacting with gRPC servers.

### Step 1: Install grpcurl on Linux

#### On Ubuntu/Debian:
```bash
# Using package manager (recommended)
sudo apt-get update
sudo apt-get install grpcurl

# Alternative: Download from GitHub
curl -sSL https://github.com/fullstorydev/grpcurl/releases/latest/download/grpcurl_$(uname -s)_$(uname -m).tar.gz | tar -xz
sudo mv grpcurl /usr/local/bin/
```

#### On CentOS/RHEL/Fedora:
```bash
# Using package manager
sudo yum install grpcurl
# or
sudo dnf install grpcurl

# Alternative: Download from GitHub
curl -sSL https://github.com/fullstorydev/grpcurl/releases/latest/download/grpcurl_$(uname -s)_$(uname -m).tar.gz | tar -xz
sudo mv grpcurl /usr/local/bin/
```

#### On Arch Linux:
```bash
sudo pacman -S grpcurl
```

### Step 2: Discover Available Services

Once your gRPC server is running, discover what services are available:

```bash
# List all available services on the server
grpcurl --plaintext localhost:9090 list
```

**Expected Output:**
```
com.techprimer.greeting.GreetingService
com.techprimer.greeting.LoginService
```

### Step 3: Explore Service Methods

Discover what methods are available in the services:

```bash
# List all methods in the GreetingService
grpcurl --plaintext localhost:9090 list com.techprimer.greeting.GreetingService

# List all methods in the LoginService
grpcurl --plaintext localhost:9090 list com.techprimer.greeting.LoginService
```

**Expected Output:**
```
GreetingService:
Greeting

LoginService:
Login
```

### Step 4: View Service Schema

Inspect the service definition and message schemas:

```bash
# Describe the services
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingService
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.LoginService

# Describe the methods
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingService.Greeting
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.LoginService.Login

# Describe message types
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingRequest
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.GreetingResponse
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.LoginRequest
grpcurl --plaintext localhost:9090 describe com.techprimer.greeting.LoginResponse
```

### Step 5: Test Login with Valid Credentials

Test the login service with the correct credentials:

```bash
# Login with valid credentials
grpcurl --plaintext -d '{"user_name": "admin", "pass": "admin"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login
```

**Expected Response (Success):**
```json
{
  "message": "Congratulations for login"
}
```

### Step 6: Test Login with Invalid Credentials

Test the login service with incorrect credentials:

```bash
# Login with wrong username
grpcurl --plaintext -d '{"user_name": "John", "pass": "admin"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login

# Login with wrong password
grpcurl --plaintext -d '{"user_name": "admin", "pass": "wrongpass"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login

# Login with both wrong credentials
grpcurl --plaintext -d '{"user_name": "John", "pass": "wrongpass"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login
```

**Expected Response (Failure):**
```json
{
  "message": "Failed to login"
}
```

## 🧪 Additional Testing Examples

### Test with different scenarios:
```bash
# Test with empty credentials
grpcurl --plaintext -d '{"user_name": "", "pass": ""}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login

# Test with case sensitivity (should fail)
grpcurl --plaintext -d '{"user_name": "Admin", "pass": "admin"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login

# Test with extra spaces (should fail)
grpcurl --plaintext -d '{"user_name": " admin ", "pass": "admin"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login
```

### Using a JSON file for complex requests:
```bash
# Create a JSON file with credentials
cat > login_request.json << EOF
{
  "user_name": "admin",
  "pass": "admin"
}
EOF

# Use the file in grpcurl
grpcurl --plaintext -d @login_request.json \
  localhost:9090 com.techprimer.greeting.LoginService/Login
```

### Verbose output for debugging:
```bash
# Add -v flag for verbose output
grpcurl -v --plaintext -d '{"user_name": "admin", "pass": "admin"}' \
  localhost:9090 com.techprimer.greeting.LoginService/Login
```

## 🧪 Testing the Greeting Service

You can also test the GreetingService which provides a simple greeting functionality:

```bash
# Test the greeting service with a message
grpcurl --plaintext -d '{"message": "Hello from client"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

**Expected Response:**
```json
{
  "message": "Received your: Hello from client. Hello from server"
}
```

### Test with different messages:
```bash
# Test with empty message
grpcurl --plaintext -d '{"message": ""}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting

# Test with special characters
grpcurl --plaintext -d '{"message": "Hello World! @#$%"}' \
  localhost:9090 com.techprimer.greeting.GreetingService/Greeting
```

## 🔧 Linux-Specific Commands

### Check if the server is running:
```bash
# Check if port 9090 is listening
netstat -tulpn | grep 9090

# Alternative using ss command
ss -tulpn | grep 9090

# Check if the process is running
ps aux | grep java
```

### Kill the process if needed:
```bash
# Find and kill the process using port 9090
sudo lsof -ti:9090 | xargs kill -9

# Alternative method
sudo fuser -k 9090/tcp
```

### Monitor server logs:
```bash
# Follow the application logs
tail -f greeting-service/target/spring-boot.log

# Check system logs
journalctl -u your-service-name -f
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
Modify `GreetingServiceImpl.java` to implement the new methods or update authentication logic.

### 4. Test Changes
```bash
cd greeting-service
mvn spring-boot:run
```

### 5. Test with grpcurl
Use the grpcurl commands above to test the new functionality.

## 📁 Key Files Explained

| File | Purpose |
|------|---------|
| `greeting.proto` | Service contract definition using Protocol Buffers |
| `GreetingServiceImpl.java` | Login authentication logic implementation |
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

### Create a Systemd Service (Linux)
```bash
# Create service file
sudo tee /etc/systemd/system/grpc-login.service << EOF
[Unit]
Description=gRPC Login Service
After=network.target

[Service]
Type=simple
User=your-user
WorkingDirectory=/path/to/your/app
ExecStart=/usr/bin/java -jar grpc-spring-boot-example-0.0.1-SNAPSHOT.jar
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Enable and start the service
sudo systemctl daemon-reload
sudo systemctl enable grpc-login
sudo systemctl start grpc-login
```

## 🔍 Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Check what's using the port
   sudo lsof -i :9090
   
   # Kill the process
   sudo lsof -ti:9090 | xargs kill -9
   ```

2. **Protobuf Compilation Errors**
   ```bash
   # Ensure you have the correct protobuf compiler version
   cd greeting-common
   mvn clean compile
   ```

3. **gRPC Connection Issues**
   ```bash
   # Verify the server is running
   netstat -tulpn | grep 9090
   
   # Check firewall settings
   sudo ufw status
   sudo iptables -L
   ```

4. **grpcurl Installation Issues**
   ```bash
   # Verify grpcurl is installed
   which grpcurl
   grpcurl --version
   
   # Reinstall if needed
   sudo apt-get remove grpcurl
   sudo apt-get install grpcurl
   ```

## 📚 Additional Resources

- [gRPC Official Documentation](https://grpc.io/docs/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Protocol Buffers Guide](https://developers.google.com/protocol-buffers)
- [gRPC Spring Boot Starter](https://github.com/yidongnan/grpc-spring-boot-starter)
- [grpcurl Documentation](https://github.com/fullstorydev/grpcurl)
- [Linux System Administration](https://www.linux.org/)

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
- Linux community for robust development tools

---

**Happy coding on Linux! 🐧** 