FROM airhacks/glassfish
COPY ./target/database.war ${DEPLOYMENT_DIR}
