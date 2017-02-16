![alt tag](https://raw.github.com/conekta/conekta-java/master/readme_files/cover.png)

# Conekta Java 2.1.0

This is a java library that allows interaction with https://api.conekta.io API.

## Installation

### Gradle
Add the compile line to your `build.gradle` inside the dependencies section.

```groovy
compile 'io.conekta:conekta-java:2.1.0'
```

### Maven
Add the dependency to your `pom.xml`.

```xml
<dependency>
  <groupId>io.conekta</groupId>
  <artifactId>conekta-java</artifactId>
  <version>2.1.0</version>
</dependency>
```

### Source code
Obtain the latest version of the Conekta Java bindings with:

    git clone https://github.com/conekta/conekta-java

To get started, add the following to your Java code:

    import io.conekta;

## Usage
```java    
Conekta.setApiKey("1tv5yJp3xnVZ7eK67m4h");
JSONObject customerJSON = new JSONObject("{"
    + "'name': 'James Howlett', "
    + "'email': 'james.howlett@forces.gov',"
    + "'phone': '+5213353319758',"
    + "'payment_sources': [{"
        + "'token_id': 'tok_test_visa_4242',"
        + "'type': 'card'"
    + "}]"
+ "}");

Customer customer = Customer.create(customerJSON);
System.out.println(customer);

{
  livemode=false,
  phone=+5213353319758,
  name=Thane Kyrell,
  created_at=1485908856,
  id=cus_2fwNt8hqeq2vsGZk2,
  email=thane@jelucan.org,
  payment_sources={}
}

```

## Documentation

Please see https://www.conekta.com/docs/api for up-to-date documentation.

## Tests

The library has JUnit tests and you can run them separately.


## Notes on SSL cert

If you cannot connect to https://api.conekta.io, try installing the [certificate](https://github.com/conekta/conekta-java/blob/master/ssl_data/ca_bundle.pem) in your java environment:
```
keytool -import -noprompt -trustcacerts -alias conekta -file %PROJECT_PATH%\ssl_data\ca_bundle.pem -keystore %JAVA_HOME%\lib\security\cacerts -storepass changeit
```

License
-------
Developed by [Conekta](https://www.conekta.io). Available with [MIT License](LICENSE).

We are hiring
-------------

If you are a comfortable working with a range of backend languages (Java, Python, Ruby, PHP, etc) and frameworks, you have solid foundation in data structures, algorithms and software design with strong analytical and debugging skills.
Send your CV, github to quieroser@conekta.io
