import json

# JSON에서 최신 매핑 데이터 읽기
mapping_path = r"c:\Users\user\Desktop\dopaming\dopaming-Trader\src\main\resources\items_mapping.json"
sql_path = r"c:\Users\user\Desktop\dopaming\dopaming-Trader\src\main\resources\data.sql"

with open(mapping_path, 'r', encoding='utf-8') as f:
    mapping = json.load(f)

# SQL 파일의 2행부터 약 178행까지가 INSERT 구문임
# 안전하게 새로 생성
sql_header = "-- 아이템 매핑 데이터\nINSERT INTO item_image_mapping (item_name, image_filename) VALUES\n"
values = []
for item, img in mapping.items():
    # Escape single quotes
    clean_item = item.replace("'", "''")
    values.append(f"('{clean_item}', '{img}')")

# SQL 다시 쓰기 (매핑 부분만 교체)
full_sql_content = ""
with open(sql_path, 'r', encoding='utf-8') as f:
    full_sql_content = f.read()

# 기존 INSERT 구문 찾기
start_marker = "-- 아이템 매핑 데이터"
end_marker = ");" # INSERT의 마지막 닫는 괄호를 찾아야 함

# 1. 기존 매핑 섹션 통째로 날리고 새로 작성
mapping_section = sql_header + ",\n".join(values) + ";"

# 기존 파일에서 post 시작 전까지를 찾아서 교체
post_marker = "INSERT INTO member"
parts = full_sql_content.split(post_marker)

new_content = mapping_section + "\n\n" + post_marker + parts[1]

with open(sql_path, 'w', encoding='utf-8') as f:
    f.write(new_content)

print(f"Successfully synced data.sql with {len(mapping)} items from JSON.")
