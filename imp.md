# Port Mappings

- User Service : 8080
- Product Service : 8081
- Cart Service : 8082
- Order Service : 8083
- Payment Service : 8084
- Notification Service : 8085
- Search Service : 8086
- Shipping Service : 8087
- Review Service : 8088
- Review Service : 8089
- Wishlist Service : 8090
- Analytics Service : 8091
- Dashboard Service : 8092
- Recommendation Service : 8093
- Promotion Service : 8094
---
- API Gateway : 9191
- Eureka Server : 8761
- Config Server : 8888
- Zipkin Server : 9411
- Datadog Server : 8125
- Prometheus Server : 9090
- Grafana Server : 3000
- Kibana Server : 5601
- Redis Server : 6379
- MySQL Server : 3306
- RabbitMQ Server : 5672
- MongoDB Server : 27017
- Elasticsearch Server : 9200
- Kafka Server : 9092

---

---

## To enable swagger documentation in each service disable ControllerAdvice

---

---
To user Instamojo payment gateway

```
<a href="https://www.instamojo.com/@instamojo/" rel=”im-checkout” data-behaviour=”remote” data-style=”light” data-text=”Checkout With Instamojo” >
    InstMojo
</a>
```

---

---

Advanced Features (Optional)
Search Functionality:

- Use Elasticsearch to implement a fast and scalable search feature.

- Recommendation System:

        Build a simple recommendation system to suggest products based on user behavior.

- Analytics Dashboard:

      Use Spring Boot Actuator and Prometheus to monitor microservices.
      Display analytics data (e.g., popular products, sales trends) in the React app.

- CI/CD Pipeline:

      Set up a CI/CD pipeline using GitHub Actions or Jenkins to automate testing and deployment.

- Service Communication:

      Use Feign Client or RestTemplate for synchronous communication.

- Asynchronous Communication: 
     
      Use RabbitMQ or Kafka for asynchronous communication (e.g., order placed → send notification).

- Centralized Logging:

      Use tools like ELK Stack (Elasticsearch, Logstash, Kibana) or Splunk for centralized logging and monitoring.

🔹 Order Tracking: Real-time updates using WebSockets

---

---

object mapper mapping rabbitmq (imp)
retryable template for notifications

---

@Aggregation(pipeline = {
"{ $match: { productId: ?0 } }", // Match reviews for the given productId
"{ $group: { _id: null, averageRating: { $avg: '$rating' } } }" // Calculate average rating
})
for custom query for average rating

---

MongoDB use string for id