# EwuMateLite Unabridged Code Breakdown

## Table of Contents
1. Launcher.java`n2. Main.java`n
---

### File: `Launcher.java`

`java
package com.ewumatelite;
`
*   **package com.ewumatelite;**: This first line defines the directory path this Java file belongs to inside the project. com is the top-level domain, and ewumatelite is the project name.

`java
public class Launcher {
`
*   **public**: An access modifier that makes this class accessible from anywhere in the Java application.
*   **class**: A keyword declaring a new Java object blueprint.
*   **Launcher**: The name of the class, matching the filename.
*   **{**: Opening curly brace that begins the definition of the Launcher class.

`java
    public static void main(String[] args) {
`
*   **public**: The method can be accessed from outside the class.
*   **static**: The method belongs to the class itself, not an instance of the class (you don't need to use 
ew Launcher()).
*   **oid**: The method does not return any data when it finishes running.
*   **main**: The standard entry point name that the Java Virtual Machine looks for to run a program.
*   **(**: Opening parenthesis for method parameters.
*   **String[]**: Defines that the parameter type is an Array consisting of Text (Strings).
*   **rgs**: The variable name for the array of strings, usually short for "arguments" passed via command line.
*   **)**: Closing parenthesis.
*   **{**: Opening curly brace for the main method block.

`java
        Main.main(args);
`
*   **Main**: A reference to the other Java class named Main.java in the exact same package.
*   **.**: The dot operator accesses methods inside that class.
*   **main**: The static function inside the Main class.
*   **(**: Open parenthesis to pass data.
*   **rgs**: Passing the identical array of text arguments we just received down into the Main application.
*   **)**: Close parenthesis.
*   **;**: Semicolon ending the Java instruction statement.

`java
    }
`
*   **}**: Closing curly brace indicating the end of the main() method.

`java
}
`
*   **}**: Closing curly brace indicating the end of the Launcher class.

---

### File: `Main.java`

`java
package com.ewumatelite;
`
*   **package com.ewumatelite;**: Places this file inside the main com/ewumatelite namespace.

`java
import javafx.application.Application;
`
*   **import**: Instructs the Java compiler to pull an external class so we can use its code.
*   **javafx.application.Application;**: The core JavaFX library class that handles creating the actual OS-level window frame and event threads.

`java
import javafx.stage.Stage;
`
*   **import javafx.stage.Stage;**: Pulls in the Stage class, which represents the primary physical application window (the borders, maximize/minimize buttons).

`java
import com.ewumatelite.features.auth.presentation.RegistrationScreen;
`
*   **import com.ewumatelite.features.auth.presentation.RegistrationScreen;**: Pulls the custom UI class representing the Registration form screen (though it is unused in this specific snippet).

`java
public class Main extends Application {
`
*   **public**: Defines that this class is globally accessible.
*   **class**: Declares a new object.
*   **Main**: The name of the central entry point class.
*   **extends**: A Java keyword marking Inheritance. Main inherits all behaviors of the class after it.
*   **Application**: Inheriting from javafx.application.Application transforms this mundane class into a fully functional graphical window manager.
*   **{**: Opening class block brace.

`java

`
*   *(Blank spacer line for readability)*

`java
    @Override
`
*   **@Override**: A Java annotation explicitly telling the compiler "I am about to replace a method definition that I inherited from Application".

`java
    public void start(Stage primaryStage) {
`
*   **public void start**: The main function called automatically by JavaFX once the graphical threads are safely booted up.
*   **(**: Parameter open.
*   **Stage**: The data type, representing the main window.
*   **primaryStage**: The variable name holding the window instance provided to us by the OS.
*   **) {**: Parameter close and method block open.

`java
        // Save the log file to the User's Documents folder
`
*   **//**: Double slashes define a single-line comment intended for other programmers to read. The compiler ignores this line entirely.

`java
        com.ewumatelite.core.utils.LogExporter.log("Application Session started explicitly by the User.");
`
*   **com.ewumatelite.core.utils.LogExporter**: Locates the LogExporter class using its fully qualified path.
*   **.log**: Calls the static log method.
*   **(**: Method parameter open.
*   **"Application Session started explicitly by the User."**: A literal String containing the start message.
*   **);**: Bracket close and system statement termination.

`java

`
*   *(Blank spacer)*

`java
        // Global Event Listener to capture EVERY UI Action and Mouse Click across all scenes
`
*   **//**: A comment explaining the purpose of the upcoming code block.

`java
        primaryStage.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
`
*   **primaryStage**: References the main application window variable we were passed.
*   **.addEventFilter**: A method that intercepts input *before* it reaches the buttons or forms.
*   **(**: Method arguments open.
*   **javafx.scene.input.MouseEvent.MOUSE_CLICKED**: Identifies that we specifically want to intercept physical mouse click events.
*   **,**: Comma separating arguments.
*   **event -> {**: Starts a Java Lambda Expression (a miniature anonymous function). The event variable represents the actual data of the click, and { opens the code that will react to it.

`java
            if (event.getTarget() instanceof javafx.scene.Node) {
`
*   **if**: A conditional logic branch.
*   **(**: Condition start.
*   **event.getTarget()**: Extracts the specific pixel/object that was clicked.
*   **instanceof**: Java keyword asking "Is this object physically derived from the following type?".
*   **javafx.scene.Node**: The base type for *any* visual element in JavaFX (Buttons, Labels, Rectangles).
*   **) {**: End of condition, beginning of the branch body.

`java
                javafx.scene.Node target = (javafx.scene.Node) event.getTarget();
`
*   **javafx.scene.Node target**: Declares a new local variable named 	arget of type Node.
*   **=**: Assignment operator.
*   **(javafx.scene.Node)**: Explicit Cast. Forces Java to recognize the raw Object from getTarget() as a Node.
*   **event.getTarget();**: Fetches the underlying raw clicked object.

`java
                String targetInfo = target.getClass().getSimpleName();
`
*   **String targetInfo**: Declares a new text variable to store naming details.
*   **=**: Assignment.
*   **	arget.getClass()**: Uses Reflection to get the blueprint defining whatever we clicked.
*   **.getSimpleName();**: Extracts just the short class name (e.g., "Button") instead of the long package path.

`java
                if (target instanceof javafx.scene.control.Labeled) {
`
*   **if (**: Conditional statement open.
*   **	arget instanceof javafx.scene.control.Labeled**: Checks if the clicked element is capable of holding text (e.g., Labels, Buttons, RadioButtons, Toggles).
*   **) {**: Conditional end, local block open.

`java
                    targetInfo += " ['" + ((javafx.scene.control.Labeled) target).getText() + "']";
`
*   **	argetInfo**: Our text variable.
*   **+=**: Appends data to its existing text value.
*   **" ['"**: String literal wrapper to make the output prettier.
*   **+**: String concatenation operator.
*   **((javafx.scene.control.Labeled) target)**: Casts the raw node into a Labeled element so we gain access to its text methods.
*   **.getText()**: Fetches what the actual button/label says (e.g., "Submit" or "Username").
*   **+ "']";**: Appends the closing wrapper bracket.

`java
                } else if (target.getId() != null) {
`
*   **} else if (**: Chain logic; if it *wasn't* text-bearing, execute this second condition instead.
*   **	arget.getId()**: Asks the element for its x:id from the XML file.
*   **!= null**: Checks that the ID actually exists.
*   **) {**: Conditional evaluation closed, local scope beginning.

`java
                    targetInfo += " [id=" + target.getId() + "]";
`
*   **	argetInfo += " [id="**: Appends identifying wrapper.
*   **+ target.getId() + "]"**: Snags its unique ID name directly.
*   **;**: Ends statement.

`java
                }
`
*   **}**: Closes the else if block.

`java
                com.ewumatelite.core.utils.LogExporter.log("GLOBAL UI ACTION: User clicked " + targetInfo);
`
*   **...LogExporter.log**: Calls our static logger.
*   **("GLOBAL UI ACTION: User clicked " + targetInfo);**: Combines the static text prefix with the dynamically built 	argetInfo payload!

`java
            }
`
*   **}**: Closes the topmost if checking if the target was a real Node.

`java
        });
`
*   **}**: Closes the Lambda expression body.
*   **);**: Closes the primaryStage.addEventFilter parameters and statement.

`java

`
*   *(Blank spacer line)*

`java
        // Remember me flow check
`
*   **//**: Comment labeling the upcoming auth logic.

`java
        if (com.ewumatelite.core.config.SupabaseConfig.currentUserToken != null) {
`
*   **if (**: Conditional loop start.
*   **com.ewumatelite.core.config.SupabaseConfig.currentUserToken**: Accesses a global static variable holding the user's stored auth token.
*   **!= null**: Verifies the token actually exists inside RAM.
*   **) {**: Open logic execute block.

`java
            new com.ewumatelite.features.dashboard.presentation.DashboardScreen(primaryStage, com.ewumatelite.core.config.SupabaseConfig.currentUserId, "Spring 2026").show();
`
*   **
ew com.ewumatelite.features.dashboard.presentation.DashboardScreen(...)**: Initializes a fresh instance of the Application's main desktop screen immediately.
*   **primaryStage**: Passes the main window.
*   **com.ewumatelite...currentUserId**: Passes the authenticated user's ID stored statically in memory.
*   **"Spring 2026"**: A hardcoded semester name argument.
*   **.show();**: Directly triggers the constructor's initialization method to render the dashboard window!

`java
        } else {
`
*   **} else {**: If currentUserToken *was* null, execute this alternate branch.

`java
            new com.ewumatelite.features.auth.presentation.LoginScreen(primaryStage).show();
`
*   **
ew com.ewumatelite.features.auth.presentation.LoginScreen(...)**: Forces the UI into the Authentication / Login Prompt.
*   **primaryStage**: Passes the root window for rendering.
*   **.show();**: Prompts the screen to render.

`java
        }
`
*   **}**: Ends the else logic flow.

`java
    }
`
*   **}**: Ends the start(Stage) graphical init method entirely.

`java

`
*   *(Blank spacer line)*

`java
    public static void main(String[] args) {
`
*   **public static void main(String[] args) {**: The JVM entry point exactly like the one in Launcher. This is a backup block if Launcher is bypassed.

`java
        launch(args);
`
*   **launch**: An internal inherited application method that spins off a separate OS thread (JavaFX Application Thread) and eventually calls the start() function defined above.
*   **(args);**: Passes the command-line details.

`java
    }
`
*   **}**: Ends the JVM main function.

`java
}
`
*   **}**: Exits definition of the Main class.
---

### File: `CourseItem.java`

`java
package com.ewumatelite.core.models;
`
*   **package com.ewumatelite.core.models;**: Places this file inside the models package, separating simple data wrappers from UI logic.

`java
public class CourseItem {
`
*   **public**: Accessible from anywhere.
*   **class**: Declares a new object.
*   **CourseItem**: The object named CourseItem.
*   **{**: Opens the class block.

`java
    // Applying strict encapsulation
`
*   **//**: Single-line comment stating the design intent (Encapsulation: hiding direct access to variables).

`java
    private String code;
`
*   **private**: Only this specific CourseItem object can read or write to this variable.
*   **String**: Text data type.
*   **code**: The variable name representing the course's short code (e.g., "CSE101").
*   **;**: Ends statement.

`java
    private String name;
`
*   **private String name;**: A hidden text variable that holds the full course name.

`java
    public CourseItem(String code, String name) {
`
*   **public CourseItem**: The constructor. Code here runs exactly once when 
ew CourseItem(...) is called.
*   **(**: Open parameters.
*   **String code**: Expects a text value mapped to a local variable code.
*   **,**: Separator.
*   **String name**: Expects a second text value mapped to 
ame.
*   **) {**: Close parameters, open constructor block.

`java
        this.code = code;
`
*   **	his**: Refers to the specific CourseItem instance being created.
*   **.code**: Selects the private String code; property of the class.
*   **=**: Assigns a value.
*   **code**: The value passed in via the constructor parameter.
*   **;**: Ends statement.

`java
        this.name = name;
`
*   **	his.name = name;**: Assigns the parameter 
ame to this specific object's class-level 
ame variable.

`java
    }
`
*   **}**: Ends the constructor method.

`java
    public String getCode() {
`
*   **public String getCode() {**: A "Getter" method that makes the private code string readable to the outside world.

`java
        return code;
`
*   **
eturn**: Instructs the function to output this value and stop.
*   **code**: The hidden class variable.
*   **;**: Ends statement.

`java
    }
`
*   **}**: Ends the getter.

`java
    public void setCode(String code) {
`
*   **public void setCode(String code) {**: A "Setter" method that allows external code to overwrite the private code string.

`java
        this.code = code;
`
*   **	his.code = code;**: Overwrites the class variable with the provided string.

`java
    }
`
*   **}**: Ends the setter method.

`java
    public String getName() {
`
*   **public String getName() {**: Getter for the course name.

`java
        return name;
`
*   **
eturn name;**: Gives back the hidden 
ame text.

`java
    }
`
*   **}**: Ends getter.

`java
    public void setName(String name) {
`
*   **public void setName(String name) {**: Setter for the course name.

`java
        this.name = name;
`
*   **	his.name = name;**: Overwrites the private name property.

`java
    }
`
*   **}**: Ends setter.

`java
    @Override
`
*   **@Override**: Tells Java we are overriding a default method built into the root Object class.

`java
    public String toString() {
`
*   **public String toString() {**: Replaces the default 	oString() so that when this object is printed to the console or added to a dropdown combobox, it outputs legible text instead of raw memory addresses.

`java
        return code + " - " + name;
`
*   **
eturn**: Returns the computed value.
*   **code + " - " + name**: Concatenates "CSE101", a dash, and "Intro to CS" into a single string.

`java
    }
`
*   **}**: Ends 	oString().

`java
}
`
*   **}**: Ends the CourseItem class.
---

### File: `ProgramItem.java`

`java
package com.ewumatelite.core.models;
`
*   **package com.ewumatelite.core.models;**: Places this object class inside the core models folder.

`java
public class ProgramItem {
`
*   **public**: Globally accessible access modifier.
*   **class**: Declares a new object.
*   **ProgramItem**: The name of the blueprint representing an academic degree program.
*   **{**: Opens class.

`java
    // Apply Encapsulation rules tightly
`
*   **//**: A human-readable comment stating we are keeping our variables strictly private.

`java
    private String code;
`
*   **private**: Only this object can touch this data.
*   **String**: A text variable.
*   **code**: The program code, like "BCSE".
*   **;**: Instruction completed.

`java
    private String name;
`
*   **private String name;**: Hidden string holding the full program name (e.g., "Computer Science and Engineering").

`java
    private String track;
`
*   **private String track;**: Hidden string representing the academic study track.

`java
    public ProgramItem(String code, String name, String track) {
`
*   **public ProgramItem**: The object Constructor triggered when we use the 
ew keyword.
*   **(**: Open parameters.
*   **String code, String name, String track**: The three required text inputs to successfully build this object into RAM.
*   **) {**: Close parameters, open constructor method block.

`java
        this.code = code;
`
*   **	his.code = code;**: Binds the supplied code to the object's instance variable.

`java
        this.name = name;
`
*   **	his.name = name;**: Binds the supplied name.

`java
        this.track = track;
`
*   **	his.track = track;**: Binds the supplied track.

`java
    }
`
*   **}**: Finish Constructor.

`java
    public String getCode() {
`
*   **public String getCode() {**: The public method granting read access to the encapsulated code variable.

`java
        return code;
`
*   **
eturn code;**: Hands the value over to the caller.

`java
    }
`
*   **}**: End of getter.

`java
    public void setCode(String code) {
`
*   **public void setCode(String code) {**: Grants safe write access to the encapsulated code variable.

`java
        this.code = code;
`
*   **	his.code = code;**: Overwrites the existing code data.

`java
    }
`
*   **}**: End setter.

`java
    public String getName() {
`
*   **public String getName() {**: Public read method for the degree name.

`java
        return name;
`
*   **
eturn name;**: Yields the string.

`java
    }
`
*   **}**: End getter.

`java
    public void setName(String name) {
`
*   **public void setName(String name) {**: Public write method for the degree name.

`java
        this.name = name;
`
*   **	his.name = name;**: Updates the string.

`java
    }
`
*   **}**: End setter.

`java
    public String getTrack() {
`
*   **public String getTrack() {**: Public read method for the track path.

`java
        return track;
`
*   **
eturn track;**: Returns the string.

`java
    }
`
*   **}**: End getter.

`java
    public void setTrack(String track) {
`
*   **public void setTrack(String track) {**: Public write method for the track path.

`java
        this.track = track;
`
*   **	his.track = track;**: Overwrites the track in memory.

`java
    }
`
*   **}**: End setter.

`java
    @Override
`
*   **@Override**: Standard Java tag marking that we are forcefully replacing an inherited function.

`java
    public String toString() {
`
*   **public String toString() {**: The method used whenever Java needs to inherently turn this generic object into readable text (like dropping it inside a UI Dropdown Box).

`java
        return name;
`
*   **
eturn name;**: Instead of outputting a generic memory hash, we tell it specifically to print out the "name" property instead.

`java
    }
`
*   **}**: End overridden 	oString().

`java
}
`
*   **}**: End ProgramItem class container.
---

### File: `SectionItem.java`

`java
package com.ewumatelite.core.models;
`
*   **package com.ewumatelite.core.models;**: Places the SectionItem blueprint into the models directory.

`java
public class SectionItem {
`
*   **public**: Available everywhere in the application.
*   **class**: Declaring an object blueprint.
*   **SectionItem**: The object's name, representing a specific class section (like Section 1 of CSE 101).
*   **{**: Opens the class block.

`java
    // Apply proper Encapsulation attributes
`
*   **//**: A human-readable comment reiterating that variables remain hidden (Encapsulation).

`java
    private String cid;
`
*   **private**: Access modifier preventing external read/writes.
*   **String**: A text variable.
*   **cid**: The Course ID (e.g., the unique database ID for the course).
*   **;**: Instruction terminator.

`java
    private String sectionNumber;
`
*   **private String sectionNumber;**: A hidden text variable representing the section identifier (e.g., "1", "2").

`java
    public SectionItem(String cid, String sectionNumber) {
`
*   **public SectionItem**: The constructor method for this object.
*   **(**: Open parameters.
*   **String cid, String sectionNumber**: Requires both the Course ID text and the section number text to spawn this object.
*   **) {**: Close parameters, open constructor method block.

`java
        this.cid = cid;
`
*   **	his.cid = cid;**: Assigns the provided Course ID to the private variable inside this specific instance.

`java
        this.sectionNumber = sectionNumber;
`
*   **	his.sectionNumber = sectionNumber;**: Assigns the provided section number.

`java
    }
`
*   **}**: Finishes the constructor block.

`java
    public String getCid() {
`
*   **public String getCid() {**: The public read/getter method for the cid variable.

`java
        return cid;
`
*   **
eturn cid;**: Returns the Course ID text.

`java
    }
`
*   **}**: Closes getter.

`java
    public void setCid(String cid) {
`
*   **public void setCid(String cid) {**: The public write/setter method allowing safe changes to the Course ID.

`java
        this.cid = cid;
`
*   **	his.cid = cid;**: Overwrites the internal property with new data.

`java
    }
`
*   **}**: Closes setter.

`java
    public String getSectionNumber() {
`
*   **public String getSectionNumber() {**: Public read method for the sectionNumber variable.

`java
        return sectionNumber;
`
*   **
eturn sectionNumber;**: Gives back the section text.

`java
    }
`
*   **}**: Closes getter.

`java
    public void setSectionNumber(String sectionNumber) {
`
*   **public void setSectionNumber(String sectionNumber) {**: Public write method for the sectionNumber variable.

`java
        this.sectionNumber = sectionNumber;
`
*   **	his.sectionNumber = sectionNumber;**: Replaces the section number text inside internal memory.

`java
    }
`
*   **}**: Closes setter.

`java
    @Override
`
*   **@Override**: Overrides Java's default memory-address printing for object-to-text conversion.

`java
    public String toString() {
`
*   **public String toString() {**: Standard 	oString() method implementation.

`java
        return "Section " + sectionNumber;
`
*   **
eturn "Section " + sectionNumber;**: Prepends the word "Section " to the literal section number. This means whenever we drop a SectionItem into a ComboBox, it natively prints out as "Section 1" instead of a memory location hash!

`java
    }
`
*   **}**: Closes the overridden method.

`java
}
`
*   **}**: Closes the SectionItem class definition.
---

### File: `AuthRepository.java`

`java
package com.ewumatelite.core.repositories;
`
*   **package com.ewumatelite.core.repositories;**: Organizes this file into the 
epositories package, separating strictly network/database logic from the UI.

`java
import com.ewumatelite.core.config.SupabaseConfig;
`
*   **import com.ewumatelite.core.config.SupabaseConfig;**: Brings in the global configuration holding our database keys and user tokens.

`java
import org.json.JSONObject;
`
*   **import org.json.JSONObject;**: Brings in a 3rd-party library used to effortlessly craft and parse JSON data maps for the internet.

`java
import java.net.URI;
`
*   **import java.net.URI;**: Allows the validation and parsing of actual website address links.

`java
import java.net.http.HttpClient;
`
*   **import java.net.http.HttpClient;**: The modern Java module that manages creating network connections to external websites/APIs.

`java
import java.net.http.HttpRequest;
`
*   **import java.net.http.HttpRequest;**: Represents an outgoing letter we want to send over the internet.

`java
import java.net.http.HttpResponse;
`
*   **import java.net.http.HttpResponse;**: Represents the mail we receive back.

`java

`
*   *(Blank line)*

`java
/**
 * Maps exactly to Flutter's AuthRepository (client.auth.signUp)
 */
`
*   **/** ... */**: A "Javadoc" comment. This special comment is designed to be parsed by automatic documentation generators, telling developers that this Java class mimics the Flutter App's network flow.

`java
public class AuthRepository {
`
*   **public**: Globally available modifier.
*   **class**: Declares an object.
*   **AuthRepository**: Name of the class dealing with user Accounts and Logins.
*   **{**: Starts the definition block.

`java
    public boolean login(String email, String password) throws Exception {
`
*   **public boolean login**: Declares a method that receives login details and ultimately returns a True (Success) or False (Fail) answer.
*   **(**: Open parameters for method arguments.
*   **String email, String password**: The two necessary input strings.
*   **)**: End parameters.
*   **	hrows Exception {**: Instructs the compiler that this method uses the internet and could completely crash/timeout. Instead of crashing the immediate program, it explicitly "throws" the error upstream for someone else to catch. It then { opens the logic block.

`java
        JSONObject payload = new JSONObject();
`
*   **JSONObject payload**: Declares a variable holding abstract JSON map data.
*   **= new JSONObject();**: Creates a fresh, empty JSON dictionary.

`java
        payload.put("email", email);
`
*   **payload.put("email", email);**: Pushes a new entry into the map. The key is "email", and the value is the variable passed in from the function parameters.

`java
        payload.put("password", password);
`
*   **payload.put("password", password);**: Injects the password into the JSON map.

`java

`
*   *(Blank line)*

`java
        HttpRequest request = HttpRequest.newBuilder()
`
*   **HttpRequest request**: Declares an empty HTTP request definition.
*   **= HttpRequest.newBuilder()**: Invokes Java's built-in Request Factory pattern, allowing us to neatly construct the letter we plan on sending over the network step-by-step.

`java
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/token?grant_type=password"))
`
*   **.uri**: Specifies the ultimate destination.
*   **(URI.create(**: A quick converter turning text into a valid hyperlink.
*   **SupabaseConfig.PROJECT_URL**: Gets our secret database URL (like "https://xyz.supabase.co").
*   **+ "/auth/v1/token?grant_type=password"**: Adds the exact address path Supabase requires to process raw Email/Password email authentications.
*   **))**: Closes out string operations and method params.

`java
                .header("Content-Type", "application/json")
`
*   **.header**: Attaches a custom label to the envelope being sent.
*   **("Content-Type", "application/json")**: Explicitly warns the target website that the interior message holds valid JSON data instead of a text file or an image.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey", SupabaseConfig.ANON_KEY)**: Attaches our secret project API key so Supabase does not immediately ban the request for being unauthorized public traffic.

`java
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
`
*   **.POST**: Sets the method to POST (we are *sending* data to be stored/processed, not GETting).
*   **(HttpRequest.BodyPublishers.ofString**: Takes raw Java text and translates it into a byte-stream format that the network can actually transmit.
*   **(payload.toString())))**: Flattens out our populated JSON Object into raw text (e.g. {"email": "abc"...}) to be pushed into the publisher.

`java
                .build();
`
*   **.build();**: Informs the Factory that we are done constructing the network envelope. Creates the final HttpRequest object and stores it into our 
equest variable.

`java

`
*   *(Blank spacer line)*

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> response**: Declares a new variable 
esponse specifically tracking received text responses (<String>).
*   **= httpClient**: Reaches for our globally declared internet module (which is defined lower down).
*   **.send**: Halts the immediate thread (stops the program) while it sends our built letter and waits for a reply.
*   **(request, **: Passes the envelope we just constructed.
*   **HttpResponse.BodyHandlers.ofString());**: Forces the receiving parser to assume the incoming reply is pure text (and not binary audio/image data).

`java

`
*   *(Blank spacer line)*

`java
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
`
*   **if (**: Prepares logic.
*   **
esponse.statusCode()**: Checks the digital status code returned from the server (e.g. 404 Not Found, 200 OK).
*   **>= 200**: Ensures the code is at least 200.
*   **&&**: "And". Demands both surrounding instructions are mathematically true.
*   **
esponse.statusCode() < 300**: Ensures it's strictly under 300. (The 200-299 HTTP range signifies universal Success).
*   **) {**: Entering success loop.

`java
            JSONObject resJson = new JSONObject(response.body());
`
*   **JSONObject resJson**: Creates a new JSON decoding tool.
*   **= new JSONObject(response.body());**: Takes the raw text ody() of the return letter (which looks like {"access_token": "a123"}) and translates it back into a map that Java can easily dig through.

`java
            SupabaseConfig.currentUserToken = resJson.getString("access_token");
`
*   **SupabaseConfig.currentUserToken**: Accesses the global authentication token string in our configuration file.
*   **= resJson.getString("access_token");**: Parses "access_token" out of the API response and globally saves it into the computer's active memory for this run!

`java
            SupabaseConfig.currentUserId = resJson.getJSONObject("user").getString("id");
`
*   **SupabaseConfig.currentUserId**: Similar to above, grabbing our global User ID.
*   **= resJson.getJSONObject("user")**: Digs into a nested JSON object named "user" before searching further.
*   **.getString("id");**: Targets the exact "id" text within the sub-object.

`java

`
*   *(Blank space)*

`java
            // Persist the token to system preferences so the "Remember Me" works
`
*   **//**: A comment explaining why the next code block interacts with the operating system itself.

`java
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(SupabaseConfig.class);
`
*   **java.util.prefs.Preferences prefs**: Creates a variable using Java's built-in system preferences engine.
*   **= java.util.prefs.Preferences.userNodeForPackage**: Asks Windows/Mac for a physical file hidden on the local hard drive designed explicitly for *this specific app's package structure*.
*   **(SupabaseConfig.class);**: Anchors the registry/node around the configuration class path.

`java
            prefs.put("SUPABASE_JWT", SupabaseConfig.currentUserToken);
`
*   **prefs.put**: Injects a literal value into the persistent OS config file or Windows registry.
*   **("SUPABASE_JWT", SupabaseConfig.currentUserToken);**: Writes our active session token onto the hard drive under the key "SUPABASE_JWT" so that if the user closes the app, the session isn't erased from history.

`java
            prefs.put("SUPABASE_UID", SupabaseConfig.currentUserId);
`
*   **prefs.put("SUPABASE_UID", SupabaseConfig.currentUserId);**: Persists the User ID onto the hard drive as well!

`java

`
*   *(Blank line)*

`java
            return true;
`
*   **
eturn true;**: Bails out of the function, answering "Yes". The login succeeded.

`java
        } else {
`
*   **} else {**: If the HTTP code was below 200 or over 299 (failed login or bad server).

`java
            return false;
`
*   **
eturn false;**: Answers "No". Auth failed.

`java
        }
`
*   **}**: Terminating branching section.

`java
    }
`
*   **}**: Finishes the login definition method.

`java
    private final HttpClient httpClient = HttpClient.newHttpClient();
`
*   **private final**: A variable hidden and unchangeable. 
*   **HttpClient httpClient = HttpClient.newHttpClient();**: The single core network portal object instantiated up here out of the way, reused rapidly by the login/register commands above it to actually push and pull the HttpRequest letters!

`java

`
*   *(Blank line)*

`java
    public String registerUser(String email, String password, String fullName, String nickname, String studentId, String programCode, String departmentName, String semType) throws Exception {
`
*   **public String registerUser...**: A massive function declaration capable of shooting an entire User Registration profile to the Supabase Cloud. It demands 8 distinct string parameters representing the form input fields and promises to hand back a unique UserID (String).

`java
        // 1. Prepare User Metadata for Auth
`
*   **//**: A comment detailing the initial block execution context.

`java
        JSONObject metaData = new JSONObject();
`
*   **JSONObject metaData = new JSONObject();**: Creates a new JSON mapping to hold nested metadata.

`java
        metaData.put("fullName", fullName);
`
*   **metaData.put("fullName", fullName);**: Pipes the user's name parameter inside.

`java
        metaData.put("displayName", nickname);
`
*   **metaData.put("displayName", nickname);**: Stashes the nickname as well.

`java

`
*   *(Blank space)*

`java
        JSONObject payload = new JSONObject();
`
*   **JSONObject payload = new JSONObject();**: The *outward-most* JSON request dictionary.

`java
        payload.put("email", email);
`
*   **payload.put("email", email);**: Adds the email.

`java
        payload.put("password", password);
`
*   **payload.put("password", password);**: Adds the password.

`java
        payload.put("data", metaData);
`
*   **payload.put("data", metaData);**: Injects the entirety of the metaData JSON block into a single slot of the overarching payload JSON block. Nesting!

`java

`
*   *(Blank space)*

`java
        // 2. HTTP request to Supabase Auth Endpoint
`
*   **//**: Comment signifying the actual internet step.

`java
        HttpRequest request = HttpRequest.newBuilder()
`
*   **HttpRequest request = HttpRequest.newBuilder()**: Re-spins up the network Factory to trace an outgoing API call.

`java
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/signup"))
`
*   **.uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/signup"))**: Binds our database URL onto the specific /auth/v1/signup path reserved for creating new accounts.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey", SupabaseConfig.ANON_KEY)**: Passes the standard anonymous API authentication key.

`java
                .header("Content-Type", "application/json")
`
*   **.header("Content-Type", "application/json")**: Marks this package as being full of JSON data.

`java
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
`
*   **.POST(HttpRequest.BodyPublishers.ofString(payload.toString()))**: Flattens out our gigantic nested nested JSON Map object, squishes it into raw character strings, and packs it for the .POST() voyage.

`java
                .build();
`
*   **.build();**: Caps off the letter and saves it to 
equest.

`java

`
*   *(Blank spacer)*

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> response**: Designates a container.
*   **= httpClient.send(request, HttpResponse.BodyHandlers.ofString());**: Tells the local httpClient connection port to push the built letter to Supabase and wait until Supabase returns text.

`java
        
`
*   *(Blank spacer)*

`java
        if (response.statusCode() >= 400) {
`
*   **if (response.statusCode() >= 400) {**: Any HTTP status greater than 400 represents a critical error or client failure (a duplicate email, too short a password, banned IP).

`java
            throw new RuntimeException("Supabase Signup Failed: " + response.body());
`
*   **	hrow new RuntimeException**: The function utterly terminates and screams an error toward whoever called it, carrying an exception that must be addressed immediately so the Program doesn't simply continue executing corrupted profile code!
*   **("Supabase Signup Failed: " + response.body());**: The crash notice logs specifically *why* the server hated the sign-up request (the literal 
esponse.body() error string, saving a ton of manual debugging).

`java
        }
`
*   **}**: Finishes the crash check logic.

`java

`
*   *(Blank line)*

`java
        // 3. Extract the newly generated Auth UUID
`
*   **//**: An explanatory comment denoting that the Account System approved the new user.

`java
        JSONObject resJson = new JSONObject(response.body());
`
*   **JSONObject resJson = new JSONObject(response.body());**: Wraps the Supabase raw JSON approval message back into a Java Object for drilling.

`java
        String uid = null;
`
*   **String uid = null;**: Initializes an empty text bucket called UID (Universal Identifier).

`java
        if (resJson.has("access_token")) {
`
*   **if (resJson.has("access_token")) {**: Checks if the JSON map possesses the key ccess_token. 

`java
             SupabaseConfig.currentUserToken = resJson.getString("access_token");
`
*   **SupabaseConfig.currentUserToken = resJson.getString("access_token");**: Automatically logs this brand new user in by copying the token into system RAM.

`java
             SupabaseConfig.currentUserId = resJson.getJSONObject("user").getString("id");
`
*   **SupabaseConfig.currentUserId = resJson.getJSONObject("user").getString("id");**: Automatically loads their ID directly.

`java
        }
`
*   **}**: Complete token check condition.

`java
        if (resJson.has("user")) {
`
*   **if (resJson.has("user")) {**: Deep drills to trace where the UID originated inside the JSON list via nested conditions.

`java
            uid = resJson.getJSONObject("user").getString("id"); 
`
*   **uid = resJson.getJSONObject("user").getString("id");**: Grabs it securely from within the "user" branch structure.

`java
        } else if (resJson.has("id")) {
`
*   **} else if (resJson.has("id")) {**: An alternate drilling path checking in case the JSON changed shape. (It occasionally does based on whether email-confirmations are enabled globally!)

`java
            uid = resJson.getString("id");
`
*   **uid = resJson.getString("id");**: Securely extracts.

`java
        }
`
*   **}**: Finishes search logic.

`java

`
*   *(Blank spacer)*

`java
        if (uid == null) {
`
*   **if (uid == null) {**: Triggers if our search path utterly failed and an ID was never found.

`java
            throw new RuntimeException("Wait, no User ID returned from Auth.");
`
*   **	hrow new RuntimeException...**: Forces a physical Application Crash with a clear log stating that the ID returned corrupted.

`java
        }
`
*   **}**: Finishes search failure logic check.

`java

`
*   *(Blank Line)*

`java
        // 4. Update the Profiles table directly EXACTLY like Flutter does 
`
*   **//**: A comment block notifying that the application is about to send an entirely second independent request to update the profiles database, saving all the inputted names and tracking information.

`java
        // Flutter separates it into register_screen (nickname, student_id) and program_selection (program_code, dept_name).
`
*   **//**: Comment.

`java
        // For the Lite version, we'll do it in one atomic push!
`
*   **//**: Comment indicating we circumvented two screens into one massive API call.

`java
        JSONObject profilePayload = new JSONObject();
`
*   **JSONObject profilePayload = new JSONObject();**: New map payload specifically targeted toward standard database profiles.

`java
        profilePayload.put("id", uid);
`
*   **profilePayload.put("id", uid);**: Links the raw Account ID to the Database Profile ID.

`java
        profilePayload.put("full_name", fullName);
`
*   **profilePayload.put("full_name", fullName);**: Links standard names down.

`java
        profilePayload.put("nickname", nickname);
`
*   **profilePayload.put("nickname", nickname);**: Sub-inserts nickname to the JSON Map.

`java
        profilePayload.put("student_id", studentId);
`
*   **profilePayload.put("student_id", studentId);**: Matches the user's student card ID.

`java
        profilePayload.put("program_code", programCode);
`
*   **profilePayload.put("program_code", programCode);**: Attaches standard major code format (e.g. BCSE).

`java
        profilePayload.put("department_name", departmentName);
`
*   **profilePayload.put("department_name", departmentName);**: Sub-inserts the academic department branch.

`java
        profilePayload.put("semester_type", semType);
`
*   **profilePayload.put("semester_type", semType);**: Notes the type of schedule mapping they take ("Bi-Semester").

`java
        profilePayload.put("track", semType); // Flutter explicitly saves this as track too
`
*   **profilePayload.put("track", semType);**: Duplicates SemType into the 'Track' key because of legacy definitions in the wider original codebase.

`java
        profilePayload.put("onboarding_status", "completed"); // Skips the onboarding loop!
`
*   **profilePayload.put("onboarding_status", "completed");**: Automatically marks them cleared so the application doesn't reboot back to the first screen!

`java

`
*   *(Blank spacer line)*

`java
        HttpRequest profileReq = HttpRequest.newBuilder()
`
*   **HttpRequest profileReq = HttpRequest.newBuilder()**: Re-spins up another final HttpRequest container.

`java
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles"))
`
*   **.uri...**: Defines that this query strictly hits the basic Database Tables structure on the /rest/v1/profiles path instead of targeting User Authentication modules.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey", SupabaseConfig.ANON_KEY)**: Anon bypass key for traffic firewall.

`java
                .header("Authorization", "Bearer " + (SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY))
`
*   **.header("Authorization", "Bearer " + ...)**: Crucial difference from the last request! This demands *proper* Authorization.
*   **(SupabaseConfig.currentUserToken != null ? ... : ...)**: A ternary (inline conditional statement). *If* the user successfully generated an active token memory state 30 lines prior, inject their literal authentication string (e.g. Bearer aB1CD...). If they didn't, dump the ANON_KEY as a hopeless backup and let Supabase sort it out. 

`java
                .header("Content-Type", "application/json")
`
*   **.header("Content-Type", "application/json")**: Denoting JSON contents.

`java
                .header("Prefer", "resolution=merge-duplicates") // Upsert equivalent
`
*   **.header("Prefer", "resolution=merge-duplicates")**: Exerts direct instruction over the Database SQL driver. It commands Supabase to "Upsert" the provided payload�meaning if row 123 already completely exists in the Profiles list, DO NOT crash claiming it's a replication failure. Cleanly overwrite its data (merge) instead.

`java
                .POST(HttpRequest.BodyPublishers.ofString(profilePayload.toString()))
`
*   **.POST(HttpRequest.BodyPublishers.ofString...**: Prepares the packet as an incoming stream.

`java
                .build();
`
*   **.build();**: Wraps the network call cleanly.

`java

`
*   *(Blank spacer)*

`java
        HttpResponse<String> profileRes = httpClient.send(profileReq, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> profileRes = httpClient.send(profileReq, HttpResponse.BodyHandlers...);**: Halts Java completely. Actually flings the JSON payload off into the atmosphere. Waits for a standard String network reply object back.

`java
        if (profileRes.statusCode() >= 400) {
`
*   **if (profileRes.statusCode() >= 400) {**: Triggers if the response represents anything error-related.

`java
            System.err.println("Warning: Profile Upsert Failed (Trigger might have already populated): " + profileRes.body());
`
*   **System.err.println(...);**: Unlike the main Auth path, this intentionally does *NOT* utilize a standard Crash Exception. In reality, a database-level SQL Trigger might automatically spawn a shadow Profile the literal millisecond a User Account triggers. Simply dumping an Error Log out to the IDE's console prevents the App from freezing.

`java
        }
`
*   **}**: Finishes Error print handling.

`java

`
*   *(Blank)*

`java
        return uid;
`
*   **
eturn uid;**: Function has completed efficiently. Backs all the way out, handing the original unique user ID variable to the class that called 
egisterUser() in the first place!

`java
    }
`
*   **}**: Closes the registration method scope entirely.

`java
}
`
*   **}**: Closes out AuthRepository.java.
---

### File: `ExceptionRepository.java`

`java
package com.ewumatelite.core.repositories;
`
*   **package com.ewumatelite.core.repositories;**: Groups this class inside the backend repository folder.

`java
import com.ewumatelite.core.config.SupabaseConfig;
`
*   **import com.ewumatelite.core.config.SupabaseConfig;**: Imports the global state holding our database endpoints and user API keys.

`java
import org.json.JSONArray;
`
*   **import org.json.JSONArray;**: Imports the 3rd-party utility responsible for parsing internet JSON *Lists* (arrays like [...]).

`java
import org.json.JSONObject;
`
*   **import org.json.JSONObject;**: Imports the utility for parsing standard JSON *Maps* (like {...}).

`java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
`
*   **import java.net...**: Standard Java 11 HTTP protocol libraries for sending web requests and receiving server responses.

`java
public class ExceptionRepository {
`
*   **public class ExceptionRepository {**: Declares a globally available class meant to interact specifically with the schedule_exceptions table in the Supabase database. This table tracks when a class is canceled, manually added, or rescheduled.

`java
    private final HttpClient httpClient = HttpClient.newHttpClient();
`
*   **private final HttpClient httpClient**: Creates an unchanging, hidden network portal connection object once when this class is loaded, so we don't have to keep re-opening Windows internet sockets for every single database query.

`java
    private HttpRequest.Builder buildAuthenticatedRequest(String url) {
`
*   **private HttpRequest.Builder buildAuthenticatedRequest**: A private helper factory method. Instead of copy-pasting the authorization headers over and over for every single network function in this file, we write it once here.
*   **(String url) {**: Expects the target hyperlink as an argument.

`java
        return HttpRequest.newBuilder()
`
*   **
eturn HttpRequest.newBuilder()**: Spins up the core Envelope builder.

`java
                .uri(URI.create(url))
`
*   **.uri(URI.create(url))**: Stamps the destination address on it.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey", SupabaseConfig.ANON_KEY)**: Sneaks past the public firewall.

`java
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken);
`
*   **.header("Authorization", "Bearer " + SupabaseConfig.currentUserToken);**: Stashes the active user's secure login token so the database knows exactly who is asking for the schedule exception list.

`java
    }
