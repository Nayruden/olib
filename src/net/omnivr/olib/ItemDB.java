package net.omnivr.olib;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Nayruden
 */
public class ItemDB {

    static Map<String, Integer> items;

    public static int nameOrIDToID(String name) {
        ensureDBLoaded();

        Integer id = null;
        try {
            id = Integer.parseInt(name); // Assume it's an id first
        } catch (NumberFormatException e) {
            id = items.get(name); // Maybe it's a name
        } finally {
            if (id == null || !items.containsValue(id.intValue())) {
                return -1;
            }
            return id.intValue();
        }
    }

    private static void ensureDBLoaded() {
        if (items != null) {
            return;
        }

        items = new HashMap<String, Integer>();
        Scanner scanner = new Scanner(ItemDB.class.getResourceAsStream("/items.db"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("#")) {
                continue;
            }

            String pieces[] = line.split(":", 2);
            try {
                int item_id = Integer.parseInt(pieces[1]);
                items.put(pieces[0], item_id);
            } catch (NumberFormatException e) { // Ignore
            }
        }
    }
}
