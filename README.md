<div align="center">

![banner](readme_files/banner.png)

# Conekta Java v2.1.5

![java badge](readme_files/java-badge.png)
![conekta badge](readme_files/conekta-badge.png)

</div>

This is a java library that allows interaction with https://api.conekta.io API.

## Installation

### Gradle
Add the compile line to your `build.gradle` inside the dependencies section.

```groovy
compile 'io.conekta:conekta-java:2.1.5'
```

### Maven
Add the dependency to your `pom.xml`.

```xml
<dependency>
  <groupId>io.conekta</groupId>
  <artifactId>conekta-java</artifactId>
  <version>2.1.5</version>
</dependency>
```

### Direct download  <a href="https://oss.sonatype.org/service/local/repositories/releases/content/io/conekta/conekta-java/2.1.5/conekta-java-2.1.5.jar">conekta-java-2.1.5.jar</a>

### Source code
Obtain the latest version of the Conekta Java bindings with:

    git clone https://github.com/conekta/conekta-java

To get started, add the following to your Java code:

    import io.conekta;

## Usage
### Create a customer
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
  payment_methods={}
}
```
### Create an order with a card charge

```java
JSONObject completeOrderJSON = new JSONObject("{" +
        "'currency': 'mxn'," +
        "'metadata': {" +
        "    'test': true"+
        "}," +
        "'line_items': [{" +
        "    'name': 'Box of Cohiba S1s'," +
        "    'description': 'Imported From Mex.'," +
        "    'unit_price': 35000," +
        "    'quantity': 1," +
        "    'tags': ['food', 'mexican food']," +
        "    'type': 'physical'" +
        "}]," +
        "'customer_info': { " +
        "    'name': 'John Constantine'," +
        "    'phone': '+5213353319758'," +
        "    'email': 'hola@hola.com'" +
        "}," +
        "'charges': [{" +
        "    'payment_method': {" +
        "        'type': 'card'," +
        "        'token_id': 'tok_test_visa_4242'" +
        "    }, " +
        "    'amount': 35000" +
        "}]" +
        "}");

Order completeOrder = Order.create(completeOrderJSON);

System.out.println(completeOrder.charges.get(0));

{
  device_fingerprint=123456789abcdefghijkmnopqrstuv,
  amount=35000,
  livemode=false,
  fee=1467,
  created_at=1488302828,
  description=Payment from order,
  paid_at=1488302831,
  currency=MXN,
  id=58b5b2ecdba34d7988fdf95c,
  customer_id=,
  order_id=ord_2g6SGGQrB92zcbYxe,
  payment_method={
    last4=4242,
    name=Jorge Lopez,
    exp_month=12,
    exp_year=19,
    type=credit,
    brand=visa,
    auth_code=270292
  },
  object=charge,
  status=paid
}
```

### Create an order with an OXXO Pay charge
```java
JSONObject completeOrderJSON = new JSONObject("{" +
        "'currency': 'mxn'," +
        "'metadata': {" +
        "    'test': true"+
        "}," +
        "'line_items': [{" +
        "    'name': 'Box of Cohiba S1s'," +
        "    'description': 'Imported From Mex.'," +
        "    'unit_price': 35000," +
        "    'quantity': 1," +
        "    'tags': ['food', 'mexican food']," +
        "    'type': 'physical'" +
        "}]," +
        "'customer_info': { " +
        "    'name': 'John Constantine'," +
        "    'phone': '+5213353319758'," +
        "    'email': 'hola@hola.com'" +
        "}," +
        "'charges': [{" +
        "    'payment_method': {" +
        "        'type': 'oxxo_cash'" +
        "    }, " +
        "    'amount': 35000" +
        "}]" +
        "}");

Order oxxoOrder = Order.create(completeOrderJSON);

Charge charge = oxxoOrder.charges.get(0);

OxxoPayment oxxoPayment = (OxxoPayment) charge.payment_method;

