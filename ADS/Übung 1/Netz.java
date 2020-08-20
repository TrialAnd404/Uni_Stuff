import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Netz {
    public static void main(String[] args){
        ArrayList<Route> routes = new ArrayList<Route>();
        ArrayList<City> cities = new ArrayList<City>();
        cities.add(new City("A"));
        cities.add(new City("B"));
        cities.add(new City("C"));
        cities.add(new City("D"));
        cities.add(new City("E"));
        cities.add(new City("F"));
        cities.add(new City("G"));

        cities.add(new City("H"));
        cities.add(new City("I"));
        cities.add(new City("J"));
        cities.add(new City("K"));
        cities.add(new City("L"));


        //Mehrere runs für benchmarking
        for(int tests=0; tests< 100; tests++) {

            for (int i = 0; i < cities.size() - 1; i++) {
                for (int j = i + 1; j < cities.size(); j++) {
                    Random randomDistance = new Random();
                    int distance = 1 + randomDistance.nextInt(10);
                    routes.add(new Route(cities.get(i), cities.get(j), distance));
                }
            }
            routes = sortRoutes(routes);
            /*
            System.out.println("Network1");
            singleNetwork(routes, cities);
            */

            System.out.println("Network2:");
            multipleNetworks(routes, cities);
            routes.clear();
        }
    }

    public static void singleNetwork(ArrayList<Route> rtList, ArrayList<City> ctList){
        ArrayList<City> connectedCities = new ArrayList<>() ;
        ArrayList<City> cities =  ctList;
        ArrayList<Route> routes;
        routes = rtList;
        ArrayList<Route> network = new ArrayList<>();

        network.add(routes.get(0));
        connectedCities.add(routes.get(0).from);
        connectedCities.add(routes.get(0).to);
        rtList.remove(0);

        while(connectedCities.size() < cities.size())
        {
            for(int i=0; i<routes.size(); i++){
                if (connectedCities.contains(routes.get(i).from))
                {
                    network.add(routes.get(i));
                    connectedCities.add(routes.get(i).to);
                    routes.remove(i);
                    break;
                }else if(connectedCities.contains(routes.get(i).to)){
                    network.add(routes.get(i));
                    connectedCities.add(routes.get(i).from);
                    routes.remove(i);
                    break;
                }
            }
        }

        for(int i =0; i<network.size(); i++){
            System.out.println(network.get(i).from.name + " " +
                    network.get(i).to.name + " " +
                    network.get(i).distance);
        }
    }

    public static void multipleNetworks(ArrayList<Route> rtList, ArrayList<City> ctList){
        ArrayList<ArrayList<City>> cityClusters = new ArrayList<ArrayList<City>>();
        ArrayList<City> connectedCities = new ArrayList<City>();
        ArrayList<City> cities = ctList;
        ArrayList<Route> routes = rtList;
        ArrayList<Route> network = new ArrayList<Route>();

        /* ????? */
        //ArrayList<ArrayList<Route>> networks = new ArrayList<ArrayList<Route>>();

        network.add(routes.get(0));
        ArrayList<City> newCluster = new ArrayList<City>();
        newCluster.add(routes.get(0).from);
        newCluster.add(routes.get(0).to);
        cityClusters.add(newCluster);
        connectedCities.add(routes.get(0).from);
        connectedCities.add(routes.get(0).to);
        routes.remove(0);

        while(connectedCities.size() < cities.size())
        {
            for (Route r : routes){
                for (ArrayList<City> cluster : cityClusters){
                    if(cluster.contains(r.to)&&cluster.contains(r.from)){
                        routes.remove(r);
                        break;
                    } else if(cluster.contains(r.to)){
                        if (connectedCities.contains(r.from)) {
                            for (ArrayList<City> mergeCluster : cityClusters)
                                if(mergeCluster.contains(r.from)){
                                    cluster.addAll(0, mergeCluster);
                                    network.add(r);
                                    routes.remove(r);
                                }
                        } else {
                            cluster.add(r.from);
                            network.add(r);
                            connectedCities.add(r.from);
                            routes.remove(r);
                        }
                        break;
                    }else if(cluster.contains(r.from)){
                        if (connectedCities.contains(r.to)) {
                            for (ArrayList<City> mergeCluster : cityClusters)
                                if(mergeCluster.contains(r.to)){
                                    cluster.addAll(0, mergeCluster);
                                    network.add(r);
                                    routes.remove(r);
                                }
                        } else {
                            cluster.add(r.to);
                            network.add(r);
                            connectedCities.add(r.to);
                            routes.remove(r);
                        }
                        break;
                    }
                    newCluster = new ArrayList<City>();
                    newCluster.add(r.from);
                    newCluster.add(r.to);
                    cityClusters.add(newCluster);
                    routes.remove(r);
                    break;
                }
                break;
            }
            //Connect leftovers
            if (cityClusters.size()>1){
                for (Route r:routes){
                    for(int i=0; i<cityClusters.size()-1; i++){
                        for(int j=i; j<cityClusters.size(); j++){
                            if(r.connects(cityClusters.get(i),cityClusters.get(j)))
                                routes.add(r);
                                routes.remove(r);
                                break;
                        }
                    }
                }
            }
        }

        for(int i =0; i<network.size(); i++){
            System.out.println(network.get(i).from.name + " " +
                    network.get(i).to.name + " " +
                    network.get(i).distance);
        }
    }

    public static ArrayList<Route> sortRoutes(ArrayList<Route> unsortedRoutes){
        ArrayList<Route> sortedRoutes = unsortedRoutes;
        boolean swapMeDaddy = true;

        while(swapMeDaddy){
            swapMeDaddy = false;
            for(int i=0; i<sortedRoutes.size()-1; i++){
                if(sortedRoutes.get(i).distance > sortedRoutes.get(i+1).distance){
                    Collections.swap(sortedRoutes, i, i+1);
                    swapMeDaddy=true;
                }
            }
        }
        return sortedRoutes;
    }

    public static class Route{
        public City from;
        public City to;
        public int distance;

        public Route(City from, City to, int distance){
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        public boolean connects(ArrayList<City> cluster1, ArrayList<City> cluster2){
            if(cluster1.contains(from)&&cluster2.contains(to)
                return true;
            if(cluster1.contains(to)&&cluster12.contains(from))
                return true;
            return false;
        }
    }

    public static class City{
        public String name;

        public City(String name) {
            this.name = name;
        }
    }
}
