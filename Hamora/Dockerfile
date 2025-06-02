# Dùng image có sẵn Maven và JDK 17
FROM maven:3.9.6-eclipse-temurin-17

# Tạo thư mục làm việc
WORKDIR /app

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build WAR file
RUN mvn clean package -DskipTests

# Chạy WAR sau khi build
CMD ["java", "-jar", "target/Hamora-0.0.1-SNAPSHOT.war"]
