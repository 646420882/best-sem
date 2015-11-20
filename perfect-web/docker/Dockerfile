FROM jetty:9.3

COPY start.sh /
COPY ROOT.war /var/lib/jetty/webapps/

RUN chmod 0775 /start.sh

ENTRYPOINT ["/start.sh"]