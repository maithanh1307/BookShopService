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

**DISTRIBUTED TRANSACTION
- bao gồm 4 tính chất của ACID
    atomicity
    consistency
    isolation
    durability

**SAGA PATTERN
- gồm có 2 phương thức: choreography, orchestration
    choreography: event based
    orchestration: command based - điều phối các local transaction

**DOCKER
- là một nền tảng về containerization
- containerization
    là một công nghệ ảo hóa
    giúp giải quyết vấn đề bằng cách đóng gói ứng dụng và tất cả các phụ thuộc

- docker engine
là một ứng dụng client server gồm có 3 thành phần chính: Docker cli, docker api, docker daemon
- docker image
sẽ định nghĩa tất cả những gì cần phải chạy của 1 ứng dụng
không thay đổi được từ bên ngoài
sau khi image được chạy sẽ tạo ra container
    tính nhất quán giữa các môi trường
    cách ly môi trường
    phụ thuộc
    ứng dụng
- docker hub
là một registry service được cung cấp bởi docker

**Kubernet
- là một hệ thống điều phối container mã nguồn mở được phát triển bởi google
- lợi ích của kubernets:
    khả năng mở rộng
    tính sẵn có
    triển khai/giao hàng liên tục
    tính di động
- thách thức trong việc triển khai kubernet
    bảo mật
    chi phí
    thách thức về mạng
    tài nguyên
    độ phức tạp
- kubernets architecture
    control plane
    worker node
- install kubernetes
https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download

**VPS Hosting
- dùng để up những project microservice
- lợi ích
    hiệu suất
    quản lí dễ dàng
    chi phí hợp lí
    ứng dụng đa dạng