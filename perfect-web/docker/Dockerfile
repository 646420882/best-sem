FROM jetty:9.3

COPY start.sh /
COPY ROOT.war /var/lib/jetty/webapps/

RUN chmod +x /start.sh

ENTRYPOINT ["/start.sh"]