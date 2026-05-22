server:
  port: ${SERVER_PORT:8080}

spring:
  config:
    import: optional:file:./.env[.properties]

  application:
    name: mock-investment

  datasource:
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3307}/${MYSQL_DATABASE:auth_demo}?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: ${MYSQL_USER:auth}
    password: ${MYSQL_PASSWORD:auth1234}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    open-in-view: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time_zone: Asia/Seoul

  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID:dummy-google-client-id}
            client-secret: ${GOOGLE_CLIENT_SECRET:dummy-google-client-secret}
            scope:
              - openid
              - profile
              - email

          kakao:
            client-id: ${KAKAO_CLIENT_ID:dummy-kakao-client-id}
            client-secret: ${KAKAO_CLIENT_SECRET:dummy-kakao-client-secret}
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            scope:
              - profile_nickname
              - profile_image
              - account_email
            client-name: Kakao
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id

app:
  admin:
    emails: ${ADMIN_EMAILS:}

  jwt:
    issuer: auth-demo
    secret: ${JWT_SECRET:YmFzZTY0LWVuY29kZWQtMzItYnl0ZS1taW4tc2VjcmV0LWtleS0xMjM0NTY=}
    access-token-ttl: ${JWT_ACCESS_TOKEN_TTL:10m}

  auth:
    refresh-token-ttl: ${REFRESH_TOKEN_TTL:14d}
    refresh-cookie:
      name: refresh_token
      path: /
      secure: ${REFRESH_COOKIE_SECURE:false}
      same-site: ${REFRESH_COOKIE_SAME_SITE:Lax}
      domain: ${REFRESH_COOKIE_DOMAIN:}

  oauth2:
    authorized-redirect-uri: ${OAUTH2_AUTHORIZED_REDIRECT_URI:http://localhost:8080/invest.html}
    failure-redirect-uri: ${OAUTH2_FAILURE_REDIRECT_URI:http://localhost:8080/login-fail.html}

  cors:
    allowed-origins:
      - http://localhost:3000
      - http://localhost:8080

  kis:
    enabled: ${KIS_ENABLED:false}
    virtual: ${KIS_VIRTUAL:true}
    app-key: ${KIS_APP_KEY:}
    app-secret: ${KIS_APP_SECRET:}
    rest-base-url: ${KIS_REST_BASE_URL:https://openapi.koreainvestment.com:9443}
    virtual-rest-base-url: ${KIS_VIRTUAL_REST_BASE_URL:https://openapivts.koreainvestment.com:29443}
    websocket-url: ${KIS_WS_URL:ws://ops.koreainvestment.com:21000}
    virtual-websocket-url: ${KIS_VIRTUAL_WS_URL:ws://ops.koreainvestment.com:31000}
    cust-type: ${KIS_CUST_TYPE:P}
    connect-timeout: ${KIS_CONNECT_TIMEOUT:5s}
    request-timeout: ${KIS_REQUEST_TIMEOUT:10s}
    realtime-max-codes-per-stream: ${KIS_REALTIME_MAX_CODES_PER_STREAM:30}
    master:
      kospi-url: ${KIS_MASTER_KOSPI_URL:https://new.real.download.dws.co.kr/common/master/kospi_code.mst.zip}
      kosdaq-url: ${KIS_MASTER_KOSDAQ_URL:https://new.real.download.dws.co.kr/common/master/kosdaq_code.mst.zip}
      konex-url: ${KIS_MASTER_KONEX_URL:https://new.real.download.dws.co.kr/common/master/konex_code.mst.zip}
