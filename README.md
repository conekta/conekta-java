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
JSONObject valid_payment_method;
JSONObject valid_visa_card;
valid_visa_card = new JSONObject("{'card':'tok_test_visa_4242'}");
valid_payment_method = new JSONObject("{'description':'Stogies'," +
  "'reference_id':'9839-wolf_pack'," +
  "'amount':20000," +
  "'currency':'MXN'}");
JSONObject params = valid_payment_method.put("card", valid_visa_card.get("card"));
try {
  Charge charge = Charge.create(params);
} catch(Error e) {
  System.out.println(e.toString());
}

// System.out.println(charge.toString());

{
  amount=20000,
  id=530680e6d7e1a076e4000595,
  status=paid,
  description=Stogies,
  reference_id=9839-wolf_pack,
  created_at=1392935143,
  livemode=false,
  payment_method={
    exp_month=12,
    exp_year=19,
    name=JorgeLopez,
    auth_code=725872,
    brand=visa,
    last4=4242
  },
  currency=MXN
}
```

## Documentation

Please see https://www.conekta.io/docs/api for up-to-date documentation.

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
