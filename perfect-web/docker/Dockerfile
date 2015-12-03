FROM tomcat:8-jre8

COPY start.sh /
COPY ROOT.war /usr/local/tomcat/webapps/

RUN chmod 0775 /start.sh
RUN rm -rf /usr/local/tomcat/webapps/*/

ENTRYPOINT ["/start.sh"]