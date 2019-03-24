package comp1206.sushi.Tabs;


import comp1206.sushi.server.ServerInterface;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MapTab extends MainTab  {

    ServerInterface server;
    ImageView imageView;
    String url;
    public MapTab(String name, ServerInterface server) {
        super(name);
        this.server = server;

        url = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=1200x1200&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=AIzaSyBpfkax4_sdXUMxPblYjXTzfeBq1OaXCjs";
        Image image = new Image(url);
        image.
        imageView = new ImageView();
        imageView.setImage(image);

        this.setContent(imageView);
    }



}
