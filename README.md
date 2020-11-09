# ecommerce-jpa
# Getting Started  

### MySQL 8 docker  
* [MySQL Docker Official image](https://hub.docker.com/_/mysql)  
```sh
docker run --name mysql8 --network minha-rede -v $(pwd)/mysql-datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 -d mysql:8
```