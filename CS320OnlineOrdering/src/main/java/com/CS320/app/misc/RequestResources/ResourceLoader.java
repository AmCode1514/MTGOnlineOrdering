package com.CS320.app.misc.RequestResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.CS320.app.requests.GetAvailableItemsResponse;

public class ResourceLoader {
    private ResourceLoader() {

    }
    public static String[][] getItems() {
        File starting = new File(System.getProperty("user.dir"));
        File items = new File(starting,"/src/main/java/com/CS320/app/misc/RequestResources/Items.csv");
        try {
        FileInputStream itemStream = new FileInputStream(items);
        Scanner itemScanner = new Scanner(itemStream);
        ArrayList<String[]> itemList = new ArrayList<String []>();
        while(itemScanner.hasNextLine()) {
            String[] item = itemScanner.nextLine().split(",");
            itemList.add(item);
        }
        return (itemList.toArray(new String[0][]));
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
