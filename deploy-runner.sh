#!/bin/bash

## Usage
# 开发版本: ./deploy-runner.sh -e dev -c best-sem-web-dev -m 2g
# 测试版本: ./deploy-runner.sh -e prod -c best-sem-web-beta -p 8081
# 投入生产使用的版本: ./deploy-runner.sh -e prod -c best-sem-web -m 16g

# 模式(dev, prod)
mode=dev
# 容器名称
container=best-sem-web-dev
# 端口
http_port=8080
# JVM -Xms -Xmx 设置
heap_memory_size=8g
base_dir=$(pwd)

while getopts "e:c:p:m:" arg
    do
        case $arg in
            "e")
                mode=$OPTARG
                ;;
            "c")
                container=$OPTARG
                ;;
            "p")
                http_port=$OPTARG
                ;;
            "m")
                heap_memory_size=$OPTARG
                ;;
            "?")
                echo "unknow argument"
                ;;
        esac
    done

echo "update source code"
#git checkout develop && git pull

echo "rebuilding, please wait for a moment..."
cp $base_dir/configuration/jndi/jetty-env-$mode.xml $base_dir/perfect-web/src/main/webapp/WEB-INF/jetty-env.xml
mvn clean package -P$mode -DskipTests
cp $base_dir/perfect-web/target/perfect-web.war $base_dir/perfect-web/docker/ROOT.war
git checkout -- $base_dir/perfect-web/src/main/webapp/WEB-INF/jetty-env.xml

running=$(docker inspect --format="{{ .State.Running }}" $container 2> /dev/null)
if [ $? -eq 1 ];  then
    echo "The specific container does't exist!"
else
    # Check container running status
    if [ $running = "true" ];  then
        docker stop $container
    fi
    docker rm $container
fi

imageName=souke/sem-web
imageVersion=$(mvn org.apache.maven.plugins:maven-help-plugin:2.2:evaluate -Dexpression=project.version | egrep -v '^\[|Downloading:' | tr -d ' \n')
imageId="$(docker images -q $imageName:$imageVersion 2> /dev/null)"
if [ ! -z $imageId ];  then
    docker rmi $imageId
fi

cd $base_dir/perfect-web/docker
docker build -t $imageName:$imageVersion .
rm -f $base_dir/perfect-web/docker/ROOT.war

# Run a container
docker run --name=$container --net=host -d -e HEAP_MEMORY_SIZE=$heap_memory_size -e HTTP_PORT=$http_port $imageName:$imageVersion

echo "redeploy finished."