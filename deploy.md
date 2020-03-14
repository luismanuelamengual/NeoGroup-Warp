# Deploy Instructions

## Generate a gpg key

<pre>
gpg --gen-key
</pre>

## Upload the gpg key

<pre>
gpg --list-keys
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys 62F8F56FCC5DAFC46AA1B5032719E4F09C87AD3B
</pre>

## Create a settings.xml in ~/.m2/settings.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>sonatype-nexus-staging</id>
            <username>{nexus-user-name}</username>
            <password>{nexus-user-password}</password>
        </server>
    </servers>
</settings>

```