import json
import os
import re

history_file = 'c:/Users/user/Desktop/dopaming/dopaming-Trader/terror_zone_history.json'

data_text = """
2026. 3. 23. 오후 11:30:00 CC Glacial Trail and Drifter Cavern
2026. 3. 23. 오후 11:00:00 BC Jail and Barracks
2026. 3. 23. 오후 10:30:00 CD Cold Plains and The Cave
2026. 3. 23. 오후 10:00:00 FF Stony Field, Tristram
2026. 3. 23. 오후 9:30:00 BC Lut Gholein Sewers
2026. 3. 23. 오후 9:00:00 CC Ancient's Way and Icy Cellar
2026. 3. 23. 오후 8:30:00 CC Outer Steppes and Plains of Despair
2026. 3. 23. 오후 8:00:00 FF Burial Grounds, The Crypt, and The Mausoleum
2026. 3. 23. 오후 7:30:00 SS Chaos Sanctuary
2026. 3. 23. 오후 7:00:00 AS Cathedral, Inner Cloister, and Catacombs
2026. 3. 23. 오후 6:30:00 BA Nihlathak's Temple and Temple Halls
2026. 3. 23. 오후 6:00:00 CC Lost City, Valley of Snakes, Claw Viper Temple, and Ancient Tunnels
2026. 3. 23. 오후 5:30:00 AC Dry Hills and Halls of the Dead
2026. 3. 23. 오후 5:00:00 AA Tamoe Highland, Pit, Monastery Gate, and Outer Cloister
2026. 3. 23. 오후 4:30:00 BC Arreat Plateau and Pit of Acheron
2026. 3. 23. 오후 4:00:00 CB Spider Forest, Arachnid Lair, and Spider Cavern
2026. 3. 23. 오후 3:30:00 CA Durance of Hate
2026. 3. 23. 오후 3:00:00 CD Dark Wood and Underground Passage
2026. 3. 23. 오후 2:30:00 SS Worldstone Keep, Throne of Destruction, and Worldstone Chamber
2026. 3. 23. 오후 2:00:00 FF Far Oasis and Maggot Lair
2026. 3. 23. 오후 1:30:00 CC Ancient's Way and Icy Cellar
2026. 3. 23. 오후 1:00:00 CC Glacial Trail and Drifter Cavern
2026. 3. 23. 오후 12:30:00 CA Black Marsh, The Hole, and The Forgotten Tower
2026. 3. 23. 오후 12:00:00 CD Cold Plains and The Cave
2026. 3. 23. 오전 11:30:00 CA Arcane Sanctuary, Harem, and Palace Cellar
2026. 3. 23. 오전 11:00:00 FF Blood Moor and Den of Evil
2026. 3. 23. 오전 10:30:00 BB Kurast Bazaar, Kurast Causeway, Kurast Sewers, Ruined Temple, Disused Fane, Forgotten Reliquary, Forgotten Temple, Ruined Fane, and Disused Reliquary
2026. 3. 23. 오전 10:00:00 AB Flayer Jungle, Flayer Dungeon, and Swampy Pit
2026. 3. 23. 오전 9:30:00 BS Moo Moo Farm
2026. 3. 23. 오전 9:00:00 CB Great Marsh
2026. 3. 23. 오전 8:30:00 FF Burial Grounds, The Crypt, and The Mausoleum
2026. 3. 23. 오전 8:00:00 BA Travincal
2026. 3. 23. 오전 7:30:00 BB Bloody Foothills, Frigid Highlands and Abaddon
2026. 3. 23. 오전 7:00:00 SA Tal Rasha's Tomb, Tal Rasha's Chamber, and Canyon of the Magi
2026. 3. 23. 오전 6:30:00 SS Worldstone Keep, Throne of Destruction, and Worldstone Chamber
2026. 3. 23. 오전 6:00:00 CC Crystalline Passage and Frozen River
2026. 3. 23. 오전 5:30:00 BC Arreat Plateau and Pit of Acheron
2026. 3. 23. 오전 5:00:00 BC Lut Gholein Sewers
2026. 3. 23. 오전 4:30:00 FF Far Oasis and Maggot Lair
2026. 3. 23. 오전 4:00:00 BB Bloody Foothills, Frigid Highlands and Abaddon
2026. 3. 23. 오전 3:30:00 AA Tamoe Highland, Pit, Monastery Gate, and Outer Cloister
2026. 3. 23. 오전 3:00:00 BS Moo Moo Farm
2026. 3. 23. 오전 2:30:00 BC Jail and Barracks
2026. 3. 23. 오전 2:00:00 FF Stony Field, Tristram
2026. 3. 23. 오전 1:30:00 CC Glacial Trail and Drifter Cavern
2026. 3. 23. 오전 1:00:00 SS Chaos Sanctuary
2026. 3. 23. 오전 12:30:00 AS Cathedral, Inner Cloister, and Catacombs
2026. 3. 23. 오전 12:00:00 CB Spider Forest, Arachnid Lair, and Spider Cavern
2026. 3. 22. 오후 11:30:00 BB River of Flame and City of the Damned
2026. 3. 22. 오후 11:00:00 FF Burial Grounds, The Crypt, and The Mausoleum
2026. 3. 22. 오후 10:30:00 BC Arreat Plateau and Pit of Acheron
2026. 3. 22. 오후 10:00:00 FF Blood Moor and Den of Evil
2026. 3. 22. 오후 9:30:00 BC Rocky Waste and Stony Tomb
2026. 3. 22. 오후 9:00:00 CD Dark Wood and Underground Passage
2026. 3. 22. 오후 8:30:00 SS Worldstone Keep, Throne of Destruction, and Worldstone Chamber
2026. 3. 22. 오후 8:00:00 CC Ancient's Way and Icy Cellar
2026. 3. 22. 오후 7:30:00 CB Great Marsh
2026. 3. 22. 오후 7:00:00 CB Spider Forest, Arachnid Lair, and Spider Cavern
2026. 3. 22. 오후 6:30:00 BA Nihlathak's Temple and Temple Halls
2026. 3. 22. 오후 6:00:00 CA Black Marsh, The Hole, and The Forgotten Tower
2026. 3. 22. 오후 5:30:00 CB Great Marsh
2026. 3. 22. 오후 5:00:00 SA Tal Rasha's Tomb, Tal Rasha's Chamber, and Canyon of the Magi
2026. 3. 22. 오후 4:30:00 BB Frozen Tundra and Infernal Pit
2026. 3. 22. 오후 4:00:00 CC Lost City, Valley of Snakes, Claw Viper Temple, and Ancient Tunnels
2026. 3. 22. 오후 3:30:00 FF Stony Field, Tristram
2026. 3. 22. 오후 3:00:00 CD Cold Plains and The Cave
2026. 3. 22. 오후 2:30:00 AC Dry Hills and Halls of the Dead
2026. 3. 22. 오후 2:00:00 SS Chaos Sanctuary
2026. 3. 22. 오후 1:30:00 AB Flayer Jungle, Flayer Dungeon, and Swampy Pit
2026. 3. 22. 오후 1:00:00 CA Black Marsh, The Hole, and The Forgotten Tower
2026. 3. 22. 오후 12:30:00 SS Worldstone Keep, Throne of Destruction, and Worldstone Chamber
2026. 3. 22. 오후 12:00:00 CD Dark Wood and Underground Passage
2026. 3. 22. 오전 11:30:00 CB Spider Forest, Arachnid Lair, and Spider Cavern
2026. 3. 22. 오전 11:00:00 CC Crystalline Passage and Frozen River
2026. 3. 22. 오전 10:30:00 AC Dry Hills and Halls of the Dead
2026. 3. 22. 오전 10:00:00 AA Tamoe Highland, Pit, Monastery Gate, and Outer Cloister
2026. 3. 22. 오전 9:30:00 BC Arreat Plateau and Pit of Acheron
2026. 3. 22. 오전 9:00:00 FF Far Oasis and Maggot Lair
2026. 3. 22. 오전 8:30:00 BB Kurast Bazaar, Kurast Causeway, Kurast Sewers, Ruined Temple, Disused Fane, Forgotten Reliquary, Forgotten Temple, Ruined Fane, and Disused Reliquary
2026. 3. 22. 오전 8:00:00 CA Durance of Hate
2026. 3. 22. 오전 7:30:00 CB Great Marsh
2026. 3. 22. 오전 7:00:00 BS Moo Moo Farm
2026. 3. 22. 오전 6:30:00 BC Rocky Waste and Stony Tomb
2026. 3. 22. 오전 6:00:00 FF Blood Moor and Den of Evil
2026. 3. 22. 오전 5:30:00 BC Jail and Barracks
2026. 3. 22. 오전 5:00:00 BA Travincal
2026. 3. 22. 오전 4:30:00 BB Frozen Tundra and Infernal Pit
2026. 3. 22. 오전 4:00:00 CC Outer Steppes and Plains of Despair
2026. 3. 22. 오전 3:30:00 AC Dry Hills and Halls of the Dead
2026. 3. 22. 오전 3:00:00 BB River of Flame and City of the Damned
2026. 3. 22. 오전 2:30:00 CA Arcane Sanctuary, Harem, and Palace Cellar
2026. 3. 22. 오전 2:00:00 AS Cathedral, Inner Cloister, and Catacombs
2026. 3. 22. 오전 1:30:00 AB Flayer Jungle, Flayer Dungeon, and Swampy Pit
2026. 3. 22. 오전 1:00:00 FF Stony Field, Tristram
2026. 3. 22. 오전 12:30:00 CD Cold Plains and The Cave
2026. 3. 22. 오전 12:00:00 CB Great Marsh
"""

