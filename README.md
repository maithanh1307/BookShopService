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
