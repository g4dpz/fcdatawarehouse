FROM jboss/wildfly
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main
COPY module.xml /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main
COPY mysql-connector-java-5.1.26.jar /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
COPY fc-data-warehouse-ear/target/fc-data-warehouse-ear-1.0-SNAPSHOT.ear /opt/jboss/wildfly/standalone/deployments
COPY execute.sh /opt/jboss/wildfly/bin
COPY commands.cli /opt/jboss/wildfly/bin
RUN sh /opt/jboss/wildfly/bin/execute.sh