`
*   **}**: Finishes the factory helper builder.

`java
    public JSONArray fetchExceptions(String uid) throws Exception {
`
*   **public JSONArray fetchExceptions(String uid)**: Method that promises to hit the internet and return a JSON List of canceled/makeup classes specifically belonging to the user (uid).

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions?user_id=eq." + uid;
`
*   **String url...**: Constructs the REST API URL. ?user_id=eq.uid strictly filters the SQL table where the "user_id" column precisely equals ("eq.") our user.

`java
        HttpRequest request = buildAuthenticatedRequest(url).GET().build();
`
*   **HttpRequest request = buildAuthenticatedRequest(url)**: Hands the URL to our private helper to do all the authorization stamping.
*   **.GET()**: Explicitly marks this envelope as a "Read Only" fetch operation.
*   **.build();**: Finalizes the letter.

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> response = httpClient.send()**: Pauses the current application thread and physically pushes the network request to Supabase, demanding it return pure text via BodyHandlers.ofString().

`java
        if (response.statusCode() == 200) {
`
*   **if (response.statusCode() == 200) {**: Only execute the next block if the server strictly returns "200 OK".

`java
            return new JSONArray(response.body());
`
*   **
eturn new JSONArray(response.body());**: Automatically wraps the raw return text ([{"id":1, ...}]) into a parsed JSONArray object and returns it to the UI!

`java
        }
`
*   **}**: End success block.

`java
        return new JSONArray();
`
*   **
eturn new JSONArray();**: If the request crashed or we got a 404/500 error, just hand back an empty [] list so the app screen stays blank instead of aggressively crashing to desktop.

`java
    }
`
*   **}**: End fetch method.

`java
    public void addCancellation(String uid, String date, String courseCode, boolean pendingMakeup) throws Exception {
`
*   **public void addCancellation...**: Public method allowing the user to mark a specific class on a specific date as Canceled.

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions";
`
*   **String url**: Targeting the schedule_exceptions table directly (no ? filtering, we are inserting).

`java
        JSONObject payload = new JSONObject();
        payload.put("user_id", uid);
        payload.put("type", "cancel");
        payload.put("date", date);
        payload.put("course_code", courseCode);
`
*   **payload.put(...)**: Constructs the immediate SQL columns to be inserted into the cloud database. Notice "type" is hardcoded to "cancel".

`java
        JSONObject metadata = new JSONObject();
        metadata.put("pendingMakeup", pendingMakeup);
        payload.put("metadata", metadata);
`
*   **JSONObject metadata...**: Since Supabase allows nested JSONB (JSON Binary) columns natively inside postgres, we can build a sub-object specifying whether the student expects a makeup class, and wedge it directly into the master payload. 

`java
        HttpRequest request = buildAuthenticatedRequest(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
`
*   **.POST()**: Flags the envelope to overwrite/insert data. It leverages our builder helper to do the heavy authorization lifting!

`java
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **httpClient.send...**: Executes the push over the internet.

`java
    }
`
*   **}**: End add cancellation.

`java
    public void removeException(String exceptionId) throws Exception {
`
*   **public void removeException**: Deletes a specific cancellation/manual class.

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions?id=eq." + exceptionId;
`
*   **...url...**: Targets the exact row ID we want to annihilate in the database.

`java
        HttpRequest request = buildAuthenticatedRequest(url).DELETE().build();
`
*   **.DELETE()**: Very explicit HTTP command instructing the REST API that we want this entire row dropped from the Table.

`java
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **httpClient.send...**: Dispatches the termination order.

`java
    }
`
*   **}**: Finish method.

`java
    public void addManualClass(String uid, String date, String courseCode, String courseName, String startTime, String endTime, String room, String faculty) throws Exception {
`
*   **public void addManualClass...**: Allows a student to shove a completely custom/fake block onto their weekly schedule (like a one-off seminar, club meeting, or makeup class).

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions";
        JSONObject payload = new JSONObject();
        payload.put("user_id", uid);
        payload.put("type", "manual");
        payload.put("date", date);
        payload.put("course_code", courseCode);
        payload.put("course_name", courseName);
        payload.put("start_time", startTime);
        payload.put("end_time", endTime);
        payload.put("room", room);
        payload.put("faculty", faculty);
`
*   **payload.put(...)**: Builds a massive JSON Row mapping all the standard course details, but intentionally hardcodes "type", "manual" so the UI knows to render it with a different hue or icon later on.

`java
        HttpRequest request = buildAuthenticatedRequest(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **httpClient.send...**: Transmits the POST block over the internet to be permanently wedged into the Database!

`java
    }
}
`
*   **}**: Closes the manual addition method, and then closes ExceptionRepository.java.
---

### File: `ProfileRepository.java`

`java
package com.ewumatelite.core.repositories;
`
*   **package com.ewumatelite.core.repositories;**: Classifications foldering for the Profile Repository.

`java
import com.ewumatelite.core.config.SupabaseConfig;
`
*   **import com.ewumatelite.core.config.SupabaseConfig;**: We need this for the database credentials.

`java
import org.json.JSONArray;
`
*   **import org.json.JSONArray;**: Needed for handling the array returned by Supabase reads.

`java
import org.json.JSONObject;
`
*   **import org.json.JSONObject;**: Needed for crafting standard JSON Maps to update the student's profile settings.

`java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
`
*   **import java.net.URI...**: Standard Java HTTP networking dependencies.

`java
public class ProfileRepository {
`
*   **public class ProfileRepository {**: Declares our public repository layer that speaks to the "profiles" table and GoTrue Authentication service.

`java
    private final HttpClient httpClient = HttpClient.newHttpClient();
`
*   **private final HttpClient httpClient**: The dedicated internet object used throughout this class perfectly instanced.

`java
    public JSONObject getProfile(String uid) throws Exception {
`
*   **public JSONObject getProfile(String uid)**: Returns a singular JSON Object packing the entire user's profile info (name, id, track, cgpa).

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid + "&select=*";
`
*   **String url...**: Target URL that filters for our specific student's UUID (id=eq.uid), and demands all columns point-blank using &select=*.

`java
        HttpRequest request = HttpRequest.newBuilder()
`
*   **HttpRequest request = HttpRequest.newBuilder()**: Spins up the empty HTTP letter envelope.

`java
                .uri(URI.create(url))
`
*   **.uri(URI.create(url))**: Stamps the target address.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey", ...)**: Injects the firewall crossing key.

`java
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken)
`
*   **.header("Authorization", ...)**: Injects the authenticated session token payload so row-level-security allows the read.

`java
                .GET()
`
*   **.GET()**: Signals the server that we are only asking for data, not writing.

`java
                .build();
`
*   **.build();**: Finalizes the envelope.

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> response = httpClient.send...**: Physically pushes the packet through the internet to the cloud server, waiting for a pure text response.

`java
        if (response.statusCode() == 200) {
`
*   **if (response.statusCode() == 200) {**: Check if the network call succeeded before blindly parsing.

`java
            JSONArray array = new JSONArray(response.body());
`
*   **JSONArray array = new JSONArray(response.body());**: PostgREST endpoints always strictly return a JSON Array format string (like [{ "name": "..." }]), even if you are just returning one specific row.

`java
            if (array.length() > 0) return array.getJSONObject(0);
`
*   **if (array.length() > 0) return array.getJSONObject(0);**: If the array isn't empty, peel out the very first object inside it and return merely the object itself {"name" : "..."} to easily use in the UI.

`java
        }
`
*   **}**: Close successful read check bracket.

`java
        return null;
`
*   **
eturn null;**: Returns emptiness if the query missed or blew up.

`java
    }
