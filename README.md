
# JavaFX-Starbucks

## Project Description
The Starbucks Application simulates the operations of a Starbucks coffee shop, making use of Object-Oriented Programming (OOP) principles to model various functionalities of Starbucks stores.
It is a Maven project implemented using the JavaFX user interface (UI).

## Prerequisites
- JDK 17 
- Maven 3.6.0 or higher (for Maven builds)
- JavaFX SDK (for manual builds)

## Running the Application

### 1. Using Maven

#### Building the Project
To build the project with Maven, execute the following command:
```bash
mvn clean install
```

#### Running the Application with Maven
After building the project, run it using the following Maven command:
```bash
mvn javafx:run
```

#### Using Eclipse IDE with Maven
1. Import the project into Eclipse as a Maven project.
2. Right-click the project in the Project Explorer.
3. Select 'Run As' > 'Maven build...'.
4. In the 'Goals' field, enter `javafx:run`.
5. Click 'Run'.

### 2. Running without Maven (Using JavaFX SDK)

1. Download and extract the JavaFX SDK from [OpenJFX](https://openjfx.io/).
2. In your IDE, add the `lib` folder from the extracted JavaFX SDK as a library to the project.
3. Set the VM arguments for the project:
   - Add the following VM arguments, replacing `path/to/javafx-sdk/lib` with the actual path to the JavaFX `lib` folder:
     ```
     --module-path path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```
4. Run the `Main` class of the project.

## Application Features

### Base Features:
- **Menu Management:** Allows staff to add, update, and remove items from the menu.
- **Order Management:** Customers place orders by selecting items from the menu and can customize their orders with various options.
- **Order Processing:** Processes orders by total cost and displays a summary of the order.
- **Payment:** Accepts cash payments and calculates change.

### Bonus Features:
- **JavaFX UI**
- **User Registration/Login:** Features user authentication and allows users to continue as guests.
- **Deposit Option:** Users can add funds to their account using a "Deposit" option during the payment step.
- **Payment Choices:** Logged-in users can choose to pay with cash or their account balance.
- **Data Persistence:** User data is persisted in a file located at `/src/main/resources/users.txt`.
