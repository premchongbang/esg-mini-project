
### System Design
I am using Spring-Boot to create this demo application as it comes with tremendous configuration features and provides us with more time on actual implementation 

I have chosen modular design and separated each functionality into its own independent modular project that can run on its own. I have went for modular approach due to various benefits such as maintainability, testability, isolation, 
separation and so on, which follows SOLID principles. For instance, console application is only responsible reading the CSV file and sending over the CSV content to another rest service for storing it to the database. 
Making change to one module is less likely to impact another module making it easier to make changes, test and locate the issue due to smaller code-base.

Application consist of three modules i.e. commonModel, consoleApp and customerRestApi. Both consoleApp and customerRestApi module have got dependency over commonModel which holds shared model model class i.e. CustomerDetail class.

I have chosen Spring-data-jdbc library and H2 in-memory database for simplicity in configuring the data-source and persisting the data for simple CRUDE operations. 
If we are moving for real-world application, we can move to using some other database types such as mysql, postgreSQL, etc. which also can be easily configured in Spring-booth.
In order to create repository class, I am extending CrudeRepository which is supplied by spring-data-jdbc, to provides out of the box CRUDE functionality without needing to provide actual implementation.

I also use Record to represent CSV individual rows as we don't require mutating the model contents. Record helps to get rid of boilerplate codes and simplifies creating data classes. This fits in perfectly with our requirements 
as we will only be assigning and reading from model object.

I am using Feign client which is a declarative REST client making it easier to configure, use and easy code readability. It created thread for each request and blocks until the response has been received. For our requirement,
where we need to know if CSV file content was correctly saved before we perform another loading operation, feign client is perfect fit. We can swap to Web-client for non-blocking asynchronous solution.

For testing, I have used MockMvc where testing required connecting to database for data.

There are two main classes that run two independent application i.e. Console and Rest API application.

### **Console Application**
When running locally, this application can be accessed at port 8080. Full local url - http://localhost:8080
Once application is running, console will request for full CSV file path. for example, C:\test\doc.csv

### Rest Application
When running locally, this application can be accessed at port 8081. Full local url - http://localhost:8081
Rest API end-points
    Post /api/customer
    Get /api/customer/{customerRef}

In order to view the database UI when running locally, use this url - http://localhost:8081/h2-console
You will then need to provide correct JDBC URL i.e. jdbc:h2:mem:esg (this can be viewed on springboot startup)
