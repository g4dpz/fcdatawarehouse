FROM jboss/wildfly
ENV  SATPREDICT_URL http://satpredict.badgersoft.com


USER root

#Set the working directory to wildfly user home directory
WORKDIR /opt

# Add ActiveMQ RAR
COPY activemq/activemq-rar-5.14.4.rar /root/
RUN mkdir -p /opt/jboss/wildfly/modules/org/apache/activemq/ra/main && unzip /root/activemq-rar-5.14.4.rar -d /opt/jboss/wildfly/modules/org/apache/activemq/ra/main
COPY activemq/module.xml /opt/jboss/wildfly/modules/org/apache/activemq/ra/main/
RUN mkdir -p /opt/jboss/wildfly/modules/org/apache/activemq/ra/main/META-INF
COPY activemq/ra.xml /opt/jboss/wildfly/modules/org/apache/activemq/ra/main/META-INF/



RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main
COPY mysql/* /opt/jboss/wildfly/modules/system/layers/base/com/mysql/driver/main/
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
COPY fc-data-warehouse-ear/target/fc-data-warehouse-ear-1.0-SNAPSHOT.ear /opt/jboss/wildfly/standalone/deployments
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug"]
