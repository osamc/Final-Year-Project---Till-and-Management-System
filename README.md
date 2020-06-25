# Final Year Project - Till and Management System
My final year project for University. A project who's aim is to solve the issue that small businesses have by providing a system for configuring and creating till systems and viewing analytics on sales.


User Guide

1.1 Getting started

The pre-requisites you will need to deploy this project are:

- A setup PostgreSQL database that you know the login details too
- Java (Development worked against Java version of 1.8.0\_241, so this or newer would be recommended)

To build the parent project you will need to use Maven. The build time is long so as it uses the Maven frontend plugin to download and use NPM to build the angular project. Once this is built, there will be a jar file within the target directory of the backend project, this is the file that will be ran and deployed.

Once the Jar file has been obtained; it is then possible to run it through the command line. You will need to provide the following parameters:

- &quot;-jar &quot;: This is the jar being ran.
- &quot;--spring.datasource.url=&quot;: The value required for this is the URL for access to the database. It must be in the form of &quot;jdbc:postgresql://{URL}&quot;.
- &quot;--spring.datasource.username=&quot;: The value you will need to include the username that has access to the given PostgreSQL database.
- &quot;--spring.datasource.password=&quot;: The value you will need to provide is the password that has access to the given PostgreSQL database.

It is also possible to provide optional parameters, the most important being:

- &quot;--server.port &quot;: So that you can provide a port for the server to run on. Otherwise a default of 8443 will be used.

Here is an example that was used during the development of the project:

java -jar backend-0.0.1-SNAPSHOT.jar --spring.datasource.username=postgres --spring.datasource.password=password --spring.datasource.url=jdbc:postgresql://localhost:5432/postgres

This snippet can be then saved as a runnable file on the system, if you are using Windows you can use notepad and save this as a .bat file. On Linux and Mac, this can be saved as a bash script. Once ran, the server will be deployed, and the database tables created.

1.2 Important notes

By default, there is an admin user created for the Web Application. The details for this user are:

Login: admin

Password: admin123

After the initial login, you will be prompted to change the password. It is recommended to use some sort of password manager to keep track of your changed passwords.

Also, by default, an example seller user is created. Their details are:

Name: default

Login Code: 1234

This user can be deleted or edited within the definition section of the management application.

1.3 Making your first transaction

By opening your browser and navigating to the following URL of the server (e.g. [http://localhost:8443](http://localhost:8443/)), you will be prompted with the login screen. By using the details within section 1.2, you will then be taken to the dashboard. Initially, there will be no items within the system meaning that it will be impossible to create a transaction.

To create a product within the system, you will need to open the side bar via the burger icon and navigate to the definition section. You will be taken to the product definition page, from here we are able to define a product group by clicking on the &quot;Create Group&quot; button.

![](https://i.imgur.com/7fyOJzw.png)

All that is required for a group is a name. Once you are happy with the group name you have chosen you can then create the group, you should be navigated back to the list page and you should see the new group within the list.

From here we can then click on the &quot;Create Product&quot; button. We will be taken to the product definition page. The only mandatory fields are the product name and price. The info field is used within the till to allow sellers to find out more about the item (e.g. in a bar scenario this may be used to find out the percentage abv of the drink). By also filling the Image URL field, we will also be able to see a preview of the image to be used by the till.

![](https://i.imgur.com/YTjS9y3.png)

Now we have a product, we are now able to create a page to be shown within the till system. By going to the Pages header, we are taken to a list of pages. From here, we can click &quot;Create Page&quot; and add a new page. Here we can name and define the dimensions of the page. If we click on one of the add buttons, we can then select our example group and find our example product.

![](https://i.imgur.com/5SvmFYd.png)

By clicking save, we are then able to see the product on the page just as it would be shown within the till. We can then create the page by clicking the &quot;Create Page&quot; button in the bottom right.

![](https://i.imgur.com/r5WZpFD.png)

We&#39;ve defined everything within the system to allow for the creation of a transaction. If we open the sidebar, we can now go to the till.

![](https://i.imgur.com/WlW06tH.png)

When there isn&#39;t a seller logged in, most of the till functionality is disabled to prevent any malicious or unintended actions. To login as a seller, click the change user button. We can use the default user for this example, their login code is &quot;1234&quot;. Now we should see that the buttons have become active. We can now click on the till icon to add the item to our list, and then by selecting an amount of money we can create the transaction.
