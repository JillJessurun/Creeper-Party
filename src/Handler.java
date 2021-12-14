//the handler class will update and render all objects in the game (make sure they are shown on the screen)

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void addObject(GameObject object){
        this.object.add(object);
    }
        public void removeObject(GameObject object){
            this.object.remove(object);
        }

    public void centerObject(GameObject gameObject){
        //gameObject.
    }

    //tick method to keep updating every object (because every object is in the linkedlist)
    public void tick(){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
            centerObject(tempObject);
        }
    }
    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

}
