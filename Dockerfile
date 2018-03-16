FROM jboss/wildfly
ENV  SATPREDICT_URL http://satpredict.badgersoft.com


USER root

#Set the working directory to wildfly user home directory
WORKDIR /opt

# Add ActiveMQ RAR
RUN mkdir -p /opt/jboss/wildfly//modules/system/layers/base/org/apache/activemq
COPY activemq/* /opt/jboss/wildfly//modules/system/layers/base/org/apache/activemq

RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main
COPY mysql/* /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main/
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
COPY fc-data-warehouse-ear/target/fc-data-warehouse-ear-1.0-SNAPSHOT.ear /opt/jboss/wildfly/standalone/deployments
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug"]