{
  reference=93345678901234,
  expires_at=1491091200,
  service_name=OxxoPay,
  store_name=OXXO,
  type=oxxo
}
```

### Create an order with an SPEI charge
```java
JSONObject speiOrderJSON = new JSONObject("{" +
        "'currency': 'mxn'," +
        "'metadata': {" +
        "    'test': true"+
        "}," +
        "'line_items': [{" +
        "    'name': 'Box of Cohiba S1s'," +
        "    'description': 'Imported From Mex.'," +
        "    'unit_price': 35000," +
        "    'quantity': 1," +
        "    'tags': ['food', 'mexican food']," +
        "    'type': 'physical'" +
        "}]," +
        "'customer_info': { " +
        "    'name': 'John Constantine'," +
        "    'phone': '+5213353319758'," +
        "    'email': 'hola@hola.com'" +
        "}," +
        "'charges': [{" +
        "    'payment_method': {" +
        "        'type': 'spei'" +
        "    }, " +
        "    'amount': 35000" +
        "}]" +
        "}");

Order speiOrder = Order.create(speiOrderJSON);

Charge charge = speiOrder.charges.get(0);

{
  amount=35000,
  livemode=false,
  fee=928,
  created_at=1488563958,
  description=Payment from order,
  currency=MXN,
  id=58b9aef6dba34d2f30fe84c1,
  customer_id=cus_GziCBxxQz9G2f30fe,
  order_id=ord_2g7Ra7LQz9GziCBxx,
  payment_method={
    bank=STP,
    type=spei,
    clabe=646180111812345678
  },
  object=charge,
  status=pending_payment
}
```
### Handle Errors
```java
JSONObject incompleteOrderJSON = new JSONObject("{" +
        "'currency': 'mxn'," +
        "'metadata': {" +
        "    'test': true"+
        "}," +
        "'line_items': [{" +
        "    'name': 'Box of Cohiba S1s'," +
        "    'description': 'Imported From Mex.'," +
        "    'unit_price': 35000," +
        "    'quantity': 1," +
        "    'tags': ['food', 'mexican food']," +
        "    'type': 'physical'" +
        "}]," +
        "'customer_info': { " +
        "    'name': 'John Constantine'," +
        "    'phone': '+5213353319758'," +
        "    'email': 'hola@hola.com'" +
        "}," +
        "'charges': [{" +
        "    'amount': 35000" +
        "}]" +
        "}");

try {
   Order.create(incompleteOrderJSON);
} catch (ErrorList e) {
   System.out.println(e.details.get(0).message);
}

El parametro payment_method es requerido.
```

### Endpoints
```java
order.refund();
order.delete();
order.update();
order.capture();
order.createCharge();
order.createDiscountLine();
order.createLineItem();
order.createShippingContact();
order.createShippingLine();
order.createTaxLine();


customer.createShippingContact();
customer.createPaymentSource();
customer.createSubscription();
customer.createCard();
```
## Documentation

Please see [developers.conekta.com/api](https://developers.conekta.com/api) for up-to-date documentation.

## Tests

The library has JUnit tests and you can run them separately.


## Notes on SSL cert

If you cannot connect to https://api.conekta.io, try installing the [certificate](https://github.com/conekta/conekta-java/blob/master/ssl_data/ca_bundle.pem) in your java environment:
```
keytool -import -noprompt -trustcacerts -alias conekta -file %PROJECT_PATH%\ssl_data\ca_bundle.pem -keystore %JAVA_HOME%\lib\security\cacerts -storepass changeit
```

## License

Developed in Mexico by [Conekta](https://www.conekta.com). Available with [MIT License](LICENSE).

***

## License

Developed in Mexico by [Conekta](https://www.conekta.com). Available with [MIT License](LICENSE).

## How to contribute to the project

1. Fork the repository

2. Clone the repository
```
    git clone git@github.com:yourUserName/conekta-java.git
```
3. Create a branch
```
    git checkout develop
    git pull origin develop
    # You should choose the name of your branch
    git checkout -b <feature/my_branch>
```
4. Make necessary changes and commit those changes
```
    git add .
    git commit -m "my changes"
```
5. Push changes to GitHub
```
    git push origin <feature/my_branch>
```
6. Submit your changes for review, create a pull request

   To create a pull request, you need to have made your code changes on a separate branch. This branch should be named like this: **feature/my_feature** or **fix/my_fix**.

   Make sure that, if you add new features to our library, be sure that corresponding **unit tests** are added.

   If you go to your repository on GitHub, you’ll see a Compare & pull request button. Click on that button.

***

## We are always hiring!

If you are a comfortable working with a range of backend languages (Java, Python, Ruby, PHP, etc) and frameworks, you have solid foundation in data structures, algorithms and software design with strong analytical and debugging skills, check our open positions: https://www.conekta.com/careers
