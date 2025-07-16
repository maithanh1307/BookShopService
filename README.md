# BookShopService
** MÔ HÌNH CRUD
- có 3 tầng chính: Data layer, Bussiness Layer, Presentation
- DAL query trả lại cho DTO để Presentation hiển thị
- Presentation thay đổi DTO qua DAL và lưu vào database
- model dùng chung cả việc đọc và ghi dữ liệuliệu 

** MÔ HÌNH CQRS
- Command and Query Responsibility Segregation
- phân chia các luồng ghi và và đọc dữ liệu ra riêng biệt
- scale độc lập các thành phần đọc và ghi dữ liệuliệu

** MÔ HÌNH EVENT SOURCING
- trạng thái của object sẽ được lưu trữ dưới dạng chuỗi các sự kiện thay đổi

** AXON FRAMEWORK
- xây dựng và phát triển hệ thống phân tán

** Để chạy Axon Server trên local thì vào folder software, chọn folder axon sau đó mở terminal
    java -jar axonserver.jar
    truy cập: localhost://8024

** Chạy Axon Server trên Docker
    Nếu chưa có docker image thì mở terminal và nhập: docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver
    xem docker gồm những container nào: docker ps -a

**Common Service
- Dùng để tái sử dụng các code trong project book service

**Spring AOP (Aspect Oriented Programming)
- lập trình theo hướng khía cạnh
- nằm trong 1 phần của Spring Framework

** SWAGGER
-  là 1 open source
- sử dụng trong 1 dịch vụ web restful
- cho phép định nghĩa và thiết kế api

** NHƯỢC ĐIỂM API GATEWAY
- tăng thời gian response
- tốn tiền
- gây tắc nghẽn

** FILE APPLICATION.YML
- Dùng để cấu hình cho api gateway

**CÁCH CHẠY REDIS TRÊN DOCKER
    docker run --name some-redis -d redis
    connect redis to docker
        docker exec -it redis
        docker exec -it redis redis-cli
**MESSAGE QUEUE
- tuân thủ theo cơ chế FIFO(first in fisrt out)
- giúp cho các service có thể giao tiếp với nhau một cách mượt mà
- Producer
    đại diện cho một cái service gửi request
- consumer
    người nhận message và xử lí nhứng cái message đó
- một cái service có thể vừa làm producer và consumer
**ƯU ĐIỂM MESSAGE QUEUE
- dễ scaling hệ thống
- phân thán hệ thống
- đảm bảo duration/recovery
- hỗ trợ rate limit, batch process
**NHƯỢC ĐIỂM
- khó xử lí đồng bộ
- làm hệ thống phức tạp hơn
- cần đảm bảo message format
- cần Monitoring Queue
- một số message queue được dùng hiện nay:
    rabbitMQ, Kafka, Amazon SQS,...
    kafka là open source nên không mất phí

**APACHE KAFKA
- hệ thống pub/sub phân tán
- được viết bằng java và scala
