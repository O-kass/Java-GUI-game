import javax.swing.*;
import java.util.*;
/**
 * Write a description of class Images here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Images {
    private HashMap<String, ImageIcon> imageList;

    public Images() {
        imageList = new HashMap<>();
        getImages();
    }

    private void getImages() {
        // List of file paths
        String[] filePaths = {
                "images/Beginning.png","images/Scroll.png" ,"images/RoomDescription.png","images/Tunnel.png","images/Armoury.png" ,
                "images/Chest.jpg", "images/Book.png", "images/Cuboard.png", "images/Fountain.png", "images/Glow.png", "images/Door.png", 
                "images/Ladder.png", "images/Store.png", "images/Teleport.png"

            };

        String[] imageNames = {
                "Beginning", "StartMessage", "RoomDescription", "Tunnel", "Armoury", "Chest", "Book", "Cuboard","Fountain", "Glow", "Door","Ladder", "Store", "Teleport" 
                // Add more image names here
            };

        // Load images in a loop
        for (int i = 0; i < filePaths.length; i++) {
            
                ImageIcon image = new ImageIcon(filePaths[i]);
                imageList.put(imageNames[i], image); // Store image with name
            
        }
    }

    // Method to access an image by its name
    public ImageIcon getImage(String imageName) {
        return imageList.get(imageName); // Return image by name
    }
}

