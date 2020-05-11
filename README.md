# Fuse 7.6 Email example

This examples shows how to use Camel to connect to a SMTP server to send an email.
Remember to change the application.properties to connect to your provider.

## Import the Required Image
    oc import-image fuse7-java-openshift:1.6 --from=registry.redhat.io/fuse7/fuse-java-openshift:1.6 -n openshift --confirm

You need to be authenticated in Red Hat registry in order to import this image

## How to Deploy in OpenShift
    mvn clean -DskipTests fabric8:deploy -Popenshift
