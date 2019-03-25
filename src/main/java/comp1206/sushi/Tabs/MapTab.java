package comp1206.sushi.Tabs;


import comp1206.sushi.common.Drone;
import comp1206.sushi.common.Postcode;
import comp1206.sushi.server.ServerInterface;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;


public class MapTab extends MainTab  {

    ServerInterface server;
    ImageView imageView;
    String url;
    LatLong restaurantPos;
    public MapTab(String name, ServerInterface server) {
        super(name);
        this.server = server;

        Postcode restaurant = server.getRestaurantPostcode();
        imageView = new ImageView();
        restaurantPos = new LatLong(restaurant);
        String restaurantMarker = generateMarker(restaurantPos);
        System.out.println(restaurantMarker);
        url = "https://maps.googleapis.com/maps/api/staticmap?size=1200x1200&scale=2&maptype=roadmap"+restaurantMarker ;
        getMap();
        this.setContent(imageView);
    }

    public void getMap(){
        StringBuilder newUrl = new StringBuilder();
        newUrl.append(url);
        if (server.getDrones()!=null) {
            for (Drone drone : server.getDrones()) {

                LatLong dronePos = new LatLong(drone.getSource());
                String marker = generateMarker(dronePos);
                newUrl.append(marker);


            }
        }
        String endOfUrl = "&markers=color:red%7Clabel:R%7C50.9341876000301,-1.39568476775548&key=AIzaSyBpfkax4_sdXUMxPblYjXTzfeBq1OaXCjs";
        newUrl.append(endOfUrl);
        System.out.println(newUrl.toString());
        Image image = new Image(newUrl.toString());
        imageView.setImage(image);

    }

    public String generateMarker(LatLong latLong){
        if (latLong == restaurantPos){
//            String position = "&markers=color:red%7Clabel:R%7C" + latLong.getLatitude() + "," + latLong.getLongitude() + "&key=AIzaSyBpfkax4_sdXUMxPblYjXTzfeBq1OaXCjs" ;
            String position = "&markers=color:red%7Clabel:R%7C" + latLong.getLatitude() + "," + latLong.getLongitude();
            return position;
        }else {
            String position = "&markers=color:blue%7Clabel:D%7C" + latLong.getLatitude() + "," + latLong.getLongitude();
            return position;
        }
    }

    class LatLong{
        Double latitude;
        Double longitude;
        public LatLong(Postcode postcode){
            for (Map.Entry<String,Double> cursor: postcode.getLatLong().entrySet()) {
                if (cursor.getKey().equals("lat")){
                    latitude = cursor.getValue();
                } else {
                    longitude = cursor.getValue();
                }

            }
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }
    }



}
