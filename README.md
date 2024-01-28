
### System Design Choice
I am using Spring-Boot to create this demo application as it comes with tremendous easy configuration features and provides us with more time on actual code implementation. 

I have chosen multi-module design and separated each functionality into its own independent modular project that can run on its own. I went for modular approach due to various benefits such as maintainability, testability, isolation, 
separation and so on, which also follows SOLID principles. For instance, console application is only responsible for reading the CSV file and making rest api call to rest service for storing CSV content into a database. 
Here, making change to one module is less likely to impact another module functionality, making it easier to make any change, test and locate the issues due to smaller code-base.
We can also easily deploy each module into a separate micro-services with less effort.

Application consist of three modules i.e. commonModel, consoleApp and customerRestApi. **commonModel** is a shared module which holds model class and used by both consoleApp and customerRestApi module.

I have chosen Spring-data-jdbc library and H2 in-memory database for configuring the data-source and persisting the data for simple CRUDE operations as per the requirements. 
If we are moving for real-world application, we can move to using some other database types such as mysql, postgreSQL, etc. and spring jpa to map object to table relationship.

In order to create repository class, I am extending CrudeRepository which is supplied by spring-data-jdbc, which provides out of the box CRUDE functionality without needing to provide actual implementation.

I also use Record to represent CSV individual rows as we don't require mutating model data contents. Record get rid of boilerplate codes and simplifies creating data classes. This fits in perfectly with our requirements 
as we will only be assigning and reading from model class.

I am using Feign client which is a declarative REST client making it easier to configure, use and easy code readability. It creates thread for each request and blocks until the response has been received. For our requirement,
where we want to wait for our file to finish uploading before we try to load another one, feign client is perfect fit for now.

For Rest API security, I have set up basic authentication so that end-points are not exposed to public without basic username and password authentication.

Each deployable module code has been organised using a multi-tired architecture which compose of three layers i.e. web-layer (controller class), business layer (service class) and data layer (repository class). This architecture 
allows us to organise the code more effectively and help us in separating specific responsibility/concerns within the application.

For testing, I have used MockMvc whenever testing requires some mocking. For example mocking service methods that returns data from database operations.

There are two main classes that run two independent application i.e. Console and Rest API application.

### **Console Application**
When running locally, this application can be accessed at port 8080. Full local url - http://localhost:8080
Once application is running, console will request for full CSV file path. for example, C:\test\doc.csv

### Rest Application
When running locally, this application can be accessed at port 8081. Full local url - http://localhost:8081
Rest API end-points
    Post /api/customer
    Get /api/customer/{customerRef}

In order to view the H2 database UI when running locally, use this url - http://localhost:8081/h2-console
You will then just need to provide with correct JDBC URL i.e. jdbc:h2:mem:esg (this can be seen in console while starting-up springboot)

### Further improvements

Instead of using JSON format for Rest call, we can also send out data using multipart which is often used for file uploads and supports various content type in single request.
We can also move towards cloud solution where we can directly store and share CSV file using one of the cloud storage such as Azure, Amazon S3 bucket, etc. and access the data for storing.

CSVFileReader class implements CustomFileReader interface. In the future, we can have various flavour of file reader such as json reader,xml reader, etc. that implements CustomFileReader interface and introduce factory design pattern to process and handle different file types.

We can further introduce multi-threading while persisting the data by using CompletableFuture class which provides asynchronous computations. This class implements future interface and allows using to chain the computation steps. 
We can also introduce Web-client which provides a non-blocking and reactive approach to making http requests if requirement specifies. 

To further enhance rest API security, we can make use of JWT tokens to stop sending credentials on every request.