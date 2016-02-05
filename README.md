# ords-audit-api

The audit API for the Online Research Database Service (ORDS)

## API Documentation

The API documentation can be found at /api/1.0/audit/swagger.json

## Configuration Properties

### Database and security configuration

    ords.hibernate.configuration=hibernate.cfg.xml

Optional; the location of the hibernate configuration file.

    ords.shiro.configuration=file:/etc/ords/shiro.ini

Optional; the location of the Shiro INI file

     ords.server.configuration=serverConfig.xml

Optional; the location of the Server configuration file

### Mail server configuration

The following properties are used for the email server connection

    mail.smtp.auth=true
    mail.smtp.starttls.enable=true
    mail.smtp.host=localhost
    mail.smtp.port=587
    mail.smtp.from=daemons@sysdev.oucs.ox.ac.uk
    mail.smtp.username=
    mail.smtp.password=