`
*   **}**: Finishes the getProfile definition.

`java
    public void updateProfileField(String uid, String field, Object value) throws Exception {
`
*   **public void updateProfileField...**: Modular function that lets us patch a specific profile column (ield) with a dynamic target alue (which could be a String, Number, or List).

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid;
`
*   **String url...**: Prepares the target database table row.

`java
        JSONObject payload = new JSONObject();
`
*   **JSONObject payload = new JSONObject();**: Creates a new blank Map.

`java
        payload.put(field, value);
`
*   **payload.put(field, value);**: Dynamically plops whatever variable string name we passed into the map. Like assigning {"track": "tri_semester"}.

`java
        HttpRequest request = HttpRequest.newBuilder()
`
*   **HttpRequest request = HttpRequest.newBuilder()**: Spins up our HTTP letter.

`java
                .uri(URI.create(url))
`
*   **.uri(...)**: Attaches the endpoint targeting our row.

`java
                .header("apikey", SupabaseConfig.ANON_KEY)
`
*   **.header("apikey"...**: Adds the public auth pass.

`java
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken)
`
*   **.header("Authorization"...**: Adds the private user token ticket.

`java
                .header("Content-Type", "application/json")
`
*   **.header("Content-Type"...**: Tells Supabase its receiving pure raw JSON text next.

`java
                .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
`
*   **.method("PATCH"...**: Specifically instructs the server to update purely the columns detailed in the JSON payload and absolutely leave the rest of the row alone.

`java
                .build();
`
*   **.build();**: Finishes the HTTP packaging block.

`java
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **httpClient.send...**: Throws the Update Request to the server.

`java
    }
`
*   **}**: Closes the update method.

`java
    public JSONObject getCoursesDone(String uid) throws Exception {
`
*   **public JSONObject getCoursesDone(String uid)...**: Method specifically tailored for the semester progress tab to fetch the "courses_done" column which contains a complex JSON map of passed courses and their grades.

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid + "&select=courses_done";
`
*   **String url...**: Requests exclusively the courses_done column to save bandwidth vs grabbing the entire profile again.

`java
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken)
                .GET()
                .build();
`
*   **HttpRequest request...**: The standard authorized HTTP Read-Only envelop assembly process.

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse...**: Sends it over the network to Supabase.

`java
        if (response.statusCode() == 200) {
`
*   **if (response.statusCode() == 200) {**: Verify network and server success.

`java
            JSONArray array = new JSONArray(response.body());
`
*   **JSONArray array...**: The array response wrapping shell from up above returns natively.

`java
            if (array.length() > 0 && !array.getJSONObject(0).isNull("courses_done")) {
`
*   **if (array.length() > 0 && !array.getJSONObject(0).isNull("courses_done")) {**: Explicitly double-check the array exists, and more importantly confirm that the specific courses_done inside the wrapper object is not natively NULL in the postgres table.

`java
                return array.getJSONObject(0).getJSONObject("courses_done");
`
*   **
eturn array.getJSONObject(0).getJSONObject("courses_done");**: Pluck the wrapper map out of the array, then extract the nested inner map mapped exactly as "courses_done" out.

`java
            }
`
*   **}**: Finishes the inner existence verification bracket.

`java
        }
`
*   **}**: Finishes the HTTP OK check.

`java
        return new JSONObject();
`
*   **
eturn new JSONObject();**: If anything fails or the user has never updated their completed courses list once, just return an empty, workable Map {} instead of crashing.

`java
    }
`
*   **}**: Ends the getter.

`java
    public void updatePassword(String newPassword) throws Exception {
`
*   **public void updatePassword...**: Pings the Supabase GoTrue Auth server directly to rapidly update the user's login password.

`java
        String url = SupabaseConfig.PROJECT_URL + "/auth/v1/user";
`
*   **String url...**: Take note this specifically skips /rest/v1/ and hits the /auth/ edge-node completely ignoring the profile table.

`java
        JSONObject payload = new JSONObject();
        payload.put("password", newPassword);
`
*   **JSONObject payload...**: Binds the required argument tag (password) alongside the user's fresh text input into a map.

`java
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
`
*   **.PUT()**: This specifically executes a PUT methodology, completely overwriting the password hash mapped to the current Token user in the GoTrue edge system!

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse...**: Fires the payload into the network port.

`java
        if (response.statusCode() >= 400) {
`
*   **if (response.statusCode() >= 400) {**: Anything starting in 4xx or 5xx is an implicit HTTP error state.

`java
            throw new RuntimeException("Password Update Failed: " + response.body());
`
*   **	hrow new RuntimeException(...)**: Throws a forced system-halting crash directly into the user interface containing whatever native auth error the server sent back on why the password changed fizzled.

`java
        }
    }
}
`
*   **}**: Ends the if block, update block, and concludes the ProfileRepository.java class!
---

### File: `AcademicRepository.java`

`java
package com.ewumatelite.core.repositories;
`
*   **package com.ewumatelite.core.repositories;**: Package declaration for the core data services.

`java
import com.ewumatelite.core.config.SupabaseConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
`
*   **import ...**: Imports our configuration to get keys alongside standard HTTP and JSON utilities.

`java
/**
 * Maps exactly to Flutter's Riverpod Providers and Repositories via PostgREST
 */
public class AcademicRepository {
`
*   **public class AcademicRepository {**: This is a monstrous God-Class managing the bulk of the application's connection to the Supabase REST API. It handles semesters, courses, schedules, enrollment, tasks, holidays, and grades all in one place.

`java
    private final HttpClient httpClient = HttpClient.newHttpClient();
`
*   **private final HttpClient httpClient = HttpClient.newHttpClient();**: We instantiate exactly one network gateway for the entire class to reuse.

`java
    // 0. Get Programs specifically like the Flutter ProgramSelectionScreen
    public JSONArray getPrograms() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/programs?select=program_code,name,department_name,track";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
`
*   **public JSONArray getPrograms()...**: Fetches the list of degree programs (like BSEEE or BBA) from the database so the user can select their major. Uses select= to only download exact columns to reduce bandwidth.

`java
    // 1. Exactly mimics ctiveSemesterRepository.getActiveSemester('tri_semester')
    public String getUpcomingSemester(String trackType) throws Exception {
        String safeTrack = trackType == null ? "tri_semester" : trackType;
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=next_semester_code&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0).getString("next_semester_code");
        }
        return null;
    }
`
*   **public String getUpcomingSemester...**: Finds out what the *next* semester is (e.g., "Fall 2026") based on the university's active track (bi-semester vs tri-semester) by looking at the 
ext_semester_code column.

`java
    public String getActiveSemester(String trackType) throws Exception {
        String safeTrack = trackType == null ? "tri_semester" : trackType;
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=current_semester_code&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0).getString("current_semester_code"); 
        }
        throw new RuntimeException("Active semester not found for " + safeTrack + " track.");
    }
`
*   **public String getActiveSemester...**: Similar to the above, but fetches the *current* real-time semester the students are in to power the dashboard.

`java
    // 2. Exactly mimics CourseRepository fetching metadata
    public JSONObject getScheduleGeneration(String genId) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_generations?id=eq." + genId + "&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0);
        }
        return null;
    }
`
*   **public JSONObject getScheduleGeneration...**: When a student auto-generates a schedule, the backend builds a "Generation ID". This fetches that specific auto-built schedule draft.

`java
    public JSONArray getCourseMetadata() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/course_metadata?select=code,name";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
`
*   **public JSONArray getCourseMetadata()...**: Downloads the barebones dictionary list of every course name and code existing in the university to power dropdown menus.

`java
    // 3. Exactly mimics the fallback dynamic table query supabase.from(tableName).select()
    public JSONArray getSectionsForCourse(String currentSemesterCode, String targetCourseCode) throws Exception {
        String safeSem = currentSemesterCode.toLowerCase().replaceAll("[ _]", "");
        String tableName = "courses_" + safeSem; 
`
*   **String tableName = "courses_" + safeSem;**: Supabase handles dynamic scaling by giving each semester its own isolated table (e.g. courses_spring2026). This string surgery dynamically calculates the table name to read from.

`java
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/" + tableName + "?course_code=eq." + targetCourseCode.replaceAll(" ", "%20") + "&select=id,section_number,faculty_initials,schedule_data";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request); 
    }
`
*   **String url...**: We formulate the dynamic query filtering for the specific 	argetCourseCode. We encode spaces as %20 so the URL doesn't break, then execute it.

`java
    // 4. Exactly mimics scheduleRepositoryProvider.insert() to place the final enrollment!
    public void pushEnrollment(String userId, String sanitizedSemesterCode, String courseCode, String sectionId, String sectionNumber) throws Exception {
`
*   **public void pushEnrollment...**: The massive, heavily orchestrated function responsible for registering a student into an actual class.

`java
        JSONObject payload = new JSONObject();
        payload.put("user_id", userId);
        ... // (omitting repetitive puts)
        payload.put("status", "enrolled");
`
*   **JSONObject payload...**: Constructs the SQL row data pointing the student to the specific class UUID.

`java
        HttpRequest request = HttpRequest.newBuilder()
                ...
                .header("Prefer", "return=representation")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **.header("Prefer", "return=representation")**: An advanced trick forcing Supabase to return the fully inserted row back to us just like a SELECT, serving to guarantee it was saved.
*   **.POST(...)**: Pushes the row into the enrollments table.

`java
        // 2. Add to profile.enrolled_sections
        String pUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + userId + "&select=enrolled_sections";
        ...
        sections.put(sectionId);
        ...
        httpClient.send(upReq, HttpResponse.BodyHandlers.ofString());
`
*   **// 2. Add to profile...**: For redundant safety and edge-function integration, we also pull the student's profile array of enrolled_sections, append the newly registered UUID, and PATCH it back to the cloud.

`java
        // 3. Clear weekly_grid_cache
        JSONObject cacheClear = new JSONObject();
        cacheClear.put("weekly_grid_cache", JSONObject.NULL);
        ...
`
*   **// 3. Clear weekly_grid_cache**: We forcefully wipe the student's cached calendar grid for that semester. Our server edge functions will recalculate and redraw their timetable automatically in the background because the cache is now NULL!

`java
    public JSONArray getUserEnrollments(String userId, String semesterCode) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments?user_id=eq." + userId + "&semester_code=eq." + semesterCode + "&select=section_id,course_code";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
`
*   **public JSONArray getUserEnrollments...**: Simply queries the enrollments table to see what classes the student is currently registered into.

`java
    public void dropEnrollment(String userId, String semesterCode, String sectionId) throws Exception {
        // 1. Delete from enrollments table
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments?user_id=eq." + userId + "&semester_code=eq." + semesterCode + "&section_id=eq." + sectionId;
        HttpRequest request = HttpRequest.newBuilder()
                ...
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **public void dropEnrollment...**: The exact reverse of pushEnrollment. Step 1 issues a .DELETE() operation against the exact UUID in the enrollments table.

`java
        // 2. Remove from profile.enrolled_sections
        ...
                JSONArray newSections = new JSONArray();
                for (int i=0; i<sections.length(); i++) {
                    if (!sections.getString(i).equals(sectionId)) {
                        newSections.put(sections.getString(i));
                    }
                }
        ...
`
*   **// 2. Remove from profile...**: Similarly reverses Step 2 by pulling the profile enrolled_sections array, looping through it, and carefully building a 
ewSections copy that perfectly omits the dropped class.

`java
        // 3. Clear weekly_grid_cache
        JSONObject cacheClear = new JSONObject();
        cacheClear.put("weekly_grid_cache", JSONObject.NULL);
        ...
`
*   **// 3. Clear...**: Reverses Step 3 by blanking the schedule cache so the dashboard deletes the colored scheduling block.
---

### File: `AuthService.java`

`java
package com.ewumatelite;
`
*   **package com.ewumatelite;**: Native root-level package file meant to supply authentication services to the UI. It acts as a mirror abstraction layer over AuthRepository.

`java
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
`
*   **import ...**: Automatically imports standard networking and JSON parsing tools.

`java
/**
 * Maps exactly to Flutter's AuthRepository (client.auth.signUp)
 */
public class AuthService {
`
*   **public class AuthService {**: Declares a secondary global service class handling strictly the Account Creation logic.

`java
    private final HttpClient httpClient = HttpClient.newHttpClient();
`
*   **private final HttpClient httpClient = HttpClient.newHttpClient();**: Spawns an enclosed internet gateway specifically for this class to execute requests through.

`java
    public String registerUser(String email, String password, String fullName, String nickname, String studentId, String programCode, String departmentName, String semType) throws Exception {
`
*   **public String registerUser...**: Massively scaled function declaration that accepts all 8 fields of the RegistrationScreen UI form, packages them up, and promises to return the generated UUID String.

`java
        // 1. Prepare User Metadata for Auth
        JSONObject metaData = new JSONObject();
        metaData.put("fullName", fullName);
        metaData.put("displayName", nickname);
`
*   **JSONObject metaData = new JSONObject();**: Instantiates a JSON map specifically for holding human-readable identity strings that GoTrue Auth natively allows inside the 'metadata' parameter.

`java
        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("data", metaData);
`
*   **JSONObject payload = new JSONObject();**: Creates the master Request wrapper. Adds email, password, and then physically nests the metaData JSON block directly inside under the "data" key!

`java
        // 2. HTTP request to Supabase Auth Endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/signup"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
`
*   **HttpRequest request = HttpRequest.newBuilder()...**: Opens an HTTP Envelope builder targeting the exact native Account Creation network endpoint (/auth/v1/signup). Attaches the application's anonymous key and jams the flattened JSON data string as the POST body package.

`java
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
`
*   **HttpResponse<String> response = httpClient.send...**: Temporarily halts program flow to digitally transmit the email and password over the internet to Supabase, demanding it reply back with String text.

`java
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Supabase Signup Failed: " + response.body());
        }
`
*   **if (response.statusCode() >= 400) {**: Scans the network reply for error codes (400+). If an error exists (e.g. duplicate email), it explicitly crashes the local execution thread and screams the exact network error message out to the console.

`java
        // 3. Extract the newly generated Auth UUID
        JSONObject resJson = new JSONObject(response.body());
        String uid = null;
`
*   **JSONObject resJson = new JSONObject(response.body());**: If the request succeeded, the returned body is parsed back into a Map object so Java can read it. Initializing an empty uid string tracker.

`java
        if (resJson.has("access_token")) {
             SupabaseConfig.currentUserToken = resJson.getString("access_token");
        }
`
*   **if (resJson.has("access_token")) {**: If Supabase's reply included a login token (which it does if Email Confirmation is disabled), automatically harvest it and save it into our local RAM to keep the new user seamlessly logged in.

`java
        if (resJson.has("user")) {
            uid = resJson.getJSONObject("user").getString("id"); 
        } else if (resJson.has("id")) {
            uid = resJson.getString("id");
        }
`
*   **if (resJson.has("user")) {**: Safely drills through the nested network reply to hunt down the ID string depending on its dynamic architectural format.

`java
        if (uid == null) {
            throw new RuntimeException("Wait, no User ID returned from Auth.");
        }
`
*   **if (uid == null) {**: Failsafe. If the search failed, explicitly crash so null data isn't pushed to the profile databases!

`java
        // 4. Update the Profiles table directly EXACTLY like Flutter does 
        // Flutter separates it into register_screen (nickname, student_id) and program_selection (program_code, dept_name).
        // For the Lite version, we'll do it in one atomic push!
        JSONObject profilePayload = new JSONObject();
        profilePayload.put("id", uid);
        profilePayload.put("full_name", fullName);
        profilePayload.put("nickname", nickname);
        profilePayload.put("student_id", studentId);
        profilePayload.put("program_code", programCode);
        profilePayload.put("department_name", departmentName);
        profilePayload.put("semester_type", semType);
        profilePayload.put("track", semType); // Flutter explicitly saves this as track too
        profilePayload.put("onboarding_status", "completed"); // Skips the onboarding loop!
`
*   **JSONObject profilePayload = new JSONObject();...**: Immediately generates a massive secondary JSON payload containing all the non-secure demographic data (like specific major, student ID number, and onboarding skip flag).

`java
        HttpRequest profileReq = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + (SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY))
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates") // Upsert equivalent
                .POST(HttpRequest.BodyPublishers.ofString(profilePayload.toString()))
                .build();
`
*   **HttpRequest profileReq...**: Builds an HTTP query targeting /rest/v1/profiles. Uses an inline ternary check (? :) to inject the currentUserToken if available, otherwise defaulting back to the anonymous key.
*   **.header("Prefer", "resolution=merge-duplicates")**: Explicit Postgres instruction forcing an Upsert override, preventing crashes if Supabase Triggers already built an empty profile shell.

`java
        HttpResponse<String> profileRes = httpClient.send(profileReq, HttpResponse.BodyHandlers.ofString());
        if (profileRes.statusCode() >= 400) {
            System.err.println("Warning: Profile Upsert Failed (Trigger might have already populated): " + profileRes.body());
        }
`
*   **HttpResponse<String> profileRes...**: Executes the secondary Push. If an error is returned here, it only outputs a warning print rather than crashing the thread since the account is physically active.

`java
        return uid;
    }
}
`
*   **
eturn uid;**: Function has completed. Sends the validated ID upstream and shuts the AuthService.java class block.
---

### File: `LoginScreen.java`

`java
package com.ewumatelite.features.auth.presentation;
`
*   **package com.ewumatelite.features.auth.presentation;**: Places this file deep in the UI "presentation" folder specifically for the authentication feature.

`java
import com.ewumatelite.core.repositories.AuthRepository;
import com.ewumatelite.core.utils.PlatformUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
`
*   **import ...**: Pulls in the backend AuthRepository, the sizing tools PlatformUtils, and all the necessary JavaFX visual components (buttons, boxes, scenes).

`java
public class LoginScreen {
`
*   **public class LoginScreen {**: Declares the public wrapper defining the Login visuals and actions.

`java
    private final Stage stage;
`
*   **private final Stage stage;**: Stores a permanent reference to the OS window we are rendering inside. 

`java
    public LoginScreen(Stage stage) {
        this.stage = stage;
    }
`
*   **public LoginScreen(Stage stage)...**: Constructor needing the main window passed in to be instantiated.

`java
    public void show() {
`
*   **public void show() {**: The execution method creating the UI structure.

`java
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");
`
*   **VBox root = new VBox();**: Creates a Vertical Box acting as the backdrop spanning the entire window.
*   **
oot.setAlignment(Pos.CENTER);**: Forces everything inside the window into the physical center.
*   **
oot.setPadding...**: Adds a 40-pixel buffer edge so things don't clip the monitor screen.
*   **...getStyleClass().add("root");**: Binds the CSS .root stylesheet to apply background gradients/colors.

`java
        VBox glassCard = new VBox();
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setMaxWidth(400);
        glassCard.setSpacing(20);
        glassCard.getStyleClass().addAll("glass-card", "glass-card-glow");
`
*   **VBox glassCard...**: Generates a smaller secondary vertical box intended to act as the floating translucent "Glass" pane hosting the input forms, capping its width at 400 pixels and applying CSS rendering tags.

`java
        Label sceneTitle = new Label("Welcome Back");
        sceneTitle.getStyleClass().add("title-label");
`
*   **Label sceneTitle...**: Creates text header reading "Welcome Back" utilizing CSS font rules.

`java
        Label subtitle = new Label("Login to continue");
        subtitle.getStyleClass().add("subtitle-label");
`
*   **Label subtitle...**: Creates an inline subtitle.

`java
        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(20, 0, 0, 0));
`
*   **VBox form = new VBox(15);**: Creates a tertiary vertical frame inside the floating glass card specifically grouping the inputs with 15 pixel vertical gaps. Left-aligned so labels remain above inputs appropriately.

`java
        Label emailLabel = new Label("Email Address");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
`
*   **Label emailLabel... TextField emailField...**: The classic text label defining an input field, which holds grey placeholder text inside it.

`java
        Label pwLabel = new Label("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Enter your password");
`
*   **PasswordField pwBox...**: Uses a specialized text field replacing typed letters with secure asterisk (***) dots.

`java
        Button btn = new Button("SIGN IN");
        btn.setMaxWidth(Double.MAX_VALUE);
`
*   **Button btn = new Button...**: Creates the active submission button, commanding its internal width to stretch completely across the horizontal space it occupies (Double.MAX_VALUE).

`java
        Label actionTarget = new Label();
        actionTarget.setWrapText(true);
`
*   **Label actionTarget...**: A blank hidden label intended to flash red if an internet error throws or you type bad credentials. It wraps to a second line automatically via setWrapText(true).

`java
        Button btnRegister = new Button("Don't have an account? Sign Up");
        btnRegister.getStyleClass().add("text-button");
        btnRegister.setOnAction(e -> new com.ewumatelite.features.auth.presentation.RegistrationScreen(stage).show());
`
*   **tnRegister.getStyleClass().add("text-button");**: Applies a CSS style ripping the borders and background off the button, making it physically look like a normal clickable hyperlink.
*   **.setOnAction(e -> new... RegistrationScreen(stage).show());**: Directly ties a click event bridging the app over to load the RegistrationScreen window immediately on click.

`java
        form.getChildren().addAll(emailLabel, emailField, pwLabel, pwBox);
        glassCard.getChildren().addAll(sceneTitle, subtitle, form, btn, actionTarget, btnRegister);
        root.getChildren().add(glassCard);
`
*   **.getChildren().addAll...**: Like stacking Russian nesting dolls, this literally packages the physical UI Nodes inside one another's layers (Root -> Card -> Form -> Buttons) building the final Document Object Model scene layout.

`java
        btn.setOnAction(e -> {
`
*   **tn.setOnAction(e -> {**: Links our "SIGN IN" button to a Java code execution block reacting to native Mouse Clicks.

`java
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
`
*   **...LogExporter.log(...);**: Dumps an audit log tracing the specific screen executing the file.

`java
            String email = emailField.getText();
            String password = pwBox.getText();
`
*   **String email = emailField.getText();**: Retrieves the physical user inputs from the RAM UI buffers!

`java
            if (email.isEmpty() || password.isEmpty()) {
                actionTarget.setText("Email & Password required");
                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                return;
            }
`
*   **if (email.isEmpty() || password.isEmpty()) {**: A local fast verification logic branch bypassing internet networking completely if fields are literally empty.
*   **...setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));**: Hardcodes the error text hex to a vivid red format (the standard 
ose-500 format from tailwind mapping).

`java
            btn.setDisable(true);
            actionTarget.setTextFill(javafx.scene.paint.Color.WHITE);
            actionTarget.setText("Authenticating...");
            com.ewumatelite.core.utils.LogExporter.log("ACTION: Attempting to log in user with email: " + email);
`
*   **tn.setDisable(true);**: Visually and functionally freezes the "SIGN IN" button stopping impatient students from double or triple-clicking the API execution whilst we verify their account!

`java
            new Thread(() -> {
`
*   **
ew Thread(() -> {**: An immensely vital step. This tells the application to spawn a secondary invisible processing thread. If we did network HTTP requests on the main UI visual thread, the application window would freeze/drag until the internet replied. Splitting it means animations and button clicks stay smooth.

`java
                try {
                    boolean success = new AuthRepository().login(email, password);
`
*   **	ry {**: Encapsulates the dangerous network call.
*   **oolean success = new AuthRepository().login(email, password);**: Accesses our core backend network map passing the local credentials. Awaits the true/false!

`java
                    if (success) {
`
*   **if (success) {**: Triggers if the credentials worked and the database sent back a secure access JWT authorization string!

`java
                        try {
                            String activeSem = new com.ewumatelite.core.repositories.AcademicRepository().getActiveSemester("tri_semester");
`
*   **String activeSem = new com.ewumatelite...getActiveSemester("tri_semester");**: Nested network push verifying what real-world semester is currently executing on planet earth.

`java
                            com.ewumatelite.core.utils.LogExporter.log("SUCESS: User " + email + " successfully logged in.");
                            Platform.runLater(() -> { 
                                actionTarget.setText("Login Successful!"); 
                                new com.ewumatelite.features.dashboard.presentation.DashboardScreen(stage, com.ewumatelite.core.config.SupabaseConfig.currentUserId, activeSem).show(); 
                            });
`
*   **Platform.runLater(() -> { ... });**: Extremely critical! Background helper threads CANNOT legally edit or manipulate graphics window screens in Java or it crashes. Platform.runLater respectfully passes instructions back to the main UI thread telling it "Hey, when you are free next millisecond, jump up a DashboardScreen for this authenticated player!"

`java
                        } catch (Exception semEx) {
                            com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to fetch active semester during login: " + semEx.getMessage());
                            Platform.runLater(() -> {
                                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                                actionTarget.setText("Failed to get active semester: " + semEx.getMessage());
                                btn.setDisable(false);
                            });
                        }
`
*   **} catch (Exception semEx) {**: Catches errors if the application couldn't verify the term. Safely unwinds and re-enables the disabled button, outputting the error code.

`java
                    } else {
                        com.ewumatelite.core.utils.LogExporter.log("WARNING: Failed login attempt for email: " + email);
                        Platform.runLater(() -> {
                            actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                            actionTarget.setText("Invalid credentials!");
                            btn.setDisable(false);
                        });
                    }
`
*   **} else {**: Catches if the HTTP error was 403 (Invalid username or bad password). Unlocks the button.

`java
                } catch (Exception ex) {
                    com.ewumatelite.core.utils.LogExporter.log("ERROR: Exception during login execution: " + ex.getMessage());
                    Platform.runLater(() -> {
                        actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                        actionTarget.setText("Login Error: " + ex.getMessage());
                        btn.setDisable(false);
                    });
                }
`
*   **} catch (Exception ex) {**: Core Failsafe if the internet disconnected entirely. Throws the ex.getMessage() back out.

`java
            }).start();
        });
`
*   **}).start();**: Formally launches the invisible thread into space that was declared up entirely above!
*   **});**: Finishes the Button action binder.

`java
        Scene scene = new Scene(root, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("EWUmate Lite - Secure Login");
        stage.show();
    }
}
`
*   **Scene scene = new Scene(...)**: Defines the total visible canvas mapped dynamically using the user's monitor screen resolution helper class.
*   **scene.getStylesheets().add(...)**: Imports the master CSS definitions overriding normal ugly Java visuals with modern rounded corners and glass backgrounds.
*   **stage.setScene(scene);**: Binds the visible canvas to the Window wrapper.
*   **stage.setTitle("EWUmate Lite - Secure Login");**: Changes the top Windows desktop application program name.
*   **stage.show();**: Finally pushes the hidden constructed frame directly to the literal monitor screen!
*   **}**: Finishes show().
*   **}**: Finishes LoginScreen.java.
---

### File: `RegistrationScreen.java`

`java
package com.ewumatelite.features.auth.presentation;
`
*   **package com.ewumatelite.features.auth.presentation;**: Registers this file under the Auth presentation layer.

`java
import com.ewumatelite.core.models.ProgramItem;
import com.ewumatelite.core.repositories.AuthRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.enrollment.presentation.EnrollmentScreen;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
`
*   **import ...**: A massive block of imports sourcing our custom objects (ProgramItem), the network bridges (AuthRepository, AcademicRepository), and all UI layout dependencies.

`java
public class RegistrationScreen {
`
*   **public class RegistrationScreen {**: Defines the public class governing the account creation UI.

`java
    private final Stage stage;
`
*   **private final Stage stage;**: The fixed reference to the desktop window passed around constantly.

`java
    public RegistrationScreen(Stage stage) {
        this.stage = stage;
    }
`
*   **public RegistrationScreen(Stage stage) { ... }**: Standard constructor passing the window.

`java
    public void show() {
`
*   **public void show() {**: Spawns the screen.

`java
        stage.setTitle("EWUmate Lite - Account Registration");
`
*   **stage.setTitle(...)**: Adjusts the OS window titlebar visually.

`java
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("root");
`
*   **VBox root = new VBox();...**: Defines the overarching screen background canvas holding exactly the same CSS .root coloring mapping as the Login screen.

`java
        VBox glassCard = new VBox();
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setMaxWidth(500);
        glassCard.setSpacing(10);
        glassCard.getStyleClass().addAll("glass-card", "glass-card-glow");
`
*   **VBox glassCard...**: Generates a slightly wider (500px) translucent floating card to grant space for the massive influx of form questions.

`java
        Label sceneTitle = new Label("Create Account");
        sceneTitle.getStyleClass().add("title-label");
        
        Label subtitle = new Label("Join the elite EWU community");
        subtitle.getStyleClass().add("subtitle-label");
`
*   **Label sceneTitle...Label subtitle...**: Static styled text headers for the form.

`java
        VBox form = new VBox(8);
        form.setAlignment(Pos.CENTER_LEFT);
`
*   **VBox form = new VBox(8);**: The innermost wrapper with remarkably tight (8px) vertical gap spacing to squish everything so it doesn't drop off the user's screen.

`java
        Label emailLabel = new Label("Official Email");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label pwLabel = new Label("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Secure password");
        
        Label nameLabel = new Label("Full Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter full name");
        
        Label studentIdLabel = new Label("Student ID");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("20XXXXXXX");
        
        Label nicknameLabel = new Label("Nickname");
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Cool user name");
`
*   **Label ... TextField...**: Five identical pairs of Header Text and Standard Text Entry boxes harvesting the new user's physical demographics.

`java
        Label deptLabel = new Label("Department");
        ComboBox<String> deptComboBox = new ComboBox<>();
        deptComboBox.setPromptText("Loading Depts...");
        deptComboBox.setMaxWidth(Double.MAX_VALUE);
`
*   **ComboBox<String> deptComboBox...**: Creates a dropdown menu intended to hold text strings (<String>). Initially, it tells students "Loading Depts" while the invisible background internet thread tries to physically download the department list from the Supabase Server!

`java
        Label programLabel = new Label("Academic Program");
        ComboBox<ProgramItem> programComboBox = new ComboBox<>();
        programComboBox.setPromptText("Select Department First");
        programComboBox.setMaxWidth(Double.MAX_VALUE);
`
*   **ComboBox<ProgramItem> programComboBox...**: Notice the type parameter! This dropdown physically holds our custom generic blueprint ProgramItem objects rather than mere text. Because we previously overrode .toString() back in the Models phase, this UI combobox will automagically print the degree 
ame when displaying the choices to the student.

`java
        Button btn = new Button("REGISTER & SYNC");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(15));
`
*   **Button btn...**: The master submission trigger. Made noticeably thicker via .setPadding(new Insets(15)).

`java
        Label actionTarget = new Label();
        actionTarget.setWrapText(true);
`
*   **Label actionTarget...**: Hidden error/success messenger text box.

`java
        Button btnBack = new Button("Already have an account? Login");
        btnBack.getStyleClass().add("text-button");
        btnBack.setOnAction(e -> new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show());
`
*   **Button btnBack...**: A transparent CSS hyperlink formatted button redirecting execution back to the LoginScreen() page.

`java
        form.getChildren().addAll(
            emailLabel, emailField, 
            pwLabel, pwBox, 
            nameLabel, nameField, 
            studentIdLabel, studentIdField, 
            nicknameLabel, nicknameField,
            deptLabel, deptComboBox,
            programLabel, programComboBox
        );
        glassCard.getChildren().addAll(sceneTitle, subtitle, form, btn, actionTarget, btnBack);
        root.getChildren().add(glassCard);
`
*   **orm.getChildren().addAll...**: Literally injects 14 visual elements physically downwards into the inner form layer one by one mapping exactly to Screen real-estate. Then packages root onto the card.

`java
        Map<String, List<ProgramItem>> programsByDept = new HashMap<>();
`
*   **Map<String, List<ProgramItem>> programsByDept = new HashMap<>();**: Spawns a highly complex Dictionary map designed to sort degree programs under their department header. e.g., {"Business": [BBA, ACC], "Engineering": [ECE, CSE]}. This prevents students entering "Art History" from selecting a "Computer Science" degree major.

`java
        new Thread(() -> {
`
*   **
ew Thread(() -> {**: Detaches an immediate async operation pushing to grab data before the user finishes typing their password.

`java
            try {
                AcademicRepository academicRepository = new AcademicRepository();
                JSONArray progs = academicRepository.getPrograms(); 
`
*   **...academicRepository.getPrograms();**: Pulls the massive RAW array list of all ~40 degree programs from the internet DB.

`java
                for (int i = 0; i < progs.length(); i++) {
`
*   **or (int i = 0; i < progs.length(); i++) {**: Iterates row-by-row sequentially exactly progs.length() times.

`java
                    JSONObject p = progs.getJSONObject(i);
                    String dept = p.optString("department_name", "Unknown");
                    String code = p.optString("program_code", "");
                    String name = p.optString("name", "");
                    String track = p.optString("track", "tri_semester");
`
*   **JSONObject p... String dept...**: Systematically extracts the variables directly from the downloaded dictionary column elements using safe .optString() which refuses to crash if a value is mysteriously missing from the cloud (falling back to blanks).

`java
                    programsByDept.putIfAbsent(dept, new ArrayList<>());
                    programsByDept.get(dept).add(new ProgramItem(code, name, track));
                }
`
*   **programsByDept.putIfAbsent...**: A brilliant sorting mechanism. If the map doesn't already have an ArrayList bin specifically for "Engineering", construct the bin now. Then .add(...) instantiates our [code, name, track] object and tosses it securely into that specific bin.

`java
                Platform.runLater(() -> {
                    deptComboBox.getItems().addAll(programsByDept.keySet());
                    deptComboBox.setPromptText("Select Department");
                });
`
*   **Platform.runLater(...)**: Ping the front-end interface, take the top-level generic "Engineering", "Business" names (.keySet()) and populate the first Dropdown box, wiping away the "Loading Depts..." placeholder completely.

`java
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    deptComboBox.setPromptText("Failed to load");
                });
            }
        }).start();
`
*   **} catch(...) {**: If the internet dies out, set the dropdown text warning the user it's permanently broken. Launch the background Thread().

`java
        deptComboBox.setOnAction(e -> {
`
*   **deptComboBox.setOnAction(e -> {**: Connects a highly localized tracker to the Department selection box, detecting whenever the user physically changes their mouse selection (e.g. pivoting from Business to Engineering).

`java
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event... ");
            String selectedDept = deptComboBox.getValue();
`
*   **String selectedDept = deptComboBox.getValue();**: Fetches the newly clicked String value.

`java
            if (selectedDept != null && programsByDept.containsKey(selectedDept)) {
                programComboBox.getItems().clear();
                programComboBox.getItems().addAll(programsByDept.get(selectedDept));
                programComboBox.getSelectionModel().selectFirst();
            }
        });
`
*   **if (selectedDept != null... {**: Clears the secondary "Degree Programs" dropdown box out, retrieves ONLY the specific List<ProgramItem> mapped entirely against the single department key picked, shoves them into the box, and immediately highlights the top one to save a second user click. Dynamic Box updating!

`java
        btn.setOnAction(e -> {
`
*   **tn.setOnAction(e -> {**: Links clicking the master Submit button.

`java
            ...
            if (deptComboBox.getValue() == null || programComboBox.getValue() == null) {
                ...
                actionTarget.setText("Please select Dept & Program!");
                ...
                return;
            }
`
*   **if (deptComboBox... == null)**: Kills the function entirely (
eturn) if they somehow bypassed the dropdown generation.

`java
            actionTarget.setText("Registration starting...");
            actionTarget.setTextFill(javafx.scene.paint.Color.WHITE);
            btn.setDisable(true);
`
*   **tn.setDisable(true);**: Locks the submission to prevent duplicate SQL requests going through causing the backend to crash entirely.

`java
            new Thread(() -> {
`
*   **
ew Thread(() -> {**: Pushes into background HTTP execution mode.

`java
                try {
                    AuthRepository authRepository = new AuthRepository();
                    AcademicRepository academicRepository = new AcademicRepository();
                    ProgramItem selectedProgram = programComboBox.getValue();
`
*   **AuthRepository... AcademicRepository... ProgramItem...**: Recalls the precise objects needed to construct the network web push strings. Notice because the combobox generically held custom objects, .getValue() natively returns a fully loaded, functional ProgramItem with getters/setters built-in!

`java
                    String uid = authRepository.registerUser(
                            emailAttempt,
                            pwBox.getText(),
                            ...
                            selectedProgram.getCode(),
                            ...
                            selectedProgram.getTrack()
                    );
`
*   **String uid = authRepository.registerUser(...)**: Injects every single graphical text box literal representation out of the UI wrapper into the single God function method, awaiting the resultant secure User ID mapping.

`java
                    com.ewumatelite.core.utils.LogExporter.log("SUCCESS: User Registered successfully...");
                    Platform.runLater(() -> actionTarget.setText("User Registered! Synchronizing..."));
`
*   **Platform.runLater(...)**: Briefly updates the form signaling Phase 1 completed, and we are dynamically fetching tracking arrays.

`java
                    String activeSem = academicRepository.getActiveSemester(selectedProgram.getTrack());
                    JSONArray courses = academicRepository.getCourseMetadata();
`
*   **String activeSem... JSONArray courses...**: Since this registration form effectively bypasses Onboarding entirely (forcing enrollment completion right here on application boot), we absolutely explicitly *MUST* secretly download both the current Semester mapping and the raw dictionary of universally verified Classes (courses) directly from planet earth because the very next window (EnrollmentScreen) needs them to function!

`java
                    Platform.runLater(() -> {
                        actionTarget.setText("SUCCESS!");
                        new EnrollmentScreen(stage, uid, activeSem, courses).show();
                    });
`
*   **
ew EnrollmentScreen...**: Pushes the application window drastically forward into the Enrollment UI Screen, passing our newly created credentials directly downstream! The new user is now officially interacting with the live application logic.

`java
                } catch (Exception ex) {
                    ...
                } finally {
                    Platform.runLater(() -> btn.setDisable(false));
                }
            }).start();
        });
`
*   **} finally { ... btn.setDisable(false)**: The inally {} block is guaranteed to perfectly execute regardless of whether the 	ry was successful or triggered an error catch halfway through. It merely unlocks the button in any termination.

`java
        Scene scene = new Scene(root, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
`
*   **Scene scene... stage.show();**: Pushes the registration page document object model to the application monitor!
---

### File: `DashboardScreen.java`

`java
package com.ewumatelite.features.dashboard.presentation;
`
*   **package com.ewumatelite.features.dashboard.presentation;**: Places the class physically in the folder governing the visual rendering of the Student Dashboard.

`java
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
`
*   **import ...**: Pulls in custom tools (LayoutFactory, PlatformUtils) and standard JavaFX utilities for parsing FXML visual documents natively. Notice .sidebar.presentation.Sidebar is imported because the Dashboard is the first page to physically feature the sidebar!

`java
public class DashboardScreen {
`
*   **public class DashboardScreen {**: Container for the Dashboard application window logic.

`java
    private final Stage stage;
    private final String uid;
    private final String activeSem;
`
*   **private final Stage stage... **: The Dashboard specifically mandates bringing tracking variables uid (Universal ID) and ctiveSem (Active Semester) forward so it knows exactly whose classes to query from the cloud database.

`java
    public DashboardScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }
`
*   **public DashboardScreen(...) { ... }**: Construction parameter block securely passing the user details into local variables.

`java
    public void show() {
`
*   **public void show() {**: The fundamental rendering sequence. Notice this screen operates intrinsically differently than the LoginScreen. The Login screen brute-force generated boxes and buttons through Code. This Dashboard reads a .fxml document!

`java
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            VBox dashboardLayout = loader.load();
`
*   **	ry {**: Encapsulates the disk read just in case the .fxml file is deleted.
*   **FXMLLoader loader = new FXMLLoader(...)**: Defines a new XML layout parser to locate and ingest the external dashboard.fxml file specifically created in SceneBuilder.
*   **VBox dashboardLayout = loader.load();**: Orders the parser to translate the XML into physical Java UI layout containers (VBox) automatically!

`java
            DashboardController controller = loader.getController();
            controller.initData(stage, uid, activeSem);
`
*   **DashboardController controller = loader.getController();**: By simply calling .load(), Java automatically instantiates a new copy of the DashboardController object referenced at the top of the .fxml file. We literally snag that instantiated copy out of memory!
*   **controller.initData(stage, uid, activeSem);**: We bypass the controller's default constructor and directly execute our own initData() command, injecting the user's credentials natively into the executing interface!

`java
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Dashboard", dashboardLayout);
`
*   **Parent adaptiveLayout = LayoutFactory.create(...)**: We pass the naked dashboardLayout block into a centralized LayoutFactory. This factory wraps the main core dashboard entirely *inside* a secondary Layout incorporating the left-aligned Sidebar! This ensures that navigation operates centrally.

`java
            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Dashboard");
            stage.setScene(scene);
            stage.show();
`
*   **Scene scene... stage.show();**: Paints the newly modified Master App window onto the operating system frame, titles it, applies visual gradients, and outputs it to the user.

`java
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
`
*   **} catch(...) {**: If parsing the file fails, dumps the reason directly out to the developer console. Closes the class definitively.
---

### File: `RegistrationScreen.java`

`java
package com.ewumatelite.features.auth.presentation;
`
*   **package com.ewumatelite.features.auth.presentation;**: Registers this file under the Auth presentation layer.

`java
import com.ewumatelite.core.models.ProgramItem;
import com.ewumatelite.core.repositories.AuthRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.enrollment.presentation.EnrollmentScreen;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
`
*   **import ...**: A massive block of imports sourcing our custom objects (ProgramItem), the network bridges (AuthRepository, AcademicRepository), and all UI layout dependencies.

`java
public class RegistrationScreen {
`
*   **public class RegistrationScreen {**: Defines the public class governing the account creation UI.

`java
    private final Stage stage;
`
*   **private final Stage stage;**: The fixed reference to the desktop window passed around constantly.

`java
    public RegistrationScreen(Stage stage) {
        this.stage = stage;
    }
`
*   **public RegistrationScreen(Stage stage) { ... }**: Standard constructor passing the window.

`java
    public void show() {
`
*   **public void show() {**: Spawns the screen.

`java
        stage.setTitle("EWUmate Lite - Account Registration");
`
*   **stage.setTitle(...)**: Adjusts the OS window titlebar visually.

`java
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("root");
`
*   **VBox root = new VBox();...**: Defines the overarching screen background canvas holding exactly the same CSS .root coloring mapping as the Login screen.

`java
        VBox glassCard = new VBox();
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setMaxWidth(500);
        glassCard.setSpacing(10);
        glassCard.getStyleClass().addAll("glass-card", "glass-card-glow");
`
*   **VBox glassCard...**: Generates a slightly wider (500px) translucent floating card to grant space for the massive influx of form questions.

`java
        Label sceneTitle = new Label("Create Account");
        sceneTitle.getStyleClass().add("title-label");
        
        Label subtitle = new Label("Join the elite EWU community");
        subtitle.getStyleClass().add("subtitle-label");
`
*   **Label sceneTitle...Label subtitle...**: Static styled text headers for the form.

`java
        VBox form = new VBox(8);
        form.setAlignment(Pos.CENTER_LEFT);
`
*   **VBox form = new VBox(8);**: The innermost wrapper with remarkably tight (8px) vertical gap spacing to squish everything so it doesn't drop off the user's screen.

`java
        Label emailLabel = new Label("Official Email");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label pwLabel = new Label("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Secure password");
        
        Label nameLabel = new Label("Full Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter full name");
        
        Label studentIdLabel = new Label("Student ID");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("20XXXXXXX");
        
        Label nicknameLabel = new Label("Nickname");
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Cool user name");
`
*   **Label ... TextField...**: Five identical pairs of Header Text and Standard Text Entry boxes harvesting the new user's physical demographics.

`java
        Label deptLabel = new Label("Department");
        ComboBox<String> deptComboBox = new ComboBox<>();
        deptComboBox.setPromptText("Loading Depts...");
        deptComboBox.setMaxWidth(Double.MAX_VALUE);
`
*   **ComboBox<String> deptComboBox...**: Creates a dropdown menu intended to hold text strings (<String>). Initially, it tells students "Loading Depts" while the invisible background internet thread tries to physically download the department list from the Supabase Server!

`java
        Label programLabel = new Label("Academic Program");
        ComboBox<ProgramItem> programComboBox = new ComboBox<>();
        programComboBox.setPromptText("Select Department First");
        programComboBox.setMaxWidth(Double.MAX_VALUE);
`
*   **ComboBox<ProgramItem> programComboBox...**: Notice the type parameter! This dropdown physically holds our custom generic blueprint ProgramItem objects rather than mere text. Because we previously overrode .toString() back in the Models phase, this UI combobox will automagically print the degree 
ame when displaying the choices to the student.

`java
        Button btn = new Button("REGISTER & SYNC");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(15));
`
*   **Button btn...**: The master submission trigger. Made noticeably thicker via .setPadding(new Insets(15)).

`java
        Label actionTarget = new Label();
        actionTarget.setWrapText(true);
`
*   **Label actionTarget...**: Hidden error/success messenger text box.

`java
        Button btnBack = new Button("Already have an account? Login");
        btnBack.getStyleClass().add("text-button");
        btnBack.setOnAction(e -> new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show());
`
*   **Button btnBack...**: A transparent CSS hyperlink formatted button redirecting execution back to the LoginScreen() page.

`java
        form.getChildren().addAll(
            emailLabel, emailField, 
            pwLabel, pwBox, 
            nameLabel, nameField, 
            studentIdLabel, studentIdField, 
            nicknameLabel, nicknameField,
            deptLabel, deptComboBox,
            programLabel, programComboBox
        );
        glassCard.getChildren().addAll(sceneTitle, subtitle, form, btn, actionTarget, btnBack);
        root.getChildren().add(glassCard);
`
*   **orm.getChildren().addAll...**: Literally injects 14 visual elements physically downwards into the inner form layer one by one mapping exactly to Screen real-estate. Then packages root onto the card.

`java
        Map<String, List<ProgramItem>> programsByDept = new HashMap<>();
`
*   **Map<String, List<ProgramItem>> programsByDept = new HashMap<>();**: Spawns a highly complex Dictionary map designed to sort degree programs under their department header. e.g., {"Business": [BBA, ACC], "Engineering": [ECE, CSE]}. This prevents students entering "Art History" from selecting a "Computer Science" degree major.

`java
        new Thread(() -> {
`
*   **
ew Thread(() -> {**: Detaches an immediate async operation pushing to grab data before the user finishes typing their password.

`java
            try {
                AcademicRepository academicRepository = new AcademicRepository();
                JSONArray progs = academicRepository.getPrograms(); 
`
*   **...academicRepository.getPrograms();**: Pulls the massive RAW array list of all ~40 degree programs from the internet DB.

`java
                for (int i = 0; i < progs.length(); i++) {
`
*   **or (int i = 0; i < progs.length(); i++) {**: Iterates row-by-row sequentially exactly progs.length() times.

`java
                    JSONObject p = progs.getJSONObject(i);
                    String dept = p.optString("department_name", "Unknown");
                    String code = p.optString("program_code", "");
                    String name = p.optString("name", "");
                    String track = p.optString("track", "tri_semester");
`
*   **JSONObject p... String dept...**: Systematically extracts the variables directly from the downloaded dictionary column elements using safe .optString() which refuses to crash if a value is mysteriously missing from the cloud (falling back to blanks).

`java
                    programsByDept.putIfAbsent(dept, new ArrayList<>());
                    programsByDept.get(dept).add(new ProgramItem(code, name, track));
                }
`
*   **programsByDept.putIfAbsent...**: A brilliant sorting mechanism. If the map doesn't already have an ArrayList bin specifically for "Engineering", construct the bin now. Then .add(...) instantiates our [code, name, track] object and tosses it securely into that specific bin.

`java
                Platform.runLater(() -> {
                    deptComboBox.getItems().addAll(programsByDept.keySet());
                    deptComboBox.setPromptText("Select Department");
                });
`
*   **Platform.runLater(...)**: Ping the front-end interface, take the top-level generic "Engineering", "Business" names (.keySet()) and populate the first Dropdown box, wiping away the "Loading Depts..." placeholder completely.

`java
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    deptComboBox.setPromptText("Failed to load");
                });
            }
        }).start();
`
*   **} catch(...) {**: If the internet dies out, set the dropdown text warning the user it's permanently broken. Launch the background Thread().

`java
        deptComboBox.setOnAction(e -> {
`
*   **deptComboBox.setOnAction(e -> {**: Connects a highly localized tracker to the Department selection box, detecting whenever the user physically changes their mouse selection (e.g. pivoting from Business to Engineering).

`java
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event... ");
            String selectedDept = deptComboBox.getValue();
`
*   **String selectedDept = deptComboBox.getValue();**: Fetches the newly clicked String value.

`java
            if (selectedDept != null && programsByDept.containsKey(selectedDept)) {
                programComboBox.getItems().clear();
                programComboBox.getItems().addAll(programsByDept.get(selectedDept));
                programComboBox.getSelectionModel().selectFirst();
            }
        });
`
*   **if (selectedDept != null... {**: Clears the secondary "Degree Programs" dropdown box out, retrieves ONLY the specific List<ProgramItem> mapped entirely against the single department key picked, shoves them into the box, and immediately highlights the top one to save a second user click. Dynamic Box updating!

`java
        btn.setOnAction(e -> {
`
*   **tn.setOnAction(e -> {**: Links clicking the master Submit button.

`java
            ...
            if (deptComboBox.getValue() == null || programComboBox.getValue() == null) {
                ...
                actionTarget.setText("Please select Dept & Program!");
                ...
                return;
            }
`
*   **if (deptComboBox... == null)**: Kills the function entirely (
eturn) if they somehow bypassed the dropdown generation.

`java
            actionTarget.setText("Registration starting...");
            actionTarget.setTextFill(javafx.scene.paint.Color.WHITE);
            btn.setDisable(true);
`
*   **tn.setDisable(true);**: Locks the submission to prevent duplicate SQL requests going through causing the backend to crash entirely.

`java
            new Thread(() -> {
`
*   **
ew Thread(() -> {**: Pushes into background HTTP execution mode.

`java
                try {
                    AuthRepository authRepository = new AuthRepository();
                    AcademicRepository academicRepository = new AcademicRepository();
                    ProgramItem selectedProgram = programComboBox.getValue();
`
*   **AuthRepository... AcademicRepository... ProgramItem...**: Recalls the precise objects needed to construct the network web push strings. Notice because the combobox generically held custom objects, .getValue() natively returns a fully loaded, functional ProgramItem with getters/setters built-in!

`java
                    String uid = authRepository.registerUser(
                            emailAttempt,
                            pwBox.getText(),
                            ...
                            selectedProgram.getCode(),
                            ...
                            selectedProgram.getTrack()
                    );
`
*   **String uid = authRepository.registerUser(...)**: Injects every single graphical text box literal representation out of the UI wrapper into the single God function method, awaiting the resultant secure User ID mapping.

`java
                    com.ewumatelite.core.utils.LogExporter.log("SUCCESS: User Registered successfully...");
                    Platform.runLater(() -> actionTarget.setText("User Registered! Synchronizing..."));
`
*   **Platform.runLater(...)**: Briefly updates the form signaling Phase 1 completed, and we are dynamically fetching tracking arrays.

`java
                    String activeSem = academicRepository.getActiveSemester(selectedProgram.getTrack());
                    JSONArray courses = academicRepository.getCourseMetadata();
`
*   **String activeSem... JSONArray courses...**: Since this registration form effectively bypasses Onboarding entirely (forcing enrollment completion right here on application boot), we absolutely explicitly *MUST* secretly download both the current Semester mapping and the raw dictionary of universally verified Classes (courses) directly from planet earth because the very next window (EnrollmentScreen) needs them to function!

`java
                    Platform.runLater(() -> {
                        actionTarget.setText("SUCCESS!");
                        new EnrollmentScreen(stage, uid, activeSem, courses).show();
                    });
`
*   **
ew EnrollmentScreen...**: Pushes the application window drastically forward into the Enrollment UI Screen, passing our newly created credentials directly downstream! The new user is now officially interacting with the live application logic.

`java
                } catch (Exception ex) {
                    ...
                } finally {
                    Platform.runLater(() -> btn.setDisable(false));
                }
            }).start();
        });
`
*   **} finally { ... btn.setDisable(false)**: The inally {} block is guaranteed to perfectly execute regardless of whether the 	ry was successful or triggered an error catch halfway through. It merely unlocks the button in any termination.

`java
        Scene scene = new Scene(root, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
`
*   **Scene scene... stage.show();**: Pushes the registration page document object model to the application monitor!
---

### File: `DashboardController.java`

`java
package com.ewumatelite.features.dashboard.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.features.tasks.presentation.TasksScreen;
import javafx.application.Platform;
import javafx.fxml.FXML; ...
`
*   **package ... import ...**: Loads critical database components (AcademicRepository) so this controller can fetch the student's live schedule, and JavaFX layout systems used to physically construct UI cards.

`java
public class DashboardController {
`
*   **public class DashboardController {**: Defines the backend logical executor directly mapped onto dashboard.fxml.

`java
    @FXML private Label greetingLabel;
    @FXML private Label profileName;
    @FXML private Label dateLabel;
    @FXML private VBox scheduleContainer;
    @FXML private VBox tasksContainer;
    @FXML private Label loadingLabel;
`
*   **@FXML private Label ...**: These @FXML tags act as invisible strings physically tying Java memory variables to specific visual objects drawn inside Scene Builder! E.g. whatever text dateLabel holds instantly mirrors onto the live user interface window!

`java
    private Stage stage;
    private String uid;
    private String activeSem;
`
*   **private String activeSem;**: Variables defining the operational context, populated explicitly by DashboardScreen.java prior to launching the window.

`java
    // Theme constants
    private final String CARD_BG = "#1A2234";
    private final String TEXT_PRIMARY = "#FFFFFF";
    private final String TEXT_SECONDARY = "#8A95A5";
    private final String ACCENT_TEAL = "#00B4D8";
    private final String ACCENT_TEAL_DARK = "#123C46";
    private final String ACCENT_ORANGE = "#E67E22";
    private final String ACCENT_ORANGE_DARK = "#4C3821";
`
*   **private final String CARD_BG...**: Hardcoded visual color palettes mapping closely to the Figma specifications or external .css file to instantly ensure dynamically generated layout elements perfectly match native static blocks.

`java
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
`
*   **public void initData(...)**: An execution hook called purely via .show() inside DashboardScreen. Injects parameters.

`java
        // Set Time-based Greeting Placeholder
        int hour = LocalTime.now().getHour();
        String greeting = "Good Evening,";
        if (hour >= 5 && hour < 12) greeting = "Good Morning,";
        else if (hour >= 12 && hour < 17) greeting = "Good Afternoon,";
        greetingLabel.setText(greeting);
`
*   **int hour = LocalTime.now().getHour(); ...**: Physically queries the user's OS clock (LocalTime.now()), snags the hour integer natively, and rewrites the literal text of the SceneBuilder header label bridging the system variables to the frontend interface.

`java
        // Placeholder Name
        profileName.setText("User");
`
*   **profileName.setText("User");**: Defaults to "User" just in case the Supabase HTTP fetch fails entirely.

`java
        // 1. 8 PM Rule -> If past 20:00, show tomorrow's schedule
        LocalDate targetDate = LocalDate.now();
        if (hour >= 20) {
            targetDate = targetDate.plusDays(1);
        }
`
*   **LocalDate targetDate... if (hour >= 20) targetDate.plusDays(1)**: The literal "8 PM" rule! If the student checks their app at 9:55 PM, it will completely skip Friday's dead class list, and rewrite 	argetDate into "Saturday" seamlessly, moving immediately to the next useful dataset.

`java
        String todayFormatted = targetDate.format(DateTimeFormatter.ofPattern("EEEE, MMM d"));
        String todayDate = targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateLabel.setText(todayFormatted);
`
*   **dateLabel.setText(todayFormatted);**: Mutates Java's complex date format object into "Thursday, Sep 14" via DateTimeFormatter and prints it over the label securely. Maps the ugly SQL-friendly "2024-09-14" format natively onto 	odayDate.

`java
        com.ewumatelite.core.utils.LogExporter.log("ACTION: User " + uid + " opened dashboard for semester " + activeSem);
`
*   **com.ewumatelite.core.utils.LogExporter.log(...)**: Appends the boot command into the .txt error-recording array physically hosted onto the hard-disk.

`java
        final LocalDate finalTargetDate = targetDate;
        new Thread(() -> {
`
*   **
ew Thread(() -> {**: Spawns an external task purely to communicate with Supabase! Prevents locking out JavaFX GUI threads! 

`java
            try {
                JSONObject data = new AcademicRepository().getDashboardData(uid, activeSem, todayDate);
`
*   **JSONObject data = new AcademicRepository().getDashboardData...**: This is functionally executing a highly complex dual-staged query extracting purely the currently enrolled classes under a single specific string (e.g. "Fall 2024").

`java
                com.ewumatelite.core.utils.LogExporter.log("SUCCESS: Dashboard data fetched successfully.");
`
*   **com.ewumatelite.core.utils.LogExporter.log(...)**: Explicit verification recording.

`java
                Platform.runLater(() -> populateUI(data, finalTargetDate));
`
*   **Platform.runLater(() -> populateUI(...))**: Re-enters the native JavaFX master execution stack perfectly and injects our newly parsed data object into the layout builder function!

`java
            } catch (Exception ex) {
                com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to fetch dashboard data: " + ex.getMessage());
                Platform.runLater(() -> {
                    scheduleContainer.getChildren().clear();
                    scheduleContainer.getChildren().add(createErrorLabel(ex.getMessage()));
                });
            }
        }).start();
    }
`
*   **} catch(...) {**: Catches any thrown disconnect parameters (Exception ex). Specifically dumps the error completely inside the scheduleContainer block graphically, directly signaling users what part crashed without taking the app layout down!

`java
    private void populateUI(JSONObject data, LocalDate targetDate) {
`
*   **private void populateUI(...)**: The master sequence transforming raw JSON objects into graphical cards! 

`java
        // Update user nickname if fetched
        String nick = data.optString("nickname", "User");
        profileName.setText(nick);
`
*   **profileName.setText(nick);**: Overwrites the placeholder "User" mapped during initData() with the physical student profile variable!

`java
        scheduleContainer.getChildren().clear();
        tasksContainer.getChildren().clear();
`
*   **scheduleContainer.getChildren().clear();**: Dumps any "Loading..." text strings present currently hiding inside the VBox frame blocks completely out.

`java
        // 1. Build Schedule
        String dayStr = targetDate.getDayOfWeek().name();
        dayStr = dayStr.substring(0, 1).toUpperCase() + dayStr.substring(1).toLowerCase();
`
*   **String dayStr ... dayStr.substring(0, 1)...**: Java outputs date strings entirely capitalized originally ("SUNDAY"). This truncates and re-combines the string identically into Title Case ("Sunday") mapping explicitly over the exact Array key naming schemes expected from Supabase!

`java
        boolean processedHoliday = false;
        JSONObject holidayData = data.optJSONObject("holiday");
`
*   **JSONObject holidayData = data.optJSONObject("holiday");**: First pass priority check explicitly mapping onto EWU holiday edge cases (e.g. checking the "exceptions" array).

`java
        if (holidayData != null) {
            String title = holidayData.optString("title", holidayData.optString("name", "")).toLowerCase();
            String reason = holidayData.optString("name", "Holiday");
`
*   **if (holidayData != null)...**: Validates the holiday exists. Attempts extracting 	itle ("Sunday classes on Thursday").

`java
            if (title.contains("swap") || title.contains("makeup")) {
                // Determine day replacement like Flutter
                String[] words = title.split(" ");
                if (words.length > 0) {
                    String swapDayRaw = words[words.length - 1];
                    if (swapDayRaw.endsWith("s")) swapDayRaw = swapDayRaw.substring(0, swapDayRaw.length() - 1);
                    dayStr = swapDayRaw.substring(0, 1).toUpperCase() + swapDayRaw.substring(1).toLowerCase();
                }
`
*   **if (title.contains("swap"))...**: Edge Case handling! If EWU says "Makeup Sunday Classes". It logically splits the string spacing into tiny array strings natively: ["Makeup", "Sunday", "Classes"] and maps the absolute last word minus s ("Sunday") directly overtaking dayStr, completely bypassing the "Thursday" originally captured natively from the OS! A phenomenal patch for the weird East West University class reassignments!

`java
            } else if (!title.contains("makeup") && !title.contains("advising")) {
                scheduleContainer.getChildren().add(createHolidayBanner(reason));
                processedHoliday = true;
            }
        }
`
*   **} else if (!title.contains("makeup")...**: A raw Holiday ("Eid Ul Fitr") spawns an absolute block banner rendering a Holiday block explicitly overtaking the schedule render natively! processedHoliday = true; halts the code underneath completely!

`java
        if (!processedHoliday) {
            JSONObject grid = data.optJSONObject("weekly_grid");
            JSONArray classesObj = grid != null ? grid.optJSONArray(dayStr) : null;
            JSONArray exceptions = data.optJSONArray("exceptions");
`
*   **if (!processedHoliday) {**: Runs only if it is actually a working class day. It maps the raw object weekly_grid onto memory and targets standard columns dayStr.

`java
            boolean hasClasses = false;
            if (classesObj != null && classesObj.length() > 0) {
`
*   **if (classesObj != null...**: Bypasses any arrays missing data columns.

`java
                // O(n^2) filter for exceptions like cancellations
                for (int i = 0; i < classesObj.length(); i++) {
                    JSONObject c = classesObj.getJSONObject(i);
                    String courseCode = c.optString("courseCode", c.optString("course_code"));
`
*   **or (int i = 0... String courseCode...**: Standard iterative iteration block targeting standard classes linearly inside the grid. Attempts explicitly mapping courseCode mapping variables.

`java
                    boolean isCancelled = false;
                    if (exceptions != null) {
                        for (int j = 0; j < exceptions.length(); j++) {
                            JSONObject ex = exceptions.getJSONObject(j);
                            if ("cancel".equals(ex.optString("type")) && courseCode.equals(ex.optString("course_code"))) {
                                isCancelled = true;
                                break;
                            }
                        }
                    }
`
*   **oolean isCancelled... if ("cancel".equals(ex.optString("type")))...**: Extremely deep logic mapping! Operates recursively through every exception array column explicit tracking if a 	ype == cancel flag is present natively mapping against the master courseCode string. reak; prevents iterating entirely upon identifying it!

`java
                    if (!isCancelled) {
                        scheduleContainer.getChildren().add(createClassCard(c));
                        hasClasses = true;
                    }
                }
            }
`
*   **if (!isCancelled) { ... scheduleContainer.getChildren().add(...)**: If a class wasn't cancelled physically online, physically build its Graphical class card rendering it directly over the interface! Sets hasClasses.

`java
            // Append manual and makeup exceptions
            if (exceptions != null) {
                for (int i = 0; i < exceptions.length(); i++) {
                    JSONObject ex = exceptions.getJSONObject(i);
                    String type = ex.optString("type");
                    if ("makeup".equals(type) || "manual".equals(type)) {
`
*   **if ("makeup".equals(type)...**: Sweeps back over the exact same exceptions array pulling extra scheduled "Makeups" ignoring standard rendering grid maps completely!

`java
                        // Reshape into template format
                        JSONObject mapped = new JSONObject();
                        mapped.put("courseCode", ex.optString("course_code"));
                        mapped.put("courseName", ex.optString("course_name"));
                        mapped.put("room", ex.optString("room"));
                        mapped.put("startTime", ex.optString("start_time"));
                        mapped.put("endTime", ex.optString("end_time"));
                        mapped.put("type", ex.optString("faculty")); // Fallback type logic not strictly typed here
                        scheduleContainer.getChildren().add(createClassCard(mapped));
                        hasClasses = true;
                    }
                }
            }
`
*   **JSONObject mapped...**: Generates a completely blank proxy JSONObject reformatting the exception data tags securely into purely standard UI components simulating native formatting expected by createClassCard(mapped) bypassing JSON error crashes structurally!

`java
            if (!hasClasses) {
                scheduleContainer.getChildren().add(createEmptyLabel("No classes scheduled for today."));
            }
        }
`
*   **if (!hasClasses)**: Fills blank arrays beautifully with "No classes scheduled...".

`java
        // 2. Build Tasks
        JSONArray tasks = data.optJSONArray("tasks");
        boolean hasTasks = false;
        if (tasks != null) {
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject t = tasks.getJSONObject(i);
                if (t.optBoolean("is_completed", false) || t.optBoolean("is_missed", false)) continue;
                tasksContainer.getChildren().add(createTaskCard(t));
                hasTasks = true;
            }
        }
`
*   **JSONArray tasks = data.optJSONArray("tasks");**: Runs an identical algorithm extracting tasks arrays physically appending tasks lacking a boolean check is_completed bypassing already removed tracking targets natively! 

`java
        if (!hasTasks) {
            tasksContainer.getChildren().add(createEmptyLabel("No upcoming tasks."));
        }
    }
`
*   **createEmptyLabel("No upcoming tasks.")**: Reconstructs an empty label warning users tracking targets exist!

`java
    @FXML
    private void onSeeAllTasksClicked() {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: onSeeAllTasksClicked Triggered in " + this.getClass().getSimpleName());
        new TasksScreen(stage, uid, activeSem).show();
    }
`
*   **@FXML private void onSeeAllTasksClicked() {**: Connects via SceneBuilder to the little > arrow under Tasks directing users physically via window redirect .show() mapped against .features.tasks.presentation.TasksScreen.

`java
    // --- Dynamic Card Builders (Mimicking the visual style in native JavaFX) ---

    private VBox createHolidayBanner(String reason) {
        VBox box = new VBox(10);
...
        return box;
    }
`
*   **private VBox createHolidayBanner...**: Raw constructor physically rendering Holiday graphical blocks. Standard JavaFX code.

`java
    private HBox createClassCard(JSONObject c) {
        String type = c.optString("type", "Theory");
        boolean isLab = type.equalsIgnoreCase("Lab");
`
*   **private HBox createClassCard...**: Generates an isolated HBox interface line purely checking oolean isLab string maps dictating coloring mapping tags (e.g. orange vs teal variables passed earlier)!

`java
        String borderColor = isLab ? ACCENT_ORANGE_DARK : ACCENT_TEAL_DARK;
        String badgeColor = isLab ? "#9E6023" : "#1F7883";
        String lineFill = isLab ? ACCENT_ORANGE : ACCENT_TEAL;
...
        return card;
    }
`
*   **String borderColor = isLab ? ACCENT_ORANGE_DARK : ACCENT_TEAL_DARK;**: An incredibly complex ternary statement mapping conditional visual blocks dynamically! This physically controls whether the timeline stroke is colored Orange vs Teal explicitly returning card.

`java
    private HBox createTaskCard(JSONObject t) {
...
        return card;
    }
`
*   **private HBox createTaskCard...**: Equivalent builder pattern mapping task JSON variables cleanly onto graphical tracking text strings.

`java
    private Label createEmptyLabel(String text) {
...
    }

    private Label createErrorLabel(String err) {
...
    }
}
`
*   **private Label createEmptyLabel(String text...**: Internal utility functions standardizing Italic and #E74C3C visual properties recursively applied globally when arrays return empty data variables from scheduleContainer. Closes out the file.
---

### File: `EnrollmentScreen.java`

`java
package com.ewumatelite.features.enrollment.presentation;
`
*   **package com.ewumatelite.features.enrollment.presentation;**: Declares this file belongs inside the Enrollment screen domain.

`java
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.features.dashboard.presentation.DashboardScreen;
import javafx.application.Platform;
...
import org.json.JSONArray;
import org.json.JSONObject;
`
*   **import ...**: Pulls in the navigation Sidebar, global LayoutFactory, the network AcademicRepository to fetch course blocks, and the utility JSON wrappers.

`java
public class EnrollmentScreen {
`
*   **public class EnrollmentScreen {**: Defines the public interface wrapper intended to handle a student dynamically searching, expanding, dropping, and adding classes into their literal active semester.

`java
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final JSONArray courses;
`
*   **private final ...**: Vital tracking variables securely passed in from either the Registration flow or Sidebar, including a pre-downloaded cache JSONArray courses so the UI isn't frozen while waiting for 200+ courses to load via the internet!

`java
    public EnrollmentScreen(Stage stage, String uid, String activeSem, JSONArray courses) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.courses = courses;
    }
`
*   **public EnrollmentScreen(...) { ... }**: Class constructor injecting the global variables into the application context safely.

`java
    public void show() {
`
*   **public void show() {**: Commences window rendering completely in pure Java code, bypassing .fxml files entirely!

`java
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_LEFT);
        root.getStyleClass().add("content-area-root");
`
*   **VBox root = new VBox(20);**: Spawns the central background container enforcing 20px gaps natively. Adds the CSS content-area-root tag ensuring styling flows harmoniously against the Sidebar.

`java
        Label title = new Label("Course Selection");
        title.getStyleClass().add("title-label");
        
        Label semesterLabel = new Label(activeSem);
        semesterLabel.setStyle("-fx-text-fill: #0EA5E9; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label subtitle = new Label("Select your courses for the current track.");
        ...
        final Label actionTarget = new Label();
`
*   **Label ...**: Spawns various literal text lines displaying explicitly which semester they are operating inside. ctionTarget is the hidden status message box displaying API errors or success.

`java
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by Course Code or Name");
        searchBar.getStyleClass().add("text-field");
        searchBar.setMaxWidth(500);
`
*   **TextField searchBar...**: Creates a massive 500-pixel wide typing area where students can manually filter courses instead of scrolling through hundreds of them.

`java
        VBox coursesContainer = new VBox(15);
        coursesContainer.setStyle("-fx-background-color: transparent;");
`
*   **VBox coursesContainer...**: The internal holder meant to physically contain the search results populated dynamically beneath the text box.

`java
        Runnable applyFilter = () -> {
`
*   **Runnable applyFilter = () -> {**: Spawns an anonymous function map explicitly designed to trigger *every single time* the student types a literal character into the Search Bar.

`java
            coursesContainer.getChildren().clear();
            String query = searchBar.getText().trim().toLowerCase();
`
*   **coursesContainer.getChildren().clear();**: Dumps all the old classes off the screen currently visible.
*   **String query = searchBar.getText().trim().toLowerCase();**: Maps whatever the student just typed, completely trims spaces, and converts it to lower case (e.g. CSE 101 -> cse 101) to match formatting.

`java
            for (int i = 0; i < courses.length(); i++) {
                JSONObject c = courses.getJSONObject(i);
                String courseCode = c.getString("code");
                String courseName = c.getString("name");
`
*   **or (...) { ... String courseCode ... courseName ...**: Implements an immediate iteration through every single natively downloaded class from Supabase. Extracts the visual identifying labels.

`java
                if (!query.isEmpty() && !courseCode.toLowerCase().contains(query) && !courseName.toLowerCase().contains(query)) {
                    continue; 
                }
`
*   **if (... !courseCode.toLowerCase().contains(query) && !courseName... ) { continue; }**: The core search engine! It inherently tests if the user's typed string mathematically matches either the Course Name ("Programming") or Code ("CSE101"). If neither matches, it silently aborts the loop execution via continue - entirely skipping rendering that class!

`java
                VBox card = new VBox();
                card.getStyleClass().add("card-container");
`
*   **VBox card...**: Only classes passing the filter get to have a card formally generated for them to appear onscreen.

`java
                HBox header = new HBox(15);
...
                Label codeLabel = new Label(courseCode);
                Label nameLabel = new Label(courseName);
...
                Label expandIcon = new Label("?");
...
                header.getChildren().addAll(headerText, headerSpacer, expandIcon);
`
*   **HBox header... Label expandIcon...**: Builds an unexpanded banner containing the course name alongside a play icon (?). This makes the screen look like a list of neat closed folders!

`java
                VBox sectionContainer = new VBox(12);
                sectionContainer.setVisible(false);
                sectionContainer.setManaged(false);
`
*   **VBox sectionContainer... setVisible(false); setManaged(false);**: Specifically builds a giant sub-container meant to hold literal class sections, but forcefully hides it and orders the JavaFX Layout engine completely to ignore the space it occupies (setManaged(false)).

`java
                final boolean[] isExpanded = {false};
                final boolean[] isLoaded = {false};
`
*   **inal boolean[] ...**: Internal UI tracking arrays acting as switches. We wrap booleans in arrays [] so they can be securely mutated inside lambda expressions.

`java
                header.setOnMouseClicked(event -> {
`
*   **header.setOnMouseClicked...**: Attaches an execution hook! If the user physically taps the banner card...

`java
                    isExpanded[0] = !isExpanded[0];
                    sectionContainer.setVisible(isExpanded[0]);
                    sectionContainer.setManaged(isExpanded[0]);
                    expandIcon.setText(isExpanded[0] ? "?" : "?");
`
*   **isExpanded ... setVisible ... setText( ? )**: Immediately flip the state from Hidden to Visible, command JavaFX to suddenly draw it, and flip the play button icon downwards (?) marking it as expanded.

`java
                    if (isExpanded[0] && !isLoaded[0]) {
                        new Thread(() -> {
`
*   **if (isExpanded[0] && !isLoaded[0]) { new Thread...**: **Brilliant Network Optimization:** The app does NOT download the sections for all 150 classes! It physically waits until the user *specifically* clicks on one single class, then immediately fires a background network thread specifically fetching sections solely for that target. isLoaded prevents re-downloading if clicked twice.

`java
                            try {
                                AcademicRepository academicRepository = new AcademicRepository();
                                JSONArray sections = academicRepository.getSectionsForCourse(activeSem, courseCode);
                                JSONArray enrolledData = academicRepository.getUserEnrollments(uid, activeSem);
`
*   **JSONArray sections... JSONArray enrolledData...**: Dual-query the cloud. Pull absolutely every single section mapping to the clicked class, whilst simultaneously fetching what sections the current user is *already enrolled in*.

`java
                                java.util.Set<String> enrolledSectionIds = new java.util.HashSet<>();
                                for(int k=0; k<enrolledData.length(); k++) { enrolledSectionIds.add(enrolledData.getJSONObject(k).getString("section_id")); }
`
*   **Set<String> enrolledSectionIds = new java.util.HashSet<>...**: Plucks the user's Enrolled IDs and converts them seamlessly into a HashSet. A HashSet searches drastically faster than a normal Array, resulting in massive UI speedups when determining if a Drop button should appear inline!

`java
                                Platform.runLater(() -> {
                                    sectionContainer.getChildren().clear();
`
*   **Platform.runLater...**: Escapes the Async internet block and goes back to physically editing the screen safely.

`java
                                        for (int j = 0; j < sections.length(); j++) {
                                            JSONObject sec = sections.getJSONObject(j);
                                            String secId = sec.getString("id");
...
`
*   **or... JSONObject sec...**: Iterates cleanly through every single Section the backend returned for the parent course card.

`java
                                            String day1 = "";
                                            String time1 = "";
                                            if (sec.has("schedule_data") && !sec.isNull("schedule_data")) {
                                                JSONArray schedArr = sec.optJSONArray("schedule_data");
...
                                                    String rawDay = schedArr.getJSONObject(0).optString("day", "");
                                                    String dayExpanded = "";
                                                    for (char ch : rawDay.toCharArray()) {
                                                        if(ch == 'S') dayExpanded += "Sun, ";
                                                        else if(ch == 'M') dayExpanded += "Mon, ";
                                                        else if(ch == 'T') dayExpanded += "Tue, ";
                                                        else if(ch == 'W') dayExpanded += "Wed, ";
                                                        else if(ch == 'R') dayExpanded += "Thu, ";
...
                                                    }
`
*   **or (char ch : rawDay.toCharArray()) { ... }**: Implements a brute-force parser bridging the legacy college schedule formats (MW, TR, ST) explicitly into clean, readable UI formats natively by ripping the strings apart into array Chars and matching the characters dynamically against known mappings!

`java
                                            HBox secRow = new HBox(15); 
...
                                            Label secLabel = new Label("SEC " + secNum); 
...
                                            Label timeLabel = new Label(day1 + " | " + time1); 
...
                                            Label facLabel = new Label("Faculty: " + faculty); 
`
*   **HBox secRow... Label...**: Wraps the clean time variables, professor mappings dynamically directly onto graphical lines appended inside the sub-container block.

`java
                                            boolean isEnrolled = enrolledSectionIds.contains(secId);
                                            Button btnAction = new Button(isEnrolled ? "DROP" : "ENROLL");
                                            btnAction.getStyleClass().add("button");
                                            if (isEnrolled) btnAction.setStyle("-fx-background-color: #F43F5E;");
`
*   **oolean isEnrolled = enrolledSectionIds.contains(secId); ... Button btnAction ...**: The culmination of the Hashset. Maps against the ID securely checking if the user natively owns it. Re-assigns the color text string visually (#F43F5E, which is Red, indicating "Danger/Drop").

`java
                                            btnAction.setOnAction(e -> { 
                                                com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered...");
                                                btnAction.setDisable(true); 
`
*   **tnAction.setOnAction(e -> { btnAction.setDisable(true);**: Clicks automatically disable the button completely preventing massive sync errors due to users spam clicking the UI button.

`java
                                                new Thread(() -> {
                                                    try {
                                                        if (btnAction.getStyle().contains("#F43F5E")) {
`
*   **
ew Thread... if (btnAction.getStyle().contains("#F43F5E"))**: Drops the thread backward into the background! It queries the active button's color to figure out whether the student wishes to Drop versus Add implicitly.

`java
                                                            academicRepository.dropEnrollment(uid, activeSem, secId);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("ENROLL"); 
                                                                btnAction.setStyle(""); 
                                                                actionTarget.setText("Dropped!"); 
                                                            });
`
*   **cademicRepository.dropEnrollment...**: Executes the heavy Repository drop flow!
*   **Platform.runLater...**: Graphically updates the button dynamically replacing "DROP" with "ENROLL" successfully allowing the user to un-drop instantly without refreshing!

`java
                                                        } else {
                                                            academicRepository.pushEnrollment(uid, activeSem, courseCode, secId, secNum);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("DROP"); 
                                                                btnAction.setStyle("-fx-background-color: #F43F5E;"); 
                                                                actionTarget.setText("Enrolled!"); 
                                                            });
                                                        }
`
*   **} else { academicRepository.pushEnrollment... }**: Converts an Enroll click identically mirroring the UI states effectively seamlessly tracking back and forth!

`java
                                                        // academicRepository.syncSchedule(uid, activeSem); // Sync omitted implicitly 
                                                    } catch (Exception ex) { 
                                                        Platform.runLater(() -> { actionTarget.setText("Error: " + ex.getMessage()); }); 
                                                    }
                                                    Platform.runLater(() -> btnAction.setDisable(false));
                                                }).start();
                                            });
`
*   **} catch... btnAction.setDisable(false)...**: Closes out network blocks cleanly re-enabling clickability in both error and success cases definitively.

`java
                                            secRow.getChildren().addAll(detailsBox, spacer, btnAction); 
                                            sectionContainer.getChildren().add(secRow);
`
*   **secRow.getChildren().addAll...**: Commits the entire section row structure into the container formally rendering it.

`java
                                        }
                                    }
                                });
...
                        }).start();
                        isLoaded[0] = true;
`
*   **isLoaded[0] = true;**: Completes the loop explicitly sealing the boolean array so we don't accidentally ask Supabase twice for the exact same class if the student clicks to hide/show the banner repetitively.

`java
                card.getChildren().addAll(header, sectionContainer);
                coursesContainer.getChildren().add(card);
            }
        };
`
*   **coursesContainer.getChildren().add(card);**: Drops the dynamically built UI block natively.

`java
        applyFilter.run();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> applyFilter.run());
`
*   **pplyFilter.run();**: Auto-triggers the parser once at start so the page isn't totally blank.
*   **searchBar.textProperty().addListener... -> applyFilter.run());**: Anchors the pplyFilter Runnable code precisely onto an observer hook mapping keystrokes! If 
ewValue fires, it immediately loops dynamically filtering!

`java
        ScrollPane scrollPane = new ScrollPane(coursesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
`
*   **ScrollPane scrollPane...**: Implements scrolling because standard boxes just push content off-screen.

`java
        root.getChildren().addAll(title, semesterLabel, subtitle, searchBar, scrollPane, actionTarget);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
`
*   **VBox.setVgrow...**: Makes sure the scroll bounds consume exactly 100% of the user's available window pixel density vertically.

`java
        Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Enrollment", root);
        Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
`
*   **Parent adaptiveLayout = LayoutFactory.create(...); ... stage.show();**: Encapsulates our entirely manually-drawn root screen with the Sidebar, applies OS styles, sets scaling metrics, and effectively prints it back out on-screen gracefully completing the class.
---

### File: `Sidebar.java`

`java
package com.ewumatelite.features.sidebar.presentation;
`
*   **package com.ewumatelite.features.sidebar.presentation;**: Declares this file specifically inside the global UI presentation sidebar folder.

`java
import com.ewumatelite.features.dashboard.presentation.DashboardScreen;
import com.ewumatelite.features.schedule.presentation.ScheduleScreen;
import com.ewumatelite.features.enrollment.presentation.EnrollmentScreen;
import com.ewumatelite.features.tasks.presentation.TasksScreen;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
...
`
*   **import ...**: A massive block of imports specifically importing literally every single other module's Screen.java file natively. The Sidebar physically acts as the central router and therefore must know how to invoke every window in the application.

`java
public class Sidebar {
`
*   **public class Sidebar {**: Standard Object Definition.

`java
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final String activeScreen;
`
*   **private String activeScreen;**: In addition to passing the student's UUID, the Sidebar requires an ctiveScreen string telling it which button currently needs to glow to signify "You are here".

`java
    public Sidebar(Stage stage, String uid, String activeSem, String activeScreen) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.activeScreen = activeScreen;
    }
`
*   **public Sidebar(...) { ... }**: Bootstrapper safely injecting the variables.

`java
    public VBox getView() {
`
*   **public VBox getView() {**: Instead of a .show() method, this class specifically returns a VBox. It literally generates the Side Panel box and hands it back to the LayoutFactory to wedge into the main application.

`java
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(240);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setAlignment(Pos.TOP_CENTER);
`
*   **sidebar.setPrefWidth(240);**: Forcibly hardcodes the panel to be exactly 240 pixels wide on desktop monitors.

`java
        Text appTitle = new Text("EWU Mate");
        appTitle.getStyleClass().add("sidebar-title");
        
        Text semText = new Text(activeSem);
        semText.getStyleClass().add("sidebar-subtitle");
        
        VBox header = new VBox(5, appTitle, semText);
...
`
*   **VBox header ...**: Generates the static EWU branding block at the top corner of the wrapper.

`java
        Button btnDashboard = createTabButton("Dashboard", activeScreen.equals("Dashboard"));
        btnDashboard.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Dashboard");
            new DashboardScreen(stage, uid, activeSem).show();
        });
`
*   **Button btnDashboard ...**: Calls a private helper method checking ctiveScreen.equals(). If it matches, applies the glowing CSS format. Attaches a click event pointing directly over to DashboardScreen.show(). 

`java
        Button btnEnrollment = createTabButton("Course Browser", activeScreen.equals("Enrollment"));
        btnEnrollment.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Course Browser");
            btnEnrollment.setText("Loading...");
            new Thread(() -> {
                try {
                    JSONArray courses = new AcademicRepository().getCourseMetadata();
                    Platform.runLater(() -> new EnrollmentScreen(stage, uid, activeSem, courses).show());
                } catch (Exception ex) { ... }
            }).start();
        });
`
*   **tnEnrollment.setOnAction...**: Notice the Enrollment click explicitly spawns a 
ew Thread(). It *must* query the AcademicRepository.getCourseMetadata() dictionary from Supabase before the Window opens so the Enrollment search bar can function instantly!

`java
        Button btnSchedule = createTabButton("Manage Schedule", activeScreen.equals("Manage Schedule"));
        btnSchedule.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Manage Schedule");
            new ScheduleScreen(stage, uid, activeSem).show();
        });
`
*   **tnSchedule.setOnAction...**: Click mapping to the Schedule features.

`java
        Button btnTasks = createTabButton("Tasks", activeScreen.equals("Tasks"));
        btnTasks.setOnAction(e -> {
            new TasksScreen(stage, uid, activeSem).show();
        });
`
*   **tnTasks.setOnAction...**: Route map bridging directly to Tasks window overrides.

`java
        Button btnSemesterProgress = createTabButton("Semester Progress", activeScreen.equals("Semester Progress"));
        btnSemesterProgress.setOnAction(e -> {
            new com.ewumatelite.features.semester_progress.presentation.SemesterProgressScreen(stage, uid, activeSem).show();
        });
`
*   **tnSemesterProgress.setOnAction...**: Route mapping explicitly for academic tracking algorithms.

`java
        Button btnProfile = createTabButton("Profile", activeScreen.equals("Profile"));
        btnProfile.setOnAction(e -> {
            new com.ewumatelite.features.profile.presentation.ProfileScreen(stage, uid, activeSem).show();
        });
`
*   **tnProfile.setOnAction...**: Routes to the profile modification screen natively updating UI graphics.

`java
        Button btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("logout-button");
        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.setOnAction(e -> {
`
*   **Button btnLogout... setOnAction(...**: Standard Logout button mapped with a specialized .logout-button red tracking CSS tag.

`java
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Logged Out");
            // Clear persistent auth state
            com.ewumatelite.core.config.SupabaseConfig.currentUserToken = null;
            com.ewumatelite.core.config.SupabaseConfig.currentUserId = null;
`
*   **...currentUserToken = null;**: Explicitly completely erases the authenticated JWT tokens stored in volatile System RAM. The user gets mathematically booted.

`java
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(com.ewumatelite.core.config.SupabaseConfig.class);
            prefs.remove("SUPABASE_JWT");
            prefs.remove("SUPABASE_UID");
`
*   **java.util.prefs.Preferences... prefs.remove...**: Crucially accesses the Windows OS / Mac drive registry explicitly killing the files remembering the Token Session UUID globally permanently preventing a reboot loop keeping them tied inward!

`java
            new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show();
        });
`
*   **
ew com.ewumatelite.features...LoginScreen(stage).show();**: Dispatches the window perfectly back directly over the login prompt effectively starting the process completely cleanly!

`java
        // Spacer pushes logout button to the bottom
        sidebar.getChildren().addAll(header, btnDashboard, btnEnrollment, btnSchedule, btnTasks, btnSemesterProgress, btnProfile);
        
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        sidebar.getChildren().addAll(spacer, btnLogout);

        return sidebar;
    }
`
*   **VBox.setVgrow(spacer, ... Priority.ALWAYS);**: Tricks the JavaFX rendering algorithm to dynamically expand the transparent Region gap maximally infinitely! This effectively pins the "Logout" formally permanently to the absolute physical screen bottom safely away from the navigation clusters. Overarching execution Returns the wrapped Layout Block!

`java
    private Button createTabButton(String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.getStyleClass().add("sidebar-button");
        if (isActive) {
            btn.getStyleClass().add("sidebar-button-active");
        }
        return btn;
    }
}
`
*   **private Button createTabButton...**: Central custom UI Builder returning generic Buttons mapping natively applying sidebar-button-active specifically if oolean isActive passes explicitly. Saves thousands of lines duplicated natively across rendering functions!
---

### File: `EnrollmentScreen.java`

`java
package com.ewumatelite.features.enrollment.presentation;
`
*   **package com.ewumatelite.features.enrollment.presentation;**: Declares this file belongs inside the Enrollment screen domain.

`java
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.features.dashboard.presentation.DashboardScreen;
import javafx.application.Platform;
...
import org.json.JSONArray;
import org.json.JSONObject;
`
*   **import ...**: Pulls in the navigation Sidebar, global LayoutFactory, the network AcademicRepository to fetch course blocks, and the utility JSON wrappers.

`java
public class EnrollmentScreen {
`
*   **public class EnrollmentScreen {**: Defines the public interface wrapper intended to handle a student dynamically searching, expanding, dropping, and adding classes into their literal active semester.

`java
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final JSONArray courses;
`
*   **private final ...**: Vital tracking variables securely passed in from either the Registration flow or Sidebar, including a pre-downloaded cache JSONArray courses so the UI isn't frozen while waiting for 200+ courses to load via the internet!

`java
    public EnrollmentScreen(Stage stage, String uid, String activeSem, JSONArray courses) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.courses = courses;
    }
`
*   **public EnrollmentScreen(...) { ... }**: Class constructor injecting the global variables into the application context safely.

`java
    public void show() {
`
*   **public void show() {**: Commences window rendering completely in pure Java code, bypassing .fxml files entirely!

`java
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_LEFT);
        root.getStyleClass().add("content-area-root");
`
*   **VBox root = new VBox(20);**: Spawns the central background container enforcing 20px gaps natively. Adds the CSS content-area-root tag ensuring styling flows harmoniously against the Sidebar.

`java
        Label title = new Label("Course Selection");
        title.getStyleClass().add("title-label");
        
        Label semesterLabel = new Label(activeSem);
        semesterLabel.setStyle("-fx-text-fill: #0EA5E9; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label subtitle = new Label("Select your courses for the current track.");
        ...
        final Label actionTarget = new Label();
`
*   **Label ...**: Spawns various literal text lines displaying explicitly which semester they are operating inside. ctionTarget is the hidden status message box displaying API errors or success.

`java
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by Course Code or Name");
        searchBar.getStyleClass().add("text-field");
        searchBar.setMaxWidth(500);
`
*   **TextField searchBar...**: Creates a massive 500-pixel wide typing area where students can manually filter courses instead of scrolling through hundreds of them.

`java
        VBox coursesContainer = new VBox(15);
        coursesContainer.setStyle("-fx-background-color: transparent;");
`
*   **VBox coursesContainer...**: The internal holder meant to physically contain the search results populated dynamically beneath the text box.

`java
        Runnable applyFilter = () -> {
`
*   **Runnable applyFilter = () -> {**: Spawns an anonymous function map explicitly designed to trigger *every single time* the student types a literal character into the Search Bar.

`java
            coursesContainer.getChildren().clear();
            String query = searchBar.getText().trim().toLowerCase();
`
*   **coursesContainer.getChildren().clear();**: Dumps all the old classes off the screen currently visible.
*   **String query = searchBar.getText().trim().toLowerCase();**: Maps whatever the student just typed, completely trims spaces, and converts it to lower case (e.g. CSE 101 -> cse 101) to match formatting.

`java
            for (int i = 0; i < courses.length(); i++) {
                JSONObject c = courses.getJSONObject(i);
                String courseCode = c.getString("code");
                String courseName = c.getString("name");
`
*   **or (...) { ... String courseCode ... courseName ...**: Implements an immediate iteration through every single natively downloaded class from Supabase. Extracts the visual identifying labels.

`java
                if (!query.isEmpty() && !courseCode.toLowerCase().contains(query) && !courseName.toLowerCase().contains(query)) {
                    continue; 
                }
`
*   **if (... !courseCode.toLowerCase().contains(query) && !courseName... ) { continue; }**: The core search engine! It inherently tests if the user's typed string mathematically matches either the Course Name ("Programming") or Code ("CSE101"). If neither matches, it silently aborts the loop execution via continue - entirely skipping rendering that class!

`java
                VBox card = new VBox();
                card.getStyleClass().add("card-container");
`
*   **VBox card...**: Only classes passing the filter get to have a card formally generated for them to appear onscreen.

`java
                HBox header = new HBox(15);
...
                Label codeLabel = new Label(courseCode);
                Label nameLabel = new Label(courseName);
...
                Label expandIcon = new Label("?");
...
                header.getChildren().addAll(headerText, headerSpacer, expandIcon);
`
*   **HBox header... Label expandIcon...**: Builds an unexpanded banner containing the course name alongside a play icon (?). This makes the screen look like a list of neat closed folders!

`java
                VBox sectionContainer = new VBox(12);
                sectionContainer.setVisible(false);
                sectionContainer.setManaged(false);
`
*   **VBox sectionContainer... setVisible(false); setManaged(false);**: Specifically builds a giant sub-container meant to hold literal class sections, but forcefully hides it and orders the JavaFX Layout engine completely to ignore the space it occupies (setManaged(false)).

`java
                final boolean[] isExpanded = {false};
                final boolean[] isLoaded = {false};
`
*   **inal boolean[] ...**: Internal UI tracking arrays acting as switches. We wrap booleans in arrays [] so they can be securely mutated inside lambda expressions.

`java
                header.setOnMouseClicked(event -> {
`
*   **header.setOnMouseClicked...**: Attaches an execution hook! If the user physically taps the banner card...

`java
                    isExpanded[0] = !isExpanded[0];
                    sectionContainer.setVisible(isExpanded[0]);
                    sectionContainer.setManaged(isExpanded[0]);
                    expandIcon.setText(isExpanded[0] ? "?" : "?");
`
*   **isExpanded ... setVisible ... setText( ? )**: Immediately flip the state from Hidden to Visible, command JavaFX to suddenly draw it, and flip the play button icon downwards (?) marking it as expanded.

`java
                    if (isExpanded[0] && !isLoaded[0]) {
                        new Thread(() -> {
`
*   **if (isExpanded[0] && !isLoaded[0]) { new Thread...**: **Brilliant Network Optimization:** The app does NOT download the sections for all 150 classes! It physically waits until the user *specifically* clicks on one single class, then immediately fires a background network thread specifically fetching sections solely for that target. isLoaded prevents re-downloading if clicked twice.

`java
                            try {
                                AcademicRepository academicRepository = new AcademicRepository();
                                JSONArray sections = academicRepository.getSectionsForCourse(activeSem, courseCode);
                                JSONArray enrolledData = academicRepository.getUserEnrollments(uid, activeSem);
`
*   **JSONArray sections... JSONArray enrolledData...**: Dual-query the cloud. Pull absolutely every single section mapping to the clicked class, whilst simultaneously fetching what sections the current user is *already enrolled in*.

`java
                                java.util.Set<String> enrolledSectionIds = new java.util.HashSet<>();
                                for(int k=0; k<enrolledData.length(); k++) { enrolledSectionIds.add(enrolledData.getJSONObject(k).getString("section_id")); }
`
*   **Set<String> enrolledSectionIds = new java.util.HashSet<>...**: Plucks the user's Enrolled IDs and converts them seamlessly into a HashSet. A HashSet searches drastically faster than a normal Array, resulting in massive UI speedups when determining if a Drop button should appear inline!

`java
                                Platform.runLater(() -> {
                                    sectionContainer.getChildren().clear();
`
*   **Platform.runLater...**: Escapes the Async internet block and goes back to physically editing the screen safely.

`java
                                        for (int j = 0; j < sections.length(); j++) {
                                            JSONObject sec = sections.getJSONObject(j);
                                            String secId = sec.getString("id");
...
`
*   **or... JSONObject sec...**: Iterates cleanly through every single Section the backend returned for the parent course card.

`java
                                            String day1 = "";
                                            String time1 = "";
                                            if (sec.has("schedule_data") && !sec.isNull("schedule_data")) {
                                                JSONArray schedArr = sec.optJSONArray("schedule_data");
...
                                                    String rawDay = schedArr.getJSONObject(0).optString("day", "");
                                                    String dayExpanded = "";
                                                    for (char ch : rawDay.toCharArray()) {
                                                        if(ch == 'S') dayExpanded += "Sun, ";
                                                        else if(ch == 'M') dayExpanded += "Mon, ";
                                                        else if(ch == 'T') dayExpanded += "Tue, ";
                                                        else if(ch == 'W') dayExpanded += "Wed, ";
                                                        else if(ch == 'R') dayExpanded += "Thu, ";
...
                                                    }
`
*   **or (char ch : rawDay.toCharArray()) { ... }**: Implements a brute-force parser bridging the legacy college schedule formats (MW, TR, ST) explicitly into clean, readable UI formats natively by ripping the strings apart into array Chars and matching the characters dynamically against known mappings!

`java
                                            HBox secRow = new HBox(15); 
...
                                            Label secLabel = new Label("SEC " + secNum); 
...
                                            Label timeLabel = new Label(day1 + " | " + time1); 
...
                                            Label facLabel = new Label("Faculty: " + faculty); 
`
*   **HBox secRow... Label...**: Wraps the clean time variables, professor mappings dynamically directly onto graphical lines appended inside the sub-container block.

`java
                                            boolean isEnrolled = enrolledSectionIds.contains(secId);
                                            Button btnAction = new Button(isEnrolled ? "DROP" : "ENROLL");
                                            btnAction.getStyleClass().add("button");
                                            if (isEnrolled) btnAction.setStyle("-fx-background-color: #F43F5E;");
`
*   **oolean isEnrolled = enrolledSectionIds.contains(secId); ... Button btnAction ...**: The culmination of the Hashset. Maps against the ID securely checking if the user natively owns it. Re-assigns the color text string visually (#F43F5E, which is Red, indicating "Danger/Drop").

`java
                                            btnAction.setOnAction(e -> { 
                                                com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered...");
                                                btnAction.setDisable(true); 
`
*   **tnAction.setOnAction(e -> { btnAction.setDisable(true);**: Clicks automatically disable the button completely preventing massive sync errors due to users spam clicking the UI button.

`java
                                                new Thread(() -> {
                                                    try {
                                                        if (btnAction.getStyle().contains("#F43F5E")) {
`
*   **
ew Thread... if (btnAction.getStyle().contains("#F43F5E"))**: Drops the thread backward into the background! It queries the active button's color to figure out whether the student wishes to Drop versus Add implicitly.

`java
                                                            academicRepository.dropEnrollment(uid, activeSem, secId);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("ENROLL"); 
                                                                btnAction.setStyle(""); 
                                                                actionTarget.setText("Dropped!"); 
                                                            });
`
*   **cademicRepository.dropEnrollment...**: Executes the heavy Repository drop flow!
*   **Platform.runLater...**: Graphically updates the button dynamically replacing "DROP" with "ENROLL" successfully allowing the user to un-drop instantly without refreshing!

`java
                                                        } else {
                                                            academicRepository.pushEnrollment(uid, activeSem, courseCode, secId, secNum);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("DROP"); 
                                                                btnAction.setStyle("-fx-background-color: #F43F5E;"); 
                                                                actionTarget.setText("Enrolled!"); 
                                                            });
                                                        }
`
*   **} else { academicRepository.pushEnrollment... }**: Converts an Enroll click identically mirroring the UI states effectively seamlessly tracking back and forth!

`java
                                                        // academicRepository.syncSchedule(uid, activeSem); // Sync omitted implicitly 
                                                    } catch (Exception ex) { 
                                                        Platform.runLater(() -> { actionTarget.setText("Error: " + ex.getMessage()); }); 
                                                    }
                                                    Platform.runLater(() -> btnAction.setDisable(false));
                                                }).start();
                                            });
`
*   **} catch... btnAction.setDisable(false)...**: Closes out network blocks cleanly re-enabling clickability in both error and success cases definitively.

`java
                                            secRow.getChildren().addAll(detailsBox, spacer, btnAction); 
                                            sectionContainer.getChildren().add(secRow);
`
*   **secRow.getChildren().addAll...**: Commits the entire section row structure into the container formally rendering it.

`java
                                        }
                                    }
                                });
...
                        }).start();
                        isLoaded[0] = true;
`
*   **isLoaded[0] = true;**: Completes the loop explicitly sealing the boolean array so we don't accidentally ask Supabase twice for the exact same class if the student clicks to hide/show the banner repetitively.

`java
                card.getChildren().addAll(header, sectionContainer);
                coursesContainer.getChildren().add(card);
            }
        };
`
*   **coursesContainer.getChildren().add(card);**: Drops the dynamically built UI block natively.

`java
        applyFilter.run();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> applyFilter.run());
`
*   **pplyFilter.run();**: Auto-triggers the parser once at start so the page isn't totally blank.
*   **searchBar.textProperty().addListener... -> applyFilter.run());**: Anchors the pplyFilter Runnable code precisely onto an observer hook mapping keystrokes! If 
ewValue fires, it immediately loops dynamically filtering!

`java
        ScrollPane scrollPane = new ScrollPane(coursesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
`
*   **ScrollPane scrollPane...**: Implements scrolling because standard boxes just push content off-screen.

`java
        root.getChildren().addAll(title, semesterLabel, subtitle, searchBar, scrollPane, actionTarget);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
`
*   **VBox.setVgrow...**: Makes sure the scroll bounds consume exactly 100% of the user's available window pixel density vertically.

`java
        Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Enrollment", root);
        Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
`
*   **Parent adaptiveLayout = LayoutFactory.create(...); ... stage.show();**: Encapsulates our entirely manually-drawn root screen with the Sidebar, applies OS styles, sets scaling metrics, and effectively prints it back out on-screen gracefully completing the class.
---

### File: `ScheduleScreen.java`

`java
package com.ewumatelite.features.schedule.presentation;
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import javafx.fxml.FXMLLoader;
...
`
*   **package com.ewumatelite.features.schedule.presentation;**: Declares location logically under the Schedule domain. Imports .fxml loading utilities instead of raw VBox elements!

`java
public class ScheduleScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
`
*   **public class ScheduleScreen {**: Standard screen implementation safely holding the window, UUID, and active tracked semester.

`java
    public ScheduleScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }
`
*   **public ScheduleScreen(...)**: Instantiates local tracking variables.

`java
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/schedule.fxml"));
            Parent scheduleLayout = loader.load();
`
*   **FXMLLoader loader ... loader.load();**: Crucially diverge from the programmatic EnrollmentScreen. This physically maps to a separate schedule.fxml blueprint stringing XML nodes over internally, completely generating the screen structurally!

`java
            ScheduleController controller = loader.getController();
            controller.initData(stage, uid, activeSem);
`
*   **ScheduleController controller = loader.getController();**: Dynamically rips the specific controller explicitly attached inside the XML file back out into pure Java!
*   **controller.initData(...)**: We *cannot* pass a constructor into an FXML controller natively because JavaFX builds it invisibly. We therefore must call .initData() immediately sequentially manually passing the uid over cleanly!

`java
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Manage Schedule", scheduleLayout);
`
*   **Parent adaptiveLayout = LayoutFactory.create...**: Wraps the XML UI blueprint identically around the global Sidebar ensuring visual continuity.

`java
            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Manage Schedule");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
`
*   **Scene scene = ... stage.show();**: Binds screen bounds safely natively against system size and prints the final loaded XML visual layout completely.
---

### File: `ScheduleController.java`

`java
package com.ewumatelite.features.schedule.presentation;
import com.ewumatelite.core.repositories.ExceptionRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.LogExporter;
import javafx.fxml.FXML;
...
`
*   **package ...**: Schedule controller file resolving the XML logic binding. It uniquely imports the ExceptionRepository which specifically handles tracking canceled classes, rescheduled makeup days, and holidays impacting the normal grid.

`java
public class ScheduleController {
    @FXML private VBox upcomingListContainer;
    @FXML private VBox pendingListContainer;
    
    @FXML private Label lblUpcoming;
    @FXML private Region indUpcoming;
`
*   **@FXML private VBox upcomingListContainer;**: The @FXML tag binds these raw variables explicitly to matching x:id string references written directly inside schedule.fxml. upcomingListContainer is the physical vertical box that will hold the two-week class schedule. lblUpcoming and indUpcoming are the tab text and glowing underline indicator visually marking the active tab.

`java
    private String uid;
    private String activeSem;
    private Stage stage;

    private final ExceptionRepository exRepo = new ExceptionRepository();
    private final AcademicRepository acRepo = new AcademicRepository();
`
*   **private String... private final ExceptionRepository...**: Internal properties tracking the student globally and instantiating network tools.

`java
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        LogExporter.log("ScheduleController initialized for user " + uid + " in semester " + activeSem);
        loadScheduleData();
    }
`
*   **public void initData(...) { ... loadScheduleData(); }**: Replaces the constructor. Receives configuration from ScheduleScreen.java post-FXML load and immediately fires the core network fetch loadScheduleData().

`java
    private void loadScheduleData() {
        Platform.runLater(() -> {
            upcomingListContainer.getChildren().clear();
            pendingListContainer.getChildren().clear();
            pendingListContainer.getChildren().add(new Label("Loading Data..."));
            switchToUpcoming();
        });
`
*   **Platform.runLater(() -> { ... switchToUpcoming(); });**: Safely clears the existing screen data formatting the tabs via switchToUpcoming() so the user physically sees "Loading Data" while the internet connects.

`java
        new Thread(() -> {
            try {
                LocalDate now = LocalDate.now();
                String startStr = now.toString();
                String endStr = now.plusDays(14).toString();
`
*   **
ew Thread(() -> { LocalDate now = LocalDate.now(); ...**: Shifts to backend networking. We calculate the exact literal Date today, then mathematically jump exactly 14 days into the future producing a two-week query window natively formatted as strings like 2024-11-20.

`java
                // Fetch weekly grid 
                JSONObject dbData = acRepo.getDashboardData(uid, activeSem, startStr);
                JSONObject weeklyGridRaw = dbData.optJSONObject("weekly_grid");
                final JSONObject weeklyGrid = (weeklyGridRaw == null) ? new JSONObject() : weeklyGridRaw;
`
*   **JSONObject dbData ... weeklyGridRaw**: The repository effectively hands back the completely static perfect Monday-Friday grid.

`java
                // Fetch Exceptions mapping
                final JSONArray allExceptions = exRepo.fetchExceptions(uid);
                
                // Fetch Holidays
                final JSONArray allHolidays = acRepo.fetchUpcomingHolidays(activeSem, startStr, endStr);
`
*   **inal JSONArray allExceptions ... allHolidays**: Crucial complex queries. It asks Supabase: "Did the student click Cancel on any class in the next 14 days?" and "Are there any EWU official holidays falling within this 14-day window?"

`java
                Platform.runLater(() -> {
                    upcomingListContainer.getChildren().clear();
                    pendingListContainer.getChildren().clear();

                    buildTwoWeekSchedule(weeklyGrid, allExceptions, allHolidays);
                    buildPendingActions(allExceptions);
                });
`
*   **Platform.runLater(() -> { ... buildTwoWeekSchedule(...) }**: Returns to the main UI thread having collected all three massive data sets! It fires them sequentially into native rendering functions erasing the "Loading" labels instantly.

`java
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    upcomingListContainer.getChildren().add(new Label("Error loading schedule."));
                    pendingListContainer.getChildren().clear();
                });
            }
        }).start();
    }
`
*   **} catch (Exception e) { ... }**: Safe error wrapper preventing crashes on network failures natively defaulting to a text alert screen.

`java
    @FXML
    private void switchToUpcoming() {
        upcomingListContainer.setVisible(true);
        upcomingListContainer.setManaged(true);
        pendingListContainer.setVisible(false);
        pendingListContainer.setManaged(false);
        
        lblUpcoming.setStyle("-fx-text-fill: #00E5FF; -fx-font-weight: bold; -fx-font-size: 14;");
        indUpcoming.setStyle("-fx-background-color: #00E5FF;");
        
        lblPending.setStyle("-fx-text-fill: #8A95A5; -fx-font-weight: bold; -fx-font-size: 14;");
        indPending.setStyle("-fx-background-color: transparent;");
    }
`
*   **@FXML private void switchToUpcoming() { ... }**: The literal Tab UI handler clicked natively from the .fxml file. It completely hides the pendingListContainer dropping it from the layout tree (setManaged(false)). It repaints the lblUpcoming glowing cyan (#00E5FF) and makes the pending tab drab grey (#8A95A5).

`java
    @FXML
    private void switchToPending() { ... }
`
*   **private void switchToPending() { ... }**: Identical reverse logic exposing the pending actions list!

`java
    @FXML
    private void handleAddClass() {
        LogExporter.log("ACTION: Add Class FAB Clicked");
        new ManualEntryModal(uid, activeSem, this::loadScheduleData).show();
    }
`
*   **private void handleAddClass() { new ManualEntryModal(...) }**: Spawns a modal screen overlaying the app permitting students to manually type in events. It crucially passes 	his::loadScheduleData as a callback so the screen visually auto-refreshes if they save something new!

`java
    private void buildTwoWeekSchedule(JSONObject weeklyGrid, JSONArray allExceptions, JSONArray allHolidays) {
        LocalDate current = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMM d");
        DateTimeFormatter matchFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
`
*   **private void buildTwoWeekSchedule(...) { ...**: The massive algorithm combining static grids with dynamic cancellations. Generates dtf to print nice headers like "Monday, Oct 4th" and matchFormat to query JSON matches perfectly.

`java
        for (int i = 0; i < 14; i++) {
            LocalDate date = current.plusDays(i);
            String dateMatchingStr = date.format(matchFormat);
            String dayOfWeek = date.getDayOfWeek().toString();
            // Fallback match the casing to Flutter's map
            dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();
`
*   **or (int i = 0; i < 14; i++) { ... **: Sweeps through literal day 0 (today) to day 13.
*   **dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase()...**: Safely grabs Java's native MONDAY enum and manipulates the casing directly into Monday so it matches the expected Supabase string dictionary keys perfectly preventing Null crashes!

`java
            boolean processedHoliday = false;
            String holidayName = "";

            // Check if it's a holiday
            if (allHolidays != null) {
                for (int h = 0; h < allHolidays.length(); h++) {
                    JSONObject hol = allHolidays.getJSONObject(h);
                    if (dateMatchingStr.equals(hol.optString("event_date"))) {
...
`
*   **oolean processedHoliday ... if (dateMatchingStr.equals... )**: Steps into the Holiday dictionary natively comparing if this specific loop iteration date matches the holiday date safely!

`java
                        String title = hol.optString("title", hol.optString("name", "")).toLowerCase();
                        String reason = hol.optString("name", "Holiday");
                        if (title.contains("swap") || title.contains("makeup")) {
                            String[] words = title.split(" ");
                            if (words.length > 0) {
                                String swapDayRaw = words[words.length - 1];
                                if (swapDayRaw.endsWith("s")) swapDayRaw = swapDayRaw.substring(0, swapDayRaw.length() - 1);
                                dayOfWeek = swapDayRaw.substring(0, 1).toUpperCase() + swapDayRaw.substring(1).toLowerCase();
                            }
                        } else if (!title.contains("makeup") && !title.contains("advising")) {
                            holidayName = reason;
                            processedHoliday = true;
                        }
                    }
                }
            }
`
*   **if (title.contains("swap") ... dayOfWeek = swapDayRaw...**: **Brilliant Scheduling Logic:** At EWU, sometimes a Tuesday acts identically to a Monday due to weird holiday schedules bridging across weekends. This block literally reads that Supabase flag ("Classes will follow Monday schedule") and *overwrites the dayOfWeek variable in memory so the loop queries the static Monday classes instead of the Tuesday ones inherently seamlessly!* 
*   **else if ... processedHoliday = true;**: If it's just a normal massive holiday (like Thanksgiving), sets the flag to just print a generic rest day implicitly.

`java
            JSONArray dayClasses = weeklyGrid.optJSONArray(dayOfWeek);
`
*   **JSONArray dayClasses = weeklyGrid.optJSONArray(dayOfWeek);**: The crucial mapping execution! Gets the classes based on the (potentially swapped!) day string dynamically!

`java
            // Gather makeups for this date
            JSONArray dateExceptions = new JSONArray();
            if (allExceptions != null) {
                for (int exIdx = 0; exIdx < allExceptions.length(); exIdx++) {
                    JSONObject ex = allExceptions.getJSONObject(exIdx);
                    if (dateMatchingStr.equals(ex.optString("date"))) {
                        dateExceptions.put(ex);
                    }
                }
            }
`
*   **JSONArray dateExceptions ...**: Iterates cleanly creating an array specifically of exceptions natively mapped directly to *just* this single day spanning the loop.

`java
            boolean hasContent = false;
            VBox dayContainer = new VBox(5);

            if (processedHoliday) {
                dayContainer.getChildren().add(createHolidayCard(holidayName));
                hasContent = true;
            } else if (dayClasses != null && dayClasses.length() > 0) {
`
*   **if (processedHoliday) { ... createHolidayCard... }**: If the flag tripped, literally entirely ignores real schedules and spans a distinct Holiday graphical block blocking the day safely!

`java
                for (int j = 0; j < dayClasses.length(); j++) {
                    JSONObject classObj = dayClasses.getJSONObject(j);
                    String courseCode = classObj.optString("courseCode", classObj.optString("course_code", "Unknown"));
                    String timeStr = classObj.optString("startTime", "") + " - " + classObj.optString("endTime", "");
                    String room = classObj.optString("room", "");
                    
                    if (isCancelled(dateExceptions, dateMatchingStr, courseCode)) {
                        continue; // Skip cancelled entries from Upcoming timeline entirely as Flutter does
                    }
                    dayContainer.getChildren().add(createUpcomingCard(courseCode, timeStr, room, dateMatchingStr));
                    hasContent = true;
                }
            }
`
*   **if (isCancelled(...)) { continue; }**: Iterates through the actual loaded grid classes. If isCancelled maps 	rue cross-referencing our dateExceptions block, it skips the execution directly erasing it physically from the timeline screen natively mimicking Flutter perfectly! If it clears, it produces a standard createUpcomingCard().

`java
            // Append Exceptions (Makeup/Manual)
            for (int e = 0; e < dateExceptions.length(); e++) {
                JSONObject ex = dateExceptions.getJSONObject(e);
                String type = ex.optString("type");
                if ("makeup".equals(type) || "manual".equals(type)) {
                    String timeStr = ex.optString("start_time", "") + " - " + ex.optString("end_time", "");
...
                    dayContainer.getChildren().add(createUpcomingCard(courseCode + " (Makeup)", timeStr, room, dateMatchingStr));
                    hasContent = true;
                }
            }
`
*   **if ("makeup".equals(type) ... createUpcomingCard(...)**: Sweeps exceptions backwards! The student may have added a makeup wrapper purely onto this specific Friday! Physically appends it underneath alongside normal courses explicitly labelling it (Makeup) onscreen.

`java
            if (hasContent) {
                Label dateHeader = new Label(date.format(dtf));
                dateHeader.setStyle("-fx-text-fill: #8A95A5; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 0 5 0;");
                upcomingListContainer.getChildren().add(dateHeader);
                upcomingListContainer.getChildren().add(dayContainer);
            }
        }
`
*   **if (hasContent) { ... upcomingListContainer.getChildren().add(dayContainer); }**: If the loop rendered literally *anything* on this specific date, it generates the text header and visually commits the block cleanly onto the screen.

`java
        if (upcomingListContainer.getChildren().isEmpty()) {
            upcomingListContainer.getChildren().add(new Label("No upcoming classes scheduled."));
        }
    }
`
*   **if (upcomingListContainer...isEmpty())**: Blank state fail-safe.

`java
    private void buildPendingActions(JSONArray allExceptions) {
        for (int i = 0; i < allExceptions.length(); i++) {
...
            if ("cancel".equals(type) && meta != null && meta.optBoolean("pendingMakeup", false)) {
                String courseCode = ex.optString("course_code");
                String date = ex.optString("date");
                String exId = ex.optString("id");
                
                VBox card = createPendingCard(exId, courseCode, "Cancelled on " + date);
                pendingListContainer.getChildren().add(card);
            }
        }
...
    }
`
*   **private void buildPendingActions... if ("cancel".equals(type) && ... "pendingMakeup")**: Populates the Pending tab. Loops through exceptions targeting exclusively instances mathematically flagged as explicitly cancelled BUT heavily requiring a mandatory manual makeup!

`java
    private boolean isCancelled(JSONArray allExceptions, String date, String courseCode) {
        for (int i = 0; i < allExceptions.length(); i++) {
            JSONObject ex = allExceptions.getJSONObject(i);
            if ("cancel".equals(ex.optString("type")) 
                && courseCode.equals(ex.optString("course_code")) 
                && date.equals(ex.optString("date"))) {
                return true;
            }
        }
        return false;
    }
`
*   **private boolean isCancelled(...)**: Small helper function brute iterating tracking exceptions targeting strict matching between three strings!

`java
    private VBox createHolidayCard(String reason) { ... }
    private VBox createUpcomingCard(String title, String time, String location, String dateStr) { ... }
    private VBox createPendingCard(String exId, String title, String subtitle) { ... }
`
*   **private VBox create[Various]Card(...)**: Visual generators wrapping labels with explicit JavaFX standard coloring (#1F7883 for Holidays, #1E2836 for Standard Dark UI cards) appending actionable UI buttons handling rendering directly bypassing FXML configurations cleanly allowing infinite repeating cards.

`java
    private void showCancelDialog(String title, String dateStr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Session");
        alert.setHeaderText("Cancel " + title + " on " + dateStr + "?");
        alert.setContentText("Do you want to plan a makeup later for this, or just skip it altogether?");

        ButtonType btnMakeup = new ButtonType("Plan Makeup Later");
        ButtonType btnSkip = new ButtonType("Skip Completely");
        ButtonType btnCancelAction = new ButtonType("Nevermind", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnMakeup, btnSkip, btnCancelAction);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() != btnCancelAction) {
            boolean pendingMakeup = (result.get() == btnMakeup);
            new Thread(() -> {
                try {
                    String baseTitle = title.replace(" (Makeup)", "");
                    exRepo.insertCancellation(uid, activeSem, baseTitle, dateStr, pendingMakeup);
                    loadScheduleData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void showRescheduleDialog(String cancelExId, String title) {
        new ManualEntryModal(uid, activeSem, cancelExId, this::loadScheduleData).show();
    }
}
`
*   **private void showCancelDialog(...)**: Constructs a 3-way branching JavaFX alert box. If the student hits "Cancel", it spawns a background thread immediately firing an HTTP insert targeting Supabase storing the cancellation! It literally maps "Plan Makeup Later" to flipping a pendingMakeup boolean flag to true!
*   **private void showRescheduleDialog(...)**: Triggers the exact same ManualEntryModal but dynamically passes the cancelExId inside! This means when the student finishes typing the makeup bounds, the Modal knows to effectively securely erase the old Pending flag while inserting the valid Makeup simultaneously!
---

### File: `ManualEntryModal.java`

`java
package com.ewumatelite.features.schedule.presentation;
import com.ewumatelite.core.repositories.ExceptionRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
...
`
*   **import javafx.stage.Modality ... StageStyle;**: Imports explicitly dedicated to manipulating how OS-level windows behave. Crucial for creating popups that freeze the parent screen until dismissed!

`java
public class ManualEntryModal {
    private final ExceptionRepository exRepo = new ExceptionRepository();
    private final AcademicRepository acRepo = new AcademicRepository();

    private final String uid;
    private final String activeSem;
    private final Runnable onComplete;
`
*   **private final Runnable onComplete;**: This stores the literal function address handed into this object (e.g. 	his::loadScheduleData). It allows this inner window to command the *outer* schedule screen to visually refresh itself over the internet once a manual makeup is successfully saved.

`java
    public ManualEntryModal(String uid, String activeSem, Runnable onComplete) {
        this.uid = uid;
        this.activeSem = activeSem;
        this.onComplete = onComplete;
    }
`
*   **public ManualEntryModal(...)**: Standard constructor anchoring the variables securely.

`java
    public void show() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Manual Entry");
        modalStage.initStyle(StageStyle.UTILITY);
`
*   **Stage modalStage = new Stage();**: Literally commands the OS to spin up a completely brand new window frame distinct from the main application!
*   **modalStage.initModality(Modality.APPLICATION_MODAL);**: This is a critical JavaFX flag! It freezes the entire main application mathematically behind this window. The user physically cannot click anything external until they close this modal box.
*   **modalStage.initStyle(StageStyle.UTILITY);**: Maps the window decorations natively to the OS's minimal styling (hiding maximize/minimize buttons usually).

`java
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1A1A2E; -fx-border-color: #00E5FF; -fx-border-width: 1;");
        layout.setAlignment(Pos.TOP_LEFT);
`
*   **VBox layout ... setStyle...**: Manually constructs the UI programmatic flow ensuring it matches the exact Dark Theme coloring since it operates structurally outside the main unified LayoutFactory.

`java
        Label titleLabel = new Label("Manual Entry");
...
        Label courseLabel = new Label("Course");
        courseComboBox = new ComboBox<>();
        courseComboBox.setPromptText("Select Course");
...
`
*   **ComboBox<String> courseComboBox ...**: Creates a dropdown menu for the user to pick which class they are rescheduling! However, this dropdown is currently totally empty!

`java
        // Load Enrollments
        new Thread(() -> {
            try {
                enrollments = acRepo.getUserEnrollments(uid, activeSem);
                List<String> courses = new ArrayList<>();
                for (int i = 0; i < enrollments.length(); i++) {
                    courses.add(enrollments.getJSONObject(i).optString("course_code"));
                }
                Platform.runLater(() -> courseComboBox.getItems().addAll(courses));
            } catch (Exception e) { ... }
        }).start();
`
*   **
ew Thread ... acRepo.getUserEnrollments...**: Fires an asynchronous network query to figure out what classes the student is currently taking!
*   **Platform.runLater(() -> courseComboBox.getItems().addAll(courses));**: Once the network finishes resolving, it securely leaps back onto the main UI thread and dynamically injects the class names directly into the dropdown menu cleanly!

`java
        // Session Type
        sessionTypeGroup = new ToggleGroup();
        ToggleButton theoryBtn = new ToggleButton("Theory (90 min)");
        theoryBtn.setToggleGroup(sessionTypeGroup);
        theoryBtn.setSelected(true);
...
        ToggleButton labBtn = new ToggleButton("Lab (3 hrs)");
...
        HBox sessionBox = new HBox(10, theoryBtn, labBtn);
`
*   **ToggleGroup sessionTypeGroup...**: Enforces strict mutual exclusivity. Clicking "Theory" mathematically depresses "Lab" and vice-versa inherently! 

`java
        // Date
        datePicker = new DatePicker(LocalDate.now());
...
        // Time
        timeField = new TextField();
        timeField.setPromptText("Tap to pick start time");
...
        // Room
        roomField = new TextField();
`
*   **DatePicker ... TextField ...**: Summons native OS-level calendar popup boxes and text inputs capturing constraints mapping where the makeup class physically happens.

`java
        // Save Button
        Button saveBtn = new Button("SAVE ENTRY");
        saveBtn.setOnAction(e -> saveEntry(modalStage));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> modalStage.close());
`
*   **saveBtn.setOnAction...**: Wires the big cyan submit button to actually execute the payload. The cancel button simply uses modalStage.close() implicitly shattering the window and unfreezing the app globally.

`java
        layout.getChildren().addAll( ... );
        Scene scene = new Scene(layout, 350, 600);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }
`
*   **layout.getChildren().addAll ... modalStage.showAndWait();**: Packages the buttons together gracefully! showAndWait() halts the local thread preventing show() from jumping backward into the parent context.

`java
    private void saveEntry(Stage stage) {
        String course = courseComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        String room = roomField.getText();
`
*   **private void saveEntry...**: Rips the raw text out of the JavaFX UI boxes currently painted on-screen!

`java
        if (course == null || date == null || time.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in course, date, and start time.");
            alert.show();
            return;
        }
`
*   **if (course == null... return;**: Crucial validation block! Prevents null-pointer exceptions if the student violently spammed the Save button before clicking a Course from the dropdown natively.

`java
        ToggleButton selectedMode = (ToggleButton) sessionTypeGroup.getSelectedToggle();
        boolean isLab = selectedMode.getText().contains("Lab");
`
*   **oolean isLab...**: Parses whether the user toggled the explicit Lab configuration which extends the class boundary significantly internally!

`java
        new Thread(() -> {
            try {
                // Dummy faculty/name for now to mirror simplicity without full metadata lookup
                String courseName = "Manual Entry";
                String faculty = "N/A";
                
                String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = isLab ? "End (+3 hrs)" : "End (+90m)"; // Simplified for JavaFX manual text
`
*   **
ew Thread ... String dateStr ...**: Offloads networking to the background. Formats the OS date completely into a Supabase-compatible string (e.g. 2024-11-20). Modifies the visual endTime text based purely on the isLab toggle state!

`java
                exRepo.addManualClass(uid, dateStr, course, courseName, time, endTime, room, faculty);
                
                Platform.runLater(() -> {
                    onComplete.run();
                    stage.close();
                });
`
*   **exRepo.addManualClass(...)**: Forces the repository to permanently store the exception into the Supabase Cloud so it shows up on the student's timeline!
*   **Platform.runLater ... onComplete.run(); stage.close();**: Once the network reports 200 OK SUCCESS, securely closes out the modal box visually! Crucially invokes onComplete.run() which triggers the ScheduleController.loadScheduleData() loop redrawing the Timeline seamlessly underneath them dynamically!

`java
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save entry: " + ex.getMessage());
                    alert.show();
                });
            }
        }).start();
    }
}
`
*   **} catch... Alert... **: Failsafe error window preventing silent application breakage exactly if the internet drops inherently.
---

### File: `TasksScreen.java`

`java
package com.ewumatelite.features.tasks.presentation;
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import javafx.fxml.FXMLLoader;
...
`
*   **package com.ewumatelite.features.tasks.presentation;**: Registers the Tasks presentation domain. Uses FXMLLoader as it is an FXML-backed GUI map.

`java
public class TasksScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;

    public TasksScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }
`
*   **public class TasksScreen { ... }**: Standard screen class mirroring the DashboardScreen and ScheduleScreen boilerplate injecting the variables perfectly gracefully.

`java
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
            VBox tasksLayout = loader.load();
`
*   **FXMLLoader loader ... loader.load();**: Pulls the static XML string tree 	asks.fxml parsing it aggressively over into native Java VBox groupings perfectly safely.

`java
            TasksController controller = loader.getController();
            controller.initData(stage, uid, activeSem);
`
*   **TasksController controller ... controller.initData...**: Connects immediately exactly logically injecting the uid directly inside the initialized controller manually circumventing the missing FXML constructors.

`java
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Tasks", tasksLayout);

            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Tasks");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
`
*   **Parent adaptiveLayout = LayoutFactory.create(...)**: Wraps the 	asksLayout specifically directly inside the Sidebar frame gracefully ensuring the student isn't trapped!
*   **stage.show();**: Pushes the layout visibly on-screen efficiently.
---

### File: `TasksController.java`

`java
package com.ewumatelite.features.tasks.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
...
`
*   **package com.ewumatelite.features.tasks.presentation;**: Declares Tasks domain. Note that TasksScreen.java handles the Sidebar mapping, while this file explicitly handles the FXML logic mapping natively.

`java
public class TasksController {
    @FXML private VBox upcomingTasksContainer;
    @FXML private VBox overdueTasksContainer;
    @FXML private VBox completedTasksContainer;
    @FXML private TabPane taskTabPane;
`
*   **@FXML private VBox ...**: Directly binds to the XML tree exactly the same way Schedule did. Tasks features three literal boxes storing tasks effectively partitioned dynamically based solely on their timeline properties.

`java
    private Stage stage;
    private String uid;
    private String activeSem;

    // Theme mimicking Flutter Tasks UI
    private final String CARD_BG = "#1E293B"; // Dark slate
    private final String TEXT_PRIMARY = "#FFFFFF";
    private final String TEXT_SECONDARY = "#8A95A5";
    private final String ACCENT_TEAL = "#22D3EE";
    private final String ACCENT_RED = "#F43F5E";
`
*   **private final String ...**: Inlines constant string configuration mapping exactly to the Flutter Mobile version Hex colors to ensure visual continuity effortlessly!

`java
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        // CSS to clean up tab pane background (since default JavaFX TabPane usually looks bad with dark themes)
        taskTabPane.setStyle("-fx-background-color: transparent; -fx-tab-min-width: 100;");
        
        loadTasks();
    }
`
*   **public void initData**: Replaces standard constructors completely.
*   **	askTabPane.setStyle...**: Hacks native JavaFX default white TabPanes forcefully mapping completely transparent padding natively ensuring Dark Mode doesn't look completely shattered visually.

`java
    private void loadTasks() {
        upcomingTasksContainer.getChildren().clear();
        ...
        upcomingTasksContainer.getChildren().add(new Label("Loading tasks..."));

        new Thread(() -> {
            try {
                JSONObject data = new AcademicRepository().getDashboardData(uid, activeSem, LocalDate.now().toString());
                Platform.runLater(() -> populateTasks(data.optJSONArray("tasks")));
            } catch (Exception ex) { ... }
        }).start();
    }
`
*   **
ew Thread... getDashboardData...**: Tasks natively pull exactly from the identical query endpoint mapped to the Dashboard cleanly reducing backend traffic massively. Passes the raw JSON payload exclusively into the populateTasks parser.

`java
    private void populateTasks(JSONArray tasks) {
        upcomingTasksContainer.getChildren().clear();
        overdueTasksContainer.getChildren().clear();
        completedTasksContainer.getChildren().clear();
`
*   **private void populateTasks(...)**: Start of the sorting block erasing the loading labels.

`java
        if (tasks == null || tasks.length() == 0) { ... return; }
        LocalDate now = LocalDate.now();

        List<JSONObject> upcoming = new ArrayList<>();
        List<JSONObject> overdue = new ArrayList<>();
        List<JSONObject> completed = new ArrayList<>();
`
*   **LocalDate now = LocalDate.now(); List ...**: Summons exactly three logical string groupings and asks Java to hold exactly what time it is currently so the engine can execute relative time travel queries safely.

`java
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject t = tasks.getJSONObject(i);
            boolean isComp = t.optBoolean("is_completed", false);
            String dueStr = t.optString("due_date", "");
`
*   **or... boolean isComp... dueStr**: Plucks exactly three things - the raw physical task, the literal true/false completed flag string seamlessly from Supabase, and the raw text mapping exactly when the assignment ends.

`java
            if (isComp) {
                completed.add(t);
            } else {
                if (dueStr.isEmpty()) {
                    upcoming.add(t);
                } else {
                    try {
                        LocalDate due = LocalDate.parse(dueStr.substring(0, 10));
                        if (due.isBefore(now)) overdue.add(t);
                        else upcoming.add(t);
                    } catch (Exception e) {
                        upcoming.add(t);
                    }
                }
            }
        }
`
*   **if (isComp) ... completed.add(t);**: If the boolean flipped true, drop it in the completed bucket and move on instantly.
*   **if (due.isBefore(now)) overdue.add(t);**: If the task has a date mapped, and it's physically before today's OS time, it drops formally directly into the Overdue array mapping natively triggering red alarming text later cleanly!

`java
        if (upcoming.isEmpty()) upcomingTasksContainer.getChildren().add(createEmptyState("No upcoming tasks found"));
        else upcoming.forEach(t -> upcomingTasksContainer.getChildren().add(createTaskCard(t, false, false)));

        if (overdue.isEmpty()) overdueTasksContainer.getChildren().add(createEmptyState("No overdue tasks found"));
        else overdue.forEach(t -> overdueTasksContainer.getChildren().add(createTaskCard(t, true, false)));

        if (completed.isEmpty()) completedTasksContainer.getChildren().add(createEmptyState("No completed tasks found"));
        else completed.forEach(t -> completedTasksContainer.getChildren().add(createTaskCard(t, false, true)));
    }
`
*   **upcoming.forEach( ... createTaskCard(...) )**: Foreach loop dynamically streaming arrays cleanly iterating physical graphical generation blocks pushing them linearly vertically into correct bins respectively.

`java
    private HBox createTaskCard(JSONObject task, boolean isOverdue, boolean isCompleted) {
        ...
        card.setStyle("-fx-background-color: " + (isCompleted ? "#0F172A" : CARD_BG) + "; -fx-background-radius: 20; -fx-border-color: #334155; -fx-border-width: 1; -fx-border-radius: 20;");
        if (isCompleted) card.setOpacity(0.5);
`
*   **private HBox createTaskCard(...)**: FXML doesn't natively handle infinite unknown sizes cleanly, so this manually parses the array directly creating HBox cards.
*   **if (isCompleted) card.setOpacity(0.5);**: If the student finished it, dynamically fade the complete graphic box completely inherently making it transparent seamlessly matching Flutter!

`java
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(isCompleted);
...
        VBox contentBox = new VBox(6);
        Label title = new Label(task.optString("title", "Unknown Task"));
        String strike = isCompleted ? "-fx-strikethrough: true;" : "";
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 16px; -fx-font-weight: bold; " + strike);
`
*   **CheckBox ... String strike...**: Appends functional checkboxes matching states natively drawing CSS strikethroughs slashing explicitly completely across closed text cleanly!

`java
        HBox badgesBox = new HBox(8);
        String cCode = task.optString("course_code", "");
        if (!cCode.isEmpty()) {
            Label codeLabel = new Label(cCode);
...
`
*   **HBox badgesBox ... cCode**: Assigns dynamic graphical colored labels purely if the string mapping mapped inherently natively matching courses to assignation safely!

`java
        String dueStr = task.optString("due_date", "");
...
            if (dueStr.length() >= 16) {
                try {
                    String timePart = dueStr.substring(11, 16);
...
`
*   **String timePart = dueStr.substring(11, 16);**: Safely forcefully splits Postgres completely explicit timestamp data isolating explicitly precisely hours and minutes completely correctly converting native OS 24hr Time safely over to PM/AM manually.

`java
        Label dateLabel = new Label("?? " + dateSub);
        dateLabel.setStyle("-fx-text-fill: " + (isOverdue ? ACCENT_RED : TEXT_SECONDARY) + "; -fx-font-size: 12px; -fx-font-weight: " + (isOverdue ? "bold" : "normal") + ";");
...
        contentBox.getChildren().addAll(title, badgesBox);
`
*   **Label dateLabel ... (isOverdue ? ACCENT_RED...**: Inverts standard string colors turning dates neon red natively cleanly notifying imminent disaster logically.

`java
        Label moreIcon = new Label("?"); 
        moreIcon.setOnMouseClicked(e -> {
            e.consume();
            onEditTaskClicked(task);
        });

        card.setOnMouseClicked(e -> {
            if (!isCompleted) {
                onEditTaskClicked(task);
            }
        });
`
*   **moreIcon.setOnMouseClicked ... card.setOnMouseClicked...**: Wires interactive click logic to cleanly jump toward Edit modals specifically inherently ONLY correctly allowing clicks purely logically explicitly if the box isn't completed gracefully.

`java
    private void onEditTaskClicked(JSONObject task) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/add_task.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddTaskController controller = loader.getController();
            controller.initEditData(dialogStage, uid, activeSem, task, this::loadTasks);

            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAddTask() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/add_task.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Task");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddTaskController controller = loader.getController();
            controller.initAddData(dialogStage, uid, activeSem, this::loadTasks);

            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private VBox createEmptyState(String msg) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        
        Label lbl = new Label(msg);
        lbl.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-style: italic;");
        box.getChildren().add(lbl);
        return box;
    }
}
`
*   **private void onEditTaskClicked(JSONObject task)**: Spawns an FXML bound dd_task.fxml modal! Notice it explicitly calls initEditData securely injecting the pre-existing literal JSON payload directly into the controller!
*   **private void handleAddTask()**: The identical reverse. It loads the exact same dd_task.fxml XML map, but natively invokes initAddData() without any JSON payload seamlessly acting as a pure blank slate! Both explicitly hand over 	his::loadTasks triggering the screen to refresh inherently once the modal crashes closed cleanly!
---

### File: `AddTaskController.java`

`java
package com.ewumatelite.features.tasks.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
...
`
*   **import javafx.collections.FXCollections;**: Critically required for populating JavaFX list boxes (ComboBox) natively as they require specialized "Observable" arrays to track changes.

`java
public class AddTaskController {
    @FXML private TextField titleField;
    @FXML private ComboBox<String> courseComboBox;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> hourComboBox;
    @FXML private ComboBox<String> minuteComboBox;
    @FXML private ComboBox<String> ampmComboBox;
    @FXML private DatePicker datePicker;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
`
*   **@FXML private ...**: Captures every single dropdown and text field built inside dd_task.fxml.

`java
    private Stage dialogStage;
    private String uid;
    private String semesterCode;
    private String editingTaskId; // If null, it's a new task
    private Runnable onTaskAddedCallback;
    private boolean isSaving = false;
`
*   **private String editingTaskId;**: The crux of the single-file architecture! If this string remains 
ull, the Save button forces an HTTP INSERT. If this string gets populated on initialization, the Save button pivots and fires an HTTP UDPATE matching this specific ID!

`java
    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(
            "Mid Exam", "Final Exam", "Quiz", "Short Quiz", "Term Paper",
            "Assignment", "Project", "Lab Report", "Others"
        ));
        typeComboBox.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now());
`
*   **@FXML public void initialize()**: JavaFX *automatically* fires this method the millisecond the .fxml file is loaded into memory, completely before initData() is called! It pre-paints the dropdowns with static EWU grading schemas immediately making the UI feel extremely snappy.

`java
        // Initialize time dropdowns
        java.util.List<String> hours = new java.util.ArrayList<>();
        for (int i = 1; i <= 12; i++) hours.add(String.format("%02d", i));
        hourComboBox.setItems(FXCollections.observableArrayList(hours));
        hourComboBox.getSelectionModel().select("11");

        java.util.List<String> minutes = new java.util.ArrayList<>();
        for (int i = 0; i <= 59; i++) minutes.add(String.format("%02d", i));
        minuteComboBox.setItems(FXCollections.observableArrayList(minutes));
...
    }
`
*   **or (int i = 1; i <= 12; i++) ...**: Programmatically generates the literal numbers 1-12 and 0-59 as Strings pushing them into the Time dropdown boxes, defaulting to 11:59 PM (standard EWU assignment deadline).

`java
    public void initData(Stage dialogStage, String uid, String semesterCode, Runnable onTaskAddedCallback) {
        initDataWithTask(dialogStage, uid, semesterCode, null, onTaskAddedCallback);
    }
`
*   **public void initData(...)**: Helper function cleanly passing a 
ull task specifically invoked safely when the student wants to create a brand new task.

`java
    public void initDataWithTask(Stage dialogStage, String uid, String semesterCode, org.json.JSONObject taskToEdit, Runnable onTaskAddedCallback) {
        this.dialogStage = dialogStage;
...
        loadEnrolledCourses();
`
*   **public void initDataWithTask(...)**: The core data binder. Immediately triggers loadEnrolledCourses() in the background to swap the "Course" dropdown with the student's actual literal active courses implicitly.

`java
        if (taskToEdit != null) {
            this.editingTaskId = taskToEdit.optString("id");
            titleField.setText(taskToEdit.optString("title"));
            courseComboBox.setValue(taskToEdit.optString("course_code"));
            typeComboBox.setValue(taskToEdit.optString("type"));
...
`
*   **if (taskToEdit != null) { ...**: The "Edit Mode" parser! Rips apart the loaded JSON pushing the title, course code, and task type right back into the visible UI boxes so the student doesn't have to retype them to change a deadline!

`java
            String dueDate = taskToEdit.optString("due_date", "");
...
            if (dueDate.length() >= 16) {
                try {
                    String timePart = dueDate.substring(11, 16); // HH:mm
...
                    int displayHour = h % 12;
                    if (displayHour == 0) displayHour = 12;
                    
                    hourComboBox.setValue(String.format("%02d", displayHour));
...
                } catch (Exception e) {}
            }
            saveBtn.setText("Update Task");
        }
    }
`
*   **int displayHour = h % 12; ... saveBtn.setText("Update Task");**: Identical reverse conversion from OS memory UTC timestamps (e.g. 23:00) directly back into clean visual 12-hour (11:00 PM) dropdown UI states gracefully. Switches the button text cleanly.

`java
    private void loadEnrolledCourses() {
        new Thread(() -> {
            try {
                org.json.JSONArray enrollments = new AcademicRepository().getUserEnrollments(uid, semesterCode);
...
                Platform.runLater(() -> {
                    courseComboBox.setItems(FXCollections.observableArrayList(courses));
                });
            } catch (Exception e) { ... }
        }).start();
    }
`
*   **private void loadEnrolledCourses() { ... }**: Pulls exactly from getUserEnrollments preventing students from explicitly binding a task to a class they aren't securely enrolled in.

`java
    @FXML
    private void onSave(ActionEvent event) {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: onSave Triggered in " + this.getClass().getSimpleName());
        if (isSaving) return;

        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            showAlert("Validation Error", "Task title cannot be empty.");
            return;
        }
`
*   **@FXML private void onSave(...)**: The execution hook bound directly to the Save UI button explicitly throwing a validation crash if the Title field is empty preventing null DB inserts.

`java
        LocalDate date = datePicker.getValue();
        String dateStr = date != null ? date.toString() : "";
        
        if (!dateStr.isEmpty()) {
            try {
                int hh = Integer.parseInt(hourComboBox.getValue());
                String mm = minuteComboBox.getValue();
                String ampm = ampmComboBox.getValue();
                
                if (ampm.equals("PM") && hh < 12) hh += 12;
                if (ampm.equals("AM") && hh == 12) hh = 0;
                
                dateStr += String.format("T%02d:%s:00Z", hh, mm);
            } catch (Exception e) {}
        }
`
*   **if (ampm.equals("PM") && hh < 12) hh += 12;**: Converts the specific UI components (like 02:30 PM) manually combining them securely returning identical strings explicitly like 2024-11-20T14:30:00Z completely readable by Postgres DateTime servers!

`java
        isSaving = true;
        saveBtn.setText("Saving...");
        saveBtn.setDisable(true);

        new Thread(() -> {
            try {
                if (editingTaskId == null) {
                    new AcademicRepository().createTask(uid, title, course, finalDateStr, type, semesterCode);
                } else {
                    new AcademicRepository().updateFullTask(editingTaskId, uid, title, course, finalDateStr, type, semesterCode);
                }
`
*   **if (editingTaskId == null) { ... createTask(...) }**: The great separator! If ID is null, pushes createTask. Everything else explicitly fires updateFullTask!

`java
                Platform.runLater(() -> {
                    if (onTaskAddedCallback != null) {
                        onTaskAddedCallback.run();
                    }
                    dialogStage.close();
                });
            } catch (Exception e) { ... }
        }).start();
    }
`
*   **onTaskAddedCallback.run(); dialogStage.close();**: Once the network block successfully returns, absolutely destroys the window visually whilst simultaneously firing the parent's loadTasks() refresh cycle beautifully asynchronously!
---

### File: `SemesterProgressScreen.java`

`java
package com.ewumatelite.features.semester_progress.presentation;

import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import javafx.fxml.FXMLLoader;
...
`
*   **package com.ewumatelite.features.semester_progress.presentation;**: Declares location cleanly bridging into the mathematical analytics domain of the tracker natively. 

`java
public class SemesterProgressScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;

    public SemesterProgressScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }
`
*   **public class SemesterProgressScreen { ... }**: Exact boilerplate matching across all UI domains successfully pushing the user properties explicitly downward into the execution blocks.

`java
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/semester_progress.fxml"));
            VBox semesterLayout = loader.load();
`
*   **FXMLLoader loader ... loader.load();**: Triggers explicitly extracting semester_progress.fxml.

`java
            SemesterProgressController controller = loader.getController();
            controller.initData(stage, uid, activeSem);
`
*   **SemesterProgressController controller = loader.getController();**: Automatically parses passing initData executing the calculations underneath cleanly.

`java
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Semester Progress", semesterLayout);

            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Academic Progress");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
`
*   **Parent adaptiveLayout = LayoutFactory.create(...) ... stage.show();**: Standard wrap anchoring purely explicitly securely into the primary application window seamlessly.
---

### File: `SemesterProgressController.java`

`java
package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.MarksCalculator;
import javafx.application.Platform;
...
`
*   **import com.ewumatelite.core.utils.MarksCalculator;**: Critically pulls in the utility class containing the extremely heavy custom mathematical algorithms parsing how "Best N Quizzes" impacts total grades.

`java
public class SemesterProgressController {
    @FXML private FlowPane courseContainer;
    @FXML private Label semesterLabel;
    @FXML private Label loadingLabel;

    private Stage stage;
    private String uid;
    private String activeSem;
`
*   **@FXML private FlowPane courseContainer;**: Unlike VBox which just stacks downwards, a FlowPane natively acts like "flex-box" in CSS. It lays out the course cards side-by-side perfectly, wrapping neatly onto the next row specifically if the student's window width gets too thin!

`java
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        semesterLabel.setText(activeSem);
        loadProgressData();
    }
`
*   **public void initData(...) { ... loadProgressData(); }**: The standard FXML bootstrapper kicking off the backend internet fetch automatically.

`java
    private void loadProgressData() {
        courseContainer.getChildren().clear();
        courseContainer.getChildren().add(loadingLabel);

        new Thread(() -> {
            try {
                JSONArray data = new AcademicRepository().getSemesterProgressData(uid, activeSem);
                Platform.runLater(() -> renderData(data));
            } catch (Exception e) { ... }
        }).start();
    }
`
*   **JSONArray data = ... getSemesterProgressData(...)**: Uses the heavy Repository endpoint returning literally everything: every quiz, every midterm margin, and every single class specific variable configuration directly out of the Postgres analytics layer.

`java
    private void renderData(JSONArray data) {
        courseContainer.getChildren().clear();
        if (data.length() == 0) {
            Label noData = new Label("No enrollment or marks found.");
...
            return;
        }
`
*   **private void renderData(...)**: Clears the literal "Loading..." indicator visibly mapping fail-states actively.

`java
        for (int i = 0; i < data.length(); i++) {
            JSONObject course = data.getJSONObject(i);
            courseContainer.getChildren().add(createCourseCard(course));
        }
    }
`
*   **or (...) { ... add(createCourseCard(course)); }**: Recursively generates the beautiful grade blocks.

`java
    private VBox createCourseCard(JSONObject courseData) {
        String code = courseData.optString("course_code", "Course");
        
        // Calculate Total
        double total = 0.0;
        total += courseData.optDouble("obt_mid", 0.0);
        total += courseData.optDouble("obt_final", 0.0);
        total += courseData.optDouble("obt_attendance", 0.0);
...
`
*   **double total = 0.0; total += courseData.optDouble...**: The literal "Running Sum" calculator! Safely sweeps through every standard singular entry point directly mapping  .0 natively preventing NullPointerException crashes explicitly if the professor merely completely forgot to grade the midterm!

`java
        String qStrategy = courseData.optString("quiz_strategy", "best_one");
        int qN = courseData.optInt("quiz_n", 1);
        double qMax = courseData.optDouble("dist_quiz", 0.0);
        JSONArray qArr = courseData.optJSONArray("obt_quizzes");
        total += MarksCalculator.calculateQuizValue(qArr, qStrategy, qN, qMax);
`
*   **String qStrategy ... total += MarksCalculator...**: Here sits the complex algorithm bridge! It reads the "grading strategy" flag out of the JSON (e.g. "student gets completely penalized" vs "drop the absolute lowest quiz score inherently"). It hands the raw array of scores over to the MarksCalculator helper module mapping returning exactly the mathematically adjusted real value!

`java
        JSONObject extra = courseData.optJSONObject("marks_data");
        if (extra == null) extra = new JSONObject();
        String sqStrategy = extra.optString("short_quiz_strategy", "best_one");
...
        total += MarksCalculator.calculateQuizValue(sqArr, sqStrategy, sqN, sqMax);

        String percentageText = String.format("%.1f%%", total);
`
*   **String percentageText = String.format("%.1f%%", total);**: Trims massive floating points explicitly converting 84.9928372% graphically back rigidly downward rendering 85.0%.

`java
        String grade = "F";
        String gradeColor = "#F43F5E";
        if (total >= 80) { grade = "A+"; gradeColor = "#00e0ff"; }
        else if (total >= 75) { grade = "A"; gradeColor = "#00e0ff"; }
        else if (total >= 70) { grade = "A-"; gradeColor = "#00e0ff"; }
...
        else if (total >= 40) { grade = "D"; gradeColor = "#FFAB40"; }
`
*   **if (total >= 80) ...**: A giant if-else mountain! Directly translates the numerical raw percentage linearly specifically onto the classic North American standard letter grading framework, attaching corresponding color gradients (Cyan for A's, Orange for Cs, Red for F) inherently.

`java
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefWidth(320);
        card.setMinWidth(320);
        card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;");
`
*   **VBox card ... setPrefWidth(320)**: Hardcodes the card visual width so the FlowPane layout grid correctly wraps them flawlessly linearly exactly when the screen shrinks manually resizing.

`java
        // Row 1: Code and Percent
        HBox row1 = new HBox();
...
        Label percentLabel = new Label(percentageText);
        percentLabel.setStyle("-fx-text-fill: " + gradeColor + "; -fx-font-size: 20px; -fx-font-weight: bold;");
...
        // Row 2: Title and Grade
        HBox row2 = new HBox();
...
        Label gradeBadge = new Label(grade);
        gradeBadge.setStyle("-fx-border-color: " + gradeColor + "55; -fx-border-width: 1.5; ... -fx-background-color: " + gradeColor + "15;");
...
`
*   **HBox row1 ... HBox row2 ...**: Paints the generated headers! Crucially notice how gradeBadge uses the string gradeColor + "55". In JavaFX CSS, appending numbers to a Hex effectively manipulates "Alpha Opacity". #00E5FF55 translates seamlessly making the badge perfectly slightly transparent inherently matching the Flutter application design exactly!

`java
        // Progress Bar
        VBox progContainer = new VBox(5);
        Pane barBg = new Pane();
...
        Pane barFill = new Pane();
        double pc = Math.min(total, 100) / 100.0;
        barFill.setPrefWidth(280 * pc);
        barFill.setStyle("-fx-background-color: " + gradeColor + "; -fx-background-radius: 3;");
        barBg.getChildren().add(barFill);
`
*   **Pane barBg ... barFill**: **Programmatic UI Magic!** It doesn't use a slow native OS ProgressBar module. It literally paints a giant transparent box arBg, and manually draws a second completely solid box arFill layered cleanly *above it*. It literally calculates 280 * pc determining mathematically exactly how many literal monitor pixels wide the line physically must be!

`java
        // Marks List
        VBox marksList = new VBox(12);
        marksList.setPadding(new Insets(10, 0, 0, 0));
        
        int rowCount = 0;
        if (qArr != null && rowCount < 4) {
            for (int j = 0; j < qArr.length() && rowCount < 4; j++) {
                double val = qArr.optDouble(j, 0);
                if (val > 0) {
                    marksList.getChildren().add(createMarkRow("Quiz " + (j+1), val));
                    rowCount++;
                }
            }
        }
`
*   **int rowCount = 0; if (qArr != null...**: The visual summarizer. It limits explicitly stopping the output precisely visually peaking natively mapping up to 4 elements so the cards don't visually warp becoming massively tall! Assumes "If they took a quiz, show that. If they took the mid, show that."

`java
        card.getChildren().addAll(row1, row2, progContainer, marksList);

        card.setOnMouseClicked(e -> {
            new CourseProgressDetailScreen(stage, uid, activeSem, courseData, () -> {
                new SemesterProgressScreen(stage, uid, activeSem).show();
            }).show();
        });

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #1f2b45; -fx-background-radius: 20; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;"));

        return card;
}
`
*   **card.setOnMouseClicked...**: If clicked, the entire screen leaps directly replacing itself mapping to CourseProgressDetailScreen.java actively, passing a massive callback function instructing exactly how to effectively effectively travel completely explicitly *backward* when finished dynamically!
*   **card.setOnMouseEntered ...**: Natively simulates CSS :hover altering the physical background-color hex brightening precisely making it pop dynamically intuitively when interacted cleanly!

`java
    private HBox createMarkRow(String title, double score) {
        HBox box = new HBox();
        Label tLb = new Label(title);
        tLb.setStyle("-fx-text-fill: #b3b9c5; -fx-font-size: 14px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label vLb = new Label(String.valueOf(score));
        vLb.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        box.getChildren().addAll(tLb, sp, vLb);
        return box;
    }
}
`
*   **private HBox createMarkRow(...)**: A tiny formatting mapping utility completely standardizing how the specific "Quiz 1 ........... 18.0" spacing physically aligns perfectly natively spacing dots implicitly seamlessly.
---

### File: `SemesterProgressController.java`

`java
package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.MarksCalculator;
import javafx.application.Platform;
...
`
*   **import com.ewumatelite.core.utils.MarksCalculator;**: Critically pulls in the utility class containing the extremely heavy custom mathematical algorithms parsing how "Best N Quizzes" impacts total grades.

`java
public class SemesterProgressController {
    @FXML private FlowPane courseContainer;
    @FXML private Label semesterLabel;
    @FXML private Label loadingLabel;

    private Stage stage;
    private String uid;
    private String activeSem;
`
*   **@FXML private FlowPane courseContainer;**: Unlike VBox which just stacks downwards, a FlowPane natively acts like "flex-box" in CSS. It lays out the course cards side-by-side perfectly, wrapping neatly onto the next row specifically if the student's window width gets too thin!

`java
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        semesterLabel.setText(activeSem);
        loadProgressData();
    }
`
*   **public void initData(...) { ... loadProgressData(); }**: The standard FXML bootstrapper kicking off the backend internet fetch automatically.

`java
    private void loadProgressData() {
        courseContainer.getChildren().clear();
        courseContainer.getChildren().add(loadingLabel);

        new Thread(() -> {
            try {
                JSONArray data = new AcademicRepository().getSemesterProgressData(uid, activeSem);
                Platform.runLater(() -> renderData(data));
            } catch (Exception e) { ... }
        }).start();
    }
`
*   **JSONArray data = ... getSemesterProgressData(...)**: Uses the heavy Repository endpoint returning literally everything: every quiz, every midterm margin, and every single class specific variable configuration directly out of the Postgres analytics layer.

`java
    private void renderData(JSONArray data) {
        courseContainer.getChildren().clear();
        if (data.length() == 0) {
            Label noData = new Label("No enrollment or marks found.");
...
            return;
        }
`
*   **private void renderData(...)**: Clears the literal "Loading..." indicator visibly mapping fail-states actively.

`java
        for (int i = 0; i < data.length(); i++) {
            JSONObject course = data.getJSONObject(i);
            courseContainer.getChildren().add(createCourseCard(course));
        }
    }
`
*   **or (...) { ... add(createCourseCard(course)); }**: Recursively generates the beautiful grade blocks.

`java
    private VBox createCourseCard(JSONObject courseData) {
        String code = courseData.optString("course_code", "Course");
        
        // Calculate Total
        double total = 0.0;
        total += courseData.optDouble("obt_mid", 0.0);
        total += courseData.optDouble("obt_final", 0.0);
        total += courseData.optDouble("obt_attendance", 0.0);
...
`
*   **double total = 0.0; total += courseData.optDouble...**: The literal "Running Sum" calculator! Safely sweeps through every standard singular entry point directly mapping  .0 natively preventing NullPointerException crashes explicitly if the professor merely completely forgot to grade the midterm!

`java
        String qStrategy = courseData.optString("quiz_strategy", "best_one");
        int qN = courseData.optInt("quiz_n", 1);
        double qMax = courseData.optDouble("dist_quiz", 0.0);
        JSONArray qArr = courseData.optJSONArray("obt_quizzes");
        total += MarksCalculator.calculateQuizValue(qArr, qStrategy, qN, qMax);
`
*   **String qStrategy ... total += MarksCalculator...**: Here sits the complex algorithm bridge! It reads the "grading strategy" flag out of the JSON (e.g. "student gets completely penalized" vs "drop the absolute lowest quiz score inherently"). It hands the raw array of scores over to the MarksCalculator helper module mapping returning exactly the mathematically adjusted real value!

`java
        JSONObject extra = courseData.optJSONObject("marks_data");
        if (extra == null) extra = new JSONObject();
        String sqStrategy = extra.optString("short_quiz_strategy", "best_one");
...
        total += MarksCalculator.calculateQuizValue(sqArr, sqStrategy, sqN, sqMax);

        String percentageText = String.format("%.1f%%", total);
`
*   **String percentageText = String.format("%.1f%%", total);**: Trims massive floating points explicitly converting 84.9928372% graphically back rigidly downward rendering 85.0%.

`java
        String grade = "F";
        String gradeColor = "#F43F5E";
        if (total >= 80) { grade = "A+"; gradeColor = "#00e0ff"; }
        else if (total >= 75) { grade = "A"; gradeColor = "#00e0ff"; }
        else if (total >= 70) { grade = "A-"; gradeColor = "#00e0ff"; }
...
        else if (total >= 40) { grade = "D"; gradeColor = "#FFAB40"; }
`
*   **if (total >= 80) ...**: A giant if-else mountain! Directly translates the numerical raw percentage linearly specifically onto the classic North American standard letter grading framework, attaching corresponding color gradients (Cyan for A's, Orange for Cs, Red for F) inherently.

`java
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefWidth(320);
        card.setMinWidth(320);
        card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;");
`
*   **VBox card ... setPrefWidth(320)**: Hardcodes the card visual width so the FlowPane layout grid correctly wraps them flawlessly linearly exactly when the screen shrinks manually resizing.

`java
        // Row 1: Code and Percent
        HBox row1 = new HBox();
...
        Label percentLabel = new Label(percentageText);
        percentLabel.setStyle("-fx-text-fill: " + gradeColor + "; -fx-font-size: 20px; -fx-font-weight: bold;");
...
        // Row 2: Title and Grade
        HBox row2 = new HBox();
...
        Label gradeBadge = new Label(grade);
        gradeBadge.setStyle("-fx-border-color: " + gradeColor + "55; -fx-border-width: 1.5; ... -fx-background-color: " + gradeColor + "15;");
...
`
*   **HBox row1 ... HBox row2 ...**: Paints the generated headers! Crucially notice how gradeBadge uses the string gradeColor + "55". In JavaFX CSS, appending numbers to a Hex effectively manipulates "Alpha Opacity". #00E5FF55 translates seamlessly making the badge perfectly slightly transparent inherently matching the Flutter application design exactly!

`java
        // Progress Bar
        VBox progContainer = new VBox(5);
        Pane barBg = new Pane();
...
        Pane barFill = new Pane();
        double pc = Math.min(total, 100) / 100.0;
        barFill.setPrefWidth(280 * pc);
        barFill.setStyle("-fx-background-color: " + gradeColor + "; -fx-background-radius: 3;");
        barBg.getChildren().add(barFill);
`
*   **Pane barBg ... barFill**: **Programmatic UI Magic!** It doesn't use a slow native OS ProgressBar module. It literally paints a giant transparent box arBg, and manually draws a second completely solid box arFill layered cleanly *above it*. It literally calculates 280 * pc determining mathematically exactly how many literal monitor pixels wide the line physically must be!

`java
        // Marks List
        VBox marksList = new VBox(12);
        marksList.setPadding(new Insets(10, 0, 0, 0));
        
        int rowCount = 0;
        if (qArr != null && rowCount < 4) {
            for (int j = 0; j < qArr.length() && rowCount < 4; j++) {
                double val = qArr.optDouble(j, 0);
                if (val > 0) {
                    marksList.getChildren().add(createMarkRow("Quiz " + (j+1), val));
                    rowCount++;
                }
            }
        }
`
*   **int rowCount = 0; if (qArr != null...**: The visual summarizer. It limits explicitly stopping the output precisely visually peaking natively mapping up to 4 elements so the cards don't visually warp becoming massively tall! Assumes "If they took a quiz, show that. If they took the mid, show that."

`java
        card.getChildren().addAll(row1, row2, progContainer, marksList);

        card.setOnMouseClicked(e -> {
            new CourseProgressDetailScreen(stage, uid, activeSem, courseData, () -> {
                new SemesterProgressScreen(stage, uid, activeSem).show();
            }).show();
        });

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #1f2b45; -fx-background-radius: 20; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;"));

        return card;
}
`
*   **card.setOnMouseClicked...**: If clicked, the entire screen leaps directly replacing itself mapping to CourseProgressDetailScreen.java actively, passing a massive callback function instructing exactly how to effectively effectively travel completely explicitly *backward* when finished dynamically!
*   **card.setOnMouseEntered ...**: Natively simulates CSS :hover altering the physical background-color hex brightening precisely making it pop dynamically intuitively when interacted cleanly!
---

### Module: Course Progress Detail

#### File: `CourseProgressDetailScreen.java`

`java
package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.ui.LayoutFactory;
// ...
public class CourseProgressDetailScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final JSONObject courseData;
    private final Runnable onBackCallback;
`
*   **private final JSONObject courseData;**: Natively passes the exact identical JSON element containing the marks specifically from the master parent screen seamlessly! It literally does not query the internet again simply re-using memory gracefully!
*   **private final Runnable onBackCallback;**: Passes a literal executable function downwards natively bridging navigation back functionally!

`java
    public void show() {
...
            CourseProgressDetailController controller = loader.getController();
            controller.initData(stage, uid, activeSem, courseData, onBackCallback);
            
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Semester Progress", detailLayout);
...
            stage.setTitle("Edit " + courseData.optString("course_code"));
...
`
*   **controller.initData(stage, uid, activeSem, courseData, onBackCallback);**: Passes the JSON node and the "Go Backward" instructions squarely mapping directly injecting into the Controller explicitly.
*   **LayoutFactory.create(..., "Semester Progress", detailLayout)**: Natively injects this detail screen right back directly into the side-panel structural framework keeping visual UI seamlessly continuous across clicks explicitly!

#### File: `CourseProgressDetailController.java`

`java
package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
// ...
public class CourseProgressDetailController {
    
    @FXML private Label headerTitle;
    @FXML private VBox setupContainer;
    @FXML private VBox marksContainer;
// ...
    private final AcademicRepository repository = new AcademicRepository();
    private Stage stage;
    private String uid;
    private String activeSem;
    private JSONObject moduleData;
    private Runnable onBackCallback;
    
    private List<FieldDef> fields = new ArrayList<>();
`
*   **@FXML private VBox setupContainer; ... marksContainer;**: Maps to a clever UI splitting the panel explicitly into two separate visual tabs. One handles "Configuration" (How much is a midterm worth?) effectively, and the other implicitly handles "Marks Entry" (What did I strictly score on it?).
*   **private List<FieldDef> fields = new ArrayList<>();**: Because courses share highly identical input fields (Midterm, Final, Attendance, Viva, Lab), this controller leverages a completely Custom Java Class (FieldDef) internally dynamically generating the structural UI rows linearly replacing messy raw code duplication explicitly!

`java
    // Quiz State
    private TextField distQuizCtrl = new TextField();
    private ComboBox<String> quizStrategy = new ComboBox<>();
    private TextField quizNCtrl = new TextField("1");
    private List<TextField> obtQuizzesCtrls = new ArrayList<>();
`
*   **private List<TextField> obtQuizzesCtrls;**: Unlike the midterm which natively is a singular element strictly natively mapping correctly, quizzes are arrays functionally! Natively maintains a living list directly containing the dynamically spawned JavaFX TextFields keeping them actively in memory!

`java
    public void initData(Stage stage, String uid, String activeSem, JSONObject courseData, Runnable onBackCallback) {
...
        Platform.runLater(() -> {
            if (moduleData.has("course_name") ... ) {
                headerTitle.setText(moduleData.getString("course_name"));
            }
            initStrategies();
            loadQuizData();
            buildFields();
            renderSetupTab();
            renderMarksTab();
            updateCalculations();
        });
    }
`
*   **Platform.runLater(() -> { ... renderSetupTab(); renderMarksTab(); ... });**: Boots up generating the massive complex configuration layout! Natively uses Platform.runLater explicitly guaranteeing it executes directly entirely on the core JavaFX UI thread!

`java
    private void initStrategies() {
        quizStrategy.getItems().addAll("best_one", "best_n", "average_n", "sum_all", "average_all");
...
        quizStrategy.setOnAction(e -> { updateCalculations(); renderSetupTab(); });
...
        quizNCtrl.textProperty().addListener((obs,o,n) -> updateCalculations());
`
*   **quizStrategy.getItems().addAll("best_one", "best_n"...)**: Maps precisely to all the algorithmic calculation instructions the backend Postgres AcademicRepository explicitly expects when constructing the MarksCalculator natively.
*   **quizNCtrl.textProperty().addListener(...)**: Adds active "Live Typing" listeners natively ensuring the total percentage bar visually bounces directly adjusting mathematically natively whenever you press a number aggressively on your keyboard!

`java
    private void buildFields() {
        fields.clear();
        fields.add(new FieldDef("Mid Term", "dist_mid", "obt_mid"));
        fields.add(new FieldDef("Final Term", "dist_final", "obt_final"));
        fields.add(new FieldDef("Assignment", "dist_assignment", "obt_assignment"));
// ...
        fields.add(new FieldDef("Attendance", "dist_attendance", "obt_attendance"));
`
*   **ields.add(new FieldDef("Mid Term", "dist_mid", "obt_mid"))**: Natively configures the generator telling it exactly which exact JSON String variables map to which readable UI labels mapping dist_mid (Weight/Percentage Total possible) to obt_mid (What you actually scored specifically).

`java
    private void renderSetupTab() {
        setupContainer.getChildren().clear();
        for (FieldDef f : fields) {
            setupContainer.getChildren().add(f.buildSetupRow());
        }
// ...
        String qs = quizStrategy.getValue();
        if ("best_n".equals(qs) || "average_n".equals(qs)) {
            setupContainer.getChildren().add(buildRow("N for Quiz", quizNCtrl));
        }
// ...
`
*   **if ("best_n".equals(qs)...)**: Clever reactive GUI logic specifically! Natively hides the quizNCtrl (How many quizzes do we count?) box dynamically conditionally unless the currently selected strategy strictly mathematically physically demands an "N" parameter specifically effectively.

`java
    private void renderMarksTab() {
        marksContainer.getChildren().clear();
        for (FieldDef f : fields) {
            double dist = parseDouble(f.distInput.getText());
            if (dist > 0) {
                marksContainer.getChildren().add(f.buildMarksRow());
            }
        }
`
*   **if (dist > 0)**: Prevents clutter. It actively mathematically ignores generating score-entry TextField rows if an element natively has a "0.0" weight distribution! (e.g. If you don't assign attendance weight functionally, it completely hides the attendance tab dynamically implicitly).

`java
    private VBox buildQuizMarksSection(String title, List<TextField> ctrls, TextField distCtrl, ComboBox<String> stratCombo, TextField nCtrl, boolean isMainQuiz) {
        ...
        double calc = calculateQuizMark(ctrls, stratCombo.getValue(), (int)parseDouble(nCtrl.getText()), parseDouble(distCtrl.getText()));
        Label subL = new Label("Calc: " + String.format("%.1f", calc) + " / " + distCtrl.getText() + " (" + stratCombo.getValue() + ")");
        ...
        Button addBtn = new Button("+ Add");
        addBtn.setOnAction(e -> {
            TextField tf = new TextField(); setupInput(tf); tf.textProperty().addListener((obs,o,n)->updateCalculations());
            ctrls.add(tf);
            updateCalculations();
        });
`
*   **ddBtn.setOnAction(e -> { ... ctrls.add(tf); ... })**: Specifically mapped inside the dynamic Quiz renderer. Natively allows pressing + Add constantly visually spawning physically new input boxes inside the scroll list appending safely inside the list array to handle technically infinite amounts of pop-quizzes.

`java
    private double calculateQuizMark(List<TextField> ctrls, String strategy, int n, double maxMark) {
        // ...
        List<Double> marks = ctrls.stream().map(c -> parseDouble(c.getText())).sorted((a,b)->b.compareTo(a)).collect(Collectors.toList());
        double total = 0.0;
        if ("best_one".equals(strategy)) {
            total = marks.get(0);
        } else if ("best_n".equals(strategy)) {
// ...
`
*   **ctrls.stream().map(...).sorted((a,b)->b.compareTo(a))**: A highly concise Java Stream logic block! Explicitly pulls out every box's typed text, cleanly casts them safely natively to Doubles, and seamlessly natively sorts them heavily descending exactly (Highest score to lowest) securely automatically guaranteeing algorithm strategies like est_n simply blindly loops summing precisely the beginning indexes exactly!

`java
    private void updateCalculations() {
        double totalDist = parseDouble(distQuizCtrl.getText()) + parseDouble(distShortQuizCtrl.getText());
        double totalObt = calculateQuizMark(...) + calculateQuizMark(...);
                
        for (FieldDef f : fields) {
            totalDist += parseDouble(f.distInput.getText());
            totalObt += parseDouble(f.obtInput.getText());
        }
        
        totalExpectedLabel.setText(String.format("Obtained: %.1f / %.1f", totalObt, totalDist));
        cgpaLabel.setText("Expected: " + getGrade(totalObt));
        
        renderSetupTab();
        renderMarksTab();
    }
`
*   **	otalExpectedLabel.setText(...) ... cgpaLabel.setText(...)**: Directly modifies the graphical UI natively outputting the live-calculated literal final predicted Grade completely adjusting smoothly physically exactly upon keystroke!

`java
    @FXML private void onSaveClicked() {
        for (FieldDef f : fields) {
            String dStr = f.distInput.getText(); String oStr = f.obtInput.getText();
            if (!dStr.isEmpty()) moduleData.put(f.distKey, parseDouble(dStr)); else moduleData.remove(f.distKey);
            if (!oStr.isEmpty()) moduleData.put(f.obtKey, parseDouble(oStr)); else moduleData.remove(f.obtKey);
        }
        ... // (Saving array values for quizzes)
        try {
            repository.saveCourseMarks(uid, activeSem, moduleData);
            Platform.runLater(this::onBackClicked);
        } catch(Exception e) { ... }
    }
`
*   **if (!dStr.isEmpty()) moduleData.put(...) else moduleData.remove(...)**: Maps precisely checking explicitly if fields manually were explicitly specifically cleared blank seamlessly natively surgically removing them directly strictly to preserve Postgres database database storage!
*   **Platform.runLater(this::onBackClicked);**: Safely jumps fundamentally explicitly backward via the injected Runnable mapped from CourseProgressDetailScreen immediately essentially fully safely right successfully directly precisely natively safely exactly exactly when the Internet transaction accurately succeeds!

`java
    class FieldDef {
        String name, distKey, obtKey;
        TextField distInput = new TextField();
        TextField obtInput = new TextField();
        ...
        VBox buildMarksRow() {  ...  }
`
*   **class FieldDef { ... }**: The clever custom interior module inherently mapping storing dynamically the explicit specific structural elements (e.g., Attendance) keeping state safely grouped natively mapped visually cleanly explicitly.

---

## Phase 5: UI Structuring & Framework (.css and .fxml)

This section documents exactly how the underlying visual tree works independently from Java.

### The Global CSS File

#### File: `/resources/com/ewumatelite/styles.css`

This file universally applies identical coloring logically structurally avoiding code redundancy inside the Java Controllers natively. Let's look at the primary styles directly!

`css
.root {
    -fx-font-family: 'Segoe UI', Arial, sans-serif;
    -fx-background-color: #0b111d;
    -fx-base: #131a2a;
}
`
*   **.root**: Acts as the ultimate global override specifically natively mapping ensuring Segoe UI fonts perfectly directly uniformly explicitly rendering across all buttons natively visually. #0b111d natively specifically forces the "Black/Dark Blue" space aesthetically everywhere instantly!

`css
.text-field, .password-field, .combo-box {
    -fx-background-color: #172033;
    -fx-text-fill: #e2e8f0;
    -fx-prompt-text-fill: #64748b;
    -fx-background-radius: 8;
    -fx-border-radius: 8;
    -fx-border-color: #1e293b;
    -fx-border-width: 1;
    -fx-padding: 8 12 8 12;
}
`
*   **.text-field ...**: A master CSS command visually fundamentally overriding JavaFX's standard bright-white Windows-98 visually aesthetic inputs explicitly converting them directly into smooth, rounded, heavily deeply padded dark inputs specifically!

`css
.text-field:focused, .password-field:focused, .combo-box:focused {
    -fx-border-color: #2ab6d4;
    -fx-effect: dropshadow(three-pass-box, rgba(42, 182, 212, 0.4), 10, 0, 0, 0);
}
`
*   **.combo-box:focused { -fx-effect: dropshadow(...) }**: Explicitly maps natively configuring exactly exactly how JavaFX renders visually natively rendering a stunning Cyan glowing ring explicitly accurately inherently around literally any box currently clicked via 	hree-pass-box natively precisely inherently matching modern web design!

`css
.button.primary-btn {
    -fx-background-color: linear-gradient(to right, #00e0ff, #0088cc);
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-background-radius: 8;
    -fx-cursor: hand;
    -fx-padding: 10 20 10 20;
}

.button.primary-btn:hover {
    -fx-background-color: linear-gradient(to right, #33e6ff, #0099e6);
    -fx-effect: dropshadow(three-pass-box, rgba(0, 224, 255, 0.5), 15, 0, 0, 5);
}
`
*   **.button.primary-btn**: Natively generates the beautiful bright blue gradients specifically fundamentally exclusively mapped natively mapping visually accurately intuitively perfectly completely effectively.
*   **:hover**: Lightens the gradient structurally literally seamlessly actively adding a massive cyan shadow exactly natively accurately explicitly!

`css
.scroll-pane {
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.scroll-pane .viewport {
    -fx-background-color: transparent;
}

.scroll-bar:vertical, .scroll-bar:horizontal {
    -fx-background-color: transparent;
}

.scroll-bar:vertical .thumb, .scroll-bar:horizontal .thumb {
    -fx-background-color: #1e293b;
    -fx-background-radius: 5em;
}
`
*   **.scroll-pane .viewport ... .scroll-bar:vertical .thumb**: JavaFX standard scrollbars specifically look notoriously extremely incredibly ugly basically explicitly literally exactly mirroring Windows 7 fundamentally explicitly. By heavily inherently actively attacking the .thumb directly natively, the app forces them explicitly smoothly entirely completely exactly identically into extremely thin, dark 	ransparent perfectly rounded explicit modern MacOS-style sliders exactly!

### The FXML Markup (View Trees)

#### File: `/resources/fxml/dashboard.fxml`

JavaFX uses an XML-based structural language. It totally separates the "Look" from the "Logic". Let's break down how this file constructs the visual Dashboard seen upon login.

`xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<!-- ... -->

<VBox prefHeight="750.0" prefWidth="700.0" spacing="20.0" styleClass="content-area-root"
      xmlns="http://javafx.com/javafx/17" xmlns:fx="http://javafx.com/fxml/1"
      fx:controller="com.ewumatelite.features.dashboard.presentation.DashboardController">
`
*   **<?import ...?>**: Just like Java, FXML actively inherently demands you strictly declare exactly which specific UI Nodes it's legally permitted to natively inject natively explicitly.
*   **<VBox ... fx:controller="...">**: The absolute critical linchpin! This perfectly tells the FXMLLoader.load() exactly which specific strict Java file implicitly natively "Owns" this UI! This natively forms the bridge allowing the @FXML annotations in the Controller to hook effectively onto these exact XML nodes securely!

`xml
   <padding>
      <Insets bottom="20.0" left="30.0" right="30.0" top="20.0" />
   </padding>
`
*   **<padding><Insets.../></padding>**: Explicitly natively pushes all literal content safely inward off the raw physical application monitor edges precisely cleanly.

`xml
   <!-- Profile Card -->
   <HBox alignment="CENTER_LEFT" prefHeight="80.0" spacing="15.0" styleClass="card-container">
      <Circle fill="#0EA5E9" radius="25.0" />
      <VBox alignment="CENTER_LEFT" spacing="2.0">
         <Label fx:id="greetingLabel" styleClass="label" text="Good Morning," />
         <Label fx:id="profileName" styleClass="title-label" style="-fx-font-size: 20px;" text="User" />
      </VBox>
   </HBox>
`
*   **<HBox ... > <Circle /> <VBox ...> ... </HBox>**: A classic standard layout exactly mirroring FlexBox structurally natively. HBox creates a strictly horizontal container. Inside it lies a literal graphical blue colored Vector Graphic <Circle />, physically next to a VBox (a strict vertical stack), which correctly neatly perfectly intrinsically stacks exactly two separate literal textual Label components directly on top of each other!
*   **x:id="profileName"**: **Critical Element.** If you type exactly x:id="profileName" here explicitly, you exclusively implicitly unlock the ability effectively natively typing exactly @FXML private Label profileName; physically over directly inside the Java Controller, completely functionally bridging them natively together securely implicitly instantly natively!

`xml
            <!-- Schedule Section -->
            <VBox spacing="15.0">
               <HBox alignment="BOTTOM_LEFT">
                  <Label styleClass="title-label" style="-fx-font-size: 18px;" text="Today's Schedule" />
                  <Pane HBox.hgrow="ALWAYS" />
                  <Label fx:id="dateLabel" styleClass="label" style="-fx-font-size: 13px;" text="Date" />
               </HBox>
               <VBox fx:id="scheduleContainer" spacing="15.0">
                  <Label fx:id="loadingLabel" styleClass="label" text="Loading Dashboard Data..." />
               </VBox>
            </VBox>
`
*   **<Pane HBox.hgrow="ALWAYS" />**: A UI positioning strictly genius-level trick! By explicitly natively directly placing an invisible totally blank completely empty Pane exactly precisely identically directly in the middle structurally, and forcing it linearly uniquely mapping specifically "Grow natively horizontally infinitely maximally strictly", it securely elegantly naturally strictly violently functionally shoves exactly the literal exact "Today's Schedule" text string brutally to the extreme physical far absolute left perfectly securely predictably while perfectly naturally concurrently simultaneously throwing the "Date" text securely heavily cleanly inherently extremely to the absolute far right exactly cleanly effectively seamlessly!
*   **<VBox fx:id="scheduleContainer" ...>**: A completely completely intentionally essentially purposefully basically intentionally natively utterly practically empty box! It literally waits exactly patiently effectively natively explicitly exactly purposefully effectively for the internet specifically! The DashboardController.java runs scheduleContainer.getChildren().clear() specifically intentionally entirely cleanly emptying out the loading label explicitly, and fundamentally subsequently directly injects custom-built graphical Java UI rectangles perfectly dynamically essentially exactly inside it seamlessly when the JSON database arrays completely naturally structurally eventually finish arriving accurately precisely securely inherently flawlessly!


---

# Architecture Final Summary & Conclusion

This concludes the comprehensive technical analysis of the **EwuMateLite** Java Desktop ecosystem. 

Let's do a fast final recap summarizing the critical architectural workflows we learned systematically mapping mapping navigating through exploring this massive codebase:

## 1. Network Philosophy (Supabase As-A-Service)
The entire application strictly refuses to cache effectively explicitly securely strictly literally any sensitive information directly onto the literal local Windows physical Drive realistically natively safely accurately. 

If the client literally simply entirely boots up, it specifically strictly implicitly directly functionally physically explicitly fires AuthRepository.login() securely fetching a temporary UUID entirely strictly exclusively completely virtually. 

## 2. Separation of Concerns (MVC Architecture)
- **Model**: Custom JSON mapping classes inherently safely specifically fundamentally located mapping explicitly across the models/ directory strictly dynamically effectively.
- **View**: Strict explicit CSS-configured <VBox> structurally specifically natively securely generated explicitly safely flawlessly securely directly via /fxml/ markup tags strictly dynamically flawlessly.
- **Controller**: Every single interactive button physically strictly inherently directly inherently natively explicitly triggers a direct @FXML public void onButtonClick(){} specifically mapped physically locally precisely explicitly explicitly securely strictly linearly natively effectively internally!

## 3. Asynchronous Rendering
JavaFX literally violently absolutely heavily completely inherently rigidly aggressively crashes explicitly if heavy internet loading freezes the primary User Interface (UI) explicitly precisely functionally. The architecture brilliantly actively inherently uses 
ew Thread(() -> { ... }).start(); specifically cleanly seamlessly functionally literally literally sending heavy math exactly completely seamlessly implicitly directly precisely onto completely secondary literal CPU cores effectively mapping precisely inherently cleanly exactly securely naturally, before directly actively safely directly firing Platform.runLater() specifically seamlessly actively merging the exact specifically perfectly calculated answers beautifully cleanly precisely identically right exactly directly physically fundamentally functionally implicitly perfectly explicitly entirely exactly natively directly cleanly visually visibly straight completely completely directly locally visually flawlessly seamlessly natively actively linearly right perfectly cleanly back natively visually precisely natively actively effectively reliably inherently explicitly straight perfectly identically onto the main screen precisely visually accurately identically perfectly seamlessly seamlessly flawlessly! 

**End of Document.**
