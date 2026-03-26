import json
import re

history_file = 'c:/Users/user/Desktop/dopaming/dopaming-Trader/terror_zone_history.json'

mapping = {
    "Blood Moor and Den of Evil": "FF",
    "Cold Plains and The Cave": "CD",
    "Burial Grounds, The Crypt, and The Mausoleum": "FF",
    "Burial Grounds, Crypt, and Mausoleum": "FF",
    "Stony Field, Tristram": "FF",
    "Stony Field": "FF",
    "Dark Wood and Underground Passage": "CD",
    "Dark Wood": "CD",
    "Black Marsh, The Hole, and The Forgotten Tower": "CA",
    "Black Marsh and The Hole": "CA",
    "Black Marsh": "CA",
    "Jail and Barracks": "BC",
    "Cathedral, Inner Cloister, and Catacombs": "AS",
    "Cathedral and Catacombs": "AS",
    "Tamoe Highland, Pit, Monastery Gate, and Outer Cloister": "AA",
    "The Pit": "AA",
    "Tristram": "FF",
    "Moo Moo Farm": "BS",
    "Secret Cow Level": "BS",
    "Lut Gholein Sewers": "BC",
    "Rocky Waste and Stony Tomb": "BC",
    "Dry Hills and Halls of the Dead": "AC",
    "Far Oasis and Maggot Lair": "FF",
    "Far Oasis": "FF",
    "Lost City, Valley of Snakes, Claw Viper Temple, and Ancient Tunnels": "CC",
    "Lost City, Valley of Snakes, and Claw Viper Temple": "CC",
    "Arcane Sanctuary, Harem, and Palace Cellar": "CA",
    "Arcane Sanctuary": "CA",
    "Tal Rasha's Tomb, Tal Rasha's Chamber, and Canyon of the Magi": "SA",
    "Tal Rasha's Tombs and Tal Rasha's Chamber": "SA",
    "Tal Rasha's Tombs": "SA",
    "Spider Forest, Arachnid Lair, and Spider Cavern": "CB",
    "Spider Forest and Spider Cavern": "CB",
    "Great Marsh": "CB",
    "Flayer Jungle, Flayer Dungeon, and Swampy Pit": "AB",
    "Flayer Jungle and Flayer Dungeon": "AB",
    "Kurast Bazaar, Kurast Causeway, Kurast Sewers, Ruined Temple, Disused Fane, Forgotten Reliquary, Forgotten Temple, Ruined Fane, and Disused Reliquary": "BB",
    "Kurast Bazaar, Ruined Temple, and Disused Fane": "BB",
    "Travincal": "BA",
    "Durance of Hate": "CA",
    "Outer Steppes and Plains of Despair": "CC",
    "River of Flame and City of the Damned": "BB",
    "City of the Damned and River of Flame": "BB",
    "Chaos Sanctuary": "SS",
    "Bloody Foothills, Frigid Highlands, and Abaddon": "BB",
    "Bloody Foothills, Frigid Highlands and Abaddon": "BB",
    "Bloody Foothills and Frigid Highlands": "BB",
    "Arreat Plateau and Pit of Acheron": "BC",
    "Frozen Tundra and Infernal Pit": "BB",
    "Frozen Tundra": "BB",
    "Crystalline Passage and Frozen River": "CC",
    "Glacial Trail and Drifter Cavern": "CC",
    "Nihlathak's Temple and Temple Halls": "BA",
    "Nihlathak's Temple": "BA",
    "Nihlathak's Temple, Halls of Anguish, Halls of Pain, and Halls of Vaught": "BA",
    "Ancient's Way and Icy Cellar": "CC",
    "Worldstone Keep, Throne of Destruction, and Worldstone Chamber": "SS"
}

with open(history_file, 'r', encoding='utf-8') as f:
    history = json.load(f)

count = 0
for date, days_data in history.items():
    for time, zone in days_data.items():
        # Check if starts with ?? or has no tier
        if zone.startswith('?? ') or re.match(r'^[A-Z]{2}\s', zone) is None:
            raw_name = zone[3:] if zone.startswith('?? ') else zone
            new_tier = mapping.get(raw_name, "??")
            if new_tier != "??":
                history[date][time] = f"{new_tier} {raw_name}"
                count += 1

with open(history_file, 'w', encoding='utf-8') as f:
    json.dump(history, f, indent=2, ensure_ascii=False)

print(f"Refined {count} entries in history.")
