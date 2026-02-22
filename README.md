# Pandemonium: Server app
## 🍔 Ingredients
- Spring Boot 3.2.5
- PostgreSQL 16
- Thymeleaf

## Deployment

- Update JAR version
- Update JAR version in `production/server/Dockerfile`
- Delete previous version

```shell
docker-compose build --no-cache pandemona-server
```

If all:
```shell
docker-compose up -d --force-recreate pandemona-client
```

If server only:
```shell
docker-compose up -d --force-recreate pandemona-server
```