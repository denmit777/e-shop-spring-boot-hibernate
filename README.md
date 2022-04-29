Адрес стартовой страницы: http://localhost:8081/e-shop
Email отправителя: "tt0188723@gmail.com" 

При регистрации адрес почты должен содержать логин пользователя,
иначе - переход на страницу ошибки.

Для получения письма после выбора заказа на странице Good и нажатия на кнопку Submit:
Login получателя письма: beigruq
Email получателя письма: beigruq@yopmail.com 
При регистрации 

Папка с images: resources/images

REST CONTROLLERS:

UserController:
GetAllUsers(GET): http://localhost:8081/users;
GetUserById(GET): http://localhost:8081/users/id;
SaveUser(POST): http://localhost:8081/users + body;
UpdateUser(PUT): http://localhost:8081/users/id + body;
DeleteUser(DELETE): http://localhost:8081/users/id;

GoodController:
GetAllGoods(GET): http://localhost:8081/goods;
GetGoodById(GET): http://localhost:8081/goods/id;
SaveGood(POST): http://localhost:8081/goods + body;
UpdateGood(PUT): http://localhost:8081/goods/id + body;
DeleteGood(DELETE): http://localhost:8081/goods/id;

OrderController:
GetAllOrders(GET): http://localhost:8081/orders;
GetOrderById(GET): http://localhost:8081/orders/id;
GetOrderByUserId(GET): http://localhost:8081/orders/users/userId;
SaveOrder(POST): http://localhost:8081/orders + body;
UpdateOrder(PUT): http://localhost:8081/orders/id + body;
DeleteOrder(DELETE): http://localhost:8081/orders/id;

ImageController:
GetAllImages(GET): http://localhost:8081/orders/images;
DownloadImage(GET): http://localhost:8081/orders/images/id;
UploadImage(POST): http://localhost:8081/orders/images + body;
DeleteImage(DELETE): http://localhost:8081/orders/images/id;