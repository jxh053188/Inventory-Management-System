package Main;
/*
<p></b>Javadoc comments for future enhancements are included in the comments of the "Main class". The comments for the runtime error can be located in the MainFormController class comments.</b><p/>
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;


/**
 * This class starts the application. This class loads the main form page and populates the part and product inventory with some sample data.
 *      <h1><b></>Future Enhancements</b></h1>
 *      <p><b></>This is a simple inventory system that has quite a bit of room to grow and expand future capabilities. An easy enhancement that added is the ability to print out the parts and products on paper. This would allow businesses to complete a paper inventory and also back up their data.
 *      A more complicated enhancement would be the incorporation of a point-of-sale(POS) system. A POS system would simplify updating inventory since both parts and products could be automatically updated each sale.</b></p>
 *      <p></b>Javadoc comments for future enhancements are included in the comments of the "Main class". The comments for the runtime error can be located in the MainFormController class comments.<p/>
 *       @author Jarred Harkness
 *
 */

public class Main extends Application {

    /**
     * The start method loads the main form. This method will load the main form when the application starts.
      * @param stage
     * @throws Exception
     */
    public void start(Stage stage) throws Exception{
        Parent parent = FXMLLoader.load(
        getClass().getResource("../views/mainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method sets sample data. This method will set sample data in the parts and products inventory when the application loads.
     * @param args
     */
    public static void main(String[] args){
        Part part1 = new InHouse(1, "Bike Frame", 5.59, 2, 1, 5, 152);
        Part part2 = new Outsourced(2, "Wheel", 11.75, 8, 1, 10, "Cool Wheelz");
        Part part3 = new InHouse(3, "Seat", 8.99, 15, 5, 20, 531);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Product product1 = new Product(1, "Street Bike", 152.85, 10, 3, 15);
        Product product2 = new Product(2, "Mountain Bike", 163.45, 5, 1, 10);
        Product product3 = new Product(3, "Electric Bike", 345.55, 3, 1, 5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch(args);
    }



}
