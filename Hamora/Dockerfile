# Sử dụng JDK 17 chính thức
FROM eclipse-temurin:17-jdk

# Tạo thư mục chứa project
WORKDIR /app

# Copy toàn bộ mã nguồn vào trong container
COPY . .

# Build project bằng Maven wrapper (mvnw)
RUN mvn clean package -DskipTests

# Chạy file WAR sau khi build (chỉnh đúng tên WAR)
CMD ["java", "-jar", "target/Hamora-0.0.1-SNAPSHOT.war"]
