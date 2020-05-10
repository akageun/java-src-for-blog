## docker-compose
#### 실행하기
> docker-compose -f docker-compose.yml up -d

#### 종료하기
> docker-compose -f docker-compose.yml stop

or

> docker-compose -f docker-compose.yml stop && docker-compose -f docker-compose.yml rm -vf

#### 확인하기
> netstat -an |grep '2181\|9092\|9000'

## docker
#### kafka container
######  로그 보기
> docker container logs kafka

#### zookeeper container
###### 로그 보기
> docker container logs zookeeper


## kafka-manager
- http://localhost:9000/
