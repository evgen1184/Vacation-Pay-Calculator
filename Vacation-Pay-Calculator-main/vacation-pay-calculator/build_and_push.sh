gradlew clean build
docker image build ./ -t nikolayshved/vacation-image

# run vacation-service container
# docker run -p 8081:8081 -d --name vacation-service nikolayshved/vacation-image
# stop vacation-service container
# docker stop vacation-service
