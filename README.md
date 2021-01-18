Online marketplace coding exercise
This is a solution to the coding exercise given my NotOnTheHighStreet

Technologies and frameworks used
gradle v5.2.1
java 8

Requirements assumptions
  In the following scenarios a product will not be scanned and a Runtime exception is thrown
  A product to be scanned in null
  The name of a product to be scanned is null or empty
  The price of a product to be scanned is null or negative number
  The code of a product to be scanned is null or empty
  The order that promotion rules are applied matters (changing the order will affect the result). Here the assumption is that the discount on the total amount of the purchase should apply last
  The system supports only £ as a currency, so I am not doing any special handling for currency.
  
Notes
  The 3 set of test data defined in the exercise can be found in the CheckoutTestClass (last 3 unit-tests)

Environment requirements to run the exercise
JRE 8
Command line instructions to run the exercise

From the root folder:

Run the following to run all tests
./gradlew clean test
