# Graph Report - architect-iot-lab  (2026-09-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 370 nodes · 533 edges · 68 communities (11 shown, 57 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 12 edges (avg confidence: 0.81)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a0423b29`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- lombok.RequiredArgsConstructor
- HtvLabSecurityAutoConfiguration.java
- HtvLabUserAuthServiceImpl.java
- JwtTokenService
- ErrorCode
- BaseDomain.java
- HtvLabAuthenticationConverter.java
- SensorType
- HtvSecurityProperties
- AppTest
- UserApplication.java
- Result
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- App
- JwtsecretGen.sh
- alert-rule-service
- com.htv:architect-iot-lab
- com.htv:java-parent
- common-api-contract
- common-core
- common-domain
- common-error
- common-event
- common-iot
- dashboard-bff-service
- device-management-service
- farm-management-service
- farm-task-service
- htv-cache-extentions
- htv-cache-starter
- htv-common-extentions
- htv-common-starter
- htv-database-extentions
- htv-database-starter
- htv-graphql-extentions
- htv-graphql-starter
- htv-grpc-extentions
- htv-grpc-starter
- htv-kafka-extentions
- htv-kafka-starter
- htv-mqtt-extentions
- htv-mqtt-starter
- htv-security-extentions
- htv-security-starter
- java-bom
- quarkus-platform
- spring-platform
- telemetry-query-service
- user-management-service

## God Nodes (most connected - your core abstractions)
1. `HtvSecurityProperties` - 16 edges
2. `JwtTokenService` - 13 edges
3. `ErrorCode` - 13 edges
4. `AuthenticatedUser` - 12 edges
5. `HtvLabSecurityAutoConfiguration` - 11 edges
6. `RefreshTokenStore` - 11 edges
7. `AppUser` - 11 edges
8. `HtvLabSecurityWebAutoConfiguration` - 10 edges
9. `HtvLabAuthenticationConverter` - 10 edges
10. `ApiResponse` - 9 edges

## Surprising Connections (you probably didn't know these)
- `AuthController` --references--> `JwtTokenService`  [EXTRACTED]
  apps/user-management-service/src/main/java/com/htv/user/controller/AuthController.java → spring-starter/htv-security-starter/src/main/java/com/htv/security/token/JwtTokenService.java
- `AuthController` --references--> `HtvLabUserAuthService`  [EXTRACTED]
  apps/user-management-service/src/main/java/com/htv/user/controller/AuthController.java → spring-starter/htv-security-starter/src/main/java/com/htv/security/service/HtvLabUserAuthService.java
- `HtvLabUserAuthServiceImpl` --implements--> `HtvLabUserAuthService`  [EXTRACTED]
  apps/user-management-service/src/main/java/com/htv/user/service/HtvLabUserAuthServiceImpl.java → spring-starter/htv-security-starter/src/main/java/com/htv/security/service/HtvLabUserAuthService.java
- `ApiResponse` --references--> `ApiError`  [EXTRACTED]
  libs/common-api-contract/src/main/java/com/htv/common/contract/ApiResponse.java → libs/common-api-contract/src/main/java/com/htv/common/contract/ApiError.java
- `HtvLabSecurityAutoConfiguration` --references--> `HtvSecurityProperties`  [EXTRACTED]
  spring-starter/htv-security-starter/src/main/java/com/htv/security/HtvLabSecurityAutoConfiguration.java → spring-starter/htv-security-starter/src/main/java/com/htv/security/HtvSecurityProperties.java

## Import Cycles
- None detected.

## Communities (68 total, 57 thin omitted)

### Community 0 - "lombok.RequiredArgsConstructor"
Cohesion: 0.09
Nodes (24): annotation, AdminController, PingController, GetMapping, RequestMapping, RestController, UserController, UserUpdateRequest (+16 more)

### Community 1 - "HtvLabSecurityAutoConfiguration.java"
Cohesion: 0.11
Nodes (27): abstracthttpconfigurer, bcryptpasswordencoder, corsconfiguration, headersconfigurer, immutablesecret, javax.crypto.SecretKey, nimbusjwtdecoder, nimbusjwtencoder (+19 more)

### Community 2 - "HtvLabUserAuthServiceImpl.java"
Cohesion: 0.10
Nodes (20): AuthController, RequestMapping, RestController, HtvLabUserAuthServiceImpl, Override, email, notblank, objects (+12 more)

### Community 3 - "JwtTokenService"
Cohesion: 0.07
Nodes (20): concurrenthashmap, instant, KnowledgeModels, Note, Permission, Role, Topic, EventEnvelope (+12 more)

### Community 4 - "ErrorCode"
Cohesion: 0.09
Nodes (20): httpstatus, jakarta.servlet.http.HttpServletRequest, ApiError, FieldError, PageResult, ErrorCode, CONFLICT, DEVICE_OFFLINE (+12 more)

### Community 5 - "BaseDomain.java"
Cohesion: 0.10
Nodes (23): AppUser, Entity, BaseDomain, InfoUser, AppUserRepository, createdby, createddate, EntityListeners (+15 more)

### Community 6 - "HtvLabAuthenticationConverter.java"
Cohesion: 0.13
Nodes (18): arrays, collection, collectors, grantedauthority, jwtauthenticationtoken, org.springframework.core.convert.converter.Converter, org.springframework.security.authentication.AbstractAuthenticationToken, org.springframework.security.authorization.AuthorizationDecision (+10 more)

### Community 7 - "SensorType"
Cohesion: 0.17
Nodes (10): Device, IotModels, MqttTopic, SensorType, HUMIDITY, LIGHT, MOTION, PRESSURE (+2 more)

### Community 8 - "HtvSecurityProperties"
Cohesion: 0.27
Nodes (10): arraylist, duration, hashset, lombok.Data, org.springframework.boot.context.properties.ConfigurationProperties, org.springframework.http.HttpMethod, AccessRule, Cors (+2 more)

### Community 9 - "AppTest"
Cohesion: 0.32
Nodes (4): junit.framework.Test, junit.framework.TestCase, AppTest, testsuite

### Community 10 - "UserApplication.java"
Cohesion: 0.50
Nodes (3): UserApplication, org.springframework.boot.autoconfigure.SpringBootApplication, springapplication

## Knowledge Gaps
- **56 isolated node(s):** `RefreshRequest`, `Note`, `Permission`, `Role`, `Topic` (+51 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 166 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **57 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `HtvSecurityProperties` connect `HtvSecurityProperties` to `HtvLabSecurityAutoConfiguration.java`, `JwtTokenService`, `HtvLabAuthenticationConverter.java`?**
  _High betweenness centrality (0.073) - this node is a cross-community bridge._
- **Why does `JwtTokenService` connect `JwtTokenService` to `lombok.RequiredArgsConstructor`, `HtvLabSecurityAutoConfiguration.java`, `HtvLabUserAuthServiceImpl.java`, `HtvSecurityProperties`?**
  _High betweenness centrality (0.046) - this node is a cross-community bridge._
- **What connects `RefreshRequest`, `Note`, `Permission` to the rest of the system?**
  _56 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `lombok.RequiredArgsConstructor` be split into smaller, more focused modules?**
  _Cohesion score 0.09176788124156546 - nodes in this community are weakly interconnected._
- **Should `HtvLabSecurityAutoConfiguration.java` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `HtvLabUserAuthServiceImpl.java` be split into smaller, more focused modules?**
  _Cohesion score 0.10252100840336134 - nodes in this community are weakly interconnected._
- **Should `JwtTokenService` be split into smaller, more focused modules?**
  _Cohesion score 0.07394957983193277 - nodes in this community are weakly interconnected._