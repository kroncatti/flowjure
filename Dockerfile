FROM amazoncorretto:11

WORKDIR ./

COPY target/my-project.jar /

CMD java -cp my-project.jar clojure.main -m my-project.main