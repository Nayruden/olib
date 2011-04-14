package net.omnivr.olib;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Nayruden
 */
public class ItemDB {

    public static class ItemIDAndDurability {

        final int id;
        final int durability;

        ItemIDAndDurability(int id, int durability) {
            this.id = id;
            this.durability = durability;
        }

        public int getID() {
            return id;
        }

        public int getDurability() {
            Boolean durability_ = durability_important.get(id);
            if (durability_ != null && durability_) {
                return durability;
            }
            return -1;
        }
    }
    static Map<String, ItemIDAndDurability> items;
    static Map<Integer, Boolean> durability_important;

    public static ItemIDAndDurability nameOrIDToID(String name) {
        ensureDBLoaded();

        ItemIDAndDurability item = null;
        try {
            String pieces[] = name.split(",", 2);
            int id = Integer.parseInt(pieces[0]); // Assume it's an id first
            if (pieces.length == 2 && durability_important.containsKey(id)) {
                item = new ItemIDAndDurability(id, Integer.parseInt(pieces[1]));
            } else {
                item = new ItemIDAndDurability(id, 0);
            }
        } catch (NumberFormatException e) {
            item = items.get(name); // Maybe it's a name
        } finally {
            return item;
        }
    }

    private static void ensureDBLoaded() {
        if (items != null) {
            return;
        }

        items = new HashMap<String, ItemIDAndDurability>();
        durability_important = new TreeMap<Integer, Boolean>();
        Scanner scanner = new Scanner(ItemDB.class.getResourceAsStream("/items.db"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("#")) {
                continue;
            }

            String pieces[] = line.split(",", 3);
            try {
                int item_id = Integer.parseInt(pieces[1]);
                int item_durability = pieces.length < 3 ? 0 : Integer.parseInt(pieces[2]);
                items.put(pieces[0], new ItemIDAndDurability(item_id, item_durability));
                Boolean durability = durability_important.get(item_id);
                if (item_durability > 0 && (durability == null || !durability)) {
                    durability_important.put(item_id, Boolean.TRUE);
                }
            } catch (NumberFormatException e) { // Ignore
            }
        }
    }
}