new_history = {}
for line in data_text.strip().split('\n'):
    m = re.match(r'(\d{4})\.\s(\d+)\.\s(\d+)\.\s(오전|오후)\s(\d+):(\d+):(\d+)\s+(.*)', line)
    if m:
        year, month, day, ampm, hour, minute, second, zone = m.groups()
        h = int(hour)
        if ampm == '오후' and h < 12: h += 12
        if ampm == '오전' and h == 12: h = 0
        date_key = f"{year}-{int(month):02d}-{int(day):02d}"
        time_key = f"{h:02d}:{int(minute):02d}"
        if date_key not in new_history: new_history[date_key] = {}
        new_history[date_key][time_key] = zone

# Load current
with open(history_file, 'r', encoding='utf-8') as f:
    current_history = json.load(f)

# Merge
for date_key, day_data in new_history.items():
    if date_key not in current_history:
        current_history[date_key] = {}
    for time_key, zone in day_data.items():
        current_history[date_key][time_key] = zone

# Sort keys
sorted_history = {}
for date in sorted(current_history.keys(), reverse=True):
    day_map = current_history[date]
    sorted_day = {t: day_map[t] for t in sorted(day_map.keys(), reverse=True)}
    sorted_history[date] = sorted_day

with open(history_file, 'w', encoding='utf-8') as f:
    json.dump(sorted_history, f, indent=2, ensure_ascii=False)

print("Merged historical data successfully.")
